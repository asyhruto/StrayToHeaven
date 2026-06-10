package com.example;

import java.io.File;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class RehomeScreen extends Application {
    private String savedImagePath = "No image selected";

    @Override
    public void start(Stage primaryStage) {
        start(primaryStage, null);
    }

    public void start(Stage primaryStage, UI mainApp) {
        
        // background and main container
        VBox root = new VBox(20);
        root.setPadding(new Insets(20, 20, 20, 20));
        root.setStyle("-fx-background-color: #838F58;"); 
        root.setAlignment(Pos.TOP_CENTER);

        // navigation bar with active menu highlighting
        HBox navBar = Navigation.NavBar(primaryStage, "Rehome", mainApp);

        // big pink box for the form
        VBox contentArea = new VBox(25);
        VBox.setVgrow(contentArea, Priority.ALWAYS);
        contentArea.setAlignment(Pos.CENTER);
        contentArea.setPadding(new Insets(40));
        contentArea.setStyle("-fx-background-color: #F9D1D9; -fx-background-radius: 20;");

        // catchy header
        Label lblHeader = new Label("Rehome a Pet");
        lblHeader.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: white;");
        
        Label lblSubtitle = new Label("A safe bridge to their next heaven on earth.");
        lblSubtitle.setStyle("-fx-font-size: 15px; -fx-text-fill: white; -fx-padding: 0 0 10 0;");

        // for user to upload pet picture
        ImageView petImageView = new ImageView();
        petImageView.setFitWidth(150); petImageView.setFitHeight(150);
        petImageView.setPreserveRatio(true);
        
        StackPane imageBox = new StackPane(petImageView);
        imageBox.setPrefSize(160, 160); imageBox.setMaxSize(160, 160);
        imageBox.setStyle("-fx-border-color: #838F58; -fx-border-width: 2; -fx-border-radius: 10; -fx-background-color: white; -fx-background-radius: 10;");
        
        Button btnUpload = new Button("Add Picture");
        btnUpload.setStyle("-fx-background-color: #838F58; -fx-text-fill: white; -fx-background-radius: 10; -fx-cursor: hand; -fx-font-weight: bold; -fx-padding: 8 20;");
        btnUpload.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
            File file = fileChooser.showOpenDialog(primaryStage);
            if (file != null) {
                savedImagePath = file.getAbsolutePath();
                petImageView.setImage(new Image(file.toURI().toString()));
            }
        });

        VBox imageSection = new VBox(10, imageBox, btnUpload);
        imageSection.setAlignment(Pos.CENTER);

        // the big detailed input forms
        GridPane form = new GridPane();
        form.setHgap(15); form.setVgap(15);
        form.setAlignment(Pos.CENTER);

        // input styling
        String inputStyle = "-fx-background-color: white; -fx-border-color: #838F58; -fx-border-radius: 10; -fx-background-radius: 10; -fx-padding: 8; -fx-pref-width: 200;";

        // form fields for pet details
        TextField txtName = new TextField(); txtName.setPromptText("Pet Name (e.g. Luna)"); txtName.setStyle(inputStyle);
        TextField txtBreed = new TextField(); txtBreed.setPromptText("Pet Breed (e.g. Scottish Fold)"); txtBreed.setStyle(inputStyle);
        TextField txtAge = new TextField(); txtAge.setPromptText("Pet Age (e.g. 3)"); txtAge.setStyle(inputStyle);
        
        // dropdown for gender selection
        ComboBox<String> cbGender = new ComboBox<>();
        cbGender.getItems().addAll("Male", "Female");
        cbGender.setPromptText("Select Gender");
        cbGender.setStyle(inputStyle);

        // add form fields to the grid layout
        form.add(txtName, 0, 0); form.add(txtBreed, 1, 0);
        form.add(txtAge, 0, 1); form.add(cbGender, 1, 1);

        // make submit button hover effect
        Button btnSubmit = new Button("Submit Application");
        
        String submitDefault = "-fx-background-color: #838F58; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 10; -fx-padding: 10 30; -fx-font-size: 16px; -fx-cursor: hand;";
        String submitHover = "-fx-background-color: #6C7A43; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 10; -fx-padding: 10 30; -fx-font-size: 16px; -fx-cursor: hand; -fx-scale-x: 1.05; -fx-scale-y: 1.05;";
        
        btnSubmit.setStyle(submitDefault);
        btnSubmit.setOnMouseEntered(e -> btnSubmit.setStyle(submitHover));
        btnSubmit.setOnMouseExited(e -> btnSubmit.setStyle(submitDefault));
        
        // submit function
        btnSubmit.setOnAction(e -> {
            try {
                int age = Integer.parseInt(txtAge.getText());
                String gender = cbGender.getValue() != null ? cbGender.getValue() : "Unknown";
                
                Rehome r = new Rehome("R001", "U001", txtName.getText(), txtBreed.getText(), age, gender, "Needs love");
                r.submitPet();
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Successfully submitted " + txtName.getText() + " for rehoming!");
                alert.show();
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Please ensure all fields, including age as a number, are filled properly.").show(); // add function call for error handling placeholder
            }
        });

        contentArea.getChildren().addAll(lblHeader, lblSubtitle, imageSection, form, btnSubmit);
        
        // Scroll pane for fit the area display
        ScrollPane scrollPane = new ScrollPane(contentArea);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-control-inner-background: #838F58; -fx-padding: 0;");
        root.getChildren().addAll(navBar, scrollPane);

        primaryStage.setScene(new Scene(root, 950, 600)); 
        primaryStage.setTitle("Strays To Heaven - Rehome Portal");
        primaryStage.show();
    }

    // function to make nav buttons fast
    private Button createNavButton(String text, String style) {
        Button btn = new Button(text);
        btn.setStyle(style);
        return btn;
    }
    
    // alert popup placeholder
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
