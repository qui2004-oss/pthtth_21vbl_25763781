package bai9;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Scanner;

public class AuctionClient {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            AuctionService auctionService = (AuctionService) registry.lookup("AuctionService");

            Scanner scanner = new Scanner(System.in);
            
            System.out.print("Nhập tên người dùng của bạn: ");
            String username = scanner.nextLine();

            boolean running = true;
            while (running) {
                System.out.println("\n========== HỆ THỐNG ĐẤU GIÁ TRỰC TUYẾN ==========");
                System.out.println("1. [Dành cho Seller] Đăng ký sản phẩm đấu giá");
                System.out.println("2. [Dành cho Bidder] Xem danh sách sản phẩm");
                System.out.println("3. [Dành cho Bidder] Đưa ra giá thầu (Bid)");
                System.out.println("4. Thoát");
                System.out.print("Chọn chức năng (1-4): ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Đọc bỏ dòng trống

                switch (choice) {
                    case 1:
                        System.out.println("\n--- ĐĂNG KÝ SẢN PHẨM ĐẤU GIÁ ---");
                        System.out.print("Nhập mã sản phẩm (ID): ");
                        String id = scanner.nextLine();
                        System.out.print("Nhập tên sản phẩm: ");
                        String name = scanner.nextLine();
                        System.out.print("Nhập giá khởi điểm: ");
                        double startingPrice = scanner.nextDouble();

                        boolean added = auctionService.addItem(id, name, startingPrice);
                        if (added) {
                            System.out.println(" Thêm sản phẩm đấu giá thành công!");
                        } else {
                            System.out.println(" Thêm thất bại! Mã ID đã tồn tại hoặc giá không hợp lệ.");
                        }
                        break;

                    case 2:
                        System.out.println("\n--- DANH SÁCH SẢN PHẨM ĐANG ĐẤU GIÁ ---");
                        List<Item> items = auctionService.getItems();
                        if (items.isEmpty()) {
                            System.out.println("Hiện chưa có sản phẩm nào.");
                        } else {
                            for (Item item : items) {
                                System.out.println(item);
                            }
                        }
                        break;

                    case 3:
                        System.out.println("\n--- ĐẶT GIÁ THẦU ---");
                        System.out.print("Nhập mã sản phẩm muốn đấu giá: ");
                        String itemId = scanner.nextLine();
                        System.out.print("Nhập giá thầu của bạn: ");
                        double bidAmount = scanner.nextDouble();

                        boolean success = auctionService.placeBid(itemId, username, bidAmount);
                        if (success) {
                            System.out.println(" Đặt giá thầu thành công! Bạn đang giữ giá cao nhất.");
                        } else {
                            System.out.println(" Đặt giá thầu thất bại! Giá của bạn phải lớn hơn giá hiện tại.");
                        }
                        break;

                    case 4:
                        running = false;
                        System.out.println("Cảm ơn bạn đã tham gia hệ thống đấu giá!");
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
