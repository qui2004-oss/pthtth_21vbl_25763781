package bai7;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class GeometryClient {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            GeometryService geometryService = (GeometryService) registry.lookup("GeometryService");

            Scanner scanner = new Scanner(System.in);
            boolean running = true;

            while (running) {
                System.out.println("\n========== TÍNH DIỆN TÍCH HÌNH HỌC ==========");
                System.out.println("1. Tính diện tích hình chữ nhật");
                System.out.println("2. Tính diện tích hình tròn");
                System.out.println("3. Tính diện tích hình tam giác");
                System.out.println("4. Thoát");
                System.out.print("Chọn chức năng (1-4): ");

                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        System.out.print("Nhập chiều rộng: ");
                        double width = scanner.nextDouble();
                        System.out.print("Nhập chiều cao: ");
                        double height = scanner.nextDouble();
                        double rectArea = geometryService.rectangleArea(width, height);
                        System.out.printf("Diện tích hình chữ nhật: %.2f\n", rectArea);
                        break;
                    case 2:
                        System.out.print("Nhập bán kính: ");
                        double radius = scanner.nextDouble();
                        double circleArea = geometryService.circleArea(radius);
                        System.out.printf("Diện tích hình tròn: %.2f\n", circleArea);
                        break;
                    case 3:
                        System.out.print("Nhập độ dài đáy: ");
                        double base = scanner.nextDouble();
                        System.out.print("Nhập chiều cao: ");
                        double triHeight = scanner.nextDouble();
                        double triArea = geometryService.triangleArea(base, triHeight);
                        System.out.printf("Diện tích hình tam giác: %.2f\n", triArea);
                        break;
                    case 4:
                        running = false;
                        System.out.println("Thoát chương trình.");
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