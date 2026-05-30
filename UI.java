/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.StrayToHeaven;

/**
 *
 * @author user
 */

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

public class UI extends Application{
    
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
    
    //logo
    private final String LOGO = "file:C:\\Users\\user\\Pictures\\logoStrayToHeaven.jpg";
    
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
        
        Image catContImg = new Image("file:C:\\Users\\user\\Pictures\\catContainer.jfif");
        ImageView catView = new ImageView(catContImg);
        catView.setFitWidth(340);
        catView.setFitHeight(380);
        
        //clips rectangle view into curved corners
        Rectangle clip = new Rectangle(340, 380);
        clip.setArcWidth(40);
        clip.setArcHeight(40);
        catView.setClip(clip);
        
        catCont.getChildren().add(catView);
        leftside.getChildren().add(catCont);
        
        //rightside
        VBox loginForm = new VBox(18);
        loginForm.setPrefSize(340, 380);
        loginForm.setMaxSize(340, 380);
        loginForm.setPadding(new Insets(30, 40, 30, 40));
        
        loginForm.setStyle("-fx-background-color: rgba(249, 209, 217, 0.9); -fx-background-radius: 0 25 25 0;");
        loginForm.setAlignment(Pos.CENTER_LEFT);
        
        Image logoImg = new Image(LOGO);
        ImageView logoView = new ImageView(logoImg);
        logoView.setFitWidth(35);
        logoView.setPreserveRatio(true);
        loginForm.getChildren().add(logoView);
        VBox.setMargin(logoView, new Insets(0, 0, -5, 0));
        
        Text formTitle = new Text("Login");
        formTitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 28));
        formTitle.setFill(Color.web("#838F58"));
        loginForm.getChildren().add(formTitle);
        
        String inputFieldStyle = "-fx-background-color: transparent; -fx-border-color: transparent transparent #4A4A4A transparent; -fx-border-width: 1; -fx-text-fill: #333333; -fx-padding: 4 0 4 0; -fx-font-size: 14px;";
        
        Label emailLabel = new Label("Email: ");
        emailLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px;");
        loginForm.getChildren().add(emailLabel);

        emailInput = new TextField();
        emailInput.setStyle(inputFieldStyle);
        loginForm.getChildren().add(emailInput);

        Label pw = new Label("Password:");
        pw.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px;");
        loginForm.getChildren().add(pw);

        pwInput = new PasswordField();
        pwInput.setStyle(inputFieldStyle);
        loginForm.getChildren().add(pwInput);

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

    public void DashboardUI(){
        BorderPane mainRoot = new BorderPane();
        mainRoot.setStyle(MATCHA_BG);
        
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
        
        Label userDashboard = new Label("USER DASHBOARD");
        userDashboard.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));
        userDashboard.setStyle("-fx-text-fill: #838F58");
        
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
        
        btHome.setStyle("-fx-background-color: transparent; -fx-text-fill: #838F58; -fx-font-weight: bold; -fx-font-size: 13px; -fx-underline: true;");
        
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
        
        Button adoptNow = new Button("Adopt Now");
        adoptNow.setStyle("-fx-background-color: #F9D1D9; -fx-text-fill: #333333; -fx-padding: 10 25 10 25; -fx-font-weight: bold; -fx-background-radius: 10; -fx-cursor: hand;");
        
        Button viewPetList = new Button("Pet List");
        viewPetList.setStyle("-fx-background-color: #F9D1D9; -fx-text-fill: #333333; -fx-padding: 10 25 10 25; -fx-font-weight: bold; -fx-background-radius: 10; -fx-cursor: hand;");
        
        actionBt.getChildren().addAll(adoptNow, viewPetList);
        
        //display cat
        HBox petDisplay = new HBox(20);
        petDisplay.setAlignment(Pos.CENTER);
        
        String[] catImg = {
            "file:C:\\Users\\user\\Pictures\\cat1.jfif",
            "file:C:\\Users\\user\\Pictures\\cat2.jfif",
            "file:C:\\Users\\user\\Pictures\\cat3.jfif",
            "file:C:\\Users\\user\\Pictures\\cat4.jfif",
            "file:C:\\Users\\user\\Pictures\\cat5.jfif",
            "file:C:\\Users\\user\\Pictures\\cat6.jfif"
        };
        
        for (int i = 0; i < 6; i++){
            VBox BubblePet = new VBox(8);
            BubblePet.setAlignment(Pos.CENTER);
            
            Circle petCircle = new Circle(38);
            
            try{
                Image catPic = new Image(catImg[i]);
                javafx.scene.paint.ImagePattern ip = new javafx.scene.paint.ImagePattern(catPic);
                petCircle.setFill(ip);
            } catch (Exception e){
                petCircle.setFill(Color.web(PINK));
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
        
        btHome.setOnAction((ActionEvent e) -> {
            DashboardUI();
        });
        
        btRehome.setOnAction((ActionEvent e) -> {
            RehomeScreen rehomePage = new RehomeScreen();
            rehomePage.start(window);
        });
        
        adoptNow.setOnAction((ActionEvent e) -> {
            AdoptionUI();
        });
        
        viewPetList.setOnAction((ActionEvent e) -> {
            PetManagerGUI petPage = new PetManagerGUI();
            petPage.start(window);
        });
        
        btAdopt.setOnAction((ActionEvent e) -> {
            AdoptionUI();
        });
        
        btDonate.setOnAction((ActionEvent e) -> {
            DonationUI();
        });
        
        btLogout.setOnAction((ActionEvent e) -> {
            LoginUI();
        });
        
        window.setScene(new Scene(mainRoot));
        window.setWidth(950);
        window.setHeight(600);
    }
    
    public void AdoptionUI(){
        
        //navBar
        BorderPane mainRoot = new BorderPane();
        mainRoot.setStyle(MATCHA_BG);
        
        HBox navBar = Navigation.NavBar(window, "Adopt", this);
        
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
        
        Text title = new Text("ADOPTION REQUEST");
        title.setFont(Font.font("Tahoma", FontWeight.BOLD, 22));
        title.setFill(Color.web("#838F58"));
        
        Label reqLabel = new Label("Pet ID: ");
        reqLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        reqLabel.setWrapText(true);
        reqLabel.setTextAlignment(CENTER);
        
        TextField petIDField = new TextField();
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
    
    public void DonationUI(){
        DonationScreen donationPage = new DonationScreen();
        donationPage.display(window);
    }
    
    public void AdminDashboardUI(){
        AdminPage adminPage = new AdminPage();
        adminPage.start(window);
    }
}
