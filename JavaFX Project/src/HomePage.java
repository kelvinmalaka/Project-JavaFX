import javafx.scene.Parent;

import java.util.Arrays;
import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class HomePage {
	
	private MenuBar navBar;
    private Menu menu;
    private MenuItem menuItemA, menuItemB, menuItemC;
    private CartPage cartPage;

    private BorderPane container;
	 private Label tableTitleLabel, cupNameLabel, priceLabel;
	 private TableView<Cup> cupTableView;
	 private Spinner<Integer> quantitySpinner;
	 private Button addToCartButton;
    public HomePage() {
        init();
    }

    private void init() {
    	 container = new BorderPane();

         navBar = new MenuBar();
         menu = new Menu("Menu");

         menuItemA = new MenuItem("Home");
         menuItemB = new MenuItem("Chart");
         menuItemC = new MenuItem("Log Out");

         menuItemB.setOnAction(e -> redirectToCartPage());
         menuItemC.setOnAction(e -> redirectToMainPage());

         menu.getItems().addAll(menuItemA, menuItemB, menuItemC);
         navBar.getMenus().add(menu);
         container.setTop(navBar);
         
         cartPage = new CartPage();

         tableTitleLabel = new Label("Cup List");
         cupNameLabel = new Label("Cup Name: ");
         cupNameLabel.setFont(Font.font("Arial", 20));
         cupNameLabel.setStyle("-fx-font-weight: bold;");
         
         priceLabel = new Label("Price: ");
         priceLabel.setFont(Font.font("Arial", 20));
         priceLabel.setStyle("-fx-font-weight: bold;");
         
         cupTableView = new TableView<>();
         TableColumn<Cup, String> cupNameColumn = new TableColumn<>("Cup Name");
         TableColumn<Cup, Double> priceColumn = new TableColumn<>("Price");

         cupNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
         priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

         quantitySpinner = new Spinner<>(1, 20, 1);

         addToCartButton = new Button("Add to Cart");
         addToCartButton.setOnAction(e -> addToCartButtonClicked());

         GridPane gridPane = new GridPane();
         gridPane.setVgap(10);
         gridPane.setHgap(10);
         gridPane.setPadding(new Insets(10, 10, 10, 10));

         gridPane.add(tableTitleLabel, 0, 0, 2, 1);
         gridPane.add(cupTableView, 0, 1, 1, 1);

         VBox cupInfoVBox = new VBox(5);
         cupInfoVBox.getChildren().addAll(cupNameLabel, quantitySpinner, priceLabel, addToCartButton);

         gridPane.add(cupInfoVBox, 1, 1);
         container.setLeft(gridPane);

         // Sample data for TableView
         List<Cup> cupData = Arrays.asList(
                 new Cup("Porcelain small cup", 15000),
                 new Cup("Porcelain jug", 33000),
                 new Cup("Glass jug", 35000),
                 new Cup("Wooden cup", 8000),
                 new Cup("Ceramic tea cup set", 280000),
                 new Cup("Plastic jug", 20000),
                 new Cup("Plastic small cup", 12000),
                 new Cup("Plastic normal cup", 17000),
                 new Cup("Japanese tea cup", 100000),
                 new Cup("Tester cup", 12345)
         );

         ObservableList<Cup> observableCupData = FXCollections.observableArrayList(cupData);
         cupTableView.setItems(observableCupData);
         cupTableView.getColumns().addAll(cupNameColumn, priceColumn);
     
    }



    private void redirectToMainPage() {
        try {
            // Implement the logic for redirecting to the main page
            // This might involve creating a new stage and loading the main page content

           
            Stage primaryStage = new Stage();
            Main mainPage = new Main();
            mainPage.start(primaryStage);

            // Close the current stage (home page)
            Stage currentStage = (Stage) container.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
            // Handle any exceptions here
        }
    }

    private void redirectToCartPage() {
        // Create a new stage for the cart page
        Stage primaryStage = new Stage();

        //there is a getContainer() method in  CartPage class
        Parent cartPageParent = cartPage.getContainer();

        // Set the scene with the cart page container
        primaryStage.setScene(new Scene(cartPageParent, 1000, 500));
        primaryStage.show();

        // Close the current stage (home page)
        Stage currentStage = (Stage) container.getScene().getWindow();
        currentStage.close();
    }


	



	private void addToCartButtonClicked() {
	    // Validation logic 
	    Cup selectedCup = cupTableView.getSelectionModel().getSelectedItem();
	    int quantity = quantitySpinner.getValue();

	    // Check if cartPage is null
	    if (cartPage == null) {
	        System.err.println("Error: Cart page is not initialized.");
	        return;
	    }

	    // Check if selectedCup is null
	    if (selectedCup == null) {
	        showAlert("Error", "Please select a cup to be added.");
	        return;
	    }

	    // Check if cupNameLabel or priceLabel is null
	    if (cupNameLabel == null || priceLabel == null) {
	        System.err.println("Error: Cup name label or price label is not initialized.");
	        return;
	    }

	    // Validation: A cup must be selected before adding it to the cart
	    if (quantity > 0) {
	        double totalPrice = selectedCup.getPrice() * quantity; // Calculate total price

	        // Logic for adding the cup to the cart
	        CartItem cartItem = new CartItem(selectedCup.getName(), selectedCup.getPrice(), quantity);

	        // Check if the cup is already in the cart
	        if (cartPage.containsCup(selectedCup.getName())) {
	            // Condition 2: If the cup is already in the cart, update the quantity
	            cartPage.updateCartItemQuantity(selectedCup.getName(), quantity);
	        } else {
	            // Condition 1: If the cup is not in the cart, add a new cart item
	            cartPage.addCartItem(cartItem);
	        }

	        // Display the information
	        cupNameLabel.setText("Cup Name: " + selectedCup.getName());
	        priceLabel.setText("Price: $" + totalPrice);
	        System.out.println("Quantity: " + quantity);

	        showAlertInfo("Message", "Item successfully added to the cart.\nTotal Price: $" + totalPrice);
	    } else {
	        showAlert("Error", "Quantity must be greater than 0.");
	    }
	}

	   private void showAlertInfo(String title, String content) {
		   
		   Alert alert = new Alert(Alert.AlertType.INFORMATION);
		    alert.setTitle("message");
		    alert.setHeaderText("Chart Info"); 
		    alert.setContentText("Item Successfully Added!");
	        alert.showAndWait();
		   
		   
	   }
	   
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Please select a cup to be added!");
        alert.setTitle("Error");
        alert.setHeaderText("Chart Error");
        alert.setContentText(content);
        alert.showAndWait();
    }
    private void switchScene(Parent parent) {
        Stage stage = (Stage) container.getScene().getWindow();
        stage.setScene(new Scene(parent, 1000, 500));
    }
    

    public Parent getContainer() {
        return container;
    }
}
