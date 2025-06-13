package theknife;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

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
    private Button backButton;

    @FXML
    private ImageView arrowImageView;

    @FXML
    public void initialize() {
        updateButtonVisibility();
        loadFavorites();

        try {
            Image arrowImage = new Image(getClass().getResourceAsStream("/images/left-arrow.png"));
            arrowImageView.setImage(arrowImage);
            ColorAdjust whiteEffect = new ColorAdjust();
            whiteEffect.setBrightness(1.0);
            arrowImageView.setEffect(whiteEffect);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadFavorites() {
        favoritesListView.getItems().clear();
        if (UserSession.getInstance().getFavorites().isEmpty()) {
            favoritesListView.getItems().add("No favorites yet.");
            favoritesListView.setDisable(true);
        } else {
            favoritesListView.setDisable(false);
            for (Restaurant r : UserSession.getInstance().getFavorites()) {
                favoritesListView.getItems().add(r.getName());
            }
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

    @FXML
    private void handleBack() {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/view/dashboard.fxml"));
            javafx.scene.Parent root = loader.load();

            javafx.scene.Scene scene = backButton.getScene();
            scene.setRoot(root);

            javafx.stage.Stage stage = (javafx.stage.Stage) scene.getWindow();
            stage.setTitle("Dashboard");
            stage.setMaximized(true);
            stage.setFullScreenExitHint("");
            stage.setFullScreenExitKeyCombination(javafx.scene.input.KeyCombination.NO_MATCH);
            stage.setFullScreen(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleGoHome() {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/view/dashboard.fxml"));
            javafx.scene.Parent root = loader.load();

            javafx.scene.Scene scene = backButton.getScene();
            scene.setRoot(root);

            javafx.stage.Stage stage = (javafx.stage.Stage) scene.getWindow();
            stage.setTitle("Dashboard");
            stage.setMaximized(true);
            stage.setFullScreenExitHint("");
            stage.setFullScreenExitKeyCombination(javafx.scene.input.KeyCombination.NO_MATCH);
            stage.setFullScreen(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}