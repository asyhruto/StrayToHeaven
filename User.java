public class User {
    private String userID;
    private String userName;
    private String userEmail;
    private String userPass;

    public User(String userName, String userEmail, String userPass, String userID) {
        this.userID = userID;
        this.name = userName;
        this.email = userEmail;
        this.password = password;
    }

    public String getName() {
        return userName;
    }

    public String getEmail() {
        return userEmail;
    }

    public String getPassword() {
        return userPass;
    }

    public String getUserID() {
        return userID;
    }

    public void login(String email, String password) {
        if (this.userEmail.equals(email) && this.userPass.equals(password)) {
            System.out.println("Login successful!"); //add function to redirect to the home page, this is only a placeholder
        } else {
            System.out.println("Invalid email or password."); // add function call for empty input box so user can try again, just placeholder
        }
    }

    public void getDetails() {
        System.out.println("User ID: " + userID); 
        System.out.println("Name: " + userName);
        System.out.println("Email: " + userEmail);
    }

}