package bai8;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class FlightServer {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.createRegistry(1099);
            FlightBookingService flightService = new FlightBookingServiceImpl();
            registry.rebind("FlightBookingService", flightService);

            System.out.println("Flight Server đã sẵn sàng phục vụ...");
            System.out.println("Các chuyến bay mặc định: VN123 (50 ghế), VJ456 (30 ghế), QH789 (20 ghế)");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}