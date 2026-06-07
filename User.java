public class User {
    private String userID;
    private String userName;
    private String userEmail;
    private String userPass;

    public User(String userID, String userName, String userEmail, String userPass) {
        this.userID = userID;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPass = userPass;
    }
    
    //returns boolean as we use boolean for login
    public boolean login(String email, String password){
        return this.userEmail.equals(email) && this.userPass.equals(password);
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

    public void getDetails() {
        System.out.println("User ID: " + userID); 
        System.out.println("Name: " + userName);
        System.out.println("Email: " + userEmail);
    }

}
