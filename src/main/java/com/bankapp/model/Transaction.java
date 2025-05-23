package com.bankapp.model;

import java.sql.Timestamp;

public class Transaction {
    private int transactionId;
    private String accountNumber;
    private String transactionType; // deposit, withdrawal, transfer
    private double amount;
    private Timestamp timestamp;

    public Transaction(int transactionId, String accountNumber, String transactionType, double amount, Timestamp timestamp) {
        this.transactionId = transactionId;
        this.accountNumber = accountNumber;
        this.transactionType = transactionType;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    // Getters and setters
    public int getTransactionId() { return transactionId; }
    public void setTransactionId(int transactionId) { this.transactionId = transactionId; }

    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public String getType() { return transactionType; }
    public void setType(String transactionType) { this.transactionType = transactionType; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public Timestamp getTimestamp() { return timestamp; }
    public void setTimestamp(Timestamp timestamp) { this.timestamp = timestamp; }


    public class ScheduleTransaction{
        ScheduleTransaction(String fromAccount, String toAccount, double amount, Timestamp delaySeconds){
            
        }
    }
}
