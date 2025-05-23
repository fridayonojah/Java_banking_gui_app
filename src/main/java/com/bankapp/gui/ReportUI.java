package com.bankapp.gui;

import com.bankapp.manager.DatabaseDataManager;
import com.bankapp.model.Transaction;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ReportUI extends JFrame {
    private JTable transactionTable;
    private DefaultTableModel tableModel;
    private JTextField accountNumberField;
    private JButton searchButton;

    public ReportUI(int customerId) {
        setTitle("Transaction Reports");
        setSize(680, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Top Panel (search bar)
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        accountNumberField = new JTextField("Enter account number...", 20);
        searchButton = new JButton("Search");
        topPanel.add(accountNumberField);
        topPanel.add(searchButton);
        add(topPanel, BorderLayout.NORTH);

        // Table setup
        String[] columns = {"Transaction ID", "Account Number", "Type", "Amount", "Timestamp"};
        tableModel = new DefaultTableModel(columns, 0);
        transactionTable = new JTable(tableModel);
        transactionTable.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(transactionTable);
        add(scrollPane, BorderLayout.CENTER);

        // Load recent transactions
        loadRecentTransactions(customerId);

        // Search by account number
        searchButton.addActionListener(e -> {
            String accNum = accountNumberField.getText().trim();
            if (!accNum.isEmpty()) {
                loadTransactionsByAccountNumber(accNum);
            }
        });
    }

    private void loadRecentTransactions(int customerId) {
        tableModel.setRowCount(0); // Clear previous data
        List<Transaction> transactions = DatabaseDataManager.getRecentTransactionsByCustomerId(customerId, 5);
        for (Transaction tx : transactions) {
            tableModel.addRow(new Object[]{
                    tx.getTransactionId(),
                    tx.getAccountNumber(),
                    tx.getType(),
                    tx.getAmount(),
                    tx.getTimestamp()
            });
        }
    }

    private void loadTransactionsByAccountNumber(String accountNumber) {
        tableModel.setRowCount(0); // Clear previous data
        List<Transaction> transactions = DatabaseDataManager.getTransactionsByAccountNumber(accountNumber);
        for (Transaction tx : transactions) {
            tableModel.addRow(new Object[]{
                    tx.getTransactionId(),
                    tx.getAccountNumber(),
                    tx.getType(),
                    tx.getAmount(),
                    tx.getTimestamp()
            });
        }
    }
}
