package com.bankapp.gui;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AccountManagementUI extends JFrame {
    private JTable accountTable;
    private JButton createAccountButton;
    private DefaultTableModel tableModel;

    public AccountManagementUI() {
        setTitle("Manage Accounts");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Layout
        setLayout(new BorderLayout());

        // Table
        tableModel = new DefaultTableModel(new String[]{"Account No.", "Type", "Balance"}, 0);
        accountTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(accountTable);
        add(scrollPane, BorderLayout.CENTER);

        // Bottom panel with create account button
        JPanel bottomPanel = new JPanel();
        createAccountButton = new JButton("Create New Account");
        bottomPanel.add(createAccountButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public JButton getCreateAccountButton() {
        return createAccountButton;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }
}
