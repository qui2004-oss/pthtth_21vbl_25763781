package bai9;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface AuctionService extends Remote {
    // Chức năng dành cho Seller
    boolean addItem(String id, String name, double startingPrice) throws RemoteException;

    // Chức năng dành cho Bidder[cite: 3]
    List<Item> getItems() throws RemoteException;
    boolean placeBid(String itemId, String bidderName, double amount) throws RemoteException;
}
