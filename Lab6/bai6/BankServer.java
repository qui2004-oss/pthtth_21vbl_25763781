package bai6;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class BankServer {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.createRegistry(1099);
            
            // Khởi tạo tài khoản ngân hàng với số dư ban đầu là 1,000,000
            BankAccountService bankService = new BankAccountServiceImpl(1000000.0);
            registry.rebind("BankService", bankService);

            System.out.println("Bank Server đã khởi tạo với số dư ban đầu: 1,000,000");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}