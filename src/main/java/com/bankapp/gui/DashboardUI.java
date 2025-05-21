// Dashboard UI class
package com.bankapp.gui;

import javax.swing.*;
import java.awt.*;


public class DashboardUI extends JFrame {
    private JLabel welcomeLabel;
    private JMenuItem viewAccountMenuItem, logoutMenuItem;
    private JMenuItem depositMenuItem, withdrawMenuItem, transferMenuItem, scheduleTransactionMenuItem;
    private JMenuItem viewStatementsMenuItem;
    private static String username;

    public DashboardUI(String username) {
        DashboardUI.username = username;
        setTitle("Banking App - Dashboard");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        // Layout
        setLayout(new BorderLayout());

        // Welcome Label
        welcomeLabel = new JLabel("Welcome, " + username + "!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(welcomeLabel, BorderLayout.CENTER);

        // Menu Bar
        JMenuBar menuBar = new JMenuBar();

        // Account Menu
        JMenu accountMenu = new JMenu("Account");
        viewAccountMenuItem = new JMenuItem("View Account");
        logoutMenuItem = new JMenuItem("Logout");
        accountMenu.add(viewAccountMenuItem);
        accountMenu.addSeparator();
        accountMenu.add(logoutMenuItem);

        // Transaction Menu
        JMenu transactionMenu = new JMenu("Transaction");
        depositMenuItem = new JMenuItem("Deposit");
        withdrawMenuItem = new JMenuItem("Withdraw");
        transferMenuItem = new JMenuItem("Transfer");
        transactionMenu.add(depositMenuItem);
        transactionMenu.add(withdrawMenuItem);
        transactionMenu.add(transferMenuItem);

        // Reports Menu
        JMenu reportsMenu = new JMenu("Reports");
        viewStatementsMenuItem = new JMenuItem("View Statements");
        reportsMenu.add(viewStatementsMenuItem);


        JMenu scheduleTransactionMenu = new JMenu("Schedule Transaction");
        scheduleTransactionMenuItem = new JMenuItem("Start Transation");
        scheduleTransactionMenu.add(scheduleTransactionMenuItem);

        // Add Menus to Bar
        menuBar.add(accountMenu);
        menuBar.add(transactionMenu);
        menuBar.add(reportsMenu);
        menuBar.add(scheduleTransactionMenu);

        setJMenuBar(menuBar);
    }

    // Getter for MenuItems so Controller can attach Listeners
    public JMenuItem getLogoutMenuItem() {
        return logoutMenuItem;
    }

    public JMenuItem getViewAccountMenuItem() {
        return viewAccountMenuItem;
    }

    public static String getUsername(){
        return username;
    }

    public JMenuItem getTransactionMenuItem() {
        return transferMenuItem;
    }

    public JMenuItem getReportMenuItem() {
        return viewStatementsMenuItem;
    }

    public JMenuItem getScheduleTransferMenuItem() {
       return scheduleTransactionMenuItem;
    }
}
