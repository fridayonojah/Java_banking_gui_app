package com.bankapp.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import com.bankapp.gui.WithdrawUI;
import com.bankapp.manager.DatabaseDataManager;
import com.bankapp.model.Account;
import com.bankapp.model.Customer;
import com.bankapp.utils.Notifier;

public class WithdrawController {
    public WithdrawController(WithdrawUI view, String username) {
        view.getWithdrawButton().addActionListener(new ActionListener() {
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
                    List<Account> accounts = DatabaseDataManager.loadAccounts();
                    for (Account acc : accounts) {
                        if (acc.getAccountNumber().equals(accNum) && acc.getCustomerId() == customer.getId()) {
                            if (acc.getBalance() < amount) {
                                Notifier.showNotification("Insufficient funds!", "Error");
                                return;
                            }
                            acc.setBalance(acc.getBalance() - amount);
                            // TransactionFileManager.saveTransaction(accNum, "Withdraw", amount);
                            // AccountFileManager.saveAllAccounts(accounts);


                            DatabaseDataManager.withdraw(acc.getAccountId(), amount);
                            Notifier.showNotification("Withdrawal successful!", "Success");
                            return;
                        }
                    }
                    Notifier.showNotification("Account not found!", "Error");
                } catch (NumberFormatException ex) {
                    Notifier.showNotification("Invalid amount!", "Error");
                }
            }
        });
    }
}