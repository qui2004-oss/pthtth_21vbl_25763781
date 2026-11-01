package bai2;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) {
        try {
            CalculatorService service = new CalculatorServiceImpl();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("CalculatorService", service);
            System.out.println("Server Bài 2 đã sẵn sàng...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}