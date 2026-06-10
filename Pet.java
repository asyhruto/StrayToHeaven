package com.example;

public class Pet {
    private String petID;
    private int petAge;
    private String petBreed;
    private String petGender;
    private String petTraits;
    private String petStatus;

    // Constructor to create a new pet with the provided details, allowing for pet management and tracking within the shelter system.
    public Pet(String petID, int petAge, String petBreed, String petGender, String petTraits, String petStatus) {
        this.petID = petID;
        this.petAge = petAge;
        this.petBreed = petBreed;
        this.petGender = petGender;
        this.petTraits = petTraits;
        this.petStatus = petStatus;
    }

    //Getters for JAVAFX TableView compatibility
    public String getPetID() {
        return petID;
    }
    public int getPetAge() {
        return petAge;
    }
    public String getPetBreed() {
        return petBreed;
    }
    public String getPetGender() {
        return petGender;
    }
    public String getPetTraits() {
        return petTraits;
    }
    public String getPetStatus() {
        return petStatus;
    }

    // Update the status and track current state of the pet (e.g., Available, Adopted, Rehomed)
    public void updateStatus(String newStatus) {
        this.petStatus = newStatus;
    }

}
