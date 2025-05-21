package com.bankapp.gui;

import javax.swing.*;

import com.bankapp.controller.DashboardController;
import com.bankapp.manager.DatabaseDataManager;
import com.bankapp.model.Customer;
import com.bankapp.utils.AuditLogger;
import com.bankapp.utils.Notifier;
import com.bankapp.utils.PasswordUtils;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RegistrationUI extends JFrame {
    private JTextField usernameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JButton registerButton;
    private JLabel backToLoginLabel;

    public RegistrationUI() {
        setTitle("Group E Banking App - Register");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Create Your Account");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(15);

        
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField(15);

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(15);

        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordField = new JPasswordField(15);

        registerButton = new JButton("Register");
        registerButton.setFont(new Font("Arial", Font.BOLD, 14));
        registerButton.setBackground(new Color(70, 130, 180));
        registerButton.setForeground(Color.WHITE);

        // "Back to Login" link
        backToLoginLabel = new JLabel("<html><u>Back to Login</u></html>");
        backToLoginLabel.setForeground(Color.BLUE);
        backToLoginLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        backToLoginLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SwingUtilities.invokeLater(() -> {
                    new LoginUI().setVisible(true); 
                });
                dispose(); // Close Registration Form
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = getUsername();
                String password = getPassword();
                String email = getEmail();

                Customer customer = DatabaseDataManager.loadUserCredentials(username);
                
                // Check if customer email or username already exist
                if (customer != null) {
                    Notifier.showNotification("User with this email or username already exist!", "Login Attempt");
                }

                // Hash Password Here
                String passHash = PasswordUtils.hashPassword(password);
                
                // Save customer info in the database
                if (DatabaseDataManager.createUser(username, passHash, email)) {
                    AuditLogger.log(getUsername(), "Customer info saved successfuly", "Success");
                    Notifier.showNotification("Hi " + getUsername() + " your details were saveed successfully ", "Registration Alert");

                    // RegistrationUI registrationUI = new RegistrationUI();
                    // registrationUI.dispose(); // Close Registration Ui

                    DashboardUI dashboardView = new DashboardUI(username);
                    dashboardView.setVisible(true);
                    new DashboardController(dashboardView);


                } else {
                    AuditLogger.log(getUsername(), "Registration Attempt", "Registration Failed!");
                    Notifier.showNotification("Apologies, your details were not saved!", "Registration Failed");
                }
            }
        });

        // Positioning elements
        gbc.gridy = 0; panel.add(titleLabel, gbc);
        gbc.gridy = 1; panel.add(usernameLabel, gbc);
        gbc.gridy = 2; panel.add(usernameField, gbc);
        gbc.gridy = 3; panel.add(emailLabel, gbc);
        gbc.gridy = 4; panel.add(emailField, gbc);
        gbc.gridy = 5; panel.add(passwordLabel, gbc);
        gbc.gridy = 6; panel.add(passwordField, gbc);
        gbc.gridy = 7; panel.add(confirmPasswordLabel, gbc);
        gbc.gridy = 8; panel.add(confirmPasswordField, gbc);
        gbc.gridy = 9; panel.add(registerButton, gbc);
        gbc.gridy = 10; panel.add(backToLoginLabel, gbc); // "Back to Login" link

        add(panel);
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


}
