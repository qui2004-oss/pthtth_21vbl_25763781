package Lab8_JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class NhanVienNangCao {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:nhanvien_nangcao.db";
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(url);
            stmt = conn.createStatement();

            // Tạo bảng có thêm cột luong
            String sqlCreate = "CREATE TABLE IF NOT EXISTS NhanVienLuong ("
                    + "id INTEGER PRIMARY KEY, "
                    + "ten TEXT NOT NULL, "
                    + "chuc_vu TEXT, "
                    + "luong REAL"
                    + ");";
            stmt.executeUpdate(sqlCreate);

            stmt.executeUpdate("DELETE FROM NhanVienLuong;");
            stmt.executeUpdate("INSERT INTO NhanVienLuong VALUES (1, 'An', 'Giam doc', 30000000);");
            stmt.executeUpdate("INSERT INTO NhanVienLuong VALUES (2, 'Binh', 'IT', 15000000);");
            stmt.executeUpdate("INSERT INTO NhanVienLuong VALUES (3, 'Chi', 'IT', 18000000);");
            stmt.executeUpdate("INSERT INTO NhanVienLuong VALUES (4, 'Dung', 'Ke toan', 12000000);");
            stmt.executeUpdate("INSERT INTO NhanVienLuong VALUES (5, 'Em', 'Ke toan', 13000000);");

            // 1. Tính SUM, AVG, MIN, MAX
            String sqlStats = "SELECT SUM(luong) AS tong_luong, "
                    + "AVG(luong) AS luong_tb, "
                    + "MIN(luong) AS luong_thap_nhat, "
                    + "MAX(luong) AS luong_cao_nhat "
                    + "FROM NhanVienLuong;";
            rs = stmt.executeQuery(sqlStats);
            if (rs.next()) {
                System.out.println("=== THỐNG KÊ LƯƠNG TOÀN CÔNG TY ===");
                System.out.printf("Tổng lương:       %,.0f VNĐ%n", rs.getDouble("tong_luong"));
                System.out.printf("Lương trung bình: %,.0f VNĐ%n", rs.getDouble("luong_tb"));
                System.out.printf("Lương thấp nhất:  %,.0f VNĐ%n", rs.getDouble("luong_thap_nhat"));
                System.out.printf("Lương cao nhất:   %,.0f VNĐ%n", rs.getDouble("luong_cao_nhat"));
            }
            rs.close();

            // 2. Đếm số lượng nhân viên theo chức vụ (COUNT và GROUP BY)
            String sqlGroup = "SELECT chuc_vu, COUNT(id) AS so_luong FROM NhanVienLuong GROUP BY chuc_vu;";
            rs = stmt.executeQuery(sqlGroup);
            System.out.println("\n=== SỐ LƯỢNG NHÂN VIÊN THEO CHỨC VỤ ===");
            while (rs.next()) {
                System.out.printf("Chức vụ: %-12s | Số lượng: %d%n",
                        rs.getString("chuc_vu"),
                        rs.getInt("so_luong"));
            }

        } catch (SQLException e) {
            System.err.println("Lỗi SQL: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Lỗi đóng kết nối: " + e.getMessage());
            }
        }
    }
}