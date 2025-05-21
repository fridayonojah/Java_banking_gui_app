package com.bankapp.gui;
import javax.swing.*;
import java.awt.*;

public class TransactionUI extends JFrame {
    private JComboBox<String> transactionTypeCombo;
    private JTextField accountNumberField;
    private JTextField targetAccountField; 
    private JTextField amountField;
    private JButton processButton;

    public TransactionUI() {
        setTitle("Bank Transactions");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(6, 2, 10, 10));

        add(new JLabel("Transaction Type:"));
        transactionTypeCombo = new JComboBox<>(new String[]{"Deposit", "Withdraw", "Transfer"});
        add(transactionTypeCombo);

        add(new JLabel("Your Account Number:"));
        accountNumberField = new JTextField();
        add(accountNumberField);

        add(new JLabel("Target Account (For Transfer):"));
        targetAccountField = new JTextField();
        add(targetAccountField);

        add(new JLabel("Amount:"));
        amountField = new JTextField();
        add(amountField);

        add(new JLabel("")); // empty cell
        processButton = new JButton("Process Transaction");
        add(processButton);
    }

    public JComboBox<String> getTransactionTypeCombo() {
        return transactionTypeCombo;
    }

    public JTextField getAccountNumberField() {
        return accountNumberField;
    }

    public JTextField getTargetAccountField() {
        return targetAccountField;
    }

    public JTextField getAmountField() {
        return amountField;
    }

    public JButton getProcessButton() {
        return processButton;
    }
}
