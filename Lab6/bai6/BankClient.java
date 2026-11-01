package bai6;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class BankClient {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            BankAccountService bankService = (BankAccountService) registry.lookup("BankService");

            Scanner scanner = new Scanner(System.in);
            boolean running = true;

            while (running) {
                System.out.println("\n========== QUẢN LÝ TÀI KHOẢN NGÂN HÀNG ==========");
                System.out.println("1. Kiểm tra số dư");
                System.out.println("2. Gửi tiền");
                System.out.println("3. Rút tiền");
                System.out.println("4. Thoát");
                System.out.print("Chọn chức năng (1-4): ");

                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        System.out.printf("Số dư hiện tại: %,.2f VNĐ\n", bankService.getBalance());
                        break;
                    case 2:
                        System.out.print("Nhập số tiền cần gửi: ");
                        double depositAmount = scanner.nextDouble();
                        bankService.deposit(depositAmount);
                        System.out.println("Gửi tiền thành công!");
                        break;
                    case 3:
                        System.out.print("Nhập số tiền cần rút: ");
                        double withdrawAmount = scanner.nextDouble();
                        boolean success = bankService.withdraw(withdrawAmount);
                        if (success) {
                            System.out.println("Rút tiền thành công!");
                        } else {
                            System.out.println("Rút tiền thất bại! Số dư không đủ.");
                        }
                        break;
                    case 4:
                        running = false;
                        System.out.println("Cảm ơn bạn đã sử dụng dịch vụ!");
                        break;
                    default:
                        System.out.println("Lựa chọn không hợp lệ!");
                }
            }
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}