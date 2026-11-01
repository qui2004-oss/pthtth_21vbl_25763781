package BaiLab;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class TextFile {
    public static void main(String[] args) {
        Path file = Path.of("data", "ghi_chu.txt");

        // Yêu cầu mở rộng 2: In đường dẫn tuyệt đối của tệp
        System.out.println("Đường dẫn tuyệt đối của tệp: " + file.toAbsolutePath());
        System.out.println("--------------------------------------------------");

        try {
            // Tạo thư mục cha nếu chưa tồn tại
            if (file.getParent() != null) {
                Files.createDirectories(file.getParent());
            }

            // Yêu cầu mở rộng 1: Sử dụng CREATE và APPEND để ghi tiếp/nối thêm nội dung
            try (BufferedWriter writer = Files.newBufferedWriter(
                    file, 
                    StandardCharsets.UTF_8, 
                    StandardOpenOption.CREATE, 
                    StandardOpenOption.APPEND)) {
                
                writer.write("Java I/O làm việc với các luồng dữ liệu.");
                writer.newLine();
                writer.write("BufferedWriter giúp ghi văn bản hiệu quả.");
                writer.newLine();
                writer.write("UTF-8 hỗ trợ tiếng Việt ổn định.");
                writer.newLine();
            }

            // Đọc và hiển thị dữ liệu chuẩn UTF-8 (Đánh số thứ tự)
            System.out.println("=== NỘI DUNG TỆP (ĐỌC BẰNG UTF-8) ===");
            try (BufferedReader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
                String line;
                int number = 1;
                while ((line = reader.readLine()) != null) {
                    System.out.printf("%d. %s%n", number++, line);
                }
            }

            // Yêu cầu mở rộng 3: Thử đọc tệp bằng Charset khác (ISO-8859-1 / US-ASCII)
            System.out.println("\n=== NỘI DUNG TỆP (ĐỌC BẰNG ISO_8859_1) ===");
            try (BufferedReader reader = Files.newBufferedReader(file, StandardCharsets.ISO_8859_1)) {
                String line;
                int number = 1;
                while ((line = reader.readLine()) != null) {
                    System.out.printf("%d. %s%n", number++, line);
                }
            }

        } catch (IOException e) {
            System.err.println("Lỗi xử lý tệp " + file + ": " + e.getMessage());
        }
    }
}
