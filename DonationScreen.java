package com.mycompany.ooprehome;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class DonationScreen extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        start(primaryStage, null);
    }

    public void start(Stage primaryStage, UI mainApp) {
        // background
        VBox root = new VBox(20); 
        root.setPadding(new Insets(20, 20, 20, 20));
        root.setStyle("-fx-background-color: #838F58;"); 

        // navigation bar with active menu highlighting
        HBox navBar = Navigation.NavBar(primaryStage, "Donate", mainApp);

        // big pink box for the form
        VBox contentArea = new VBox(20); 
        contentArea.setAlignment(Pos.CENTER);
        contentArea.setPadding(new Insets(20, 20, 20, 20));
        contentArea.setStyle("-fx-background-color: #F9D1D9; -fx-background-radius: 20;");
        VBox.setVgrow(contentArea, Priority.ALWAYS); 

        // catchy header
        Label lblHeader = new Label("Support Our Strays 🐾");
        lblHeader.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: white;");
        
        Label lblSubtitle = new Label("Every Ringgit saves a life.");
        lblSubtitle.setStyle("-fx-font-size: 15px; -fx-text-fill: white; -fx-padding: 0 0 10 0;");

        // the big detailed input forms
        GridPane form = new GridPane();
        form.setHgap(20); form.setVgap(15);
        form.setAlignment(Pos.CENTER);

        String inputStyle = "-fx-background-color: white; -fx-border-color: #838F58; -fx-border-radius: 10; -fx-background-radius: 10; -fx-padding: 10; -fx-pref-width: 250; -fx-font-size: 14px;";
        String labelStyle = "-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 15px;";
        String radioStyle = "-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px;";

        // amount and frequency
        Label lblAmount = new Label("Donation (RM):");
        lblAmount.setStyle(labelStyle);
        TextField txtAmount = new TextField(); 
        txtAmount.setPromptText("e.g. 50.00"); 
        txtAmount.setStyle(inputStyle);
        
        Label lblFreq = new Label("Frequency:");
        lblFreq.setStyle(labelStyle);
        
        // radio buttons for one time or monthly
        ToggleGroup freqGroup = new ToggleGroup();
        RadioButton rbOneTime = new RadioButton("One-time");
        rbOneTime.setStyle(radioStyle);
        rbOneTime.setToggleGroup(freqGroup);
        rbOneTime.setSelected(true); // default option
        
        RadioButton rbMonthly = new RadioButton("Monthly");
        rbMonthly.setStyle(radioStyle);
        rbMonthly.setToggleGroup(freqGroup);
        
        HBox freqBox = new HBox(15, rbOneTime, rbMonthly);
        freqBox.setAlignment(Pos.CENTER_LEFT);

        // purpose and payment method
        Label lblPurpose = new Label("Donation Purpose:");
        lblPurpose.setStyle(labelStyle);
        ComboBox<String> cbPurpose = new ComboBox<>();
        cbPurpose.getItems().addAll("General Shelter Fund", "Food & Treats", "Medical & Vet Bills", "Shelter Maintenance");
        cbPurpose.setPromptText("Select");
        cbPurpose.setStyle(inputStyle);
        
        Label lblPayment = new Label("Payment Method:");
        lblPayment.setStyle(labelStyle);
        ComboBox<String> cbPayment = new ComboBox<>();
        cbPayment.getItems().addAll("FPX (Online Banking)", "Touch 'n Go eWallet", "Credit/Debit Card");
        cbPayment.setPromptText("Select payment");
        cbPayment.setStyle(inputStyle);

        // message for the shelter
        Label lblMessage = new Label("Message (Optional):");
        lblMessage.setStyle(labelStyle);
        TextArea txtMessage = new TextArea();
        txtMessage.setPromptText("Leave a message");
        txtMessage.setPrefRowCount(2); // make it short so it fits
        txtMessage.setStyle("-fx-background-color: white; -fx-border-color: #838F58; -fx-border-radius: 10; -fx-background-radius: 10; -fx-pref-width: 250;");

        // add everything to the grid
        form.add(lblAmount, 0, 0); form.add(txtAmount, 1, 0);
        form.add(lblFreq, 2, 0); form.add(freqBox, 3, 0);
        
        form.add(lblPurpose, 0, 1); form.add(cbPurpose, 1, 1);
        form.add(lblPayment, 2, 1); form.add(cbPayment, 3, 1);
        
        form.add(lblMessage, 0, 2); form.add(txtMessage, 1, 2, 3, 1); // makes the text box stretch across

        // make submit button hover effect
        Button btnSubmit = new Button("Donate Now");
        
        String submitDefault = "-fx-background-color: #838F58; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 10; -fx-padding: 12 40; -fx-font-size: 16px; -fx-cursor: hand;";
        String submitHover = "-fx-background-color: #6C7A43; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 10; -fx-padding: 12 40; -fx-font-size: 16px; -fx-cursor: hand; -fx-scale-x: 1.05; -fx-scale-y: 1.05;";
        
        btnSubmit.setStyle(submitDefault);
        btnSubmit.setOnMouseEntered(e -> btnSubmit.setStyle(submitHover));
        btnSubmit.setOnMouseExited(e -> btnSubmit.setStyle(submitDefault));
        
        // submit function with all the new data
        btnSubmit.setOnAction(e -> {
            try {
                double amount = Double.parseDouble(txtAmount.getText());
                String purpose = cbPurpose.getValue() != null ? cbPurpose.getValue() : "General Shelter Fund";
                String payment = cbPayment.getValue() != null ? cbPayment.getValue() : "Not Selected";
                String freq = rbMonthly.isSelected() ? "Monthly" : "One-time";
                
                // check if they picked a payment method
                if (payment.equals("Not Selected")) {
                    new Alert(Alert.AlertType.WARNING, "Please select a payment method to continue!").show();
                    return;
                }
                
                // create dummy user and process donation
                User dummy = new User("Guest", "guest@mail.com", "pass", "U001");
                // just putting today's date manually for the backend
                Donation donation = new Donation("D100", dummy, amount, "2026-05-29");
                donation.processDonation();
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Donation Successful! ");
                alert.setContentText("Thank you for your " + freq + " RM " + amount + " donation towards " + purpose + " via " + payment + "!");
                alert.show();
            } catch (NumberFormatException ex) {
                new Alert(Alert.AlertType.ERROR, "Please enter a valid number for the donation amount.").show();
            }
        });

        contentArea.getChildren().addAll(lblHeader, lblSubtitle, form, btnSubmit);
        root.getChildren().addAll(navBar, contentArea);

        primaryStage.setScene(new Scene(root, 950, 600)); 
        primaryStage.setTitle("Strays To Heaven - Donation Portal");
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
