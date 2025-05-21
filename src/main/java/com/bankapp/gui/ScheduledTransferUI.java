package com.bankapp.gui;

import javax.swing.*;
import java.awt.*;

public class ScheduledTransferUI extends JFrame {
    private JTextField fromAccountField;
    private JTextField toAccountField;
    private JTextField amountField;
    private JTextField delayField;
    private JButton scheduleButton;

    public ScheduledTransferUI() {
        setTitle("Schedule Transfer");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(5, 2, 10, 10));

        add(new JLabel("From Account:"));
        fromAccountField = new JTextField();
        add(fromAccountField);

        add(new JLabel("To Account:"));
        toAccountField = new JTextField();
        add(toAccountField);

        add(new JLabel("Amount:"));
        amountField = new JTextField();
        add(amountField);

        add(new JLabel("Delay (seconds):"));
        delayField = new JTextField();
        add(delayField);

        scheduleButton = new JButton("Schedule Transfer");
        add(scheduleButton);

        add(new JLabel()); 
    }

    public JTextField getFromAccountField() {
        return fromAccountField;
    }

    public JTextField getToAccountField() {
        return toAccountField;
    }

    public JTextField getAmountField() {
        return amountField;
    }

    public JTextField getDelayField() {
        return delayField;
    }

    public JButton getScheduleButton() {
        return scheduleButton;
    }
}
