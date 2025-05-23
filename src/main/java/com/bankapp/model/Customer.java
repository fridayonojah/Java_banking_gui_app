package com.bankapp.model;

public class Customer {
    private int id;
    private String username;
    private String email;
    private String password; 
    private String fullname; 


    public Customer(int id, String username, String email, String password, String fullname) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.fullname = fullname;
        
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public String getFullname() { return fullname; }

}
