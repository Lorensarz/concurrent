package com.petrov.bank;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConcurrentBank {
    private final Map<Integer, BankAccount> accounts;
    private final Lock lock;

    public ConcurrentBank() {
        this.accounts = new ConcurrentHashMap<>();
        this.lock = new ReentrantLock();
    }

    public BankAccount createAccount(int initialBalance) {
        BankAccount bankAccount = new BankAccount(initialBalance);
            accounts.put(bankAccount.hashCode(), bankAccount);
            return bankAccount;
    }

    public void transfer(BankAccount fromAccount, BankAccount toAccount, int amount) {
            if (fromAccount.getBalance() >= amount) {
                fromAccount.withdraw(amount);
                toAccount.deposit(amount);
            } else {
                System.out.println("Недостаточно средств для перевода");
            }
    }

    public int getTotalBalance() {
        int totalBalance = 0;
        lock.lock();
        try {
            for (BankAccount account : accounts.values()) {
                totalBalance += account.getBalance();
            }
        } finally {
            lock.unlock();
        }
        return totalBalance;
    }
}
