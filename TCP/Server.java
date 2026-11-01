package TCP;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private static final int PORT = 7000;

    public static void main(String[] args) {
        System.out.println("=== SERVER DANG CHAY TAI PORT " + PORT + " ===");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client moi da ket noi: " + clientSocket.getInetAddress());
                // Tao thread moi xu ly ket noi client (Cau 1)
                new ClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
            System.err.println("Loi Server: " + e.getMessage());
        }
    }
}

class ClientHandler extends Thread {
    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            // Gui danh sach dich vu cho client ngay khi ket noi (Cau 3)
            String menu = "\n=== DANH SACH DICH VU DE XU LY ===\n" +
                          "1. Dao nguoc toan bo chuoi va in hoa ky tu dau cua tung tu\n" +
                          "2. Dao nguoc tu va in hoa ky tu dau cua tung tu, giu nguyen vi tri\n" +
                          "3. Dem so luong tu cua tat ca cac dong goi den\n" +
                          "4. Dem so luong tu cua tung dong\n" +
                          "Goi 'EXIT.' de thoat chuong trinh.";
            out.println(menu);

            String requestLine;
            while ((requestLine = in.readLine()) != null) {
                if (requestLine.trim().equalsIgnoreCase("EXIT.")) { // Cau 5
                    System.out.println("Client yeu cau ngat ket noi.");
                    out.println("BYE");
                    break;
                }

                // Format request: "SERVICE_CHOICE|DATA_LINE"
                String[] parts = requestLine.split("\\|", 2);
                if (parts.length < 2) {
                    out.println("Dinh dang yeu cau khong hop le.");
                    continue;
                }

                int service = Integer.parseInt(parts[0].trim());
                String data = parts[1];

                // Xu ly tung dich vu
                String result = processService(service, data);
                out.println(result);
            }

        } catch (IOException e) {
            System.out.println("Client ngat ket noi dot ngot.");
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String processService(int serviceChoice, String data) {
        switch (serviceChoice) {
            case 1:
                return service1(data);
            case 2:
                return service2(data);
            case 3:
                return service3(data);
            case 4:
                return service4(data);
            default:
                return "Dich vu khong hop le!";
        }
    }

    // Dich vu 1: Dao nguoc toan bo chuoi va in hoa ky tu dau cua tung tu
    // Vi du: "PHAT TRIEN HE THONG TICH HOP" -> "Poh Hcit Gnoht Eh Neirt Tahp"
    private String service1(String input) {
        String reversed = new StringBuilder(input).reverse().toString();
        String[] words = reversed.toLowerCase().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String w : words) {
            if (!w.isEmpty()) {
                sb.append(Character.toUpperCase(w.charAt(0)))
                  .append(w.substring(1))
                  .append(" ");
            }
        }
        return sb.toString().trim();
    }

    // Dich vu 2: Dao nguoc tu va in hoa ky tu dau cua tung tu, giu nguyen vi tri
    // Vi du: "PHAT TRIEN HE THONG TICH HOP" -> "Tahp Neirt Eh Gnoht Hcit Poh"
    private String service2(String input) {
        String[] words = input.toLowerCase().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String w : words) {
            if (!w.isEmpty()) {
                String revWord = new StringBuilder(w).reverse().toString();
                sb.append(Character.toUpperCase(revWord.charAt(0)))
                  .append(revWord.substring(1))
                  .append(" ");
            }
        }
        return sb.toString().trim();
    }

    // Dich vu 3: Dem so luong tu của cac dong
    // Nhận chuoi chua nhieu dong ngan cach bang \n
    private String service3(String data) {
        String[] lines = data.split("\n");
        int totalWords = 0;
        for (String line : lines) {
            String trimmed = line.trim();
            if (!trimmed.isEmpty()) {
                totalWords += trimmed.split("\\s+").length;
            }
        }
        return "Tong so tu cua tat ca cac dong: " + totalWords;
    }

    // Dich vu 4: Dem so luong tu cua tung dong
    private String service4(String data) {
        String[] lines = data.split("\n");
        StringBuilder sb = new StringBuilder("Ket qua dem tu theo tung dong:\n");
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i].trim();
            int count = line.isEmpty() ? 0 : line.split("\\s+").length;
            sb.append(" - Dong ").append(i + 1).append(": ").append(count).append(" tu\n");
        }
        return sb.toString().trim();
    }
}