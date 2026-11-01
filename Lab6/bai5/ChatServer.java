package bai5;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ChatServer {
    public static void main(String[] args) {
        try {
            // Khởi tạo RMI Registry tại cổng 1099
            Registry registry = LocateRegistry.createRegistry(1099);
            
            ChatService chatService = new ChatServiceImpl();
            registry.rebind("ChatService", chatService);
            
            System.out.println("Server Chat đã sẵn sàng làm việc...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}