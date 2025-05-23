package com.bankapp.gui;

import javax.swing.*;
import com.formdev.flatlaf.FlatIntelliJLaf;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel createAccountLabel;

    public LoginUI() {
        try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
        } catch (Exception e) {
            System.err.println("Failed to apply FlatLaf theme.");
        }

        setTitle("Group E Banking App - Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        // Title at top
        JLabel titleLabel = new JLabel("Group E Bank", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 10, 5, 10));
        add(titleLabel, BorderLayout.NORTH);

        // Form Panel with full-width fields
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.gridx = 0;
        gbc.gridwidth = 2;

        // Username Field
        usernameField = new JTextField();
        usernameField.putClientProperty("JTextField.placeholderText", "Username");

        // Password Field
        passwordField = new JPasswordField();
        passwordField.putClientProperty("JTextField.placeholderText", "Password");

        // Add fields
        gbc.gridy = 0; formPanel.add(usernameField, gbc);
        gbc.gridy = 1; formPanel.add(passwordField, gbc);

        // Login Button
        loginButton = new JButton("Login");
        loginButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        loginButton.setBackground(new Color(30, 144, 255));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginButton.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        gbc.gridy = 2;
        formPanel.add(loginButton, gbc);

        // Create Account Link
        createAccountLabel = new JLabel("<html><u>Create an account</u></html>");
        createAccountLabel.setForeground(new Color(30, 144, 255));
        createAccountLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        createAccountLabel.setHorizontalAlignment(SwingConstants.CENTER);
        createAccountLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        gbc.gridy = 3;
        formPanel.add(createAccountLabel, gbc);

        // Add everything
        add(formPanel, BorderLayout.CENTER);


        createAccountLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SwingUtilities.invokeLater(() -> {
                    new RegistrationUI().setVisible(true); 
                });
                dispose(); // Close Registration Form
            }
        });
    }

    public String getUsername() {
        return usernameField.getText().trim();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public void addLoginListener(ActionListener listener) {
        loginButton.addActionListener(listener);
    }
}
