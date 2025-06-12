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
}
