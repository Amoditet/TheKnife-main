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
    private Button heartButton;
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
    @FXML
    private Button RestaurantsBtn;
    @FXML
    private javafx.scene.layout.HBox guestButtons;
    @FXML
    private javafx.scene.layout.HBox userButtons;

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
            bookBtn.setOnAction(event -> openBookingsPage());
        }
        if (favoriteBtn != null) {
            favoriteBtn.managedProperty().bind(favoriteBtn.visibleProperty());
            favoriteBtn.setOnAction(event -> openFavoritesPage());
        }
        if (signInBtn != null) {
            signInBtn.managedProperty().bind(signInBtn.visibleProperty());
            signInBtn.setOnAction(event -> openLoginPage());
        }
        if (RestaurantsBtn != null) {
            RestaurantsBtn.managedProperty().bind(RestaurantsBtn.visibleProperty());
            RestaurantsBtn.setOnAction(event -> openRestaurantsPage());
        }
        if (guestButtons != null) {
            guestButtons.managedProperty().bind(guestButtons.visibleProperty());
        }
        if (userButtons != null) {
            userButtons.managedProperty().bind(userButtons.visibleProperty());
        }

        updateButtonVisibility();
    }

    public void updateButtonVisibility() {
        theknife.UserSession userSession = theknife.UserSession.getInstance();
        if (userSession.isNotLoggedIn()) {
            if (guestButtons != null) {
                guestButtons.setVisible(true);
            }
            if (userButtons != null) {
                userButtons.setVisible(false);
            }
            favoriteButton.setVisible(false);
            bookButton.setVisible(false);
            reviewButton.setVisible(false);
            if (heartButton != null) {
                heartButton.setVisible(false);
            }
            if (RestaurantsBtn != null) {
                RestaurantsBtn.setVisible(false);
            }
            if (bookBtn != null) {
                bookBtn.setVisible(false);
            }
            if (favoriteBtn != null) {
                favoriteBtn.setVisible(false);
            }
            if (signInBtn != null) {
                signInBtn.setVisible(true);
            }
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
                if (guestButtons != null) {
                    guestButtons.setVisible(false);
                }
                if (userButtons != null) {
                    userButtons.setVisible(true);
                }
                favoriteButton.setVisible(true);
                bookButton.setVisible(true);
                reviewButton.setVisible(true);
                if (heartButton != null) {
                    heartButton.setVisible(true);
                }
                if (RestaurantsBtn != null) {
                    RestaurantsBtn.setVisible(false);
                }
                if (bookBtn != null) {
                    bookBtn.setVisible(true);
                }
                if (favoriteBtn != null) {
                    favoriteBtn.setVisible(true);
                }
                if (signInBtn != null) {
                    signInBtn.setVisible(false);
                }
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
                if (guestButtons != null) {
                    guestButtons.setVisible(false);
                }
                if (userButtons != null) {
                    userButtons.setVisible(true);
                }
                favoriteButton.setVisible(false);
                bookButton.setVisible(false);
                reviewButton.setVisible(false);
                if (heartButton != null) {
                    heartButton.setVisible(false);
                }
                if (RestaurantsBtn != null) {
                    RestaurantsBtn.setVisible(true);
                }
                if (bookBtn != null) {
                    bookBtn.setVisible(false);
                }
                if (favoriteBtn != null) {
                    favoriteBtn.setVisible(false);
                }
                if (signInBtn != null) {
                    signInBtn.setVisible(false);
                }
                if (RestaurantsBtn != null) {
                    RestaurantsBtn.setVisible(false);
                }
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
                if (guestButtons != null) {
                    guestButtons.setVisible(false);
                }
                if (userButtons != null) {
                    userButtons.setVisible(false);
                }
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
        javafx.scene.control.TextInputDialog dateDialog = new javafx.scene.control.TextInputDialog();
        dateDialog.setTitle("Booking Date");
        dateDialog.setHeaderText("Enter booking date (YYYY-MM-DD)");
        java.util.Optional<String> date = dateDialog.showAndWait();
        if (!date.isPresent()) {
            return;
        }
        javafx.scene.control.TextInputDialog timeDialog = new javafx.scene.control.TextInputDialog();
        timeDialog.setTitle("Booking Time");
        timeDialog.setHeaderText("Enter booking time (HH:MM)");
        java.util.Optional<String> time = timeDialog.showAndWait();
        if (!time.isPresent()) {
            return;
        }
        UserSession.getInstance().addBooking(new Booking(restaurant.getName(), date.get(), time.get()));
        openBookingsPage();
    }

    @FXML
    private void handleLeaveReview() {
        UserSession session = UserSession.getInstance();
        Review existing = session.getReview(restaurant.getId());

        if (existing != null) {
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("You already reviewed this restaurant");
            javafx.scene.control.ButtonType editBtn = new javafx.scene.control.ButtonType("Edit");
            javafx.scene.control.ButtonType deleteBtn = new javafx.scene.control.ButtonType("Delete");
            alert.getButtonTypes().setAll(editBtn, deleteBtn, javafx.scene.control.ButtonType.CANCEL);
            java.util.Optional<javafx.scene.control.ButtonType> result = alert.showAndWait();
            if (!result.isPresent() || result.get() == javafx.scene.control.ButtonType.CANCEL) {
                return;
            }
            if (result.get() == deleteBtn) {
                session.deleteReview(restaurant.getId());
                return;
            }
        }

        int initialRating = existing != null ? existing.getRating() : 5;
        javafx.scene.control.ChoiceDialog<Integer> ratingDialog = new javafx.scene.control.ChoiceDialog<>(initialRating, java.util.Arrays.asList(1,2,3,4,5));
        ratingDialog.setTitle("Rating");
        ratingDialog.setHeaderText("Select rating 1-5");
        java.util.Optional<Integer> rating = ratingDialog.showAndWait();
        if (!rating.isPresent()) {
            return;
        }

        String initialText = existing != null ? existing.getText() : "";
        javafx.scene.control.TextInputDialog textDialog = new javafx.scene.control.TextInputDialog(initialText);
        textDialog.setTitle("Review");
        textDialog.setHeaderText("Write your review");
        java.util.Optional<String> text = textDialog.showAndWait();
        if (!text.isPresent()) {
            return;
        }

        session.addOrUpdateReview(restaurant.getId(), new Review(session.getUsername(), rating.get(), text.get()));
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

    private void openLoginPage() {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/view/login.fxml"));
            javafx.scene.Parent root = loader.load();
            javafx.stage.Stage stage = (javafx.stage.Stage) signInBtn.getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(root));
            stage.setMaximized(true);
            stage.setFullScreen(true);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openFavoritesPage() {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/view/favorite.fxml"));
            javafx.scene.Parent root = loader.load();
            javafx.stage.Stage stage = (javafx.stage.Stage) favoriteBtn.getScene().getWindow();
            javafx.scene.Scene scene = favoriteBtn.getScene();
            scene.setRoot(root);
            stage.setTitle("Favorites");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openBookingsPage() {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/view/bookings.fxml"));
            javafx.scene.Parent root = loader.load();
            javafx.stage.Stage stage = (javafx.stage.Stage) bookBtn.getScene().getWindow();
            javafx.scene.Scene scene = bookBtn.getScene();
            scene.setRoot(root);
            stage.setTitle("Bookings");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openRestaurantsPage() {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/view/restaurants.fxml"));
            javafx.scene.Parent root = loader.load();
            javafx.stage.Stage stage = (javafx.stage.Stage) RestaurantsBtn.getScene().getWindow();
            javafx.scene.Scene scene = RestaurantsBtn.getScene();
            scene.setRoot(root);
            stage.setTitle("My Restaurants");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
