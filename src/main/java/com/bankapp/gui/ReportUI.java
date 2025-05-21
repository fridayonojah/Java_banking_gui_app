package com.bankapp.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ReportUI extends JFrame {
    private JTable transactionTable;
    private DefaultTableModel tableModel;
    private JTextField accountNumberField;
    private JButton searchButton;

    public ReportUI() {
        setTitle("Transaction Reports");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());

        topPanel.add(new JLabel("Account Number:"));
        accountNumberField = new JTextField(15);
        topPanel.add(accountNumberField);

        searchButton = new JButton("Search");
        topPanel.add(searchButton);

        add(topPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"Date", "Type", "Amount"}, 0);
        transactionTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(transactionTable);

        add(scrollPane, BorderLayout.CENTER);
    }

    public JTable getTransactionTable() {
        return transactionTable;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public JTextField getAccountNumberField() {
        return accountNumberField;
    }

    public JButton getSearchButton() {
        return searchButton;
    }
}
