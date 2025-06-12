package theknife;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class RestaurantsController {
    @FXML
    private ListView<String> restaurantListView;

    @FXML
    public void initialize() {
        loadRestaurants();
    }

    private void loadRestaurants() {
        restaurantListView.getItems().clear();
        // Placeholder: in real app we'd load restaurants owned by the user
        restaurantListView.getItems().add("My Restaurant 1");
    }

    @FXML
    private void handleGoHome() {
        // TODO: Implement navigation to home page
        System.out.println("Go Home button clicked");
    }

    @FXML
    private void handleClose() {
        // TODO: Implement close functionality
        System.out.println("Close button clicked");
    }

    @FXML
    private void handleAdd() {
        // TODO: Implement add restaurant functionality
        System.out.println("Add button clicked");
    }
}
