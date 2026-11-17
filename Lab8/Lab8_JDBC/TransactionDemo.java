package Lab8_JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionDemo {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:sanpham.db";
        Connection conn = null;
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;

        try {
            conn = DriverManager.getConnection(url);

            // Tắt chế độ tự động lưu (auto-commit) để bắt đầu Transaction
            conn.setAutoCommit(false);

            // Thao tác 1: Giảm số lượng SP01 đi 2
            String sql1 = "UPDATE SanPham SET soluong = soluong - 2 WHERE maSP = ?;";
            pstmt1 = conn.prepareStatement(sql1);
            pstmt1.setString(1, "SP01");
            pstmt1.executeUpdate();

            // Giả lập lỗi bằng cách bỏ comment dòng dưới đây để test rollback:
            // if (true) throw new SQLException("Lỗi hệ thống giả lập giữa giao dịch!");

            // Thao tác 2: Tăng số lượng SP02 lên 5
            String sql2 = "UPDATE SanPham SET soluong = soluong + 5 WHERE maSP = ?;";
            pstmt2 = conn.prepareStatement(sql2);
            pstmt2.setString(1, "SP02");
            pstmt2.executeUpdate();

            // Nếu cả 2 thao tác thành công -> Xác nhận lưu thay đổi
            conn.commit();
            System.out.println("Giao dịch thành công! Dữ liệu đã được commit.");

        } catch (SQLException e) {
            System.err.println("Giao dịch thất bại: " + e.getMessage());
            // Hủy bỏ toàn bộ thay đổi nếu có bất kỳ lỗi nào xảy ra
            if (conn != null) {
                try {
                    System.out.println("Đang thực hiện rollback dữ liệu...");
                    conn.rollback();
                } catch (SQLException ex) {
                    System.err.println("Lỗi khi rollback: " + ex.getMessage());
                }
            }
        } finally {
            try {
                if (pstmt1 != null) pstmt1.close();
                if (pstmt2 != null) pstmt2.close();
                if (conn != null) {
                    conn.setAutoCommit(true); // Khôi phục trạng thái mặc định
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("Lỗi đóng kết nối: " + e.getMessage());
            }
        }
    }
}