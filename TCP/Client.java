package TCP;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 7000;

    public static void main(String[] args) {
        try (
            Socket socket = new Socket(SERVER_IP, SERVER_PORT); // Cau 2
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Scanner scanner = new Scanner(System.in)
        ) {
            // Nhan va in danh sach dich vu tu Server
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
                if (line.contains("Goi 'EXIT.'")) break;
            }

            while (true) {
                System.out.println("\n-------------------------------------------");
                System.out.print("Nhap so dich vu muon chon (1-4) hoac nhap 'EXIT.' de thoat: ");
                String inputChoice = scanner.nextLine().trim();

                if (inputChoice.equalsIgnoreCase("EXIT.")) { // Cau 5
                    out.println("EXIT.");
                    break;
                }

                int serviceChoice;
                try {
                    serviceChoice = Integer.parseInt(inputChoice);
                    if (serviceChoice < 1 || serviceChoice > 4) {
                        System.out.println("Vui long chon tu 1 den 4!");
                        continue;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Lua chon khong hop le!");
                    continue;
                }

                // Nhap cac dong chuoi, dung lai khi nhan dau "." (Cau 3)
                System.out.println("Nhap cac dong chuoi (Nhap dau '.' tren 1 dong rieng de ket thuc nhap):");
                StringBuilder dataBuffer = new StringBuilder();
                while (true) {
                    String inputLine = scanner.nextLine();
                    if (inputLine.trim().equals(".")) {
                        break;
                    }
                    dataBuffer.append(inputLine).append("\n");
                }

                String dataToSend = dataBuffer.toString().trim();
                if (dataToSend.isEmpty()) {
                    System.out.println("Du lieu rong, vui long thu lai.");
                    continue;
                }

                // Gui yeu cau theo dinh dang: SERVICE|DATA
                // Thay the newline bang bien phu neu la DV 3 hoac 4
                String formattedData = dataToSend.replace("\n", "\\n");
                out.println(serviceChoice + "|" + formattedData);

                // Nhan ket qua tu Server (Cau 4)
                String response = in.readLine();
                if (response != null) {
                    System.out.println("\n[KET QUA TU SERVER]:");
                    System.out.println(response.replace("\\n", "\n"));
                }
            }

            System.out.println("Da ngat ket noi voi Server thanh cong.");

        } catch (IOException e) {
            System.err.println("Loi ket noi toi Server: " + e.getMessage());
        }
    }
}