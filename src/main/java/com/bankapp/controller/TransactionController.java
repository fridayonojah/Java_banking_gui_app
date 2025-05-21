package com.bankapp.controller;

import javax.swing.*;

import com.bankapp.gui.TransactionUI;
import com.bankapp.manager.DatabaseDataManager;
import com.bankapp.model.Account;
import com.bankapp.model.Customer;
import com.bankapp.utils.AccountFileManager;
import com.bankapp.utils.Notifier;
import com.bankapp.utils.TransactionFileManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TransactionController {
    private TransactionUI view;
    private String username;

    public TransactionController(TransactionUI view, String username) {
        this.view = view;
        this.username = username;

        this.view.getProcessButton().addActionListener(new ProcessTransactionListener());
    }

    class ProcessTransactionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String type = (String) view.getTransactionTypeCombo().getSelectedItem();
            String accNum = view.getAccountNumberField().getText().trim();
            String targetAccNum = view.getTargetAccountField().getText().trim();
            String amountStr = view.getAmountField().getText().trim();

            if (accNum.isEmpty() || amountStr.isEmpty()) {
                Notifier.showNotification("Please fill in all required fields!", "Missing Fields");
                return;
            }

            double amount;
            try {
                amount = Double.parseDouble(amountStr);
                if (amount <= 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException ex) {
                Notifier.showNotification("Invalid amount entered!", "Incorrect Input");
                return;
            }


            Customer customerExist = DatabaseDataManager.loadUserCredentials(username);
            if (customerExist == null) {
                Notifier.showNotification("Customer does not exist", "Customer Not found");
            }

            Account userAcc = null;
            Account targetAcc = null;

            // Fetching accounts records
            List<Account> accounts = DatabaseDataManager.loadAccounts();
            for (Account acc : accounts) {
                if (acc.getAccountNumber().equals(accNum) && acc.getCustomerId() == customerExist.getId()) {
                    userAcc = acc;
                }
                if (acc.getAccountNumber().equals(targetAccNum)) {
                    targetAcc = acc;
                }
            }

            if (userAcc == null) {
                Notifier.showNotification("No record of sender account found!", "Account Not found");
                return;
            }

            switch (type) {
                case "Deposit":
                    userAcc.setBalance(userAcc.getBalance() + amount);
                    TransactionFileManager.saveTransaction(userAcc.getAccountNumber(), "Deposit", amount);
                    break;

                case "Withdraw":
                    if (userAcc.getBalance() < amount) {
                        Notifier.showNotification("Insufficient balance!", "Low Balance");
                        return;
                    }
                    userAcc.setBalance(userAcc.getBalance() - amount);
                    TransactionFileManager.saveTransaction(userAcc.getAccountNumber(), "Withdraw", amount);
                    break;

                case "Transfer":
                    if (targetAcc == null) {
                        Notifier.showNotification("Receipient account not found!", "Receipient Error!");
                        return;
                    }
                    if (userAcc.getBalance() < amount) {
                        Notifier.showNotification("Insufficient balance!", "Low Balance");
                        return;
                    }
                    userAcc.setBalance(userAcc.getBalance() - amount);
                    targetAcc.setBalance(targetAcc.getBalance() + amount);
                    TransactionFileManager.saveTransaction(userAcc.getAccountNumber(), "Transfer to " + targetAccNum, amount);
                    TransactionFileManager.saveTransaction(targetAcc.getAccountNumber(), "Transfer from " + userAcc.getAccountNumber(), amount);
                    break;
            }

            AccountFileManager.saveAllAccounts(accounts);
            Notifier.showNotification("Transaction successfully!", "Transaction success!");
        }
    }
}
