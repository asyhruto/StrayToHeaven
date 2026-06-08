/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

//cara nak call navigation
// HBox navBar = Navigation.NavBar(window, "Adopt/Rehome/Donation..", this);
package com.mycompany.StrayToHeaven;

/**
 *
 * @author user
 */

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class Navigation {
    // Color constants
    private static final String MATCHA_BG = "-fx-background-color: #838F58;";
    private static final String PINK = "-fx-background-color: #F9D1D9; -fx-text-fill: #333333; -fx-font-weight: bold;";
    private static final String MATCHA = "-fx-background-color: #838F58; -fx-text-fill: white; -fx-font-weight: bold;";
    
    // Logo file path
    private static final String LOGO = "images/STHLogo.jpg";
    
    // Method to create a navigation bar with active menu highlighting, text hover animations, box shadows, and an profile button hover switch.
    public static HBox NavBar(Stage window, String activeMenu, UI mainApp){
        HBox navBar = new HBox(20);
        navBar.setPadding(new Insets(15, 25, 15, 25));
        navBar.setAlignment(Pos.CENTER_LEFT);
        
        // Navigation bar box styling including a clean border radius and drop shadow layout effect
        navBar.setStyle("-fx-background-color: white; -fx-background-radius: 30; -fx-border-radius: 30; -fx-border-color: #F9D1D9; -fx-border-width: 2; " +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 5);");
        HBox.setMargin(navBar, new Insets(20, 20, 0, 20));
        
        // Load and set the logo image configuration details
        ImageView logoView = new ImageView();
        try {
            java.net.URL logoUrl = Navigation.class.getResource(LOGO);
            if (logoUrl != null) {
                Image logoImg = new Image(logoUrl.toExternalForm());
                logoView.setImage(logoImg);
                logoView.setFitHeight(40); // Fixed size boundary constraint
                logoView.setPreserveRatio(true); 
            } else {
                System.err.println("File image " + LOGO + " not found in the resources folder!");
            }
        } catch (Exception e) {
            System.err.println("Error loading logo: " + e.getMessage());
        }

        Button btHome = new Button("Home");
        Button btRehome = new Button("Rehome");
        Button btAdopt = new Button("Adopt");
        Button btDonate = new Button("Donation");
        Button btLogout = new Button("Logout");
        
        String navLinkStyle = "-fx-background-color: transparent; -fx-text-fill: #4A4A4A; -fx-font-weight: bold; -fx-font-size: 13px; -fx-cursor: hand;";
        String activeStyle = "-fx-background-color: transparent; -fx-text-fill: #838F58; -fx-font-weight: bold; -fx-font-size: 13px; -fx-underline: true; -fx-cursor: hand;";
        
        Button[] menu = {btHome, btAdopt, btRehome, btDonate, btLogout};
        for (Button btn : menu){
            btn.setStyle(navLinkStyle);
            
            // Check state conditions to identify whether the given button element is currently flagged as active
            boolean isActive = (btn == btHome && activeMenu.equalsIgnoreCase("Home")) ||
                               (btn == btRehome && activeMenu.equalsIgnoreCase("Rehome")) ||
                               (btn == btAdopt && activeMenu.equalsIgnoreCase("Adopt")) ||
                               (btn == btDonate && activeMenu.equalsIgnoreCase("Donation")) ||
                               (btn == btLogout && !activeMenu.equalsIgnoreCase("Home") && 
                                                   !activeMenu.equalsIgnoreCase("Rehome") && 
                                                   !activeMenu.equalsIgnoreCase("Adopt") && 
                                                   !activeMenu.equalsIgnoreCase("Donation"));
            
            // Only attach programmatic mouse hover interactions to non-active buttons
            if (!isActive) {
                btn.setOnMouseEntered(e -> {
                    btn.setStyle("-fx-background-color: transparent; -fx-text-fill: #838F58; -fx-font-weight: bold; -fx-font-size: 13px; -fx-cursor: hand; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 5);");
                });
                btn.setOnMouseExited(e -> {
                    btn.setStyle(navLinkStyle);
                });
            }
        }
        
        // Render permanent target color styles depending on the validated menu parameters
        if (activeMenu.equalsIgnoreCase("Home")) {
            btHome.setStyle(activeStyle);
        }
        else if (activeMenu.equalsIgnoreCase("Rehome")) {
            btRehome.setStyle(activeStyle);
        }
        else if (activeMenu.equalsIgnoreCase("Adopt")) {
            btAdopt.setStyle(activeStyle);
        }
        else if (activeMenu.equalsIgnoreCase("Donation")) {
            btDonate.setStyle(activeStyle);
        }
        else {
            btLogout.setStyle(activeStyle);
        }
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        HBox rightNav = new HBox(12);
        rightNav.setAlignment(Pos.CENTER_RIGHT);
        
        TextField search = new TextField();
        search.setPromptText("🔍 Search...");
        search.setPrefWidth(150);
        search.setStyle("-fx-background-color: #f7fafc; -fx-background-color: #f7fafc; -fx-border-color: #F1D1D9; -fx-border-radius: 15px; -fx-background-radius: 15px; -fx-padding: 5px 12px; -fx-font-size: 12px; -fx-pref-width: 100px;");

        Button profile = new Button("👤");
        String profileStyle = "-fx-background-color: #F9D1D9; -fx-text-fill: #333333; -fx-background-radius: 50em; -fx-min-width: 32px; -fx-min-height: 32px; -fx-max-width: 32px; -fx-max-height: 32px; -fx-cursor: hand;";
        profile.setStyle(profileStyle);
        
        // Hover effects handling logic configuration for the profile menu container button
        profile.setOnMouseEntered(e -> {
            profile.setStyle("-fx-background-color: #838F58; -fx-text-fill: #FBFBF7; -fx-background-radius: 50em; -fx-min-width: 32px; -fx-min-height: 32px; -fx-max-width: 32px; -fx-max-height: 32px; -fx-cursor: hand;");
        });
        profile.setOnMouseExited(e -> {
            profile.setStyle(profileStyle);
        });
        
        rightNav.getChildren().addAll(search, profile);
        navBar.getChildren().addAll(logoView, btHome, btRehome, btAdopt, btDonate, btLogout, spacer, rightNav);
        
        // Action routing rules to process structural interface state switches
        btHome.setOnAction((ActionEvent e) -> {
            System.out.println("Navigating to Home Screen...");
            if (mainApp != null) mainApp.DashboardUI();
            else System.out.println("Error: Cannot navigate to Home Screen.");
        });
        
        btRehome.setOnAction((ActionEvent e) -> {
            System.out.println("Navigating to Rehome Screen...");
            if (mainApp != null) {   
                RehomeScreen rehomePage = new RehomeScreen();
                rehomePage.start(window, mainApp);
            }
             else {
                System.out.println("Error: Cannot navigate to Rehome Screen.");
            }
        });
        
        btAdopt.setOnAction((ActionEvent e) -> {
            System.out.println("Navigating to Adoption Screen...");
            if (mainApp != null) { 
                mainApp.AdoptionUI();
            }
            else {
                System.out.println("Error: Cannot navigate to Adoption Screen.");
            }
        });
        
        btDonate.setOnAction((ActionEvent e) -> {
            System.out.println("Navigating to Donation Screen...");
            if (mainApp != null) { 
                 DonationScreen donationPage = new DonationScreen();
                donationPage.start(window, mainApp);
            }
            else {
                System.out.println("Error: Cannot navigate to Donation Screen.");
            }
        });
        
        btLogout.setOnAction((ActionEvent e) -> {
            if (mainApp != null) { 
                mainApp.LoginUI();
                System.out.println("Logging out and returning to Login Screen...");
            }
        });
        return navBar;
    }
}
