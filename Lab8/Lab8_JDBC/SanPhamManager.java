package Lab8_JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SanPhamManager {
    private static final String URL = "jdbc:sqlite:sanpham.db";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public void taoBang() {
        String sql = "CREATE TABLE IF NOT EXISTS SanPham ("
                + "maSP TEXT PRIMARY KEY, "
                + "tenSP TEXT NOT NULL, "
                + "gia REAL, "
                + "soluong INTEGER"
                + ");";
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = getConnection();
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.err.println("Lỗi tạo bảng: " + e.getMessage());
        } finally {
            closeResources(null, stmt, conn);
        }
    }

    // CREATE: Thêm sản phẩm bằng PreparedStatement
    public void themSanPham(String maSP, String tenSP, double gia, int soLuong) {
        String sql = "INSERT OR REPLACE INTO SanPham (maSP, tenSP, gia, soluong) VALUES (?, ?, ?, ?);";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, maSP);
            pstmt.setString(2, tenSP);
            pstmt.setDouble(3, gia);
            pstmt.setInt(4, soLuong);
            pstmt.executeUpdate();
            System.out.println("Đã thêm/cập nhật SP: " + tenSP);
        } catch (SQLException e) {
            System.err.println("Lỗi thêm SP: " + e.getMessage());
        } finally {
            closeResources(null, pstmt, conn);
        }
    }

    // READ: Liệt kê tất cả sản phẩm
    public void hienThiTatCa() {
        String sql = "SELECT * FROM SanPham;";
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            System.out.println("\n--- DANH SÁCH SẢN PHẨM ---");
            while (rs.next()) {
                System.out.printf("Mã: %-6s | Tên: %-20s | Giá: %10.2f | SL: %d%n",
                        rs.getString("maSP"),
                        rs.getString("tenSP"),
                        rs.getDouble("gia"),
                        rs.getInt("soluong"));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi đọc dữ liệu: " + e.getMessage());
        } finally {
            closeResources(rs, stmt, conn);
        }
    }

    // READ: Tìm kiếm sản phẩm theo tên
    public void timKiemTheoTen(String tuKhoa) {
        String sql = "SELECT * FROM SanPham WHERE tenSP LIKE ?;";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + tuKhoa + "%");
            rs = pstmt.executeQuery();
            System.out.println("\n--- KẾT QUẢ TÌM KIẾM '" + tuKhoa + "' ---");
            while (rs.next()) {
                System.out.printf("Mã: %-6s | Tên: %-20s | Giá: %10.2f | SL: %d%n",
                        rs.getString("maSP"),
                        rs.getString("tenSP"),
                        rs.getDouble("gia"),
                        rs.getInt("soluong"));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi tìm kiếm: " + e.getMessage());
        } finally {
            closeResources(rs, pstmt, conn);
        }
    }

    // UPDATE: Cập nhật giá và số lượng theo maSP
    public void capNhatSanPham(String maSP, double giaMoi, int soLuongMoi) {
        String sql = "UPDATE SanPham SET gia = ?, soluong = ? WHERE maSP = ?;";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setDouble(1, giaMoi);
            pstmt.setInt(2, soLuongMoi);
            pstmt.setString(3, maSP);
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Đã cập nhật sản phẩm có mã: " + maSP);
            } else {
                System.out.println("Không tìm thấy mã sản phẩm: " + maSP);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi cập nhật: " + e.getMessage());
        } finally {
            closeResources(null, pstmt, conn);
        }
    }

    // DELETE: Xóa sản phẩm theo maSP
    public void xoaSanPham(String maSP) {
        String sql = "DELETE FROM SanPham WHERE maSP = ?;";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, maSP);
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Đã xóa sản phẩm có mã: " + maSP);
            } else {
                System.out.println("Không tìm thấy mã sản phẩm để xóa: " + maSP);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi xóa sản phẩm: " + e.getMessage());
        } finally {
            closeResources(null, pstmt, conn);
        }
    }

    // Hàm hỗ trợ đóng tài nguyên nghiêm ngặt
    private void closeResources(ResultSet rs, Statement stmt, Connection conn) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            System.err.println("Lỗi đóng tài nguyên: " + e.getMessage());
        }
    }
}