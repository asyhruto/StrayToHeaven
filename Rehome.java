package com.mycompany.ooprehome;

public class Rehome {

private String rehomeID;
private String userID;
private String petName;
private int petAge;
private String petBreed;
private String petGender;
private String petTraits;
    
// Constructor to initialize the Rehome request form
    public Rehome (String rehomeID, String userID, String petName, String petBreed, int petAge, String petGender, String petTraits) {
        this.rehomeID = rehomeID;
        this.userID = userID;
        this.petName = petName;
        this.petBreed = petBreed;
        this.petAge = petAge;
        this.petGender = petGender;
        this.petTraits = petTraits;       
    }
    
// Prints a confirmation message that the rehome application has been submitted  
    public void submitPet() {
        System.out.println("Confirmation: Rehome application for '" + petName + "' has been successfully submitted!");
    }

// Returns a formatted text string containing the request ID and pet name
    public String getRehomeDetails() {
        return "Request ID: " + rehomeID + " | Pet Name: " + petName;
    }
}



