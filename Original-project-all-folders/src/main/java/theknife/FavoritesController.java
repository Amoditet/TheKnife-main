package theknife;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class FavoritesController {
    @FXML
    private BorderPane rootPane;
    @FXML
    private ListView<String> favoritesListView;
    @FXML
    private HBox guestButtons;
    @FXML
    private HBox userButtons;
    @FXML
    private Button signInBtn;
    @FXML
    private Button bookBtn;
    @FXML
    private Button favoriteBtn;
    @FXML
    private Button exitBtn;
    @FXML
    private Button RestaurantsBtn;

    @FXML
    public void initialize() {
        updateButtonVisibility();
        loadFavorites();
    }

    private void loadFavorites() {
        favoritesListView.getItems().clear();
        for (Restaurant r : UserSession.getInstance().getFavorites()) {
            favoritesListView.getItems().add(r.getName());
        }
    }

    private void updateButtonVisibility() {
        UserSession session = UserSession.getInstance();
        if (session.isNotLoggedIn()) {
            guestButtons.setVisible(true);
            userButtons.setVisible(false);
            signInBtn.setVisible(true);
            favoriteBtn.setVisible(false);
            bookBtn.setVisible(false);
            if (RestaurantsBtn != null) {
                RestaurantsBtn.setVisible(false);
            }
        } else {
            String role = session.getRole();
            guestButtons.setVisible(false);
            userButtons.setVisible(true);
            signInBtn.setVisible(false);
            if ("client".equalsIgnoreCase(role) || "cliente".equalsIgnoreCase(role)) {
                favoriteBtn.setVisible(true);
                bookBtn.setVisible(true);
                if (RestaurantsBtn != null) {
                    RestaurantsBtn.setVisible(false);
                }
            } else if ("owner".equalsIgnoreCase(role) || "ristoratore".equalsIgnoreCase(role)) {
                favoriteBtn.setVisible(false);
                bookBtn.setVisible(false);
                if (RestaurantsBtn != null) {
                    RestaurantsBtn.setVisible(true);
                }
            } else {
                favoriteBtn.setVisible(false);
                bookBtn.setVisible(false);
                if (RestaurantsBtn != null) {
                    RestaurantsBtn.setVisible(false);
                }
            }
        }
    }

    @FXML
    private void handleExitAction() {
        javafx.application.Platform.exit();
        System.exit(0);
    }
}
