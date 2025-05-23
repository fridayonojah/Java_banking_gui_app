package com.bankapp.controller;

import com.bankapp.gui.TransferUI;
import com.bankapp.manager.DatabaseDataManager;
import com.bankapp.model.Account;
import com.bankapp.model.Customer;
// import com.bankapp.utils.AccountFileManager;
import com.bankapp.utils.Notifier;
import com.bankapp.utils.TransactionFileManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class TransferController {
    public TransferController(TransferUI view, String username) {
        view.getTransferButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fromAcc = view.getSenderField().getText().trim();
                String toAcc = view.getReceiverField().getText().trim();
                String amountStr = view.getAmountField().getText().trim();

                if (fromAcc.isEmpty() || toAcc.isEmpty() || amountStr.isEmpty()) {
                    Notifier.showNotification("All fields are required!", "Missing Fields");
                    return;
                }

                try {
                    double amount = Double.parseDouble(amountStr);
                    if (amount <= 0) throw new NumberFormatException();

                    Customer customer = DatabaseDataManager.loadUserCredentials(username);
                    List<Account> accounts = DatabaseDataManager.loadAccounts();

                    Account fromAccount = null, toAccount = null;
                    for (Account acc : accounts) {
                        if (acc.getAccountNumber().equals(fromAcc) && acc.getCustomerId() == customer.getId()) {
                            fromAccount = acc;
                        } else if (acc.getAccountNumber().equals(toAcc)) {
                            toAccount = acc;
                        }
                    }

                    if (fromAccount == null || toAccount == null) {
                        Notifier.showNotification("Account(s) not found!", "Error");
                        return;
                    }
                    if (fromAccount.getBalance() < amount) {
                        Notifier.showNotification("Insufficient funds!", "Error");
                        return;
                    }

                    fromAccount.setBalance(fromAccount.getBalance() - amount);
                    toAccount.setBalance(toAccount.getBalance() + amount);

                    // TransactionFileManager.saveTransaction(fromAccount.getAccountNumber(), "Transfer to " + toAccount.getAccountNumber(), amount);
                    // TransactionFileManager.saveTransaction(toAccount.getAccountNumber(), "Transfer from " + fromAccount.getAccountNumber(), amount);

                    // AccountFileManager.saveAllAccounts(accounts);
                    try {
                        DatabaseDataManager.updateAccountBalance(fromAccount.getAccountNumber(), amount);
                        DatabaseDataManager.updateAccountBalance(toAccount.getAccountNumber(), amount); 
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    } 

                    Notifier.showNotification("Transfer successful!", "Success");

                } catch (NumberFormatException ex) {
                    Notifier.showNotification("Invalid amount!", "Error");
                }
            }
        });
    }
}