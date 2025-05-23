package com.bankapp.controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Random;

import com.bankapp.model.Account;
import com.bankapp.model.Customer;
import com.bankapp.utils.AuditLogger;
import com.bankapp.utils.Notifier;
import com.bankapp.gui.AccountManagementUI;
import com.bankapp.manager.DatabaseDataManager;


public class AccountManagementController {
    private AccountManagementUI view;
    private String username;
    private int activeUserId;

    public AccountManagementController(AccountManagementUI view, String username) {
        this.view = view;
        this.username = username;

        loadAccounts();

        this.view.getCreateAccountButton().addActionListener(new CreateAccountListener());
    }

    private void loadAccounts() {
        Customer customer = DatabaseDataManager.loadUserCredentials(username);
        this.activeUserId = customer.getId();

        List<Account> accounts = DatabaseDataManager.loadAccountsByCustomerId(activeUserId);
        if (accounts == null) {
            AuditLogger.log(username, "Fetching accounts", "Accounts Failed!");
            Notifier.showNotification("No accounts found at the moment", "No account yet");
        }

        for (Account acc : accounts) {
            view.getTableModel().addRow(new Object[]{
                    acc.getAccountNumber(), acc.getAccountType(), acc.getBalance()
            }); 
        }
       
    }

    class CreateAccountListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String accountType = (String) JOptionPane.showInputDialog(view, 
                "Select Account Type", "New Account",
                JOptionPane.QUESTION_MESSAGE, null, 
                new String[]{"Savings", "Current"}, "Savings");

            if (accountType != null) {
                String accNum = generateAccountNumber();
                double initialBalance = 0.0;

                // Save account details
                boolean newAccount = DatabaseDataManager.createAccount(accNum, activeUserId, accountType);
                if (newAccount == false) {
                    Notifier.showNotification("Apologies, an error occurred trying to create account!", "Account creation failed."); 
                }

                if (newAccount) {
                    Notifier.showNotification("Account created successfully!", "Account created.");
                
                    Account newAcc = new Account(1, accNum, activeUserId, accountType, initialBalance);
                    // Update table
                    view.getTableModel().addRow(new Object[]{
                            newAcc.getAccountNumber(), newAcc.getAccountType(), newAcc.getBalance()
                    }); 
                }
                
            }
        }

        public static String generateAccountNumber() {
            Random random = new Random();
            int firstDigit = random.nextInt(9) + 1;  
            long randomDigits = (long) (Math.random() * 1_000_000_000L);
            return String.format("%d%09d", firstDigit, randomDigits);
        }
    }
}

