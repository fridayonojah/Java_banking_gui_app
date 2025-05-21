package com.bankapp.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TransactionFileManager {
    private static final String FILE_PATH = "transactions.txt";

    public static void saveTransaction(String accountNumber, String type, double amount) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            bw.write(accountNumber + "," + type + "," + amount + "," + timestamp);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    public static List<String[]> loadTransactions(String accountNumber) {
        List<String[]> transactions = new java.util.ArrayList<>();

        try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4 && parts[0].equals(accountNumber)) {
                    transactions.add(new String[]{parts[3], parts[1], parts[2]}); // date, type, amount
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return transactions;
    }


    public static void recordTransaction(String toAccount, String string, double amount) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'recordTransaction'");
    }

}
