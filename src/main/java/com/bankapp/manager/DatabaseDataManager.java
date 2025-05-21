package com.bankapp.manager;
import com.bankapp.model.Account;
import com.bankapp.model.Customer;
import com.bankapp.model.Transaction;
import com.bankapp.model.Transaction.ScheduleTransaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;


// Utility class to handle all DB operations
public class DatabaseDataManager {

    // Create a new user
    public static boolean createUser(String username, String password, String email) {
        String sql = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, email);
            pstmt.executeUpdate();
            System.out.println("User created: " + username);
            return true;
        } catch (SQLException e) {
            System.out.println(e);
            System.err.println("Error creating user: " + e.getMessage());
            return false;
        }
    }


    public static Customer loadUserCredentials(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            System.err.println("user" + rs);
            if (rs.next()) {
                return new Customer(rs.getInt("id"), rs.getString("username"), rs.getString("email"), rs.getString("password"));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching user credentials: " + e.getMessage());
        }
        return null;
    }
    


    // Create a new account
    public static boolean createAccount(String accountNumber, int userId, String accountType) {
        String sql = "INSERT INTO accounts (accountNumber, customerId, account_type, balance) VALUES (?, ?, ?, 0.0)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, accountNumber);
            pstmt.setInt(2, userId);
            pstmt.setString(3, accountType);
            pstmt.executeUpdate();
            System.out.println("Account created for user ID: " + userId);
            return true;
        } catch (SQLException e) {
            System.err.println("Error creating account: " + e.getMessage());
            return false;
        }
    }



    // Deposit into an account
    public static boolean deposit(int accountId, double amount) {
        String updateBalance = "UPDATE accounts SET balance = balance + ? WHERE id = ?";
        String insertTransaction = "INSERT INTO transactions (account_id, amount, transaction_type) VALUES (?, ?, 'deposit')";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement updateStmt = conn.prepareStatement(updateBalance);
             PreparedStatement insertStmt = conn.prepareStatement(insertTransaction)) {

            conn.setAutoCommit(false);

            // Update balance
            updateStmt.setDouble(1, amount);
            updateStmt.setInt(2, accountId);
            updateStmt.executeUpdate();

            // Insert transaction
            insertStmt.setInt(1, accountId);
            insertStmt.setDouble(2, amount);
            insertStmt.executeUpdate();

            conn.commit();
            System.out.println("Deposit completed to account ID: " + accountId);
            return true;

        } catch (SQLException e) {
            System.err.println("Deposit failed: " + e.getMessage());
            return false;
        }
    }

    // Withdraw from an account
    public static boolean withdraw(int accountId, double amount) {
        String updateBalance = "UPDATE accounts SET balance = balance - ? WHERE id = ? AND balance >= ?";
        String insertTransaction = "INSERT INTO transactions (account_id, amount, transaction_type) VALUES (?, ?, 'withdraw')";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement updateStmt = conn.prepareStatement(updateBalance);
             PreparedStatement insertStmt = conn.prepareStatement(insertTransaction)) {

            conn.setAutoCommit(false);

            // Update balance (only if sufficient funds)
            updateStmt.setDouble(1, amount);
            updateStmt.setInt(2, accountId);
            updateStmt.setDouble(3, amount);
            int rowsAffected = updateStmt.executeUpdate();

            if (rowsAffected == 0) {
                System.err.println("Withdrawal failed: insufficient funds.");
                conn.rollback();
                return false;
            }

            // Insert transaction
            insertStmt.setInt(1, accountId);
            insertStmt.setDouble(2, amount);
            insertStmt.executeUpdate();

            conn.commit();
            System.out.println("Withdrawal completed from account ID: " + accountId);
            return true;

        } catch (SQLException e) {
            System.err.println("Withdrawal failed: " + e.getMessage());
            return false;
        }
    }

    // Get account balance
    public static double getAccountBalance(int accountId) {
        String sql = "SELECT balance FROM accounts WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, accountId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("balance");
            }
        } catch (SQLException e) {
            System.err.println("Error fetching balance: " + e.getMessage());
        }
        return 0.0;
    }

    // Fetch transactions for an account
    public static List<Transaction> fetchTransactions(String accountNumber) {
        List<Transaction> transactions = new ArrayList<>(); 
        String sql = "SELECT * FROM transactions WHERE accountNumber = ? ORDER BY timestamp DESC";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, accountNumber);
            ResultSet rs = pstmt.executeQuery();
            System.out.println("Transaction History:");
            while (rs.next()) {
                transactions.add(new Transaction(
                rs.getInt("transactionId"), 
                rs.getString("accountNumber"),
                rs.getString("transaction_type"),
                rs.getDouble("amount"),
                rs.getTimestamp("timestamp")));
               
            }
        } catch (SQLException e) {
            System.err.println("Error fetching transactions: " + e.getMessage());
        }
        return transactions;
    }


    // Schedule background transactions
    public static boolean scheduleTransfer(String fromAccount, String toAccount, double amount, int delaySeconds){
        String sql = "INSERT INTO accounts (fromAccount, toAccount, amount, delaySeconds) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, fromAccount);
            pstmt.setString(2, toAccount);
            pstmt.setDouble(3, amount);
            pstmt.setInt(4, delaySeconds);
            pstmt.executeUpdate();

            return true;
        } catch (SQLException e) {
            System.err.println("Error scheduling transaction: " + e.getMessage());
            return false;
        }
    }



    public static Account getAccountById(int customerId) {
        String sql = "SELECT * FROM accounts WHERE customerId = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, customerId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Account(rs.getString("accountNumber"), rs.getInt("customerId"), 
                rs.getString("accountType"), rs.getDouble("balance"));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching user credentials: " + e.getMessage());
        }
        return null;
    }

    public static List<Account> loadAccounts() {
        String sql = "SELECT * FROM accounts";
        List<Account> accounts = new ArrayList<>(); 
        
        try (Connection conn = DatabaseManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) { 
                accounts.add(new Account(
                    rs.getString("accountNumber"), 
                    rs.getInt("customerId"), 
                    rs.getString("accountType"), 
                    rs.getDouble("balance")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching Accounts: " + e.getMessage());
        }
        
        return accounts; 
    }

}
