package theknife;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;

import theknife.UserSession;
import theknife.Restaurant;

import java.util.Optional;

public class RestaurantsController {
    @FXML
    private ListView<String> restaurantListView;

    @FXML
    private Button addRestaurantBtn;

    @FXML
    public void initialize() {
        loadRestaurants();
    }

    @FXML
    private void handleAddRestaurant() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Restaurant");
        dialog.setHeaderText("Enter restaurant name");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent() && !result.get().trim().isEmpty()) {
            UserSession session = UserSession.getInstance();
            int id = session.getOwnedRestaurants().size() + 1;
            Restaurant r = new Restaurant(id, result.get().trim(), "", 0.0, 0, "", "", "", "");
            session.addOwnedRestaurant(r);
            loadRestaurants();
        }
    }

    private void loadRestaurants() {
        restaurantListView.getItems().clear();
        for (Restaurant r : UserSession.getInstance().getOwnedRestaurants()) {
            restaurantListView.getItems().add(r.getName());
        }
        if (restaurantListView.getItems().isEmpty()) {
            restaurantListView.getItems().add("No restaurants added.");
        }
    }
}
