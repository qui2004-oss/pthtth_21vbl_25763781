package Lab8_JDBC;

public class QuanLySanPham {
    public static void main(String[] args) {
        SanPhamManager manager = new SanPhamManager();

        // 1. Tạo bảng
        manager.taoBang();

        // 2. Thêm ít nhất 3 sản phẩm
        manager.themSanPham("SP01", "Laptop Dell XPS", 25000000, 10);
        manager.themSanPham("SP02", "Chuot Logitech G502", 1200000, 50);
        manager.themSanPham("SP03", "Ban phim co Keychron", 2100000, 30);

        // 3. Hiển thị toàn bộ
        manager.hienThiTatCa();

        // 4. Tìm kiếm theo tên
        manager.timKiemTheoTen("Laptop");

        // 5. Cập nhật giá & số lượng
        manager.capNhatSanPham("SP02", 1100000, 45);
        manager.hienThiTatCa();

        // 6. Xóa sản phẩm
        manager.xoaSanPham("SP03");
        manager.hienThiTatCa();
    }
}