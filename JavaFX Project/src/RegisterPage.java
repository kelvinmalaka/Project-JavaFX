import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;

public class RegisterPage {

    private BorderPane container;
    private TextField usernameTf;
    private TextField emailTf;
    private PasswordField passwordTf;
    private RadioButton maleRadio;
    private RadioButton femaleRadio;
    private Button registerBtn;
    private Hyperlink loginLink;
    private Main loginPage;

    public RegisterPage() {
        init();
    }

    private void init() {
        container = new BorderPane();
        loginPage = new Main();

        // Hyperlink
        loginLink = new Hyperlink("Already have an account? Click here to login!");
        loginLink.setOnAction(e -> redirectToMainPage());

        Label title = new Label("Register");
        title.setFont(Font.font("Arial", 20));
        title.setStyle("-fx-font-weight: bold;");

        Label usernameLbl = new Label("Username:");
        Label emailLbl = new Label("Email:");
        Label passwordLbl = new Label("Password:");
        Label genderLbl = new Label("Gender");
        genderLbl.setFont(Font.font("Arial", 20));
        genderLbl.setStyle("-fx-font-weight: bold;");

        usernameTf = new TextField();
        usernameTf.setPrefColumnCount(40);
        usernameTf.setPromptText("Input your username here");

        emailTf = new TextField();
        emailTf.setPrefColumnCount(40);
        emailTf.setPromptText("Input your email here");

        passwordTf = new PasswordField();
        passwordTf.setPrefColumnCount(40);
        passwordTf.setPromptText("Input your password here");

        maleRadio = new RadioButton("Male");
        femaleRadio = new RadioButton("Female");
        ToggleGroup genderGroup = new ToggleGroup();
        maleRadio.setToggleGroup(genderGroup);
        femaleRadio.setToggleGroup(genderGroup);

        // Menggunakan HBox untuk mengelompokkan radio button "Male" dan "Female"
        HBox genderBox = new HBox(10);
        genderBox.getChildren().addAll(maleRadio, femaleRadio);

        registerBtn = new Button("Register");
        registerBtn.setOnAction(e -> attemptRegistration());

        GridPane gridPane = new GridPane();
        gridPane.add(usernameLbl, 0, 0);
        gridPane.add(usernameTf, 0, 1);
        gridPane.add(emailLbl, 0, 2);
        gridPane.add(emailTf, 0, 3);
        gridPane.add(passwordLbl, 0, 4);
        gridPane.add(passwordTf, 0, 5);
        gridPane.add(genderLbl, 0, 6);
        gridPane.add(genderBox, 0, 7);
        gridPane.add(loginLink, 0, 8);

        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setAlignment(Pos.CENTER);

        // Set alignment untuk menempatkan gridPane di tengah container
        container.setTop(title);
        container.setCenter(gridPane);
        container.setAlignment(gridPane, Pos.CENTER);

        // Set alignment untuk menempatkan registerBtn di tengah container
        BorderPane.setAlignment(title, Pos.CENTER);
        container.setBottom(registerBtn);
        container.setAlignment(registerBtn, Pos.CENTER);
        container.setPadding(new Insets(50));
    }

    public BorderPane getContainer() {
        return container;
    }

    private void attemptRegistration() {
        // Validasi
        String username = usernameTf.getText();
        String email = emailTf.getText();
        String password = passwordTf.getText();
        boolean isMaleSelected = maleRadio.isSelected();
        boolean isFemaleSelected = femaleRadio.isSelected();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || (!isMaleSelected && !isFemaleSelected)) {
        	Alert alert = new Alert(Alert.AlertType.ERROR);
  		    alert.setTitle("Error");
  		    alert.setHeaderText("Register Error"); 
  		    alert.setContentText("All fields must be filled.");
  	        alert.showAndWait();
            return;
        }

        // Validasi Email must end with @gmail.com
        if (!email.toLowerCase().endsWith("@gmail.com")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
  		    alert.setTitle("Error");
  		    alert.setHeaderText("Register Error"); 
  		    alert.setContentText("Make sure your email ends with @gmail.com");
  	        alert.showAndWait();
            return;
        }

        // Validasi Password must have a length of 8 – 15 characters inclusively
        if (password.length() < 8 || password.length() > 15) {
        	Alert alert = new Alert(Alert.AlertType.ERROR);
   		    alert.setTitle("Error");
   		    alert.setHeaderText("Register Error"); 
   		    alert.setContentText("Make sure your password has a length of 8-15 characters");
   	        alert.showAndWait();
            return;
        }

        // Validasi Password must be Alphanumeric
        if (!password.matches("^[a-zA-Z0-9]+$")) {
        	
        	Alert alert = new Alert(Alert.AlertType.ERROR);
   		    alert.setTitle("Error");
   		    alert.setHeaderText("Register Error"); 
   		    alert.setContentText("Password must be alphanumeric");
   	        alert.showAndWait();
            return;
        }

        // Validasi keunikan username dan email
        if (!isUsernameUnique(username)) {
        	 Alert alert = new Alert(Alert.AlertType.ERROR);
   		    alert.setTitle("Error");
   		    alert.setHeaderText("Register Error"); 
   		    alert.setContentText("Please choose a diffrent username");
   	        alert.showAndWait();
        	
            return;
        }

        if (!isEmailUnique(email)) {
        	 Alert alert = new Alert(Alert.AlertType.ERROR);
   		    alert.setTitle("Error");
   		    alert.setHeaderText("Register Error"); 
   		    alert.setContentText("Please choose a diffrent email");
   	        alert.showAndWait();
            return;
        }

        // Create a new user object
        String role = username.toLowerCase().contains("admin") ? "Admin" : "User";
        User newUser = new User(username, email, "", role); // Empty password, will be set later
        newUser.generateUserID();  // Generate ID setelah memastikan validasi unik

        // Set the password for the new user
        newUser.setPassword(password);

        // Add the new user to the database
        Database.addUser(newUser);
        String userID = newUser.getUserID();
        System.out.println("User ID: " + userID);
        System.out.println("Role: " + role);
        redirectToMainPage();
    }

    private boolean isEmailUnique(String newEmail) {
        ArrayList<User> userList = Database.fetchUsers();
        for (User user : userList) {
            if (user.getEmail().equals(newEmail)) {
                return false; // Email sudah ada di database
            }
        }
        return true; // Email unique
    }

    private boolean isUsernameUnique(String newUsername) {
        ArrayList<User> userList = Database.fetchUsers();
        for (User user : userList) {
            if (user.getUsername().equals(newUsername)) {
                return false; // Username sudah ada di database
            }
        }
        return true; // Username unique
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void redirectToMainPage() {
        Main mainPage = new Main();
        Stage primaryStage = new Stage();
        try {
            mainPage.start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Tutup stage saat ini (halaman registrasi)
        Stage currentStage = (Stage) container.getScene().getWindow();
        currentStage.close();
    }
}
