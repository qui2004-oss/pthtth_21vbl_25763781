package bai3;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) {
        try {
            PrimeService service = new PrimeServiceImpl();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("PrimeService", service);
            System.out.println("Server Bài 3 đã sẵn sàng...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}