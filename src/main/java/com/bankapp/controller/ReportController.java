package com.bankapp.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import com.bankapp.gui.ReportUI;
import com.bankapp.manager.DatabaseDataManager;
import com.bankapp.model.Transaction;
import com.bankapp.utils.Notifier;

public class ReportController {
    private ReportUI view;
   

    public ReportController(ReportUI view) {
        this.view = view;
        
        this.view.getSearchButton().addActionListener(new SearchListener());
    }

    class SearchListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String accountNumber = view.getAccountNumberField().getText().trim();
            if (accountNumber.isEmpty()) {
                Notifier.showNotification("Please enter an account number.", "Missing Field"); 
                return;
            }

            // List<String[]> transactions = TransactionFileManager.loadTransactions(accountNumber);
            List<Transaction> transactions = DatabaseDataManager.fetchTransactions(accountNumber);

            view.getTableModel().setRowCount(0); // clear table
            for (Transaction record : transactions) {
                view.getTableModel().addRow(new Object[]{ record });
            }
        }
    }
}
