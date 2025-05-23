package com.bankapp.controller;

import com.bankapp.gui.DepositUI;
import com.bankapp.manager.DatabaseDataManager;
import com.bankapp.model.Account;
import com.bankapp.model.Customer;
import com.bankapp.utils.AccountFileManager;
import com.bankapp.utils.Notifier;
import com.bankapp.utils.TransactionFileManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class DepositController {
    public DepositController(DepositUI view, String username) {
        view.getDepositButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String accNum = view.getAccountNumberField().getText().trim();
                String amountStr = view.getAmountField().getText().trim();

                if (accNum.isEmpty() || amountStr.isEmpty()) {
                    Notifier.showNotification("All fields are required!", "Missing Fields");
                    return;
                }

                try {
                    double amount = Double.parseDouble(amountStr);
                    if (amount <= 0) throw new NumberFormatException();

                    Customer customer = DatabaseDataManager.loadUserCredentials(username);
                    Account accountNumberExist = DatabaseDataManager.getAccountByAccountNumber(accNum);

                    if (accountNumberExist == null) {
                        Notifier.showNotification("No record exist with this account number!", "Not Found");
                        return;
                    }

                    if (accountNumberExist.getAccountNumber().equals(accNum) && accountNumberExist.getCustomerId() == customer.getId()) {
                        accountNumberExist.setBalance(accountNumberExist.getBalance() + amount);
                        // TransactionFileManager.saveTransaction(accNum, "Deposit", amount);
                        // AccountFileManager.saveAllAccounts(accounts);

                        DatabaseDataManager.deposit(accountNumberExist.getAccountId(), amount); // Update database too
                        Notifier.showNotification("Deposit successful!", "Success");
                        return;
                    } 
                } catch (NumberFormatException ex) {
                    Notifier.showNotification("Invalid amount!", "Error");
                }
            }
        });
    }
}