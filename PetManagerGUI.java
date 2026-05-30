package com.example;

import java.io.File;
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

    // ImageView for displaying the pet's picture on the profile card
    private ImageView petImageView = new ImageView();

    // Variable to store the file path of the uploaded pet image, allowing it to be associated with the pet's profile
    private String savedImagePath;

    
    public void start(Stage stage, UI mainApp) {

        stage.setTitle("Strays To Heaven - Pet Management Dashboard");

        HBox headerBar = Navigation.NavBar(stage, "Home", mainApp);

        // --- PRELOADED DUMMY DATA ---
        Pet pet1 = new Pet("C001", 2, "British Shorthair", "Female", "Calm, Friendly", "Available");
        Pet pet2 = new Pet("D001", 1, "Golden Retriever", "Male", "Playful, Energetic", "Available");
        Pet pet3 = new Pet("C002", 4, "Persian", "Male", "Lazy, Fluffy", "Available");

        PetManager.addPet(pet1);
        PetManager.addPet(pet2);
        PetManager.addPet(pet3);

        data.clear();
        data.addAll(PetManager.getAllPets());

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
        
        // --- MAIN ASSEMBLY ---
        // The main content area is structured with the header at the top, the profile card in the center, and the control buttons at the bottom, all wrapped in a ScrollPane to ensure accessibility on smaller screens or when content exceeds the fixed size of the window
        VBox mainContent = new VBox(25, headerBar, profileCard, controlRow);
        mainContent.setPadding(new Insets(10, 25, 25, 25));
        mainContent.getStyleClass().add("root-container");

        // Wrap the main content in a ScrollPane to ensure accessibility on smaller screens or when content exceeds the fixed size of the window
        ScrollPane mainScrollPane = new ScrollPane(mainContent);
        mainScrollPane.setFitToWidth(true); // Allows the content to expand horizontally to fill the available width of the ScrollPane, preventing horizontal scrolling and ensuring a cleaner user experience
        mainScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        mainScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        mainScrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent;");

        // The root container for the entire application, which allows the main content to utilize the full height of the window and provides a more immersive experience when viewing pet profiles. This also helps maintain a consistent layout as the window is resized.
        VBox root = new VBox(10, mainScrollPane);
        VBox.setVgrow(mainScrollPane, Priority.ALWAYS); // Ensures the ScrollPane expands to fill available vertical space, allowing the main content to utilize the full height of the window and providing a more immersive experience when viewing pet profiles. This also helps maintain a consistent layout as the window is resized.
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
            String imageName = "images/cat1.jpg"; // Default fallback
            if (pet.getPetID().equalsIgnoreCase("C001")) imageName = "images/cat1.jpg";
            else if (pet.getPetID().equalsIgnoreCase("D001")) imageName = "images/dog1.jpg";
            else if (pet.getPetID().equalsIgnoreCase("C002")) imageName = "images/cat2.jpg";
            else {
                // Tries to find an image matching the new pet's ID (e.g., c003.jpg)
                imageName = "images/" + pet.getPetID().toLowerCase() + ".jpg"; 
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
        titleLabel.getStyleClass().add("form-title");

        // upload pet picture
        ImageView petImageView = new ImageView();
        petImageView.setFitWidth(150); petImageView.setFitHeight(150);
        petImageView.setPreserveRatio(true);
        
        // The image box is styled to match the profile card for a cohesive look, and includes a button to allow users to upload a picture of the pet they are registering
        StackPane imageBox = new StackPane(petImageView);
        imageBox.setPrefSize(160, 160); imageBox.setMaxSize(160, 160);
        imageBox.getStyleClass().add("pet-profile-card"); // Reuse the same styling as the profile card for a cohesive look
        
        // Button to trigger the file chooser for uploading a pet picture, styled to match the application's design language
        Button btnUpload = new Button("Add Picture");
        btnUpload.getStyleClass().add("add-button");
        btnUpload.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
            File file = fileChooser.showOpenDialog(formStage);
            if (file != null) {
                savedImagePath = file.getAbsolutePath();
                petImageView.setImage(new Image(file.toURI().toString()));
            }
        });
        // Layout for the image upload section, centering the image preview and upload button together
        VBox imageSection = new VBox(10, imageBox, btnUpload);
        imageSection.setAlignment(Pos.CENTER);

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

                PetManager.addPet(newPet);
                data.add(newPet);
                
                // Set index to point directly to the newly added animal profile
                currentPetIndex = data.size() - 1;
                updateProfileCardDisplay();
                
                formStage.close();
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input for age.");
            }
        });

        // Layout for the form content, using a VBox to stack elements vertically with consistent spacing and padding
        VBox formContent = new VBox(15, titleLabel, imageSection, idInput, ageInput, breedInput, genderInput, traitsInput, saveButton);
        formContent.setPadding(new Insets(20));
        formContent.getStyleClass().add("root-container");

        // Wrap the form content in a ScrollPane to ensure accessibility on smaller screens or when content exceeds the fixed size of the form window
        ScrollPane formScrollPane = new ScrollPane(formContent);
        formScrollPane.setFitToWidth(true);
        formScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        formScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        formScrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent;");

        // Set a fixed size for the form pop-up, as the ScrollPane will handle any overflow of content gracefully
        Scene formScene = new Scene(formScrollPane, 380, 450); // Set a fixed size for the form pop-up, as the ScrollPane will handle any overflow of content gracefully
        
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

    @Override
    public void start(Stage arg0) throws Exception {
        // This method is required to satisfy the Application class contract, but the actual application logic is handled in the overloaded start method that accepts a UI instance. This allows for better integration with the overall application structure and navigation system.
        start(arg0, null);
    }
}
