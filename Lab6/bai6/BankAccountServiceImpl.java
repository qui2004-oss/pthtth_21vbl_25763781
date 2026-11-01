package bai6;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class BankAccountServiceImpl extends UnicastRemoteObject implements BankAccountService {
    private double balance;

    public BankAccountServiceImpl(double initialBalance) throws RemoteException {
        super();
        this.balance = initialBalance;
    }

    @Override
    public synchronized double getBalance() throws RemoteException {
        return balance;
    }

    @Override
    public synchronized void deposit(double amount) throws RemoteException {
        if (amount > 0) {
            balance += amount;
            System.out.println("Đã nạp: " + amount + " | Số dư mới: " + balance);
        }
    }

    @Override
    public synchronized boolean withdraw(double amount) throws RemoteException {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            System.out.println("Đã rút: " + amount + " | Số dư còn lại: " + balance);
            return true;
        }
        System.out.println("Giao dịch rút tiền thất bại (Số dư không đủ hoặc số tiền không hợp lệ).");
        return false;
    }
}