import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class CartPage {

    private MenuBar navBar;
    private Menu menu;
    private MenuItem menuItemA, menuItemB, menuItemC;

    private ObservableList<CartItem> cartItems;
    private VBox cartItemsVBox;
    private BorderPane container;

    private Label ownerLabel, deleteItemLabel, courierLabel, courierPriceLabel, totalPriceLabel;
    private TableView<Cup> cupTableView;
    private ComboBox<String> courierComboBox;
    private CheckBox insuranceCheckBox;
    private Button deleteItemButton, checkoutButton;

    public CartPage() {
        cartItems = FXCollections.observableArrayList();
        cartItemsVBox = new VBox();
        init();
    }

    private void init() {
        container = new BorderPane();
        navBar = new MenuBar();
        menu = new Menu("Menu");

        menuItemA = new MenuItem("Home");
        menuItemB = new MenuItem("Chart");
        menuItemC = new MenuItem("Log Out");

        menuItemB.setOnAction(e -> redirectToHomePage());
        menuItemC.setOnAction(e -> redirectToMainPage());

        menu.getItems().addAll(menuItemA, menuItemB, menuItemC);
        navBar.getMenus().add(menu);
        container.setTop(navBar);

        ownerLabel = new Label("vncnt's Cart");
        ownerLabel.setFont(Font.font("Arial", 24));
        ownerLabel.setStyle("-fx-font-weight: bold;");

        deleteItemLabel = new Label("Delete Item:");
        courierLabel = new Label("Courier:");
        courierPriceLabel = new Label("Courier Price:");
        totalPriceLabel = new Label("Total Price:");

        cupTableView = new TableView<>();
        TableColumn<Cup, String> cupNameColumn = new TableColumn<>("Cup Name");
        TableColumn<Cup, Double> priceColumn = new TableColumn<>("Cup Price");
        TableColumn<Cup, Integer> quantityColumn = new TableColumn<>("Quantity");
        TableColumn<Cup, Integer> totalColumn = new TableColumn<>("Total");

        cupNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));

        cupTableView.getColumns().addAll(cupNameColumn, priceColumn, quantityColumn, totalColumn);

        courierComboBox = new ComboBox<>();
        courierComboBox.getItems().addAll("JNA", "TAKA", "LoinParcel", "IRX", "JINJA");

        insuranceCheckBox = new CheckBox("Use Delivery Insurance (+$2000)");

        deleteItemButton = new Button("Delete Item");
        deleteItemButton.setOnAction(e -> deleteItemButtonClicked());

        checkoutButton = new Button("Checkout");
        checkoutButton.setOnAction(e -> checkoutButtonClicked());

        // Menggunakan GridPane untuk menyusun elemen-elemen
        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setPadding(new Insets(10, 10, 10, 10));

        // Kolom pertama
        gridPane.add(ownerLabel, 0, 0, 2, 1);
        gridPane.add(deleteItemLabel, 0, 1);
        gridPane.add(cupTableView, 0, 2);
        gridPane.add(courierLabel, 0, 3);
        gridPane.add(courierComboBox, 0, 4);
        gridPane.add(courierPriceLabel, 0, 5);
        gridPane.add(insuranceCheckBox, 0, 6);

        // Kolom kedua
        VBox buttonBox = new VBox(10);
        buttonBox.getChildren().addAll(deleteItemButton, checkoutButton);
        gridPane.add(buttonBox, 1, 1, 1, 6);

        // Kolom ketiga
        gridPane.add(totalPriceLabel, 2, 6);

        // Menetapkan GridPane ke bagian tengah BorderPane
        container.setCenter(gridPane);
    }

    private void deleteItemButtonClicked() {
        // Logika penghapusan item dari keranjang
        // Anda dapat menggunakan cupTableView.getSelectionModel().getSelectedItem() untuk mendapatkan item yang dipilih
    }

    private void checkoutButtonClicked() {
        // Logika checkout item dari keranjang
        // Anda dapat menggunakan kurir terpilih, status asuransi, dan item keranjang untuk memproses checkout
    }

    private void redirectToHomePage() {
        Stage primaryStage = new Stage();

        // Mengasumsikan ada kelas bernama HomePage untuk halaman utama Anda
        HomePage homePage = new HomePage();
        Parent homePageParent = homePage.getContainer();

        primaryStage.setScene(new Scene(homePageParent, 1000, 500));
        primaryStage.show();

        Stage currentStage = (Stage) container.getScene().getWindow();
        currentStage.close();
    }

    private void redirectToMainPage() {
        try {
            Stage primaryStage = new Stage();
            Main mainPage = new Main();
            mainPage.start(primaryStage);

            Stage currentStage = (Stage) container.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
            // Tangani setiap pengecualian di sini
        }
    }

    // Metode-metode lainnya...


    public void addCartItem(CartItem cartItem) {
        cartItems.add(cartItem);
        updateCartUI();
    }

    public void updateCartItemQuantity(String cupName, int quantity) {
        for (CartItem item : cartItems) {
            if (item.getCupName().equals(cupName)) {
                item.setQuantity(item.getQuantity() + quantity);
                updateCartUI();
                return;
            }
        }
    }

    public boolean containsCup(String cupName) {
        for (CartItem item : cartItems) {
            if (item.getCupName().equals(cupName)) {
                return true;
            }
        }
        return false;
    }

    private void updateCartUI() {
        cartItemsVBox.getChildren().clear();
        for (CartItem item : cartItems) {
            Label cartItemLabel = new Label(item.getCupName() + " - Quantity: " + item.getQuantity());
            cartItemsVBox.getChildren().add(cartItemLabel);
        }
    }

    public double getTotalPrice() {
        double totalPrice = 0.0;
        for (CartItem item : cartItems) {
            totalPrice += item.getPrice() * item.getQuantity();
        }
        return totalPrice;
    }

    public ObservableList<CartItem> getCartItems() {
        return cartItems;
    }

    public Parent getContainer() {
        return container;
    }
}
