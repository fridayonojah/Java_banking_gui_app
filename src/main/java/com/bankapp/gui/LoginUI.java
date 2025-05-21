package com.bankapp.gui;

import javax.swing.*;
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
        setTitle("Group E Banking App - Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Welcome to Group E Banking");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(15);

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(15);

        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setBackground(new Color(70, 130, 180));
        loginButton.setForeground(Color.WHITE);

        // Create Account Label (Clickable Link)
        createAccountLabel = new JLabel("<html><u>Create an account</u></html>");
        createAccountLabel.setForeground(Color.BLUE);
        createAccountLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        createAccountLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SwingUtilities.invokeLater(() -> {
                    new RegistrationUI().setVisible(true); 
                });
                dispose(); // Close Registration Form
            }
        });

        // Positioning elements
        gbc.gridy = 0; panel.add(titleLabel, gbc);
        gbc.gridy = 1; panel.add(usernameLabel, gbc);
        gbc.gridy = 2; panel.add(usernameField, gbc);
        gbc.gridy = 3; panel.add(passwordLabel, gbc);
        gbc.gridy = 4; panel.add(passwordField, gbc);
        gbc.gridy = 5; panel.add(loginButton, gbc);
        gbc.gridy = 6; panel.add(createAccountLabel, gbc); 

        add(panel);
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
