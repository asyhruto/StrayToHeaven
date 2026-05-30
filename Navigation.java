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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class Navigation {
    //coloring
    private static final String MATCHA_BG = "-fx-background-color: #838F58;";
    private static final String PINK = "-fx-background-color: #F9D1D9; -fx-text-fill: #333333; -fx-font-weight: bold;";
    private static final String MATCHA = "-fx-background-color: #838F58; -fx-text-fill: white; -fx-font-weight: bold;";
    
    //logo
    private static final String LOGO = "file:C:\\Users\\user\\Pictures\\logoStrayToHeaven.jpg";
    
    public static HBox NavBar(Stage window, String activeMenu, UI mainApp){
        HBox navBar = new HBox(20);
        navBar.setPadding(new Insets(15, 25, 15, 25));
        navBar.setAlignment(Pos.CENTER_LEFT);
        navBar.setStyle("-fx-background-color: white; -fx-background-radius: 30; -fx-border-radius: 30; -fx-border-color: #F9D1D9; -fx-border-width: 2;");
        HBox.setMargin(navBar, new Insets(20, 20, 0, 20));
        
        Image logoImg = new Image(LOGO);
        ImageView logoView = new ImageView(logoImg);
        logoView.setFitHeight(32);
        logoView.setPreserveRatio(true);
        navBar.getChildren().add(logoView);

        Button btHome = new Button("Home");
        Button btRehome = new Button("Rehome");
        Button btAdopt = new Button("Adopt");
        Button btDonate = new Button("Donation");
        Button btAbout = new Button("About");
        Button btLogout = new Button("Logout");
        
        String navLinkStyle = "-fx-background-color: transparent; -fx-text-fill: #4A4A4A; -fx-font-weight: bold; -fx-font-size: 13px; -fx-cursor: hand;";
        Button[] menu = {btHome, btAdopt, btRehome, btDonate, btAbout, btLogout};
        for (Button btn : menu){
            btn.setStyle(navLinkStyle);
        }
        
        String activeStyle = "-fx-background-color: transparent; -fx-text-fill: #838F58; -fx-font-weight: bold; -fx-font-size: 13px; -fx-underline: true; -fx-cursor: hand;";
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
        else if (activeMenu.equalsIgnoreCase("About")) {
            btAbout.setStyle(activeStyle);
        }
        else {
            btLogout.setStyle(activeStyle);
        }
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        HBox rightNav = new HBox(12);
        rightNav.setAlignment(Pos.CENTER_RIGHT);
        
        Button search = new Button("🔍");
        search.setStyle("-fx-background-color: transparent; -fx-text-fill: #838F58; -fx-font-size: 14px; -fx-cursor: hand;");
        
        Button profile = new Button("👤");
        profile.setStyle("-fx-background-color: #F9D1D9; -fx-text-fill: #333333; -fx-background-radius: 50em; -fx-min-width: 32px; -fx-min-height: 32px; -fx-max-width: 32px; -fx-max-height: 32px; -fx-cursor: hand;");
        
        rightNav.getChildren().addAll(search, profile);
        navBar.getChildren().addAll(btHome, btRehome, btAdopt, btDonate, btAbout, btLogout, spacer, rightNav);
        
        btHome.setOnAction((ActionEvent e) -> {
            if (mainApp != null) mainApp.DashboardUI();
        });
        
        btRehome.setOnAction((ActionEvent e) -> {
            RehomeScreen rehomePage = new RehomeScreen();
            rehomePage.start(window);
        });
        
        btAdopt.setOnAction((ActionEvent e) -> {
            if (mainApp != null) mainApp.AdoptionUI();
        });
        
        btDonate.setOnAction((ActionEvent e) -> {
            DonationScreen donationPage = new DonationScreen();
            donationPage.start(window);
        });
        
        btLogout.setOnAction((ActionEvent e) -> {
            if (mainApp != null) mainApp.LoginUI();
        });
        return navBar;
    }
    
}
