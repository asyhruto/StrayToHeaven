package com.example;

import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

public class AdoptionRequest {
    private static final List<AdoptionRequest> requests = new ArrayList<>();

    private String requestID;
    private String userID;
    private String petID;
    private String status;
    private String date;
    
    // Constructor to initialize an adoption request with default status "Pending"
    public AdoptionRequest(){
        this.status = "Pending";
    }
    
    // Method to create and send an adoption request, which adds the request to the static list of requests for tracking and management.
    public void sendAdoptionRequest(String requestID, String userID, String petID, String date){
        this.requestID = requestID;
        this.userID = userID;
        this.petID = petID;
        this.date = date;
        this.status = "Pending";
        addRequest(this);
    }
    
    // adding new adoption request
    private static void addRequest(AdoptionRequest request) {
        if (findByRequestID(request.requestID) == null) {
            requests.add(request);
        }
    }

    // for admin to view all adoption requests and manage them ( approve / reject )
    public static List<AdoptionRequest> getAllRequests() {
        return requests;
    }

    // Update the request status whether approved or rejected
    public static AdoptionRequest findByRequestID(String requestID) {
        for (AdoptionRequest req : requests) {
            if (req.requestID != null && req.requestID.equalsIgnoreCase(requestID)) {
                return req;
            }
        }
        return null;
    }

    // Convert the adoption request details into a string array format for display in a TableView or similar UI component, allowing for easy visualization of the request information in the admin interface.
    public String[] toRow() {
        return new String[]{requestID, userID, petID, date, status};
    }

    // update pet status and track the adoption request status
    public void updateStatus(String newStatus){
        this.status = newStatus; 
        // If the adoption is approved, change the pet's status in the system to "Adopted"
        if (newStatus.equalsIgnoreCase("Approved")) {
            PetManager.updatePetStatus(this.petID, "Adopted");
        }
    }
    
    // receipt after adopt request is processed
    public String getReceipt(){
       return "STRAY TO HEAVEN RECEIPT\n" +
               "_________________________\n" +
               String.format("%-15s : %s\n", "Request ID" , requestID) +
               String.format("%-15s : %s\n", "User ID" , userID) +
               String.format("%-15s : %s\n", "Pet ID" , petID) +
               String.format("%-15s : %s\n", "Request Date" , date) +
               String.format("%-15s : %s\n", "Status" , status.toUpperCase()) +
               "_________________________\n" +
               "Thank you for choosing to adopt! You have  such a big heart.";
    }
    
    public String getRequestID(){
        return requestID;
    }
    
    public String getUserID(){
        return userID;
    }
    
    public String getPetID(){
        return petID;
    }
    
    public String Status(){
        return status;
    }
    
    public String getDate(){
        return date;
    }

    public void start(Stage stage) {
        throw new UnsupportedOperationException("Unimplemented method 'start'");
    }
}
