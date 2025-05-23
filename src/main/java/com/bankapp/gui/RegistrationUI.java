package com.bankapp.gui;

import com.bankapp.controller.DashboardController;
import com.bankapp.manager.DatabaseDataManager;
import com.bankapp.model.Customer;
import com.bankapp.utils.AuditLogger;
import com.bankapp.utils.Notifier;
import com.bankapp.utils.PasswordUtils;
import com.formdev.flatlaf.FlatIntelliJLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RegistrationUI extends JFrame {
    private JTextField usernameField;
    private JTextField emailField, fullnameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JButton registerButton;
    private JLabel backToLoginLabel;

    public RegistrationUI() {
        try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
        } catch (Exception e) {
            System.err.println("Failed to set FlatLaf theme.");
        }

        setTitle("Group E Banking App - Register");
        setSize(420, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        // Title
        JLabel titleLabel = new JLabel("Create Your Account", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        add(titleLabel, BorderLayout.NORTH);

        // Main Form
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1;
        gbc.gridwidth = 2;

        fullnameField = new JTextField();
        fullnameField.putClientProperty("JTextField.placeholderText", "Fullname");
        
        usernameField = new JTextField();
        usernameField.putClientProperty("JTextField.placeholderText", "Username");

        emailField = new JTextField();
        emailField.putClientProperty("JTextField.placeholderText", "Email");

        passwordField = new JPasswordField();
        passwordField.putClientProperty("JTextField.placeholderText", "Password");

        confirmPasswordField = new JPasswordField();
        confirmPasswordField.putClientProperty("JTextField.placeholderText", "Confirm Password");

        registerButton = new JButton("Register");
        registerButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        registerButton.setBackground(new Color(30, 144, 255));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        registerButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        registerButton.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        backToLoginLabel = new JLabel("<html><u>Back to Login</u></html>");
        backToLoginLabel.setForeground(new Color(30, 144, 255));
        backToLoginLabel.setHorizontalAlignment(SwingConstants.CENTER);
        backToLoginLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Add components to form
        gbc.gridy = 0; formPanel.add(fullnameField, gbc);
        gbc.gridy = 1; formPanel.add(usernameField, gbc);
        gbc.gridy = 2; formPanel.add(emailField, gbc);
        gbc.gridy = 3; formPanel.add(passwordField, gbc);
        gbc.gridy = 4; formPanel.add(registerButton, gbc);
        gbc.gridy = 5; formPanel.add(backToLoginLabel, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Back to Login click handler
        backToLoginLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SwingUtilities.invokeLater(() -> {
                    new LoginUI().setVisible(true);
                });
                dispose(); // Close Login Form
            }
        });

        // Register logic
        registerButton.addActionListener(e -> {
            String username = getUsername();
            String fullname = getFullname();
            String password = getPassword();
            String email = getEmail();

            if (username.isEmpty() && fullname.isEmpty() && password.isEmpty() && email.isEmpty()) {
                Notifier.showNotification("All inputs are required!", "Missing Fields");
                return;
            }

            Customer customer = DatabaseDataManager.loadUserCredentials(username);
            if (customer != null) {
                Notifier.showNotification("User with this email or username already exists!", "Registration Attempt");
                return;
            }

            String hashedPassword = PasswordUtils.hashPassword(password);
            if (DatabaseDataManager.createUser(username, hashedPassword, email, fullname)) {
                AuditLogger.log(username, "Customer info saved successfully", "Success");
                Notifier.showNotification("Hi " + username + ", your details were saved successfully", "Registration Successful");

                DashboardUI dashboardView = new DashboardUI(username);
                dashboardView.setVisible(true);
                new DashboardController(dashboardView);
                dispose();
            } else {
                AuditLogger.log(username, "Registration Attempt", "Registration Failed");
                Notifier.showNotification("Apologies, your details were not saved!", "Registration Failed");
            }
        });
    }

    public String getUsername() {
        return usernameField.getText().trim();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public String getEmail() {
        return emailField.getText().trim();
    }

    public void addRegistrationListener(ActionListener listener) {
        registerButton.addActionListener(listener);
    }

    public String getFullname() {
        return fullnameField.getText().trim();
    }
}
