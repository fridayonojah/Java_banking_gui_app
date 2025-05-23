package com.bankapp.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class ScheduledTransferUI extends JFrame {
    private JTextField fromAccountField;
    private JTextField toAccountField;
    private JTextField amountField;
    private JTextField delayField;
    private JButton scheduleButton;

    public ScheduledTransferUI() {
        setTitle("Group E Bank - Scheduled Transfer");
        setSize(420, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 0, 12, 0);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        JLabel title = new JLabel("Schedule Transfer", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(new Color(25, 50, 80));
        gbc.gridy = 0;
        panel.add(title, gbc);

        fromAccountField = createStyledField("From account number");
        gbc.gridy++;
        panel.add(fromAccountField, gbc);

        toAccountField = createStyledField("To account number");
        gbc.gridy++;
        panel.add(toAccountField, gbc);

        amountField = createStyledField("Amount to transfer");
        gbc.gridy++;
        panel.add(amountField, gbc);

        delayField = createStyledField("Delay in seconds");
        gbc.gridy++;
        panel.add(delayField, gbc);

        scheduleButton = createRoundedButton("Schedule Transfer");
        gbc.gridy++;
        panel.add(scheduleButton, gbc);

        add(panel);

    }

    private JTextField createStyledField(String placeholder) {
        JTextField field = new JTextField();
        field.setPreferredSize(new Dimension(300, 40));
        field.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        field.setForeground(Color.DARK_GRAY);
        field.setBackground(new Color(245, 245, 245));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));

        field.setText(placeholder);
        field.setForeground(Color.GRAY);
        field.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }

            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(Color.GRAY);
                }
            }
        });

        return field;
    }

    private JButton createRoundedButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(300, 45));
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(255, 140, 0)); // Orange
        button.setBorder(BorderFactory.createLineBorder(new Color(255, 140, 0), 1));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        button.setUI(new javax.swing.plaf.basic.BasicButtonUI());

        return button;
    }

    public JTextField getFromAccountField() { return fromAccountField; }
    public JTextField getToAccountField() { return toAccountField; }
    public JTextField getAmountField() { return amountField; }
    public JTextField getDelayField() { return delayField; }
    public JButton getScheduleButton() { return scheduleButton; }
}
