package bai4;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) {
        try {
            ContactService service = new ContactServiceImpl();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("ContactService", service);
            System.out.println("Server Danh bạ đã sẵn sàng...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}