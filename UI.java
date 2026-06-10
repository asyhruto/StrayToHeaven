package com.example;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import static javafx.scene.text.TextAlignment.CENTER;
import javafx.stage.Stage;

public class UI extends Application {
    public static void main(String[] args){
        launch(args);
    }
    private TextField emailInput;
    private PasswordField pwInput;
    
    // simulation
    private final User mockUser = new User("p001", "Qaseh Maisara", "qseh@email.com", "qaseh123");
    private final String adminEmail = "admin@123.com";
    private final String adminPass = "admin123";
    
    private Stage window;
    
    //coloring
    private final String MATCHA_BG = "-fx-background-color: #838F58;";
    private final String PINK = "-fx-background-color: #F9D1D9; -fx-text-fill: #333333; -fx-font-weight: bold;";
    private final String MATCHA = "-fx-background-color: #838F58; -fx-text-fill: white; -fx-font-weight: bold;";
    
    @Override
    public void start(Stage primaryStage){
        this.window = primaryStage;
        window.setTitle("STRAY TO HEAVEN");
        LoginUI();
    }
    
    public void LoginUI(){
        
        HBox mainCont = new HBox(0);
        mainCont.setAlignment(Pos.CENTER);
        mainCont.setStyle(MATCHA_BG);
        
        //leftside
        StackPane leftside = new StackPane();
        leftside.setPadding(new Insets(20));
        leftside.setAlignment(Pos.CENTER);
        
        //container for cat image
        VBox catCont = new VBox();
        catCont.setPrefSize(340, 380);
        catCont.setMaxSize(340, 380);
        catCont.setAlignment(Pos.CENTER);
        
        catCont.setStyle("-fx-background-radius: 25; -fx-border-radius: 25; -fx-overflow-piece: hidden;");
        
        // Load the cat container image
        Image catContImg = null;
        try {
            catContImg = new Image(getClass().getResourceAsStream("images/catContainer.jfif"));
        } catch (Exception e) {
            System.out.println("Warning: catContainer.jfif not found in images/ folder!");
        }
        
        // Display the cat container image if it was loaded successfully
        ImageView catView = new ImageView(catContImg);
        catView.setFitWidth(340);
        catView.setFitHeight(380);
        
        //clips rectangle view into curved corners
        Rectangle clip = new Rectangle(340, 380);
        clip.setArcWidth(40);
        clip.setArcHeight(40);
        catView.setClip(clip);
        
        // Cat Picture to display on the left side of the login screen
        catCont.getChildren().add(catView);
        leftside.getChildren().add(catCont);
        
        //rightside
        VBox loginForm = new VBox(18);
        loginForm.setPrefSize(340, 380);
        loginForm.setMaxSize(340, 380);
        loginForm.setPadding(new Insets(30, 40, 30, 40));
        
        loginForm.setStyle("-fx-background-color: rgba(249, 209, 217, 0.9); -fx-background-radius: 0 25 25 0;");
        loginForm.setAlignment(Pos.CENTER_LEFT);
        
        // Title and input fields for the login form with enhanced styling for a more polished and user-friendly interface
        Text formTitle = new Text("Login");
        formTitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 28));
        formTitle.setFill(Color.web("#838F58"));
        loginForm.getChildren().add(formTitle);
        
        String inputFieldStyle = "-fx-background-color: transparent; -fx-border-color: transparent transparent #4A4A4A transparent; -fx-border-width: 1; -fx-text-fill: #333333; -fx-padding: 4 0 4 0; -fx-font-size: 14px;";
        
        // Email and password fields with labels
        Label emailLabel = new Label("Email: ");
        emailLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px;");
        loginForm.getChildren().add(emailLabel);

        emailInput = new TextField();
        emailInput.setStyle(inputFieldStyle);
        loginForm.getChildren().add(emailInput);

        // Password field with label
        Label pw = new Label("Password:");
        pw.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px;");
        loginForm.getChildren().add(pw);

        pwInput = new PasswordField();
        pwInput.setStyle(inputFieldStyle);
        loginForm.getChildren().add(pwInput);

        // login and cancel buttons for login page
        Button login = new Button("LOGIN");
        login.setStyle(MATCHA);
        Button cancel = new Button("CANCEL");
        cancel.setStyle(MATCHA);
        
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().addAll(login, cancel);
        loginForm.getChildren().add(hbBtn);

        final Text actiontarget = new Text();
        loginForm.getChildren().add(actiontarget);
        
        mainCont.getChildren().addAll(leftside, loginForm);

        // Event handler for the Login button to validate user credentials and navigate to the appropriate dashboard based on the user's role (admin or regular user), providing feedback on login success or failure for a seamless user experience.
        login.setOnAction((ActionEvent e) -> {
            String email = emailInput.getText();
            String password = pwInput.getText();
            
            if(email.equals(adminEmail) && password.equals(adminPass)){
                AdminDashboardUI();
            }
            else if (mockUser.login(email, password)){
                DashboardUI();
            }
            else{
                actiontarget.setFill(Color.FIREBRICK);
                actiontarget.setText("Invalid Email/Password ! Please try again.");
            }
        });
        
        // Event handler for the Cancel button to clear the input fields and reset the form
        cancel.setOnAction((ActionEvent e)-> {
            actiontarget.setFill(Color.WHITE);
            actiontarget.setText("Cancel and clear details");
            
            //to clear details
            emailInput.clear();
            pwInput.clear();
        });

        Scene scene = new Scene(mainCont, 760, 460);
        window.setScene(scene);
        window.show();
    }

    //Main dashboard UI
    public void DashboardUI(){
        BorderPane mainRoot = new BorderPane();
        mainRoot.setStyle(MATCHA_BG);
        
        HBox navBar = Navigation.NavBar(window, "Home", this);
        
        VBox topCont = new VBox();
        topCont.setPadding(new Insets(15, 15, 0, 15));
        topCont.getChildren().add(navBar);
        mainRoot.setTop(topCont);
        
        //center container
        VBox center = new VBox(25);
        center.setPadding(new Insets(20, 40, 20, 40));
        center.setAlignment(Pos.CENTER);
        
        Text introTitle = new Text("What is Stray To Heaven ?");
        introTitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 28));
        introTitle.setFill(Color.WHITE);
        
        Text intro = new Text(
            "Stray to Heaven is a dedicated and compassionate platform bridging the gap between stray cats " +
            "and loving homes. We empower communities to adopt shelter cats, facilitate responsible rehoming and " +
            "manage donations to give every cats the second chance at heaven on earth that they truly deserve.");
        intro.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
        intro.setFill(Color.web("#F9D1D9"));
        intro.setWrappingWidth(650);
        intro.setTextAlignment(CENTER);
        
        HBox actionBt = new HBox(20);
        actionBt.setAlignment(Pos.CENTER);
        
        // Button to access PetManagerGUI page
        Button viewPetList = new Button("Pet List");
        viewPetList.setStyle("-fx-background-color: #F9D1D9; -fx-text-fill: #333333; -fx-padding: 10 25 10 25; -fx-font-weight: bold; -fx-background-radius: 10; -fx-cursor: hand;");
        
        actionBt.getChildren().addAll(viewPetList);
        
        //display cat
        HBox petDisplay = new HBox(20);
        petDisplay.setAlignment(Pos.CENTER);
        
        String[] catImg = {
            "images/animalDisp1.jfif",
            "images/animalDisp2.jfif",
            "images/animalDisp3.jfif",
            "images/animalDisp4.jfif",
            "images/animalDisp5.jfif",
            "images/animalDisp6.jfif"
        };
        
        // Loop to create pet profile bubbles with circular images and display them in the pet display area, providing a visually appealing and engaging way to showcase the pets available for adoption on the dashboard.
        for (int i = 0; i < 6; i++){
            VBox BubblePet = new VBox(8);
            BubblePet.setAlignment(Pos.CENTER);
            
            Circle petCircle = new Circle(38);
            
            try{
                // Attempt to load the image for the pet profile
                Image catPic = new Image(getClass().getResourceAsStream(catImg[i]));
                javafx.scene.paint.ImagePattern ip = new javafx.scene.paint.ImagePattern(catPic);
                petCircle.setFill(ip);
            } catch (Exception e){
                // If the image fails to load, fill the circle with a solid color and print a warning message
                petCircle.setFill(Color.web(PINK));
                System.out.println("Warning: Could not find image " + catImg[i]);
            }
            
            BubblePet.getChildren().addAll(petCircle);
            petDisplay.getChildren().add(BubblePet);   
        }
        
        center.getChildren().addAll(introTitle, intro, actionBt, petDisplay);
        mainRoot.setCenter(center);
        
        //footer
        HBox footer = new HBox();
        footer.setAlignment(Pos.CENTER);
        footer.setPadding(new Insets(12));
        
        Label footerContact = new Label("Contact Us: strayToHeaven@67.com");
        footerContact.setStyle("-fx-text-fill: white; -fx-font-style: italic; -fx-font-size: 12px;");
        footer.getChildren().add(footerContact);
        mainRoot.setBottom(footer);
        
        
        
        viewPetList.setOnAction((ActionEvent e) -> {
            PetManagerGUI petPage = new PetManagerGUI(mockUser.getUserID());
            petPage.start(window, this);
        });
        
        
        window.setScene(new Scene(mainRoot));
        window.setWidth(950);
        window.setHeight(600);
    }
    
    // Adoption request form UI
    public void AdoptionUI(){
        AdoptionUI(null);
    }

    // Pet request ID prefill
    public void AdoptionUI(String prefillPetID){
        
        // Main Container
        BorderPane mainRoot = new BorderPane();
        mainRoot.setStyle(MATCHA_BG);
        
        // Navigation bar 
        HBox navBar = Navigation.NavBar(window, "Adopt", this);
        
        // Top container to hold the navigation bar with proper padding and alignment
        VBox topCont = new VBox();
        topCont.setPadding(new Insets(15, 15, 0, 15));
        topCont.getChildren().add(navBar);
        mainRoot.setTop(topCont);
        
        VBox form = new VBox(20);
        form.setPrefSize(400, 320);
        form.setMaxSize(400, 320);
        form.setPadding(new Insets(30, 40, 30, 40));
        form.setStyle("-fx-background-color: rgba(249, 209, 217, 0.95); -fx-background-radius: 20; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.15), 10, 0, 0, 5);");
        form.setAlignment(Pos.CENTER);
        
        // Title and input fields for the adoption request form
        Text title = new Text("ADOPTION REQUEST");
        title.setFont(Font.font("Tahoma", FontWeight.BOLD, 22));
        title.setFill(Color.web("#838F58"));
        
        Label reqLabel = new Label("Pet ID: ");
        reqLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        reqLabel.setWrapText(true);
        reqLabel.setTextAlignment(CENTER);
        
        TextField petIDField = new TextField();
        if (prefillPetID != null && !prefillPetID.isBlank()) {
            petIDField.setText(prefillPetID);
        }
        petIDField.setPromptText("e.g. PET101");
        petIDField.setMaxWidth(200);
        petIDField.setStyle("-fx-padding: 8; -fx-background-radius: 5;");
        
        Button submit = new Button("SUBMIT");
        submit.setStyle(MATCHA);
        Button back = new Button("BACK");
        back.setStyle(MATCHA);
        
        form.getChildren().addAll(title, reqLabel, petIDField, submit, back);
        
        StackPane centerCont = new StackPane(form);
        centerCont.setPadding(new Insets(30));
        mainRoot.setCenter(centerCont);
        
        submit.setOnAction((ActionEvent e) -> {
            String inputPetID = petIDField.getText();
            
            if(!inputPetID.isEmpty()){
                String randomReqID = "REQ-" +  (int)(Math.random() * 900 + 100);
                String logInUserID = "p001";
                String requestDate = "2026-05-30";
                
                AdoptionRequest newRequest = new AdoptionRequest();
                newRequest.sendAdoptionRequest(randomReqID, logInUserID, inputPetID, requestDate);
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Adoption Application Processed");
                alert.setHeaderText("Submission Successful!");
                
                Label receipt = new Label(newRequest.getReceipt());
                receipt.setFont(Font.font("Courier New", FontWeight.BOLD, 13));
                receipt.setStyle("-fx-text-fill: #333333; -fx-padding: 10;");
                
                alert.getDialogPane().setContent(receipt);
                alert.getDialogPane().setStyle("-fx-background-color: #F9D1D9; -fx-border-color: #838F58; -fx-border-width: 2; -fx-border-radius: 10; -fx-background-radius: 10;");
                
                alert.showAndWait();
                
                DashboardUI();
            }
            else{
                reqLabel.setText("WARNING ! Pet ID cannot be blank!");
                reqLabel.setStyle("-fx-text-fill: red;");
            }
        });
        
        back.setOnAction((ActionEvent e) -> {
            DashboardUI();
        });
        
        window.setScene(new Scene(mainRoot, 950, 600));
    }
    
    public void FavouriteUI(){
        FavouritePage favPage = new FavouritePage(mockUser.getUserID(), null);
        favPage.start(window, this);
    }
    
    public void DonationUI(){
        DonationScreen donationPage = new DonationScreen();
        donationPage.start(window, this);
    }
    
    public void AdminDashboardUI(){
        AdminPage adminPage = new AdminPage();
        adminPage.start(window, this);
    }
}
