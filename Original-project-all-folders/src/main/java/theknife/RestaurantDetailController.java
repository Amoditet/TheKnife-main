package theknife;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView; // Keep import for arrowImageView
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class RestaurantDetailController {

    @FXML
    private Text nameText;
    @FXML
    private ImageView arrowImageView;
    @FXML
    private ImageView heartImageView;
    @FXML
    private Text cuisineText;
    @FXML
    private Text ratingText;
    @FXML
    private Text reviewsText;
    @FXML
    private Text priceText;
    @FXML
    private Text distanceText;
    @FXML
    private TextArea descriptionArea;
    @FXML
    private Button closeButton;
    @FXML
    private Button backButton;

    @FXML
    private Button favoriteButton;
    @FXML
    private Button bookButton;
    @FXML
    private Button reviewButton;
    @FXML
    private Button bookBtn;
    @FXML
    private Button favoriteBtn;
    @FXML
    private Button signInBtn;

    private Restaurant restaurant;

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
        updateUI();
    }

    @FXML
    public void initialize() {
        try {
            Image arrowImage = new Image(getClass().getResourceAsStream("/images/left-arrow.png"));
            arrowImageView.setImage(arrowImage);
            javafx.scene.effect.ColorAdjust whiteEffect = new javafx.scene.effect.ColorAdjust();
            whiteEffect.setBrightness(1.0);
            arrowImageView.setEffect(whiteEffect);

            arrowImageView.sceneProperty().addListener((obs, oldScene, newScene) -> {
                if (newScene != null) {
                    javafx.stage.Stage stage = (javafx.stage.Stage) newScene.getWindow();
                    stage.setMaximized(true);
                    stage.setFullScreenExitHint("");
                    stage.setFullScreenExitKeyCombination(javafx.scene.input.KeyCombination.NO_MATCH);
                    stage.setFullScreen(true);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Image heartImage = new Image(getClass().getResourceAsStream("/images/heart.png"));
            heartImageView.setImage(heartImage);

            heartImageView.sceneProperty().addListener((obs, oldScene, newScene) -> {
                if (newScene != null) {
                    javafx.stage.Stage stage = (javafx.stage.Stage) newScene.getWindow();
                    stage.setMaximized(true);
                    stage.setFullScreenExitHint("");
                    stage.setFullScreenExitKeyCombination(javafx.scene.input.KeyCombination.NO_MATCH);
                    stage.setFullScreen(true);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (bookBtn != null) {
            bookBtn.managedProperty().bind(bookBtn.visibleProperty());
        }
        if (favoriteBtn != null) {
            favoriteBtn.managedProperty().bind(favoriteBtn.visibleProperty());
        }
        if (signInBtn != null) {
            signInBtn.managedProperty().bind(signInBtn.visibleProperty());
        }

        updateButtonVisibility();
    }

    public void updateButtonVisibility() {
        theknife.UserSession userSession = theknife.UserSession.getInstance();
        if (userSession.isNotLoggedIn()) {
            favoriteButton.setVisible(false);
            bookButton.setVisible(false);
            reviewButton.setVisible(false);
            if (bookBtn != null) {
                bookBtn.setVisible(false);
            }
            if (favoriteBtn != null) {
                favoriteBtn.setVisible(false);
            }
            if (signInBtn != null) {
                signInBtn.setVisible(true);
            }
        } else {
            String role = userSession.getRole();
            if ("client".equalsIgnoreCase(role) || "cliente".equalsIgnoreCase(role)) {
                favoriteButton.setVisible(true);
                bookButton.setVisible(true);
                reviewButton.setVisible(true);
                if (bookBtn != null) {
                    bookBtn.setVisible(true);
                }
                if (favoriteBtn != null) {
                    favoriteBtn.setVisible(true);
                }
                if (signInBtn != null) {
                    signInBtn.setVisible(false);
                }
            } else if ("owner".equalsIgnoreCase(role) || "ristoratore".equalsIgnoreCase(role)) {
                favoriteButton.setVisible(false);
                bookButton.setVisible(false);
                reviewButton.setVisible(false);
                if (bookBtn != null) {
                    bookBtn.setVisible(false);
                }
                if (favoriteBtn != null) {
                    favoriteBtn.setVisible(false);
                }
                if (signInBtn != null) {
                    signInBtn.setVisible(false);
                }
            } else {
                favoriteButton.setVisible(false);
                bookButton.setVisible(false);
                reviewButton.setVisible(false);
                if (bookBtn != null) {
                    bookBtn.setVisible(false);
                }
                if (favoriteBtn != null) {
                    favoriteBtn.setVisible(false);
                }
                if (signInBtn != null) {
                    signInBtn.setVisible(false);
                }
            }
        }
    }

    private void updateUI() {
        if (restaurant == null)
            return;

        nameText.setText(restaurant.getName());
        cuisineText.setText("Cuisine: " + restaurant.getCuisine());
        ratingText.setText(String.format("Rating: %.1f", restaurant.getRating()));
        reviewsText.setText("Reviews: " + restaurant.getReviews());
        priceText.setText("Price: " + restaurant.getPrice());
        distanceText.setText("Location: " + restaurant.getDistance());
        descriptionArea.setText(restaurant.getDescription());
    }

    @FXML
    private void handleClose() {
        try {
            javafx.application.Platform.exit();
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    private void handleFavorite() {
        if (restaurant != null) {
            theknife.UserSession.getInstance().addFavorite(restaurant);
            System.out.println("Added to favorites: " + restaurant.getName());
        }
    }

    @FXML
    private void handleBook() {
        System.out.println("Book button clicked for restaurant: " + restaurant.getName());
        // TODO: Implement booking logic
    }

    @FXML
    private void handleLeaveReview() {
        System.out.println("Leave Review button clicked for restaurant: " + restaurant.getName());
        // TODO: Implement review logic
    }

    @FXML
    private void handleGoHome() {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/view/dashboard.fxml"));
            javafx.scene.Parent root = loader.load();

            // Use any node that is guaranteed to be in the scene, e.g., closeButton
            javafx.stage.Stage stage = (javafx.stage.Stage) closeButton.getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(root));
            stage.setTitle("Dashboard");
            stage.setMaximized(true);
            stage.setFullScreenExitHint("");
            stage.setFullScreenExitKeyCombination(javafx.scene.input.KeyCombination.NO_MATCH);
            stage.setFullScreen(true);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
