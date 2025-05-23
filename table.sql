CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    fullname VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
);

CREATE TABLE transactions (
    transactionId SERIAL PRIMARY KEY,
    accountId INT REFERENCES accounts(accountId),
    transactionType VARCHAR(20) NOT NULL,
    amount DECIMAL(15,2) NOT NULL,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE accounts (
    accountId SERIAL PRIMARY KEY,
    customerId INT REFERENCES users(id),
    accountType VARCHAR(20) NOT NULL,
    balance DECIMAL(15, 2) NOT NULL,
    accountNumber VARCHAR(20) NOT NULL,
    last_modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

