package bai9;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuctionServiceImpl extends UnicastRemoteObject implements AuctionService {
    private Map<String, Item> items;

    public AuctionServiceImpl() throws RemoteException {
        super();
        items = new HashMap<>();
        
        // Thêm một số sản phẩm mẫu
        items.put("SP01", new Item("SP01", "Laptop Gaming", 15000000));
        items.put("SP02", new Item("SP02", "Đồng hồ cổ", 5000000));
    }

    @Override
    public synchronized boolean addItem(String id, String name, double startingPrice) throws RemoteException {
        if (items.containsKey(id) || startingPrice <= 0) {
            return false;
        }
        Item newItem = new Item(id, name, startingPrice);
        items.put(id, newItem);
        System.out.println("\n[SERVER LOG] Sản phẩm mới được thêm: " + name + " (Giá khởi điểm: " + startingPrice + ")");
        return true;
    }

    @Override
    public synchronized List<Item> getItems() throws RemoteException {
        return new ArrayList<>(items.values());
    }

    @Override
    public synchronized boolean placeBid(String itemId, String bidderName, double amount) throws RemoteException {
        Item item = items.get(itemId);
        if (item != null && amount > item.getCurrentPrice()) {
            double oldPrice = item.getCurrentPrice();
            item.setCurrentPrice(amount);
            item.setHighestBidder(bidderName);

            // Thông báo cập nhật giá cao nhất trên console của Server[cite: 3]
            System.out.println("\n[CẬP NHẬT ĐẤU GIÁ]");
            System.out.println("-> Sản phẩm: " + item.getName() + " (ID: " + itemId + ")");
            System.out.println("-> Người đặt: " + bidderName);
            System.out.println("-> Giá mới: " + amount + " VNĐ (Giá cũ: " + oldPrice + " VNĐ)");
            return true;
        }
        return false;
    }
}