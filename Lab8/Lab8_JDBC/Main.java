package Lab8_JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:nhanvien.db";
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // B1. Nạp driver và thiết lập kết nối
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(url);
            System.out.println("Kết nối SQLite thành công!");

            // B2. Tạo đối tượng Statement
            stmt = conn.createStatement();

            // B3.1. Tạo bảng NhanVien
            String sqlCreateTable = "CREATE TABLE IF NOT EXISTS NhanVien ("
                    + "id INTEGER PRIMARY KEY, "
                    + "ten TEXT NOT NULL, "
                    + "chuc_vu TEXT"
                    + ");";
            stmt.executeUpdate(sqlCreateTable);

            // Xóa dữ liệu cũ để tránh trùng khóa chính khi chạy lại nhiều lần
            stmt.executeUpdate("DELETE FROM NhanVien;");

            // B3.2. Thêm ít nhất 3 nhân viên
            stmt.executeUpdate("INSERT INTO NhanVien (id, ten, chuc_vu) VALUES (1, 'Nguyen Van A', 'Giam doc');");
            stmt.executeUpdate("INSERT INTO NhanVien (id, ten, chuc_vu) VALUES (2, 'Tran Thi B', 'Ke toan');");
            stmt.executeUpdate("INSERT INTO NhanVien (id, ten, chuc_vu) VALUES (3, 'Le Van C', 'Nhan vien IT');");
            System.out.println("Đã thêm dữ liệu nhân viên thành công.");

            // B4. Truy vấn và hiển thị dữ liệu
            String sqlSelect = "SELECT id, ten, chuc_vu FROM NhanVien;";
            rs = stmt.executeQuery(sqlSelect);

            System.out.println("\n--- DANH SÁCH NHÂN VIÊN ---");
            while (rs.next()) {
                int id = rs.getInt("id");
                String ten = rs.getString("ten");
                String chucVu = rs.getString("chuc_vu");
                System.out.printf("ID: %d | Tên: %-15s | Chức vụ: %s%n", id, ten, chucVu);
            }

        } catch (ClassNotFoundException e) {
            System.err.println("Không tìm thấy driver SQLite JDBC: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Lỗi SQL: " + e.getMessage());
        } finally {
            // B5. Đóng tài nguyên theo thứ tự ngược lại
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
                System.out.println("\nĐã đóng tất cả kết nối an toàn.");
            } catch (SQLException e) {
                System.err.println("Lỗi khi đóng kết nối: " + e.getMessage());
            }
        }
    }
}