package BaiLab;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class BinaryFileCopy {
    private static final int BUFFER_SIZE = 8192; // Bộ đệm 8KB

    public static void main(String[] args) {
        // Kiểm tra xem người dùng đã truyền đủ 2 tham số dòng lệnh chưa
        if (args.length != 2) {
            System.out.println("Cách dùng: java BinaryFileCopy <nguồn> <đích>");
            return;
        }

        Path source = Path.of(args[0]);
        Path target = Path.of(args[1]);

        // Kiểm tra Tiêu chí 3: Tệp nguồn không tồn tại
        if (!Files.exists(source)) {
            System.err.println("Lỗi: Tệp nguồn không tồn tại tại đường dẫn: " + source.toAbsolutePath());
            return;
        }

        long totalBytes = 0;

        // Sử dụng Try-with-resources để tự động đóng Stream
        try (InputStream input = new BufferedInputStream(Files.newInputStream(source));
             OutputStream output = new BufferedOutputStream(Files.newOutputStream(target))) {

            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;

            // Đọc và ghi dữ liệu theo từng block 8KB
            while ((bytesRead = input.read(buffer)) != -1) {
                // Chỉ ghi đúng số byte vừa đọc được (bytesRead)
                output.write(buffer, 0, bytesRead);
                totalBytes += bytesRead;
            }

            System.out.println("Đã sao chép thành công!");
            System.out.println("Tổng số byte đã sao chép: " + totalBytes + " bytes.");

            // Kiểm tra Tiêu chí 2: So sánh kích thước giữa tệp nguồn và tệp đích
            long sourceSize = Files.size(source);
            long targetSize = Files.size(target);

            System.out.println("Kích thước tệp nguồn: " + sourceSize + " bytes.");
            System.out.println("Kích thước tệp đích:   " + targetSize + " bytes.");

            if (sourceSize == targetSize) {
                System.out.println("=> Kích thước 2 tệp khớp nhau 100%.");
            } else {
                System.out.println("=> Cảnh báo: Kích thước 2 tệp KHÔNG khớp nhau!");
            }

        } catch (IOException e) {
            System.err.println("Sao chép thất bại: " + e.getMessage());
        }
    }
}