package com.mycompany.ooprehome;
import java.io.FileWriter;
import java.io.IOException;

public class Donation {

    private String donationID;
    private User donor;
    private double donationAmount;
    private String donationDate;
    
// Contructor to record new donation
public Donation(String donationID, User donor, double donationAmount, String donationDate) {
    this.donationID = donationID;
    this.donor = donor;
    this.donationAmount = donationAmount;
    this.donationDate = donationDate;
    
}

// Process of donation
public void processDonation() {
// record donation details
// validate the donation amount (must be greater than 0)
        if (this.donationAmount <= 0) {
            System.out.println("Error! Donation amount must be greater than 0.");
            return;
        }
        
      // do receipt for user
        System.out.println("=== RECEIPT ===");
        System.out.println("Thank you, " + donor.getName() + "!");
        System.out.println("Donation ID: " + donationID);
        System.out.println("Amount: RM " + donationAmount);
        System.out.println("Date: " + donationDate);
        System.out.println("===============");
        
        // save the donation record to the database file
        try (FileWriter writer = new FileWriter("donations.txt", true)) {
            writer.write(donationID + "," + donor.getName() + "," + donationAmount + "," + donationDate + "\n");
            System.out.println("Success: Donation record saved to database.");
        } catch (IOException e) {
            System.out.println("Error: Could not save donation record. " + e.getMessage());
        }
    }
}


