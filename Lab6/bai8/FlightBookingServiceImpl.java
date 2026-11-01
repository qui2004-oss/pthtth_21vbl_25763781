package bai8;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class FlightBookingServiceImpl extends UnicastRemoteObject implements FlightBookingService {
    // Quản lý số ghế trống cho từng chuyến bay
    private Map<String, Integer> flightMap;

    public FlightBookingServiceImpl() throws RemoteException {
        super();
        flightMap = new HashMap<>();
        // Khởi tạo một số chuyến bay mẫu
        flightMap.put("VN123", 50);
        flightMap.put("VJ456", 30);
        flightMap.put("QH789", 20);
    }

    @Override
    public synchronized boolean bookTicket(String flightNumber, int seats) throws RemoteException {
        if (flightMap.containsKey(flightNumber)) {
            int currentSeats = flightMap.get(flightNumber);
            if (seats > 0 && currentSeats >= seats) {
                flightMap.put(flightNumber, currentSeats - seats);
                System.out.println("Đặt " + seats + " vé cho chuyến bay " + flightNumber + " thành công.");
                return true;
            }
        }
        System.out.println("Đặt vé không thành công cho chuyến bay: " + flightNumber);
        return false;
    }

    @Override
    public synchronized int availableSeats(String flightNumber) throws RemoteException {
        return flightMap.getOrDefault(flightNumber, -1);
    }

    @Override
    public synchronized boolean cancelBooking(String flightNumber, int seats) throws RemoteException {
        if (flightMap.containsKey(flightNumber) && seats > 0) {
            int currentSeats = flightMap.get(flightNumber);
            flightMap.put(flightNumber, currentSeats + seats);
            System.out.println("Hủy " + seats + " vé cho chuyến bay " + flightNumber + " thành công.");
            return true;
        }
        return false;
    }
}