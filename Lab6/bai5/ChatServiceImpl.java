package bai5;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class ChatServiceImpl extends UnicastRemoteObject implements ChatService {
    private Scanner scanner;

    public ChatServiceImpl() throws RemoteException {
        super();
        scanner = new Scanner(System.in);
    }

    @Override
    public synchronized String sendMessage(String message) throws RemoteException {
        System.out.println("\n[Client]: " + message);
        System.out.print("[Server - Trả lời]: ");
        String reply = scanner.nextLine();
        return reply;
    }
}