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

    private String currentUserID;

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
    private UI mainApp;

    // PetManagerGUI constructors for current user tracking
    public PetManagerGUI() {
        this.currentUserID = "guest";
    }

    public PetManagerGUI(String currentUserID) {
        this.currentUserID = currentUserID == null ? "guest" : currentUserID;
    }

    public void start(Stage stage, UI mainApp) {
        this.mainApp = mainApp;

        stage.setTitle("Strays To Heaven - Pet Management Dashboard");

        HBox headerBar = Navigation.NavBar(stage, "Home", mainApp);

        // Data for pets
        Pet pet1 = new Pet("C001", 2, "British Shorthair", "Female", "Calm, Friendly", "Available");
        Pet pet2 = new Pet("D001", 1, "Golden Retriever", "Male", "Playful, Energetic", "Available");
        Pet pet3 = new Pet("C002", 4, "Persian", "Male", "Lazy, Fluffy", "Available");

        // prevent duplicate entries for pets
        if (PetManager.findPetByID("C001") == null) {
            PetManager.addPet(pet1);
        }
        if (PetManager.findPetByID("D001") == null) {
            PetManager.addPet(pet2);
        }
        if (PetManager.findPetByID("C002") == null) {
            PetManager.addPet(pet3);
        }

        // Load all pets from the PetManager into the observable list for display
        data.clear();
        data.addAll(PetManager.getAllPets());

        // Pets profile card layout and styling
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

        // Add to favourite button
        Button btnAddToFavourite = new Button("+ Add to Favourite");
        btnAddToFavourite.setStyle("-fx-background-color: #F9D1D9; -fx-text-fill: #838F58; -fx-font-weight: bold; -fx-padding: 6px 16px; -fx-background-radius: 10px; -fx-cursor: hand;");
        btnAddToFavourite.setOnAction(e -> {
            if (!data.isEmpty()) {
                Pet currentPet = data.get(currentPetIndex);
                try {
                    // open the favourite page and pass the current logged-in user's ID
                    FavouritePage favouritePage = new FavouritePage(currentUserID, currentPet.getPetID());
                    favouritePage.start(stage, mainApp);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        // assemble the status label and favourite button together in a horizontal box for better layout
        HBox statusAndFavBox = new HBox(12, statusLabel, btnAddToFavourite);
        statusAndFavBox.setAlignment(Pos.CENTER_LEFT);

        // Assemble text elements together
        textContainer.getChildren().addAll(idLabel, breedLabel, ageLabel, genderLabel, traitsLabel, statusAndFavBox);
        
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
        Button btnAdopt = new Button("Adopt Me!");
        btnAdopt.getStyleClass().add("add-button");
        btnAdopt.setOnAction(e -> openAdoptionRequest(stage, data.isEmpty() ? null : data.get(currentPetIndex).getPetID()));
        

        // Layout to arrange the pagination and registration elements cleanly
        HBox controlRow = new HBox(20, prevButton, btnAdopt, nextButton);
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

            // Attempt to load the image from the resources folder, and if it fails, set the ImageView to null to avoid displaying a broken image icon
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

    // Opens the adoption request page when the Adopt Me! button is clicked.
    private void openAdoptionRequest(Stage owner, String petID) {
        if (mainApp != null) {
            mainApp.AdoptionUI(petID);
        } else {
            System.err.println("Cannot open adoption form: main UI is not available.");
        }
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
