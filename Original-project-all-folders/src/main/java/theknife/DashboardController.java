package theknife;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

import theknife.Restaurant;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javafx.application.Platform;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class DashboardController {

    @FXML
    private Text locationText;
    @FXML
    private Text currentPageText;
    @FXML
    private Button exitBtn;
    @FXML
    private BorderPane rootPane;

    @FXML
    private HBox guestButtons;
    @FXML
    private HBox userButtons;

    @FXML
    private Button signInBtn;
    @FXML
    private Button favoriteBtn;
    @FXML
    private Button bookBtn;

    @FXML
    private TextField searchInput;

    @FXML
    private ComboBox<String> sortComboBox;

    @FXML
    private GridPane restaurantGrid;

    private List<Restaurant> allRestaurants = new ArrayList<>();

    private PauseTransition searchPause;

    private static final int PAGE_SIZE = 20;
    private int currentPage = 1;

    @FXML
    private Button loadMoreBtn;
    @FXML
    private Button loadLessBtn;

    @FXML
    private void handleExitAction() {
        Platform.exit();
        System.exit(0);
    }

    private void filterAndRenderRestaurants() {
        String searchTerm = searchInput.getText() != null ? searchInput.getText().toLowerCase() : "";
        String locationTerm = locationText.getText() != null ? locationText.getText().toLowerCase() : "";

        List<Restaurant> filtered = allRestaurants.stream()
                .filter(r -> {
                    boolean searchMatch = searchTerm.isEmpty() ||
                            (r.getName() != null && r.getName().toLowerCase().contains(searchTerm)) ||
                            (r.getDescription() != null && r.getDescription().toLowerCase().contains(searchTerm)) ||
                            (r.getCuisine() != null && r.getCuisine().toLowerCase().contains(searchTerm));
                    return searchMatch;
                })
                .filter(r -> {
                    boolean locationMatch = true;
                    if (!locationTerm.isEmpty() && locationTerm.contains(",")) {
                        String cityPart = locationTerm.split(",")[0].trim().toLowerCase();
                        locationMatch = (r.getDistance() != null && r.getDistance().toLowerCase().contains(cityPart));
                    } else if (!locationTerm.isEmpty()) {
                        locationMatch = (r.getDistance() != null
                                && r.getDistance().toLowerCase().contains(locationTerm));
                    }
                    return locationMatch;
                })
                .collect(Collectors.toList());

        String sortBy = sortComboBox.getValue();
        if (sortBy != null) {
            switch (sortBy) {
                case "Rating":
                    filtered.sort(Comparator.comparingDouble(Restaurant::getRating).reversed());
                    break;
                case "Price":
                    filtered.sort(Comparator.comparingInt(this::parsePrice));
                    break;
            }
        }

        int fromIndex = (currentPage - 1) * PAGE_SIZE;
        int toIndex = Math.min(currentPage * PAGE_SIZE, filtered.size());
        List<Restaurant> paginatedList = filtered.subList(fromIndex, toIndex);

        renderRestaurants(paginatedList);

        if (toIndex < filtered.size()) {
            loadMoreBtn.setVisible(true);
        } else {
            loadMoreBtn.setVisible(false);
        }
    }

    private void resetPagination() {
        currentPage = 1;
    }

    @FXML
    public void initialize() {
        theknife.UserSession userSession = theknife.UserSession.getInstance();

        if (userSession.isNotLoggedIn()) {
            locationText.setText("italy");
            loadRestaurants();
            resetPagination();
            filterAndRenderRestaurants();
        } else if (userSession.getLocation() != null && !userSession.getLocation().isEmpty()) {
            locationText.setText(userSession.getLocation());
            loadRestaurants();
            resetPagination();
            filterAndRenderRestaurants();
        } else {
            locationText.setText("");
        }

        Platform.runLater(() -> rootPane.requestFocus());

        sortComboBox.getItems().addAll("Rating", "Price");
        sortComboBox.setOnAction(event -> {
            resetPagination();
            filterAndRenderRestaurants();
        });

        searchPause = new PauseTransition(Duration.millis(300));
        searchInput.textProperty().addListener((obs, oldText, newText) -> {
            searchPause.stop();
            searchPause.setOnFinished(event -> {
                resetPagination();
                filterAndRenderRestaurants();
            });
            searchPause.playFromStart();
        });

        signInBtn.setOnAction(event -> {
            try {
                javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/view/login.fxml"));
                javafx.scene.Parent root = loader.load();
                javafx.stage.Stage stage = (javafx.stage.Stage) signInBtn.getScene().getWindow();
                javafx.scene.Scene scene = new javafx.scene.Scene(root);
                stage.setScene(scene);
                stage.setMaximized(true);
                stage.setFullScreen(true);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        favoriteBtn.setOnAction(event -> {
            try {
                javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(
                        getClass().getResource("view/favorites.fxml"));
                javafx.scene.Parent root = loader.load();
                javafx.stage.Stage stage = (javafx.stage.Stage) favoriteBtn.getScene().getWindow();
                javafx.scene.Scene scene = favoriteBtn.getScene();
                scene.setRoot(root);
                stage.setTitle("Favorites");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        guestButtons.managedProperty().bind(guestButtons.visibleProperty());
        userButtons.managedProperty().bind(userButtons.visibleProperty());

        signInBtn.managedProperty().bind(signInBtn.visibleProperty());
        bookBtn.managedProperty().bind(bookBtn.visibleProperty());
        favoriteBtn.managedProperty().bind(favoriteBtn.visibleProperty());
        javafx.scene.control.Button restaurantsBtn = (javafx.scene.control.Button) rootPane.lookup("#RestaurantsBtn");
        if (restaurantsBtn != null) {
            restaurantsBtn.managedProperty().bind(restaurantsBtn.visibleProperty());
            restaurantsBtn.setOnAction(event -> openRestaurantsPage());
        }

        updateButtonVisibility();

        currentPageText.setText("1");

        loadMoreBtn.setOnAction(event -> {
            currentPage++;
            currentPageText.setText(String.valueOf(currentPage));
            filterAndRenderRestaurants();
            updateLoadLessButtonVisibility();
        });

        loadLessBtn.setOnAction(event -> {
            if (currentPage > 1) {
                currentPage--;
                currentPageText.setText(String.valueOf(currentPage));
                filterAndRenderRestaurants();
                updateLoadLessButtonVisibility();
            }
        });

        // Initialize loadLessBtn visibility on startup
        updateLoadLessButtonVisibility();
    }

    private void updateLoadLessButtonVisibility() {
        loadLessBtn.setVisible(currentPage > 1);
    }

    public void updateButtonVisibility() {
        theknife.UserSession userSession = theknife.UserSession.getInstance();

        if (userSession.isNotLoggedIn()) {
            guestButtons.setVisible(true);
            userButtons.setVisible(false);
            signInBtn.setVisible(true);
            favoriteBtn.setVisible(false);
            bookBtn.setVisible(false);
            try {
                javafx.scene.control.Button restaurantsBtn = (javafx.scene.control.Button) rootPane
                        .lookup("#RestaurantsBtn");
                if (restaurantsBtn != null) {
                    restaurantsBtn.setVisible(false);
                }
            } catch (Exception e) {
                // ignoralo
            }
        } else {
            String role = userSession.getRole();
            if ("owner".equalsIgnoreCase(role) || "ristoratore".equalsIgnoreCase(role)) {
                guestButtons.setVisible(false);
                userButtons.setVisible(true);
                signInBtn.setVisible(false);
                favoriteBtn.setVisible(false);
                bookBtn.setVisible(false);
                try {
                    javafx.scene.control.Button restaurantsBtn = (javafx.scene.control.Button) rootPane
                            .lookup("#RestaurantsBtn");
                    if (restaurantsBtn != null) {
                        restaurantsBtn.setVisible(true);
                    }
                } catch (Exception e) {
                    // ignoralo
                }
            } else if ("client".equalsIgnoreCase(role) || "cliente".equalsIgnoreCase(role)) {
                guestButtons.setVisible(false);
                userButtons.setVisible(true);
                signInBtn.setVisible(false);
                favoriteBtn.setVisible(true);
                bookBtn.setVisible(true);
                try {
                    javafx.scene.control.Button restaurantsBtn = (javafx.scene.control.Button) rootPane
                            .lookup("#RestaurantsBtn");
                    if (restaurantsBtn != null) {
                        restaurantsBtn.setVisible(false);
                    }
                } catch (Exception e) {
                    // ignoralo
                }
            } else {
                guestButtons.setVisible(false);
                userButtons.setVisible(false);
                signInBtn.setVisible(false);
                favoriteBtn.setVisible(false);
                bookBtn.setVisible(false);
                try {
                    javafx.scene.control.Button restaurantsBtn = (javafx.scene.control.Button) rootPane
                            .lookup("#RestaurantsBtn");
                    if (restaurantsBtn != null) {
                        restaurantsBtn.setVisible(false);
                    }
                } catch (Exception e) {
                    // ignoralo
                }
            }
        }
    }

    private void loadRestaurants() {
        allRestaurants.clear();

        String userLocation = theknife.UserSession.getInstance().getLocation();
        if (userLocation == null || userLocation.trim().isEmpty()) {
            userLocation = locationText.getText();
        }
        if (userLocation == null || userLocation.trim().isEmpty()) {
            return;
        }
        userLocation = userLocation.trim().toLowerCase();

        String workingDir = System.getProperty("user.dir");
        java.nio.file.Path csvPath = java.nio.file.Paths.get(workingDir, "data", "michelin_my_maps.csv");

        try (java.io.BufferedReader br = java.nio.file.Files.newBufferedReader(csvPath)) {
            String line;
            boolean firstLine = true;
            int lineNumber = 0;
            int idCounter = 1;
            while ((line = br.readLine()) != null) {
                lineNumber++;
                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                String[] fields = line.split(",(?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)", -1);

                if (fields.length < 14) {
                    continue;
                }

                try {
                    int id = idCounter++;
                    String name = fields[0].trim();
                    String address = fields[1].trim();
                    String location = fields[2].trim().toLowerCase();
                    String price = fields[3].trim();
                    String cuisine = fields[4].trim().toLowerCase();
                    double rating = 0.0;
                    int reviews = 0;
                    String distance = location;
                    String imageUrl = fields[8].trim();
                    String description = fields[13].trim();

                    Restaurant restaurant = new Restaurant(id, name, cuisine, rating, reviews, price, distance,
                            imageUrl, description);
                    allRestaurants.add(restaurant);
                } catch (Exception e) {
                    System.err.println("An unexpected error occurred while parsing line " + lineNumber + ": " + line);
                    System.err.println("Exception: " + e.getMessage());
                    e.printStackTrace();
                    continue;
                }
            }

        } catch (java.io.IOException e) {
            System.err.println("IOException while reading CSV file: " + csvPath.toAbsolutePath());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println(
                    "An unexpected error occurred during CSV loading process from: " + csvPath.toAbsolutePath());
            e.printStackTrace();
        }
    }

    private void renderRestaurants(List<Restaurant> restaurants) {
        System.out.println("Rendering " + restaurants.size() + " restaurants...");
        restaurantGrid.getChildren().clear();
        int col = 0;
        int row = 0;

        if (restaurants.isEmpty()) {
            System.out.println("No restaurants to render after filtering.");
            Text noResultsText = new Text("No restaurants found matching your criteria.");
            noResultsText.setStyle("-fx-fill: #dddddd; -fx-font-size: 16px;");
            restaurantGrid.add(noResultsText, 0, 0);
            GridPane.setColumnSpan(noResultsText, 4);
            return;
        }

        for (Restaurant r : restaurants) {
            VBox card = createRestaurantCard(r);
            restaurantGrid.add(card, col, row);
            col++;
            if (col >= 4) {
                col = 0;
                row++;
            }
        }
    }

    private double parseDistance(Restaurant r) {
        try {
            if (r.getDistance() == null || r.getDistance().trim().isEmpty())
                return Double.MAX_VALUE;
            String dist = r.getDistance().toLowerCase().replaceAll("[^0-9.]", "");
            if (dist.isEmpty())
                return Double.MAX_VALUE;
            return Double.parseDouble(dist);
        } catch (Exception e) {
            System.err.println("Could not parse distance: '" + r.getDistance() + "' for " + r.getName() + ". Error: "
                    + e.getMessage());
            return Double.MAX_VALUE;
        }
    }

    private int parsePrice(Restaurant r) {
        try {
            if (r.getPrice() == null)
                return Integer.MAX_VALUE;
            return r.getPrice().length();
        } catch (Exception e) {
            System.err.println(
                    "Could not parse price: '" + r.getPrice() + "' for " + r.getName() + ". Error: " + e.getMessage());
            return Integer.MAX_VALUE;
        }
    }

    private VBox createRestaurantCard(Restaurant r) {
        VBox card = new VBox();
        card.getStyleClass().add("restaurant-card");
        card.setSpacing(5);
        card.setPrefHeight(220);
        card.setMinHeight(220);
        card.setMaxHeight(220);
        card.setPrefWidth(220);
        card.setStyle(
                "-fx-background-color: #333333; -fx-border-color: #444444; -fx-border-width: 1px; -fx-background-radius: 8px; -fx-border-radius: 8px; -fx-padding: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10,0,0,2);");

        Text title = new Text(r.getName());
        title.getStyleClass().add("card-title");
        title.setStyle("-fx-fill: #eeeeee; -fx-font-weight: bold; -fx-font-size: 16px;");
        title.setWrappingWidth(200);

        // Removed ImageView and related code as per request

        Text cuisine = new Text("Cuisine: " + r.getCuisine());
        cuisine.getStyleClass().add("card-cuisine");
        cuisine.setStyle("-fx-fill: #bbbbbb; -fx-font-size: 13px;");
        cuisine.setWrappingWidth(200);

        Text rating = new Text(String.format("Rating: %.1f (%d reviews)", r.getRating(), r.getReviews()));
        rating.getStyleClass().add("card-rating");
        rating.setStyle("-fx-fill: #bbbbbb; -fx-font-size: 13px;");

        Text distance = new Text("Location: " + r.getDistance());
        distance.getStyleClass().add("card-distance");
        distance.setStyle("-fx-fill: #bbbbbb; -fx-font-size: 13px;");

        Text price = new Text("Price: " + r.getPrice());
        price.getStyleClass().add("card-price");
        price.setStyle("-fx-fill: #bbbbbb; -fx-font-size: 13px;");

        card.getChildren().addAll(title, cuisine, rating, distance, price);

        card.setOnMouseClicked(event -> {
            try {
                javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(
                        getClass().getResource("/view/restaurant_detail.fxml"));
                javafx.scene.Parent root = loader.load();

                theknife.RestaurantDetailController controller = loader.getController();
                controller.setRestaurant(r);

                javafx.scene.Scene scene = card.getScene();
                scene.setRoot(root);

                javafx.stage.Stage stage = (javafx.stage.Stage) scene.getWindow();
                stage.setTitle(r.getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        return card;
    }

    public void setLocation(String location) {
        if (location != null && !location.isEmpty()) {
            if (!theknife.UserSession.getInstance().isLoggedIn()) {
                if (!location.toLowerCase().contains(", italy")) {
                    location = location + ", Italy";
                }
            }
            locationText.setText(location);
            resetPagination();
            loadRestaurants();
            filterAndRenderRestaurants();
        }
    }

    public void setCurrentPage(String currentPage) {
        currentPageText.setText(currentPage);
        loadRestaurants();
        filterAndRenderRestaurants();
    }

    @FXML
    private void handleLocationTextClick() {
        String currentLocation = locationText.getText();
        String cityOnly = currentLocation.contains(",") ? currentLocation.split(",")[0].trim() : currentLocation.trim();

        javafx.scene.control.TextField editField = new javafx.scene.control.TextField(cityOnly);
        editField.setPrefWidth(locationText.getBoundsInLocal().getWidth() + 20);

        javafx.scene.Parent parent = locationText.getParent();
        if (parent instanceof javafx.scene.layout.Pane) {
            javafx.scene.layout.Pane pane = (javafx.scene.layout.Pane) parent;
            int index = pane.getChildren().indexOf(locationText);
            pane.getChildren().remove(locationText);
            pane.getChildren().add(index, editField);
            editField.requestFocus();
            editField.selectAll();

            editField.setOnAction(event -> commitLocationEdit(editField, pane, index));
            editField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
                if (!isNowFocused) {
                    commitLocationEdit(editField, pane, index);
                }
            });
        }
    }

    private void commitLocationEdit(javafx.scene.control.TextField editField, javafx.scene.layout.Pane pane,
            int index) {
        String newLocation = editField.getText();
        if (newLocation != null && !newLocation.trim().isEmpty()) {
            setLocation(newLocation.trim());
        }
        pane.getChildren().remove(editField);
        if (!pane.getChildren().contains(locationText)) {
            pane.getChildren().add(index, locationText);
        }
    }

    private void openRestaurantsPage() {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(
                    getClass().getResource("/view/restaurants.fxml"));
            javafx.scene.Parent root = loader.load();
            javafx.stage.Stage stage = (javafx.stage.Stage) rootPane.getScene().getWindow();
            javafx.scene.Scene scene = rootPane.getScene();
            scene.setRoot(root);
            stage.setTitle("My Restaurants");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
