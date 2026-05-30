package com.example;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * JavaFX App - Single Animal Profile Card Dashboard
 */
public class PetManagerGUI extends Application {

    // Core data management and state tracking variables
    private PetManager petmanager = new PetManager();
    private ObservableList<Pet> data = FXCollections.observableArrayList();
    private int currentPetIndex = 0; // Tracks which animal is currently being displayed

    // UI components for the profile card
    private Label idLabel = new Label();
    private Label breedLabel = new Label();
    private Label ageLabel = new Label();
    private Label genderLabel = new Label();
    private Label traitsLabel = new Label();
    private Label statusLabel = new Label();

    // Container for the profile card elements
    private HBox profileCard = new HBox(30);

    // ImageView for displaying the pet's picture, will be updated dynamically based on the current profile
    private ImageView petImageView = new ImageView();

    @Override
    public void start(Stage stage) {

        stage.setTitle("Strays To Heaven - Pet Management Dashboard");

        // --- HEADER BAR SYSTEM ---
        HBox headerBar = new HBox(20);
        headerBar.setPadding(new Insets(15, 15, 15, 15));
        headerBar.setAlignment(javafx.geometry.Pos.CENTER);
        headerBar.getStyleClass().add("header-bar");

        // Create a layout container for the logo
        HBox logoContainer = new HBox();
        logoContainer.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

        try {
            // Tells Java to look directly inside the compiled source folder for the logo asset
            java.net.URL logoUrl = getClass().getResource("STHLogo.jpg");
            
            // If the logo file is found, create an ImageView and add it to the header
            if (logoUrl != null) {
                Image logoImg = new Image(logoUrl.toExternalForm());
                ImageView logoView = new ImageView(logoImg);
                
                // Keep the layout proportional and neat within your white header line
                logoView.setFitHeight(40); // Matches standard aesthetic header height
                logoView.setPreserveRatio(true);
                
                logoContainer.getChildren().add(logoView);

                // Debugging output to confirm the logo was loaded successfully
            } else {
                System.err.println("Could not find STHLogo.jpg inside the src package folder!");
                // Fallback text if the file is physically missing
                Label fallbackLabel = new Label("✨ STH");
                fallbackLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #838F58; -fx-font-size: 16px;");
                logoContainer.getChildren().add(fallbackLabel);
            }
        } catch (Exception e) {
            System.err.println("Error rendering logo graphic: " + e.getMessage());
        }

        // --- CENTER NAVIGATION MENU ---
        HBox centerMenu = new HBox(15);
        centerMenu.setAlignment(Pos.CENTER);
        
        // Create navigation buttons with consistent styling
        Button navHome = new Button("Home");
        Button navRehome = new Button("Rehome");
        Button navAdopt = new Button("Adopt");
        Button navDonation = new Button("Donation");
        Button navAbout = new Button("About");

        // Apply a common CSS class to all navigation buttons for uniform styling
        navHome.getStyleClass().add("nav-button");
        navRehome.getStyleClass().add("nav-button");
        navAdopt.getStyleClass().add("nav-button");
        navDonation.getStyleClass().add("nav-button");
        navAbout.getStyleClass().add("nav-button");

        centerMenu.getChildren().addAll(navHome, navRehome, navAdopt, navDonation, navAbout);

        // --- RIGHT-SIDE CONTROLS (SEARCH + PROFILE) ---
        Region spacer1 = new Region();
        HBox.setHgrow(spacer1, Priority.ALWAYS);
        Region spacer2 = new Region();
        HBox.setHgrow(spacer2, Priority.ALWAYS);

        HBox rightControls = new HBox(12);
        rightControls.setAlignment(Pos.CENTER_RIGHT);

        TextField searchField = new TextField();
        searchField.setPromptText("🔍 Search...");
        searchField.getStyleClass().add("search-field");
        searchField.setPrefWidth(120);

        Button profileButton = new Button("👤");
        profileButton.getStyleClass().add("profile-button");
        
        rightControls.getChildren().addAll(searchField, profileButton);
        headerBar.getChildren().addAll(logoContainer, spacer1, centerMenu, spacer2, rightControls);

        // --- PRELOADED DUMMY DATA ---
        Pet pet1 = new Pet("C001", 2, "British Shorthair", "Female", "Calm, Friendly", "Available");
        Pet pet2 = new Pet("D001", 1, "Golden Retriever", "Male", "Playful, Energetic", "Available");
        Pet pet3 = new Pet("C002", 4, "Persian", "Male", "Lazy, Fluffy", "Available");

        petmanager.addPet(pet1);
        petmanager.addPet(pet2);
        petmanager.addPet(pet3);

        data.addAll(pet1, pet2, pet3);

        // --- SIDE-BY-SIDE PROFILE CARD CONTAINER ---
        profileCard.getStyleClass().add("pet-profile-card");
        profileCard.setAlignment(Pos.CENTER); 
        profileCard.setPadding(new Insets(25)); 
        VBox.setVgrow(profileCard, Priority.ALWAYS);

        // Configure the pet picture properties
        petImageView.setFitWidth(280);  // Set a fixed perfect width for the picture
        petImageView.setFitHeight(320); // Set a fixed perfect height
        petImageView.setPreserveRatio(true); // Keeps the cat/dog from looking squished

        // Text container layout side (Right side)
        VBox textContainer = new VBox(12);
        textContainer.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(textContainer, Priority.ALWAYS);

        // Style the text elements with a consistent and visually appealing design
        idLabel.setStyle("-fx-font-size: 26px; -fx-font-weight: bold; -fx-text-fill: #838f58;");
        breedLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #4A5568;");
        ageLabel.setStyle("-fx-font-size: 15px; -fx-text-fill: #718096; -fx-font-weight: bold;");
        genderLabel.setStyle("-fx-font-size: 15px; -fx-text-fill: #718096; -fx-font-weight: bold;");
        traitsLabel.setStyle("-fx-font-size: 16px; -fx-font-style: italic; -fx-text-fill: #2D3748;");
        statusLabel.setStyle("-fx-background-color: #F9D1D9; -fx-text-fill: #838F58; -fx-padding: 6px 16px; -fx-background-radius: 10px; -fx-font-weight: bold;");

        // Assemble text elements together
        textContainer.getChildren().addAll(idLabel, breedLabel, ageLabel, genderLabel, traitsLabel, statusLabel);
        
        // Assemble Picture (Left) and Text Container (Right) inside the card
        profileCard.getChildren().addAll(petImageView, textContainer);
        // Update the screen with the first pet details initially
        updateProfileCardDisplay();

        // --- INTERACTIVE NAVIGATION CONTROLS ---
        Button prevButton = new Button("◀ Previous");
        Button nextButton = new Button("Next ▶");
        prevButton.getStyleClass().add("nav-arrow-button");
        nextButton.getStyleClass().add("nav-arrow-button");

        // Event handlers for pagination buttons with smooth fade transition animations
        prevButton.setOnAction(e -> {
            if (!data.isEmpty()) {
                currentPetIndex = (currentPetIndex - 1 + data.size()) % data.size();
                animateAndChangeProfile();
            }
        });

        // The next button cycles forward through the pet list, wrapping around to the start when reaching the end
        nextButton.setOnAction(e -> {
            if (!data.isEmpty()) {
                currentPetIndex = (currentPetIndex + 1) % data.size();
                animateAndChangeProfile();
            }
        });

        // Button to open the Add Pet form, styled consistently with the rest of the UI
        Button openAddFormButton = new Button("➕ New Animal");
        openAddFormButton.getStyleClass().add("add-button");
        openAddFormButton.setOnAction(e -> openAddPetFormPage(stage));

        // Layout to arrange the pagination and registration elements cleanly
        HBox controlRow = new HBox(20, prevButton, openAddFormButton, nextButton);
        controlRow.setAlignment(Pos.CENTER);

        // --- NAVIGATION WORKFLOW SYSTEM ---
        navRehome.setOnAction(e -> System.out.println("Transferring to Nur Arisa Balqis's Rehome Page..."));
        navDonation.setOnAction(e -> System.out.println("Transferring to Donation Page..."));
        navAdopt.setOnAction(e -> System.out.println("Transferring to Farhana's Adoption Request Page..."));
        profileButton.setOnAction(e -> System.out.println("Opening Profile / Logout Menu..."));

        // --- MAIN ASSEMBLY ---
        VBox root = new VBox(25, headerBar, profileCard, controlRow);
        root.setPadding(new Insets(25));
        root.getStyleClass().add("root-container");

        Scene scene = new Scene(root, 850, 550);
        
        // Load the external CSS file for styling the application
        java.net.URL cssUrl = getClass().getResource("style.css");
        if (cssUrl != null) {
            scene.getStylesheets().add(cssUrl.toExternalForm());
        }
        
        // Finalize and display the main application window
        stage.setScene(scene);
        stage.show();
    }

    // Changes text and triggers a smooth fade-in animation on the profile surface
    private void animateAndChangeProfile() {
        FadeTransition fadeOut = new FadeTransition(Duration.millis(200), profileCard);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.1);
        
        // Once the fade-out animation completes, update the profile information and fade back in
        fadeOut.setOnFinished(event -> {
            updateProfileCardDisplay();
            FadeTransition fadeIn = new FadeTransition(Duration.millis(400), profileCard);
            fadeIn.setFromValue(0.1);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        });
        
        fadeOut.play();
    }

    // Updates the profile card display with the current pet's information, including a hierarchical image loading system
    private void updateProfileCardDisplay() {
        if (data.isEmpty()) {
            idLabel.setText("No Stray Registered");
            breedLabel.setText("The shelter is currently empty.");
            ageLabel.setText("");
            genderLabel.setText("");
            traitsLabel.setText("");
            statusLabel.setVisible(false);
            petImageView.setImage(null);
        } else {
            Pet pet = data.get(currentPetIndex);
            idLabel.setText("ID: " + pet.getPetID());
            breedLabel.setText(pet.getPetBreed());
            ageLabel.setText("Age: " + pet.getPetAge() + " Years Old");
            genderLabel.setText("Gender: " + pet.getPetGender());
            traitsLabel.setText("Personality: \"" + pet.getPetTraits() + "\"");
            statusLabel.setText(pet.getPetStatus());
            statusLabel.setVisible(true);

            // Hierarchical image loading system:
            // First checks for specific hardcoded images based on known pet IDs, then falls back to a dynamic naming convention, and finally handles missing assets gracefully
            String imageName = "cat1.jpg"; // Default fallback
            if (pet.getPetID().equalsIgnoreCase("C001")) imageName = "cat1.jpg";
            else if (pet.getPetID().equalsIgnoreCase("D001")) imageName = "dog1.jpg";
            else if (pet.getPetID().equalsIgnoreCase("C002")) imageName = "cat2.jpg";
            else {
                // Tries to find an image matching the new pet's ID (e.g., c003.jpg)
                imageName = pet.getPetID().toLowerCase() + ".jpg"; 
            }

            try {
                java.net.URL imgUrl = getClass().getResource(imageName);
                if (imgUrl != null) {
                    petImageView.setImage(new Image(imgUrl.toExternalForm()));
                } else {
                    petImageView.setImage(null); 
                    System.out.println("Image asset not found: " + imageName);
                }
            } catch (Exception e) {
                petImageView.setImage(null);
            }
            }
        }

    // --- SECONDARY UI FORM POP-UP ---
    private void openAddPetFormPage(Stage parentStage) {
        Stage formStage = new Stage();
        formStage.setTitle("Register New Stray Animal");
        formStage.initModality(Modality.WINDOW_MODAL);
        formStage.initOwner(parentStage);

        // Form fields with consistent styling and clear prompts for user input
        Label titleLabel = new Label("ANIMAL REGISTRATION FORM");
        titleLabel.setMaxWidth(Double.MAX_VALUE);
        titleLabel.setAlignment(Pos.CENTER);
        titleLabel.setStyle("-fx-alignment: center; -fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #EAECE6;");

        // Input fields for pet details, styled with CSS classes for a cohesive look
        TextField idInput = new TextField();
        idInput.setPromptText("Pet ID (e.g., C001)");
        idInput.getStyleClass().add("input-field");

        // Age input field with validation for numeric input, styled consistently with other fields
        TextField ageInput = new TextField();
        ageInput.setPromptText("Pet Age (e.g., 3)");
        ageInput.getStyleClass().add("input-field");

        // Breed input field for the pet's breed, styled with the same CSS class for uniform
        TextField breedInput = new TextField();
        breedInput.setPromptText("Pet Breed (e.g., Shorthair)");
        breedInput.getStyleClass().add("input-field");

        // Gender
        ComboBox<String> genderInput = new ComboBox<>();
        genderInput.getItems().addAll("Male", "Female");
        genderInput.setPromptText("Select Gender");
        genderInput.setMaxWidth(Double.MAX_VALUE); 
        genderInput.getStyleClass().add("combobox");

        // Traits input field for the pet's personality traits, styled consistently with other input fields
        TextField traitsInput = new TextField();
        traitsInput.setPromptText("Pet Traits (e.g., Playful)");
        traitsInput.getStyleClass().add("input-field");

        // Save button to submit the form, styled to match the application's design language
        Button saveButton = new Button("Save Record");
        saveButton.getStyleClass().add("add-button");
        saveButton.setMaxWidth(Double.MAX_VALUE);

        // Event handler for the save button to create a new pet record and update the main profile display
        saveButton.setOnAction(e -> {
            try {
                Pet newPet = new Pet(
                        idInput.getText(), 
                        Integer.parseInt(ageInput.getText()), 
                        breedInput.getText(),
                        genderInput.getValue() != null ? genderInput.getValue() : "",
                        traitsInput.getText(),
                        "Available"
                );

                petmanager.addPet(newPet);
                data.add(newPet);
                
                // Set index to point directly to the newly added animal profile
                currentPetIndex = data.size() - 1;
                updateProfileCardDisplay();
                
                formStage.close();
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input for age.");
            }
        });

        // Layout for the form, using a VBox to stack elements vertically with consistent spacing and padding
        VBox formRoot = new VBox(15, titleLabel, idInput, ageInput, breedInput, genderInput, traitsInput, saveButton);
        formRoot.setPadding(new Insets(30));
        formRoot.getStyleClass().add("root-container");

        // Create the scene for the form and apply the same CSS styling as the main application for a cohesive user experience
        Scene formScene = new Scene(formRoot, 350, 360);
        
        // Load the external CSS file for styling the form, ensuring it matches the main application's design
        java.net.URL cssUrl = getClass().getResource("style.css");
        if (cssUrl != null) {
            formScene.getStylesheets().add(cssUrl.toExternalForm());
        }
        
        // Finalize and display the form as a modal window
        formStage.setScene(formScene);
        formStage.showAndWait();
    }

    // Main method to launch the JavaFX application
    public static void main(String[] args) {
        launch(args);
    }
}
