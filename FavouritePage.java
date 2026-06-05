package com.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class FavouritePage extends Application {

    @Override
    public void start(Stage primaryStage) {
        
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
        
        // FavouritePage contents
        
        VBox content = new VBox(20);
        content.setPadding(new Insets(30));
        content.setAlignment(Pos.CENTER);
        content.setStyle(
        "-fx-background-color: #F9D1D9;" +
                "-fx-background-radius: 20;");
        
        Label lblTitle = new Label("My Favourite Pets");
        lblTitle.setStyle(
                "-fx-font-size: 30px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: white;");
        
        TableView<String> favTable = new TableView<>();
        favTable.setPrefHeight(400);
        
        TableColumn<String, String> petNameCol =
                new TableColumn<>("Pet Name");
        
        TableColumn<String, String> petBreedCol =
                new TableColumn<>("Breed");
        
        TableColumn<String, String> petAgeCol =
                new TableColumn<>("Age");
        
        favTable.getColumns().addAll(
                petNameCol, 
                petBreedCol,
                petAgeCol);
        
        Button removeBtn = new Button("Remove");
        removeBtn.setStyle(
        "-fx-background-color: #838F58;" +
                "-fx-text-fill: white;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 10;");
        
        removeBtn.setOnAction(e -> {
            System.out.println("Favourite removed.");
        });
        
        content.getChildren().addAll(
                lblTitle, 
                favTable,
                removeBtn);
        
        root.getChildren().addAll(navBar, content);
        
        Scene scene = new Scene(root, 1000, 700);
        primaryStage.setTitle("My Favourites");
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }
    
    private Button createNavButton(String text, String style){
        Button btn = new Button(text);
        btn.setStyle(style);
        return btn ;
    }
    
    public static void main(String[] args) {
        launch(args);
    }

}
