package bai4;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            ContactService service = (ContactService) registry.lookup("ContactService");
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("\n--- QUẢN LÝ DANH BẠ ---");
                System.out.println("1. Thêm liên hệ");
                System.out.println("2. Tìm kiếm liên hệ");
                System.out.println("3. Xóa liên hệ");
                System.out.println("4. Thoát");
                System.out.print("Chọn chức năng: ");
                
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice == 4) break;

                switch (choice) {
                    case 1:
                        System.out.print("Nhập tên: ");
                        String nameAdd = scanner.nextLine();
                        System.out.print("Nhập số điện thoại: ");
                        String phoneAdd = scanner.nextLine();
                        service.addContact(nameAdd, phoneAdd);
                        System.out.println("Đã thêm/cập nhật thành công!");
                        break;
                    case 2:
                        System.out.print("Nhập tên cần tìm: ");
                        String nameFind = scanner.nextLine();
                        String phone = service.findContact(nameFind);
                        if (phone != null) {
                            System.out.println("Số điện thoại của " + nameFind + ": " + phone);
                        } else {
                            System.out.println("Không tìm thấy liên hệ!");
                        }
                        break;
                    case 3:
                        System.out.print("Nhập tên cần xóa: ");
                        String nameDelete = scanner.nextLine();
                        boolean isDeleted = service.deleteContact(nameDelete);
                        if (isDeleted) {
                            System.out.println("Đã xóa liên hệ thành công!");
                        } else {
                            System.out.println("Không tìm thấy liên hệ để xóa!");
                        }
                        break;
                    default:
                        System.out.println("Lựa chọn không hợp lệ!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
