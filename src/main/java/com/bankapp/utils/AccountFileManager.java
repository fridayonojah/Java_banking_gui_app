package com.bankapp.utils;




import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.bankapp.model.Account;

public class AccountFileManager {
    private static final String FILE_PATH = "accounts.txt";

    public static void saveAccount(Account account) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            bw.write(account.getCustomerId() + "," + account.getAccountNumber() + "," +
                     account.getAccountType() + "," + account.getBalance());
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveAllAccounts(List<Account> accounts) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveAllAccounts'");
    }

    // public static List<Account> loadAccounts() {
    //     List<Account> accounts = new ArrayList<>();
    //     try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
    //         String line;
    //         while ((line = br.readLine()) != null) {
    //             String[] parts = line.split(",");
    //             if (parts.length == 4) {
    //                 Account account = new Account(
    //                         parts[1], parts[0], parts[2], Double.parseDouble(parts[3])
    //                 );
    //                 accounts.add(account);
    //             }
    //         }
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    //     return accounts;
    // }
}
