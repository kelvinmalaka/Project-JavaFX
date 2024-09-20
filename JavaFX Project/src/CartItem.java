import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class CartItem {
    private final SimpleStringProperty cupName;
    private final SimpleDoubleProperty price;
    private final SimpleDoubleProperty quantity;

    public CartItem(String cupName, double price, int quantity) {
        this.cupName = new SimpleStringProperty(cupName);
        this.price = new SimpleDoubleProperty(price);
        this.quantity = new SimpleDoubleProperty(quantity);
    }

    // getters and setters

    public String getCupName() {
        return cupName.get();
    }

    public void setCupName(String cupName) {
        this.cupName.set(cupName);
    }

    public double getPrice() {
        return price.get();
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public double getQuantity() {
        return quantity.get();
    }

    public void setQuantity(double quantity) {
        this.quantity.set(quantity);
    }

    // properties

    public SimpleStringProperty cupNameProperty() {
        return cupName;
    }

    public SimpleDoubleProperty priceProperty() {
        return price;
    }

    public SimpleDoubleProperty quantityProperty() {
        return quantity;
    }
}
