package com.example;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class FavouritePage extends Application {

    private String userID;
    private String petID;

    // ObservableList to hold the favourite pets for display in the TableView
    private ObservableList<Pet> favList = FXCollections.observableArrayList();

    // Internal list to manage the favourite pets (not directly used for TableView but can be useful for logic)
    private ArrayList<FavouritePage> internalFavList = new ArrayList<>();
    private FileHandler fileHandler = new FileHandler();

    public FavouritePage(String userID, String petID) {
        // Initialize the favourite page with user and pet information
        this.userID = userID;
        this.petID = petID;
    }

    public void start(Stage stage, UI mainApp) {

        // Load existing favourites from the file to ensure we have the latest data
        internalFavList = fileHandler.loadFav();
        HBox navBar = Navigation.NavBar(stage, "Favourites", mainApp);
        
        // Check if the current pet is already in the favourites
        if (this.petID != null && this.userID != null) {
            boolean isExist = false;
            for (FavouritePage f : internalFavList) {
                if (f.getUserID().equalsIgnoreCase(this.userID) && f.getPetID().equalsIgnoreCase(this.petID)) {
                    isExist = true;
                    break;
                }
            }
            if (!isExist) {
                internalFavList.add(new FavouritePage(this.userID, this.petID));
                // save the updated favourites list to the file after adding a new favourite
                fileHandler.saveFav(internalFavList);
            }
        }

        // populate favList with Pet objects corresponding only to the current user's favourite records
        favList.clear();
        for (FavouritePage favRecord : internalFavList) {
            if (favRecord.getUserID() == null || !favRecord.getUserID().equalsIgnoreCase(this.userID)) {
                continue;
            }
            for (Pet pet : PetManager.getAllPets()) {
                if (pet.getPetID().equalsIgnoreCase(favRecord.getPetID())) {
                    favList.add(pet);
                    break;
                }
            }
        }
        
        // main container with background styling
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #838F58;");

        // FavouritePage contents
        VBox content = new VBox(20);
        content.setPadding(new Insets(30));
        content.setAlignment(Pos.CENTER);
        content.setStyle(
        "-fx-background-color: #F9D1D9;" +
                "-fx-background-radius: 20;");
        
        // Title label with enhanced styling
        Label lblTitle = new Label("My Favourite Pets");
        lblTitle.setStyle(
                "-fx-font-size: 30px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #838F58;");
        
        // TableView for displaying favourite pets
        TableView<Pet> favTable = new TableView<>();
        favTable.setPrefHeight(400);
        
        // Define columns for the TableView and bind them to the Pet properties
        TableColumn<Pet, String> petIDCol = new TableColumn<>("Pet ID");
        petIDCol.setCellValueFactory(new PropertyValueFactory<>("petID"));
        
        TableColumn<Pet, String> petBreedCol = new TableColumn<>("Breed");
        petBreedCol.setCellValueFactory(new PropertyValueFactory<>("petBreed"));
        
        TableColumn<Pet, Integer> petAgeCol = new TableColumn<>("Age");
        petAgeCol.setCellValueFactory(new PropertyValueFactory<>("petAge"));
        
        TableColumn<Pet, String> petGenderCol = new TableColumn<>("Gender");
        petGenderCol.setCellValueFactory(new PropertyValueFactory<>("petGender"));

        // Add columns to the TableView
        favTable.getColumns().addAll(petIDCol, petBreedCol, petAgeCol, petGenderCol);
        
        // Set the items for the TableView using the favList ObservableList
        favTable.setItems(favList);
        
        // Remove button to delete a pet from the favourites list
        Button removeBtn = new Button("Remove");
        removeBtn.setStyle(
        "-fx-background-color: #838F58;" +
                "-fx-text-fill: white;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 10;");
        
        // Event handler for the Remove button to delete the selected favourite pet from the list and update the file
        removeBtn.setOnAction(e -> {
            Pet selectedPet = favTable.getSelectionModel().getSelectedItem();
            if (selectedPet != null) {
                // Remove from UI list
                favList.remove(selectedPet);

                // Remove only this user's favourite entry for that pet
                internalFavList.removeIf(f -> f.getUserID().equalsIgnoreCase(this.userID)
                        && f.getPetID().equalsIgnoreCase(selectedPet.getPetID()));

                // Save the updated favourites list to the file after removal
                fileHandler.saveFav(internalFavList);

                System.out.println("Favourite removed and saved: " + selectedPet.getPetID());
            } else {
                System.out.println("No pet selected to remove.");
            }
        });
        
        content.getChildren().addAll(
                lblTitle, 
                favTable,
                removeBtn);
        
        root.getChildren().addAll(navBar, content);
        
        Scene scene = new Scene(root, 1000, 700);
        stage.setTitle("My Favourites");
        stage.setScene(scene);
        stage.show();
        
    }
    
    // Helper method to create navigation buttons with consistent styling
    private Button createNavButton(String text, String style){
        Button btn = new Button(text);
        btn.setStyle(style);
        return btn ;
    }
    
    public static void main(String[] args) {
        launch(args);
    }

    // Getter for userID to allow external access if needed
    public String getUserID() {
        return this.userID;
    }

    // Getter for petID to allow external access if needed
    public String getPetID() {
        return this.petID;
    }

    @Override
    public void start(Stage arg0) throws Exception {
        // This method is required to satisfy the Application class contract, but the actual application logic is handled in the overloaded start method that accepts a UI instance. This allows for better integration with the overall application structure and navigation system.
        start(arg0, null);
    }

}
