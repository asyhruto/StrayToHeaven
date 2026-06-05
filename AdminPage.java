package com.example;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class AdminPage extends Application {
    
    @Override
    public void start(Stage primaryStage){
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #838F58;");
        
        // nav bar 
        HBox navBar = new HBox();
        navBar.setAlignment(Pos.CENTER_LEFT);
        navBar.setPadding(new Insets(10,20,10,20));
        navBar.setStyle(
                "-fx-background-color: white;" +
                "-fx-border-color: #F9D1D9;" +
                "-fx-border-width: 2;" +
                "-fx-border-radius: 30;" +
                "-fx-background-radius: 30;");
        
        HBox linkBox = new HBox(25);
        String navBtnStyle = 
                "-fx-background-color: transparent;" +
                "-fx-text-fill: #333333;" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 14px;" +
                "-fx-cursor: hand;";
        
        // in navBar button(s)
        Button btnHome = createNavButton("Home", navBtnStyle);
        Button btnRehome = createNavButton("Rehome", navBtnStyle);
        Button btnAdopt = createNavButton("Adoption" , navBtnStyle);
        Button btnDonation = createNavButton("Donation", navBtnStyle);
        Button btnAbout = createNavButton("About", navBtnStyle);
        Button btnAdmin = createNavButton(
                        "Admin", 
                        "-fx-background-color: transparent;" +
                        "-fx-text-fill: #838F58;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 14px;" +
                        "-fx-underline: true;");
        
        linkBox.getChildren().addAll(
                btnHome, 
                btnRehome, 
                btnAdopt,
                btnDonation,
                btnAbout,
                btnAdmin);
        
        Region spacer = new Region();
       
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        TextField txtSearch = new TextField();
        txtSearch.setPromptText("🔍 Search...");
        txtSearch.setStyle(
                        "-fx-background-color: white;" +
                        "-fx-border-color: #F9D1D9;" +
                        "-fx-border-radius: 20;" +
                        "-fx-background-radius: 20;");
        
        Button btnProfile = new Button("👤");
        btnProfile.setStyle(
                        "-fx-background-color: #F9D1D9;" +
                        "-fx-text-fill: white;" +  
                        "-fx-background-radius: 50em;");
        
        HBox rightBox = new HBox(15);
        rightBox.getChildren().addAll(txtSearch,btnProfile);
        navBar.getChildren().addAll(linkBox, spacer, rightBox);
        
        // dashboard Admin content
        
        VBox content = new VBox(25);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(40));
        content.setStyle(
                    "-fx-background-color: #F9D1D9;" +
                    "-fx-background-radius: 20;");
        
        Label lblTitle = new Label("Admin Dashboard:");
        lblTitle.setStyle(
                "-fx-font-size: 28px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: white;");
        
        // Admin's options of buttons 
        
        Button manageUsersBtn = new Button("Manage Users");
        Button managePetsBtn = new Button("Manage Pets");
        Button manageReqBtn = new Button("Adoption Requests");
        Button manageDonationBtn = new Button("View Donations");
        
        String adminBtnStyle = 
                "-fx-background-color: #838F58;" +
                "-fx-text-fill: white;" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 16px;" +
                "-fx-background-radius: 10;" +
                "-fx-padding: 10 25;" + 
                "-fx-cursor: hand;";
        manageUsersBtn.setStyle(adminBtnStyle);
        managePetsBtn.setStyle(adminBtnStyle);
        manageReqBtn.setStyle(adminBtnStyle);
        manageDonationBtn.setStyle(adminBtnStyle);
        
        // actions of these buttons 
        
        manageUsersBtn.setOnAction(e -> 
                openPage("Manage Users"));
        
        managePetsBtn.setOnAction(e -> 
                openPage("Manage Pets"));
        
        manageReqBtn.setOnAction(e -> 
                openPage("Manage Requests"));
        
        manageDonationBtn.setOnAction(e -> 
                openPage("View Donations"));
        
        content.getChildren().addAll(
                lblTitle, 
                manageUsersBtn,
                manageReqBtn,
                manageDonationBtn);
        
        root.getChildren().addAll(
                navBar,
                content);
        
        Scene scene = new Scene(root, 1000, 700);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Strays to Heaven - Admin Dashboard");
        primaryStage.show();
        
   
    }
    
    private Button createNavButton(String text, String style){
        Button btn = new Button(text);
        btn.setStyle(style);
        return btn;
    }
    
     private void openPage(String title) {

        Stage stage = new Stage();

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));

        layout.setStyle("-fx-background-color: #F9D1D9;");

        Label label = new Label(title);

        label.setStyle(
                "-fx-font-size: 25px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #838F58;"
        );

        Button closeBtn = new Button("Close");

        closeBtn.setStyle(
                "-fx-background-color: #838F58;" +
                "-fx-text-fill: white;" +
                "-fx-font-weight: bold;"
        );

        closeBtn.setOnAction(e -> stage.close());

        layout.getChildren().addAll(label, closeBtn);

        Scene scene = new Scene(layout, 500, 300);

        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}