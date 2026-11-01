package BaiLab;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ProductCsvApp {
    public static void main(String[] args) {
        Path input = Path.of("data", "products.csv");
        Path report = Path.of("data", "report.txt");

        List<Product> products = new ArrayList<>();

        // 1. Tạo thư mục data và tệp products.csv mẫu nếu chưa tồn tại
        try {
            if (input.getParent() != null) {
                Files.createDirectories(input.getParent());
            }

            if (!Files.exists(input)) {
                try (BufferedWriter writer = Files.newBufferedWriter(input, StandardCharsets.UTF_8)) {
                    writer.write("ma,ten,donGia,soLuong");
                    writer.newLine();
                    writer.write("SP01,Ban phim,350000,10");
                    writer.newLine();
                    writer.write("SP02,Chuot khong day,250000,5");
                    writer.newLine();
                    writer.write("SP03,Tai nghe,600000,3");
                    writer.newLine();
                }
                System.out.println("Đã tự động tạo tệp mẫu: " + input.toAbsolutePath());
            }
        } catch (IOException e) {
            System.err.println("Lỗi khởi tạo tệp dữ liệu: " + e.getMessage());
            return;
        }

        // 2. Đọc tệp CSV và khởi tạo danh sách đối tượng Product
        try (BufferedReader reader = Files.newBufferedReader(input, StandardCharsets.UTF_8)) {
            reader.readLine(); // Bỏ qua dòng tiêu đề
            String line;
            int lineNumber = 1;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.isBlank()) continue;

                String[] parts = line.split(",", -1);
                if (parts.length != 4) {
                    System.err.println("Bỏ qua dòng " + lineNumber + ": Không đủ số cột");
                    continue;
                }

                try {
                    products.add(new Product(
                        parts[0].trim(),
                        parts[1].trim(),
                        Double.parseDouble(parts[2].trim()),
                        Integer.parseInt(parts[3].trim())
                    ));
                } catch (IllegalArgumentException e) {
                    System.err.println("Dòng " + lineNumber + " không hợp lệ: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Không đọc được CSV: " + e.getMessage());
            return;
        }

        // 3. Hiển thị danh sách và tính tổng giá trị tồn kho
        System.out.println("=== DANH SÁCH SẢN PHẨM ===");
        double total = 0;
        for (Product product : products) {
            System.out.println(product);
            total += product.inventoryValue();
        }

        // 4. Ghi kết quả báo cáo ra file report.txt
        try (BufferedWriter writer = Files.newBufferedWriter(report, StandardCharsets.UTF_8)) {
            writer.write("Số sản phẩm: " + products.size());
            writer.newLine();
            writer.write("Tổng giá trị tồn kho: %,.0f VND".formatted(total));
            writer.newLine();
            System.out.println("\nĐã ghi báo cáo thành công ra file: " + report.toAbsolutePath());
        } catch (IOException e) {
            System.err.println("Không ghi được báo cáo: " + e.getMessage());
        }
    }
}