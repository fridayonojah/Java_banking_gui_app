package com.bankapp.gui;

import com.bankapp.manager.DatabaseDataManager;
import com.bankapp.model.Account;
import com.bankapp.model.Customer;
import com.bankapp.model.Transaction;
import com.bankapp.utils.Notifier;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class DashboardUI extends JFrame {
    private JLabel welcomeLabel;
    private JPanel accountDetailsPanel;
    private static String username;
    private int activeUserId;


    private JMenuItem viewAccountMenuItem, logoutMenuItem;
    private JMenuItem depositMenuItem, withdrawMenuItem, transferMenuItem, scheduleTransactionMenuItem;
    private JMenuItem viewStatementsMenuItem;
    private DefaultTableModel transactionModel;
    private JTable transactionTable;
    private JPanel balanceListPanel;

    public DashboardUI(String username) {
        DashboardUI.username = username;
        this.setCustomerId(username);

        try {
            FlatLaf.registerCustomDefaultsSource("themes");
            UIManager.setLookAndFeel(new FlatLightLaf());

            UIManager.put("Component.arc", 15);
            UIManager.put("Button.arc", 20);
            UIManager.put("Panel.background", new Color(245, 245, 250));
        } catch (Exception e) {
            System.err.println("Failed to initialize FlatLaf");
        }

        setTitle("💳 Banking App - Dashboard");
        setSize(800, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(UIManager.getColor("Panel.background"));

        welcomeLabel = new JLabel("Welcome, " + username, SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        welcomeLabel.setBorder(new EmptyBorder(20, 10, 10, 10));
        add(welcomeLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 20, 10));
        centerPanel.setBorder(new EmptyBorder(10, 20, 10, 20));
        centerPanel.setOpaque(false);

        balanceListPanel = new JPanel();
        balanceListPanel.setLayout(new BoxLayout(balanceListPanel, BoxLayout.Y_AXIS));
        balanceListPanel.setOpaque(false);

        JScrollPane balanceScrollPane = new JScrollPane(balanceListPanel);
        balanceScrollPane.setBorder(null);
        centerPanel.add(balanceScrollPane);

        JPanel transactionsCard = new JPanel(new BorderLayout());
        transactionsCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                new EmptyBorder(20, 20, 20, 20)
        ));
        transactionsCard.setBackground(Color.WHITE);

        JLabel txTitle = new JLabel("Recent Transactions");
        txTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        transactionsCard.add(txTitle, BorderLayout.NORTH);

        String[] columns = {"Date", "Type", "Amount"};
        transactionModel = new DefaultTableModel(columns, 0);
        transactionTable = new JTable(transactionModel);
        transactionTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        transactionTable.setRowHeight(24);
        transactionTable.setShowGrid(false);
        transactionTable.setIntercellSpacing(new Dimension(0, 0));

        JScrollPane scrollPane = new JScrollPane(transactionTable);
        scrollPane.setBorder(null);
        transactionsCard.add(scrollPane, BorderLayout.CENTER);

        centerPanel.add(transactionsCard);
        add(centerPanel, BorderLayout.CENTER);

        accountDetailsPanel = new JPanel();
        accountDetailsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        accountDetailsPanel.setBackground(UIManager.getColor("Panel.background"));
        accountDetailsPanel.setBorder(new EmptyBorder(5, 0, 15, 0));

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBorder(new EmptyBorder(5, 10, 5, 10));

        JMenu accountMenu = new JMenu("👤 Account");
        viewAccountMenuItem = new JMenuItem("View Account");
        logoutMenuItem = new JMenuItem("Logout");
        accountMenu.add(viewAccountMenuItem);
        accountMenu.add(logoutMenuItem);

        JMenu transactionMenu = new JMenu("💰 Transactions");
        depositMenuItem = new JMenuItem("Deposit");
        withdrawMenuItem = new JMenuItem("Withdraw");
        transferMenuItem = new JMenuItem("Transfer");
        scheduleTransactionMenuItem = new JMenuItem("Schedule Transaction");
        transactionMenu.add(depositMenuItem);
        transactionMenu.add(withdrawMenuItem);
        transactionMenu.add(transferMenuItem);

        JMenu reportsMenu = new JMenu("Reports");
        viewStatementsMenuItem = new JMenuItem("View Statements");
        reportsMenu.add(viewStatementsMenuItem);

        menuBar.add(accountMenu);
        menuBar.add(transactionMenu);
        menuBar.add(reportsMenu);
        setJMenuBar(menuBar);

        refreshDashboard();
        startAutoRefresh();
    }

    private void refreshDashboard() {
        balanceListPanel.removeAll();
        transactionModel.setRowCount(0);

        List<Account> userAccounts = DatabaseDataManager.loadAccountsByCustomerId(activeUserId);
        // if (userAccounts == null || userAccounts.isEmpty()) {
            // Notifier.showNotification("You don't have an active account yet.", "Not Found");
            
        // } else {
            for (Account account : userAccounts) {
                JPanel singleBalanceCard = new JPanel(new BorderLayout());
                singleBalanceCard.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                        new EmptyBorder(20, 20, 20, 20)
                ));
                singleBalanceCard.setBackground(Color.WHITE);
                singleBalanceCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

                JLabel accountNumberLabel = new JLabel("Account Number: " + account.getAccountNumber());
                accountNumberLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
                singleBalanceCard.add(accountNumberLabel, BorderLayout.NORTH);

                JLabel balanceLabel = new JLabel("₦" + String.format("%.2f", account.getBalance()), SwingConstants.CENTER);
                balanceLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
                balanceLabel.setForeground(new Color(44, 102, 198));
                singleBalanceCard.add(balanceLabel, BorderLayout.CENTER);

                balanceListPanel.add(singleBalanceCard);
            }
        // }

        loadRecentTransactions();
        balanceListPanel.revalidate();
        balanceListPanel.repaint();
    }

    private void loadRecentTransactions() {
        try {
            List<Transaction> transactions = DatabaseDataManager.getRecentTransactionsByCustomerId(activeUserId, 5);
            for (Transaction tx : transactions) {
                transactionModel.addRow(new Object[]{
                        tx.getTimestamp().toString(),
                        tx.getType(),
                        "₦" + String.format("%.2f", tx.getAmount())
                });
            }
        } catch (Exception e) {
            System.err.println("Failed to load transactions: " + e.getMessage());
        }
    }

    private void startAutoRefresh() {
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> refreshDashboard());
            }
        }, 0, 10000);
    }

    public JMenuItem getViewAccountMenuItem() { return viewAccountMenuItem; }
    public static String getUsername() { return username; }
    public JMenuItem getTransferMenuItem() { return transferMenuItem; }
    public JMenuItem getWithdrawMenuItem() { return withdrawMenuItem; }
    public JMenuItem getDepositMenuItem() { return depositMenuItem; }
    public JMenuItem getReportMenuItem() { return viewStatementsMenuItem; }
    public JMenuItem getScheduleTransferMenuItem() { return scheduleTransactionMenuItem; }
    public JMenuItem getLogoutMenuItem() { return logoutMenuItem; }

    public int setCustomerId(String username) {
        Customer customer = DatabaseDataManager.loadUserCredentials(username);
        return this.activeUserId = customer.getId();
    }
}
