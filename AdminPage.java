package com.example;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.Modality;


public class AdminPage extends Application {
    
    private ObservableList<String[]> userList = FXCollections.observableArrayList();
    private ObservableList<String[]> requestList = FXCollections.observableArrayList();

    private static final String GREEN = "-fx-background-color: #838F58;"
        + " -fx-text-fill: white; "
        + "-fx-font-weight: bold;"
        + " -fx-background-radius: 8; "
        + "-fx-padding: 8 20;"
        + " -fx-cursor: hand;";
    private static final String RED   = "-fx-background-color: #e74c3c; "
        + "-fx-text-fill: white; "
        + "-fx-font-weight: bold;"
        + " -fx-background-radius: 8; "
        + "-fx-padding: 6 14;"
        + " -fx-cursor: hand;";
    private static final String INPUT = "-fx-background-color: white; "
        + "-fx-border-color: #ccc;"
        + " -fx-border-radius: 8; "
        + "-fx-background-radius: 8; "
        + "-fx-padding: 8; "
        + "-fx-pref-width: 200;";
    
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
        Button manageReqBtn = new Button("Manage Requests");
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
                openUsers(primaryStage));
        
        managePetsBtn.setOnAction(e -> 
                openPets(primaryStage));
        
        manageReqBtn.setOnAction(e -> 
                openReq(primaryStage));
        
        manageDonationBtn.setOnAction(e -> 
                openPage("View Donations"));
        
        content.getChildren().addAll(
                lblTitle, 
                manageUsersBtn,
                managePetsBtn,
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

    private void openUsers(Stage owner){
        
        Stage s = modal(owner, "Manage USers");
        TableView<String[]> table = table();
        table.setItems(userList);
        addCol(table, "User ID", 0);
        addCol(table, "Name", 1);
        addCol(table,"email",2);
        addCol(table, "Role",3);
        addDeleteCol(table,userList);
        
        TextField textID = f("User ID"), textName = f("Name"), 
                textEmail =f("Email");
        ComboBox<String> comRole = combo("User", "Admin");
        Label msg = msg();
        
        Button add = btn("Add User");
        add.setOnAction(e -> {
            if(anyEmpty(textID,textName,textEmail) || 
                    comRole.getValue() == null) {
                msg.setText("Please fill all fields.");
                return ;
            }
            
            userList.add(new String[] {
                textID.getText(),
                textName.getText(),
                textEmail.getText(),
                comRole.getValue()
            });

            clear(textID,textName,textEmail);
            comRole.setValue(null);
            msg.setText("✅ User added!");

        });

        show(s,"User Management", table, "Add User", 
        row(textID,textName), row(textEmail, comRole), add, msg);
    }

    private void openPets(Stage owner){
    
    Stage s = modal(owner,"Manage Pets");
    ObservableList<Pet> pets = FXCollections.observableArrayList(PetManager.getAllPets());
    TableView<Pet> table = new TableView<>(pets);
    table.setPlaceholder(new Label("No pets yet.")); table.setPrefHeight(220);
    petCol(table, "ID", p -> p.getPetID());
    petCol(table, "Breed", p -> p.getPetBreed());
    petCol(table, "Age", p -> String.valueOf(p.getPetAge()));
    petCol(table, "Gender", p -> p.getPetGender());
    petCol(table, "Status", p -> p.getPetStatus());

    TableColumn<Pet, String> delCol = new TableColumn<>("Action");
    delCol.setCellFactory(c -> new TableCell<>() {
        Button del = delBtn();
        {
           del.setOnAction(e -> {
                Pet p = getTableView().getItems().get(getIndex());
                PetManager.deletePetInfo(p.getPetID());
                pets.remove(p);
            });
        }
        protected void updateItem(String i , boolean empty){
        
        super.updateItem(i,empty);
        setGraphic(empty ? null : del);
        }
    });
    
    table.getColumns().add(delCol);

    TextField textID = f("Pet ID"), textBreed = f("Breed"), textAge = f("Age"),
                    textTraits = f("Traits");
    ComboBox<String> comGender = combo("Male", "Female");
    Label msg = msg();

    Button add = btn("Add Pet");
    add.setOnAction(e -> {
        try {
            if (anyEmpty(textID, textBreed, textAge) || comGender.getValue() == null){
                msg.setText("Please fill all fields.");
                return ;
            }
        Pet p = new Pet(textID.getText(), Integer.parseInt(textAge.getText()),
            textBreed.getText(), comGender.getValue(), textTraits.getText(), "Available");
        PetManager.addPet(p);
        pets.add(p);
        clear(textID, textBreed, textAge, textTraits);
        comGender.setValue(null);
        msg.setText("Pet added!");
        }catch (NumberFormatException ex) {
            msg.setText("Age must be a number.");
        }
    });
    
    show(s, "Pet Management", table, "Add Pet", row(textID, textBreed), row(textAge, comGender), row(textTraits, null), add, msg);
    }

    private void openReq(Stage owner){
    
    Stage s = modal(owner, "Adoption Requests");
    TableView<String[]> table = table();
    table.setItems(requestList);
    addCol(table, "Request ID", 0);
    addCol(table, "User ID", 1);
    addCol(table, "Pet ID", 2);
    addCol(table, "Date", 3);
    addCol(table, "Status",4);

    TableColumn<String[], String> actCol = new TableColumn<>("Action");
    actCol.setMinWidth(180);
    actCol.setCellFactory(c -> new TableCell<>(){
        Button app = new Button("Approve");
        Button rej = new Button("Reject");
        HBox box = new HBox(8, app, rej);
        {
            app.setStyle(GREEN);
            rej.setStyle(RED);
            box.setAlignment(Pos.CENTER);
            app.setOnAction(e -> {
                requestList.get(getIndex())[4]= "Approved";
                table.refresh();
            });
            rej.setOnAction(e -> {
                requestList.get(getIndex())[4] = "Rejected";
                table.refresh();
            });
        }

    protected void updateItem(String i , boolean empty) {
        super.updateItem(i, empty);
        setGraphic(empty ? null :  box) ;
    }
    }) ;

    table.getColumns().add(actCol);

    TextField textReqID = f("Request ID"), textUID = f("User ID"),
              textPID = f("Pet ID"), textDate = f("Date");
    Label msg = msg();
    
    Button add = btn("Add Request");
    add.setOnAction(e -> {
        if (anyEmpty(textReqID, textUID, textPID, textDate)) { 
            msg.setText("⚠ Fill all fields.");
            return; 
        }
        requestList.add(new String[]{
            textReqID.getText(), textUID.getText(), textPID.getText(), textDate.getText(), "Pending"});
            clear(textReqID, textUID, textPID, textDate); 
            msg.setText("✅ Request added!");
    });

    show(s, "Adoption requests", table, "Add Request", row(textReqID, textUID), row(textPID, textDate), add, msg);

    }

    private Stage modal(Stage owner, String title) {
        Stage s = new Stage();
        s.initModality(Modality.WINDOW_MODAL);
        s.initOwner(owner);
        s.setTitle(title);
        return s;
    }

    private void show(Stage s, String title, javafx.scene.Node table, String formTitle, javafx.scene.Node...nodes) {
        Label lbl = new Label(title);
        lbl.setStyle("-fx-font-size: 20px; "
        + "-fx-font-weight: bold; -fx-text-fill: white;");
        Label flbl = new Label(formTitle);
        flbl.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;"
        + " -fx-text-fill: white;");
        VBox box = new VBox(12, lbl, table, new Separator(), flbl);
        for (javafx.scene.Node n : nodes) if (n != null) box.getChildren().add(n);
        box.setPadding(new Insets(25)); box.setStyle("-fx-background-color: #838F58;");
        ScrollPane sp = new ScrollPane(box); sp.setFitToWidth(true);
        sp.setStyle("-fx-background: #838F58; -fx-background-color: #838F58;");
        s.setScene(new Scene(sp, 700, 580)); s.show();
    }

    private HBox row(javafx.scene.Node a, javafx.scene.Node b){
        HBox h = new HBox(12);
        if ( a!= null)
            h.getChildren().add(a);
        if ( b!= null)
            h.getChildren().add(b);
        return h ;

    }

    private <T> TableView<T> table(){
        TableView<T> t = new TableView<>();
        t.setPlaceholder(new Label("No data"));
        t.setPrefHeight(220);
        return t ;
    }
    
    private void addCol(TableView<String[]> t, String h, int i) { 
        TableColumn<String[],String> c = new TableColumn<>(h);
        c.setCellValueFactory(d -> new SimpleStringProperty(d.getValue()[i])); 
        t.getColumns().add(c); 
    }

    private <T> void petCol(TableView<T> t, String h, java.util.function.Function<T,String> fn) { 
        TableColumn<T,String> c = new TableColumn<>(h); 
        c.setCellValueFactory(d -> new SimpleStringProperty(fn.apply(d.getValue()))); 
        t.getColumns().add(c);
    }

    private void addDeleteCol(TableView<String[]> t, ObservableList<String[]> list) {
        TableColumn<String[],String> c = new TableColumn<>("Action");
        c.setCellFactory(col -> new TableCell<>() { Button d = delBtn(); 
        { d.setOnAction(e -> list.remove(getIndex())); } 
    
    protected void updateItem(String i, boolean empty) { 
        super.updateItem(i,empty);
        setGraphic(empty?null:d); 
        } 
    });
        t.getColumns().add(c);
    }

    private Button btn(String t) { 
        Button b = new Button(t);
        b.setStyle(GREEN); 
        b.setMinWidth(130);
        return b;
    }

    private Button delBtn(){
        Button b = new Button("Delete");
        b.setStyle(RED);
        return b;
    }

    private TextField f(String p) {
        TextField tf = new TextField();
        tf.setPromptText(p); 
        tf.setStyle(INPUT);
        return tf;
    }

    private ComboBox<String> combo(String... items) { 
        ComboBox<String> cb = new ComboBox<>(); 
        cb.getItems().addAll(items); 
        cb.setStyle(INPUT); return cb; 
    }

    private Label msg() {
        Label l = new Label();
        l.setStyle("-fx-text-fill: white; -fx-font-weight: bold;"); 
        return l;
    }

    private boolean anyEmpty(TextField... fs) { 
        for (TextField f : fs) 
            if (f.getText().trim().isEmpty()) 
            return true; 
        return false;
    }


    private void clear(TextField... fs) {
        for (TextField f : fs) f.clear();
    }

     public static void main(String[] args){
        launch(args);
    }
     
 }
