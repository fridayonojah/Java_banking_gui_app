package com.bankapp.model;

import java.sql.Timestamp;

public class Transaction {
    private int transactionId;
    private String accountNumber;
    private String type; // deposit, withdrawal, transfer
    private double amount;
    private Timestamp timestamp;

    public Transaction(int transactionId, String accountNumber, String type, double amount, Timestamp timestamp) {
        this.transactionId = transactionId;
        this.accountNumber = accountNumber;
        this.type = type;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    // Getters and setters
    public int getTransactionId() { return transactionId; }
    public void setTransactionId(int transactionId) { this.transactionId = transactionId; }

    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public Timestamp getTimestamp() { return timestamp; }
    public void setTimestamp(Timestamp timestamp) { this.timestamp = timestamp; }


    public class ScheduleTransaction{
        ScheduleTransaction(String fromAccount, String toAccount, double amount, Timestamp delaySeconds){
            
        }
    }
}
