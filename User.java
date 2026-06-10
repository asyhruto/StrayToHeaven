package com.example;

public class User {
    private String userID;
    private String userName;
    private String userEmail;
    private String userPass;

    // Constructor to create a new user with the provided details, allowing for user registration and management within the system.
    public User(String userID, String userName, String userEmail, String userPass) {
        this.userID = userID;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPass = userPass;
    }
    
    //returns boolean as we use boolean for login
    public boolean login(String email, String password){
        return this.userEmail.equals(email) && this.userPass.equals(password);
    }

    public String getName() {
        return userName;
    }

    public String getEmail() {
        return userEmail;
    }

    public String getPassword() {
        return userPass;
    }

    public String getUserID() {
        return userID;
    }

    // Method to display user details, which can be used for profile viewing or administrative purposes to manage user information within the system.
    public void getDetails() {
        System.out.println("User ID: " + userID); 
        System.out.println("Name: " + userName);
        System.out.println("Email: " + userEmail);
    }

}
