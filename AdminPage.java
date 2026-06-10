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
    private UI mainApp;

    private void refreshRequestList() {
        requestList.clear();
        for (AdoptionRequest req : AdoptionRequest.getAllRequests()) {
            requestList.add(req.toRow());
        }
    }
    // Final style
    private static final String adminBtnStyle = 
                "-fx-background-color: #838F58;" 
                + "-fx-border-radius: 10;"
                + "-fx-border-color: #EAECE6;"
                + "-fx-border-width: 3;"
                + "-fx-text-fill: white;"
                + "-fx-font-weight: bold;"
                + "-fx-font-size: 16px;" 
                + "-fx-background-radius: 10;"
                + "-fx-padding: 10 25;" 
                + "-fx-cursor: hand;";
    private static final String inputStyle = "-fx-background-color: white;" 
        + "-fx-border-color: #F9D1D9;" 
        + "-fx-border-width: 2;" 
        + "-fx-border-radius: 10;" 
        + "-fx-background-radius: 10;" 
        + "-fx-padding: 8;" 
        + "-fx-pref-width: 200;";
    private static final String GREEN = "-fx-background-color: #F9D1D9;"
        + " -fx-text-fill: #333333; "
        + "-fx-font-weight: bold;"
        + " -fx-background-radius: 10; "
        + "-fx-padding: 10 25;"
        + " -fx-cursor: hand;";
    private static final String RED   = "-fx-background-color: #e74c3c; "
        + "-fx-text-fill: white; "
        + "-fx-font-weight: bold;"
        + " -fx-background-radius: 8; "
        + "-fx-padding: 6 14;"
        + " -fx-cursor: hand;";
    
    @Override
    public void start(Stage primaryStage){
        start(primaryStage, null);
    }

    // Main method to set up the admin dashboard with navigation and options to manage users, pets, and requests.
    public void start(Stage primaryStage, UI mainApp){
        this.mainApp = mainApp;
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #838F58;");
        root.setAlignment(Pos.TOP_CENTER);
        
        // dashboard Admin content
        VBox content = new VBox(25);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(40));
        content.setStyle(
                    "-fx-background-color: #F9D1D9;" +
                    "-fx-background-radius: 20;");
        
        // Title label with enhanced styling
        Label lblTitle = new Label("ADMIN DASHBOARD");
        lblTitle.setStyle(
                "-fx-font-size: 28px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #838F58;");
        
        // Admin's options of buttons 
        Button manageUsersBtn = new Button("Manage Users");
        Button managePetsBtn = new Button("Manage Pets");
        Button manageReqBtn = new Button("Manage Requests");
        Button logoutBtn = new Button("Logout");

        // Apply consistent styling to all admin buttons
        manageUsersBtn.setStyle(adminBtnStyle);
        managePetsBtn.setStyle(adminBtnStyle);
        manageReqBtn.setStyle(adminBtnStyle);
        logoutBtn.setStyle(adminBtnStyle);

        // actions of these buttons 
        manageUsersBtn.setOnAction(e -> 
                openUsers(primaryStage));
        
        managePetsBtn.setOnAction(e -> 
                openPets(primaryStage));
        
        manageReqBtn.setOnAction(e -> 
                openReq(primaryStage));
        
        logoutBtn.setOnAction(e -> {
            if (mainApp != null) {
                mainApp.LoginUI();
            } else {
                primaryStage.close();
            }
        });
        
        // Add all components to the content area and then to the root layout
        content.getChildren().addAll(
                lblTitle, 
                manageUsersBtn,
                managePetsBtn,
                manageReqBtn,
                logoutBtn);
        
        root.getChildren().addAll(content);
        
        // Set up the scene and stage
        Scene scene = new Scene(root, 1000, 700);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Strays to Heaven - Admin Dashboard");
        primaryStage.show();
        
    }

    // Helper methods for creating styled buttons, opening modals, and displaying tables for user, pet, and request management.
    private Button createNavButton(String text, String style){
        Button btn = new Button(text);
        btn.setStyle(style);
        return btn;
    }
    
    // alert popup placeholder
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

    // Method to open the user management modal with a table of users and a form to add new users.
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
        textID.setStyle(inputStyle);
        textName.setStyle(inputStyle);
        textEmail.setStyle(inputStyle);

        ComboBox<String> comRole = combo("User", "Admin");
        Label msg = msg();
        comRole.setStyle(inputStyle);
        
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

    // Method to open the pet management modal with a table of pets and a form to add new pets, including delete functionality for existing pets.
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

        // Set a fixed width for the delete column
        delCol.setCellFactory(c -> new TableCell<>() {
            Button del = delBtn(); {
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

        // input styling
        TextField textID = f("Pet ID"), textBreed = f("Breed"), textAge = f("Age"),
                    textTraits = f("Traits");
    
        textID.setStyle(inputStyle);
        textBreed.setStyle(inputStyle);
        textAge.setStyle(inputStyle);
        textTraits.setStyle(inputStyle);

        ComboBox<String> comGender = combo("Male", "Female");
        Label msg = msg();
        comGender.setStyle(inputStyle);

        Button add = btn("Add Pet");
        add.setOnAction(e -> {
            try {
                if (anyEmpty(textID, textBreed, textAge) || comGender.getValue() == null){
                    msg.setText("Please fill all fields.");
                    return ;
                }
                // Create a new Pet object and add it to the PetManager and the observable list for display in the table.
                Pet p = new Pet(textID.getText(), Integer.parseInt(textAge.getText()),
                textBreed.getText(), comGender.getValue(), textTraits.getText(), "Available");
                PetManager.addPet(p);
                pets.add(p);
                clear(textID, textBreed, textAge, textTraits);
                comGender.setValue(null);
                msg.setText("Pet added!");
            } catch (NumberFormatException ex) {
                msg.setText("Age must be a number.");
            }
        });
    
        show(s, "Pet Management", table, "Add Pet", row(textID, textBreed), row(textAge, comGender), row(textTraits, null), add, msg);
    }

    // Method to open the adoption request management modal with a table of requests and options to approve or reject each request, as well as add new requests.
    private void openReq(Stage owner){
    
        refreshRequestList();
    
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

        // Set a custom cell factory for the action column to display Approve and Reject buttons for each request, allowing the admin to update the status of the request directly from the table.
        actCol.setCellFactory(c -> new TableCell<>(){
            Button app = new Button("Approve");
            Button rej = new Button("Reject");
            HBox box = new HBox(8, app, rej);
            {
                app.setStyle(GREEN);
                rej.setStyle(RED);
                box.setAlignment(Pos.CENTER);
                app.setOnAction(e -> {
                    String requestID = requestList.get(getIndex())[0];
                    AdoptionRequest req = AdoptionRequest.findByRequestID(requestID);
                    if (req != null) {
                        req.updateStatus("Approved");
                    } 
                    requestList.get(getIndex())[4]= "Approved";
                    table.refresh();
                });
                rej.setOnAction(e -> {
                    String requestID = requestList.get(getIndex())[0];
                    AdoptionRequest req = AdoptionRequest.findByRequestID(requestID);
                    if (req != null) {
                        req.updateStatus("Rejected");
                    }
                    requestList.get(getIndex())[4] = "Rejected";
                    table.refresh();
                });
            }

            // Override the updateItem method to display the action buttons only for pending requests and hide them for approved or rejected requests.
            protected void updateItem(String i , boolean empty) {
                super.updateItem(i, empty);
                setGraphic(empty ? null :  box) ;
            }
        });

        table.getColumns().add(actCol);

        TextField textReqID = f("Request ID"), textUID = f("User ID"),
              textPID = f("Pet ID"), textDate = f("Date");
        Label msg = msg();

        textReqID.setStyle(inputStyle);
        textUID.setStyle(inputStyle);
        textPID.setStyle(inputStyle);
        textDate.setStyle(inputStyle);
        
        Button add = btn("Add Request");
        Button refresh = btn("Refresh");

        refresh.setOnAction(e -> {
            refreshRequestList();
            table.refresh();
            msg.setText("🔄 Request list refreshed.");
        });
    
        add.setOnAction(e -> {
            if (anyEmpty(textReqID, textUID, textPID, textDate)) { 
                msg.setText("⚠ Fill all fields.");
                return; 
            }
            AdoptionRequest req = new AdoptionRequest();
            req.sendAdoptionRequest(textReqID.getText(), textUID.getText(), textPID.getText(), textDate.getText());
            requestList.add(req.toRow());
            clear(textReqID, textUID, textPID, textDate); 
            msg.setText("✅ Request added!");
        });

        show(s, "Adoption requests", table, "Add Request", row(textReqID, textUID), row(textPID, textDate), row(add, refresh), msg);

    }

    // Utility method to create a modal stage with specified owner and title, used for displaying the user, pet, and request management interfaces.
    private Stage modal(Stage owner, String title) {
        Stage s = new Stage();
        s.initModality(Modality.WINDOW_MODAL);
        s.initOwner(owner);
        s.setTitle(title);
        return s;
    }

    // Utility method to display a table with a form and message label in a structured layout within a given stage, used for the management interfaces.
    private void show(Stage s, String title, javafx.scene.Node table, String formTitle, javafx.scene.Node...nodes) {
        Label lbl = new Label(title);
        lbl.setStyle("-fx-font-size: 20px; "
            + "-fx-font-weight: bold; -fx-text-fill: white;");
        Label flbl = new Label(formTitle);
        flbl.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;"
            + " -fx-text-fill: white;");
        VBox box = new VBox(12, lbl, table, new Separator(), flbl);
        
        // Add all provided nodes to the VBox layout, ensuring that null nodes are skipped to avoid adding empty spaces.
        for (javafx.scene.Node n : nodes) if (n != null) box.getChildren().add(n);
        box.setPadding(new Insets(25)); box.setStyle("-fx-background-color: #838F58;");
        ScrollPane sp = new ScrollPane(box); sp.setFitToWidth(true);
        sp.setStyle("-fx-background: #838F58; -fx-background-color: #838F58;");
        s.setScene(new Scene(sp, 700, 580)); s.show();
    }

    // Utility method to create a horizontal box layout containing two nodes, used for structuring form fields in the management interfaces.
    private HBox row(javafx.scene.Node a, javafx.scene.Node b){
        HBox h = new HBox(12);
        if ( a!= null)
            h.getChildren().add(a);
        if ( b!= null)
            h.getChildren().add(b);
        return h ;
    }

    // Utility method to create a TableView with a placeholder message for when there is no data, used for displaying user, pet, and request information in the management interfaces.
    private <T> TableView<T> table(){
        TableView<T> t = new TableView<>();
        t.setPlaceholder(new Label("No data"));
        t.setPrefHeight(220);
        return t ;
    }

    // Utility method to add a column to a TableView for displaying string data from an array, used for the user and request tables in the management interfaces.
    private void addCol(TableView<String[]> t, String h, int i) { 
        TableColumn<String[],String> c = new TableColumn<>(h);
        c.setCellValueFactory(d -> new SimpleStringProperty(d.getValue()[i])); 
        t.getColumns().add(c); 
    }

    // Utility method to add a column to a TableView for displaying string data from a generic type using a provided function, used for the pet table in the management interface.
    private <T> void petCol(TableView<T> t, String h, java.util.function.Function<T,String> fn) { 
        TableColumn<T,String> c = new TableColumn<>(h); 
        c.setCellValueFactory(d -> new SimpleStringProperty(fn.apply(d.getValue()))); 
        t.getColumns().add(c);
    }

    // Utility method to add a delete button column to a TableView, allowing for the removal of items from the observable list when the button is clicked, used in the user and pet management interfaces.
    private void addDeleteCol(TableView<String[]> t, ObservableList<String[]> list) {
        TableColumn<String[],String> c = new TableColumn<>("Action");

        // Set a custom cell factory for the delete column to display a delete button for each row, allowing the admin to remove users or pets directly from the table.
        c.setCellFactory(col -> new TableCell<>() { Button d = delBtn(); 
            { d.setOnAction(e -> list.remove(getIndex())); } 
    
            // Override the updateItem method to display the delete button only for non-empty rows and hide it for empty rows, ensuring that the button is not shown when there is no data in the row.
            protected void updateItem(String i, boolean empty) { 
                super.updateItem(i,empty);
                setGraphic(empty?null:d); 
            } 
        });
        t.getColumns().add(c);
    }

    // Utility method to create a styled button with consistent design for the admin dashboard.
    private Button btn(String t) { 
        Button b = new Button(t);
        b.setStyle(GREEN); 
        b.setMinWidth(130);
        return b;
    }

    // Delete button with red styling for removing entries from tables.
    private Button delBtn(){
        Button b = new Button("Delete");
        b.setStyle(RED);
        return b;
    }

    // Utility method to create a styled TextField with a given prompt text.
    private TextField f(String p) {
        TextField tf = new TextField();
        tf.setPromptText(p); 
        tf.setStyle(inputStyle);
        return tf;
    }

    // Utility method to create a styled ComboBox with given items.
    private ComboBox<String> combo(String... items) { 
        ComboBox<String> cb = new ComboBox<>(); 
        cb.getItems().addAll(items); 
        cb.setStyle(inputStyle); return cb; 
    }

    // Utility method to create a styled label for displaying messages to the admin.
    private Label msg() {
        Label l = new Label();
        l.setStyle("-fx-text-fill: white; -fx-font-weight: bold;"); 
        return l;
    }

    // Utility method to check if any of the provided text fields are empty.
    private boolean anyEmpty(TextField... fs) { 
        for (TextField f : fs) 
            if (f.getText().trim().isEmpty()) 
            return true; 
        return false;
    }

    // Utility method to clear multiple text fields at once.
    private void clear(TextField... fs) {
        for (TextField f : fs) f.clear();
    }

     public static void main(String[] args){
        launch(args);
    }
     
 }
