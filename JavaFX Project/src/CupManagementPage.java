import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class CupManagementPage {

    private BorderPane container;

    public CupManagementPage() {
        init();
    }

    private void init() {
        container = new BorderPane();

        Label label = new Label("Cup Management Page");
        container.setCenter(label);
    }

    public Parent getContainer() {
        return container;
    }
}
