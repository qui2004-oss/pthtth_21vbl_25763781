package bai7;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class GeometryServer {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.createRegistry(1099);
            GeometryService geometryService = new GeometryServiceImpl();
            registry.rebind("GeometryService", geometryService);

            System.out.println("Geometry Server đã sẵn sàng phục vụ...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}