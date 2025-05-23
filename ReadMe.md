# Java GUI Banking Application

**Author**: Friday Onojah  
**Institution**: Ahmadu Bello University  
**Course**: Object-Oriented Programming  


## Overview

This is a standalone Java Banking Application built using Swing and AWT for the GUI and integrated with a PostgreSQL database via Docker. It follows the Model-View-Controller (MVC) design pattern for clean separation of concerns, modular development, and scalability.

## 🚀 Features

- User Registration & Login
- Deposit, Withdrawal, and Transfer Funds
- View Transaction History
- Admin Dashboard for Account Management
- Error Handling with Dialog Feedback
- Persistent Database Integration (PostgreSQL via Docker)
- Modular MVC Code Structure

---

## 📘 User Manual

### 1. **Launching the App**
- Ensure Docker is running and PostgreSQL container is active.
- Run the Java application via the main class `BankApp.java`.

### 2. **Registering an Account**
- Navigate to `Sign Up` screen.
- Enter required details (name, email, password, account type).
- Submit to create an account and get assigned an account number.

### 3. **Logging In**
- Navigate to `Login` screen.
- Enter valid credentials to access the user dashboard.

### 4. **User Dashboard**
- **Balance View**: Displays current balance.
- **Deposit Funds**: Enter amount and submit to deposit.
- **Withdraw Funds**: Enter amount, validate limit, and withdraw.
- **Transfer Funds**: Specify recipient account number and amount.
- **Transaction History**: Displays a list of recent transactions.
- **Logout**: Safely logs you out of the session.




