/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.StrayToHeaven;

/**
 *
 * @author user
 */
public class AdoptionRequest {
    private String requestID;
    private String userID;
    private String petID;
    private String status;
    private String date;
    
    public AdoptionRequest(){
        this.status = "Pending";
    }
    
    public void sendAdoptionRequest(String requestID, String userID, String petID, String date){
        this.requestID = requestID;
        this.userID = userID;
        this.petID = petID;
        this.date = date;
        this.status = "Pending";
    }
    
    public void updateStatus(String newStatus){
        this.status = newStatus; 
        // link petmanager qilah
    }
    
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
}
