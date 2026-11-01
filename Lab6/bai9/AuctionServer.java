package bai9;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class AuctionServer {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.createRegistry(1099);
            AuctionService auctionService = new AuctionServiceImpl();
            registry.rebind("AuctionService", auctionService);

            System.out.println("=== AUCTION SERVER ĐÃ SẴN SÀNG KHỞI CHẠY ===");
            System.out.println("Đang lắng nghe các yêu cầu đăng ký sản phẩm và ra giá thầu...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}