package bai5;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class ChatClient {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            ChatService chatService = (ChatService) registry.lookup("ChatService");

            Scanner scanner = new Scanner(System.in);
            System.out.println("=== ỨNG DỤNG CHAT RMI (Gõ 'exit' để thoát) ===");

            while (true) {
                System.out.print("\n[Client - Tin nhắn]: ");
                String message = scanner.nextLine();
                
                if (message.equalsIgnoreCase("exit")) {
                    break;
                }

                String response = chatService.sendMessage(message);
                System.out.println("[Server]: " + response);
            }
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}