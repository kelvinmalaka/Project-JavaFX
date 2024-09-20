import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {

    BorderPane container;
    GridPane formContainer;

    Label title, nameLbl, passwordLbl;
    TextField nameTf;
    PasswordField passwordTf;
    Button loginBtn;
    Hyperlink registerLink;
    private RegisterPage registerPage;
    private UserNavigationBarPage userNavigationBarPage;
    private AdminNavigationBarPage adminNavigationBarPage;

    public static void main(String[] args) {
        launch();
    }

    public void init() {
        registerPage = new RegisterPage();
        userNavigationBarPage = new UserNavigationBarPage();
        adminNavigationBarPage = new AdminNavigationBarPage();

        registerLink = new Hyperlink("Don't have an account yet? Register Here!");
        registerLink.setOnAction(e -> redirectToRegisterPage());

        container = new BorderPane();
        formContainer = new GridPane();

        title = new Label("Login");
        title.setFont(Font.font("Arial", 20));
        title.setStyle("-fx-font-weight: bold;");

        nameLbl = new Label("Name");
        passwordLbl = new Label("Password");

        nameTf = new TextField();
        nameTf.setPromptText("");
        nameTf.setPrefColumnCount(40);

        passwordTf = new PasswordField();
        passwordTf.setPromptText("Input your password here");
        passwordTf.setPrefColumnCount(40);

        loginBtn = new Button("Login");
        loginBtn.setOnAction(e -> attemptLogin());
    }

    private void redirectToNavigationBarPage(String username) {
        String userRole = getUserRole(username);

        if ("user".equals(userRole)) {
            switchScene(userNavigationBarPage.getContainer());
        } else if ("admin".equals(userRole)) {
            switchScene(adminNavigationBarPage.getContainer());
        } else {
            System.out.println("Unknown role for user: " + username);
        }
    }

    private void redirectToRegisterPage() {
        Scene registerScene = new Scene(registerPage.getContainer(), 1000, 500);
        Stage primaryStage = (Stage) container.getScene().getWindow();
        primaryStage.setScene(registerScene);
    }

    private void attemptLogin() {
        String username = nameTf.getText();
        String password = passwordTf.getText();

        if (username.isEmpty() || password.isEmpty()) {
            displayAlert("Error", "Fill out your username and password.");
        } else if (validateCredentials(username, password)) {
            redirectToNavigationBarPage(username);
        } else {
            displayAlert("Error", "Invalid username or password.");
        }
    }

    private String getUserRole(String username) {
        // Replace this with actual logic to determine the user's role
        if ("admin".equals(username)) {
            return "admin";
        } else {
            return "user";
        }
    }


    private boolean validateCredentials(String username, String password) {
        // Dummy implementation, replace with actual logic
        return true;
    }

    private void displayAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Login Error");
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void switchScene(Parent parent) {
        Scene scene = new Scene(parent, 1000, 500);
        Stage primaryStage = (Stage) container.getScene().getWindow();
        primaryStage.setScene(scene);
    }

    public void position() {
        container.setTop(title);
        container.setCenter(formContainer);
        container.setBottom(loginBtn);
        container.setPadding(new Insets(150));

        formContainer.add(nameLbl, 0, 0);
        formContainer.add(nameTf, 1, 0);

        formContainer.add(passwordLbl, 0, 1);
        formContainer.add(passwordTf, 1, 1);

        formContainer.setVgap(10);
        formContainer.setHgap(10);

        formContainer.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(title, Pos.CENTER);
        BorderPane.setAlignment(loginBtn, Pos.CENTER);

        BorderPane.setMargin(loginBtn, new Insets(10, 0, 0, 0));

        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(loginBtn, registerLink);

        container.setBottom(vbox);
    }

    @Override
    public void start(Stage stage) throws Exception {
        init();
        position();
        
        HomePage homePage = new HomePage();
        UserNavigationBarPage userNavBar = new UserNavigationBarPage();
        CartPage cartPage= new CartPage();
        
        BorderPane mainContainer = new BorderPane();
        mainContainer.setTop(userNavBar.getContainer());
        mainContainer.setCenter(homePage.getContainer());
        mainContainer.setBottom(cartPage.getContainer());
        
        Scene scene = new Scene(container, 1000, 500);
       
        
        stage.setScene(scene);
        stage.setTitle("cangkIR");
        stage.show();
    }

}