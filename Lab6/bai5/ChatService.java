package bai5;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatService extends Remote {
    // Phương thức gửi tin nhắn từ client và nhận phản hồi từ server
    String sendMessage(String message) throws RemoteException;
}
