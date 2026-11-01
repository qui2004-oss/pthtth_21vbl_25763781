package bai8;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class FlightClient {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            FlightBookingService flightService = (FlightBookingService) registry.lookup("FlightBookingService");

            Scanner scanner = new Scanner(System.in);
            boolean running = true;

            while (running) {
                System.out.println("\n========== HỆ THỐNG ĐẶT VÉ MÁY BAY ==========");
                System.out.println("1. Kiểm tra số ghế trống");
                System.out.println("2. Đặt vé");
                System.out.println("3. Hủy vé");
                System.out.println("4. Thoát");
                System.out.print("Chọn chức năng (1-4): ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Đọc bỏ dòng trống

                switch (choice) {
                    case 1:
                        System.out.print("Nhập mã chuyến bay (ví dụ: VN123, VJ456, QH789): ");
                        String checkFlight = scanner.nextLine().toUpperCase();
                        int seats = flightService.availableSeats(checkFlight);
                        if (seats != -1) {
                            System.out.println("Chuyến bay " + checkFlight + " còn " + seats + " ghế trống.");
                        } else {
                            System.out.println("Không tìm thấy chuyến bay " + checkFlight);
                        }
                        break;
                    case 2:
                        System.out.print("Nhập mã chuyến bay: ");
                        String bookFlight = scanner.nextLine().toUpperCase();
                        System.out.print("Nhập số vé muốn đặt: ");
                        int bookSeats = scanner.nextInt();
                        boolean isBooked = flightService.bookTicket(bookFlight, bookSeats);
                        if (isBooked) {
                            System.out.println("Đặt vé thành công!");
                        } else {
                            System.out.println("Đặt vé thất bại! Không đủ ghế hoặc sai mã chuyến bay.");
                        }
                        break;
                    case 3:
                        System.out.print("Nhập mã chuyến bay: ");
                        String cancelFlight = scanner.nextLine().toUpperCase();
                        System.out.print("Nhập số vé muốn hủy: ");
                        int cancelSeats = scanner.nextInt();
                        boolean isCanceled = flightService.cancelBooking(cancelFlight, cancelSeats);
                        if (isCanceled) {
                            System.out.println("Hủy vé thành công!");
                        } else {
                            System.out.println("Hủy vé thất bại! Sai mã chuyến bay hoặc thông tin không hợp lệ.");
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