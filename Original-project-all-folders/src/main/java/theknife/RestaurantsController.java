package theknife;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import theknife.UserSession;
import theknife.Restaurant;

import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import theknife.UserSession;
import theknife.Restaurant;

import java.util.Optional;
import java.util.List;

public class RestaurantsController {
    @FXML
    private ListView<String> restaurantListView;

    @FXML
    private Button addRestaurantBtn;

    @FXML
    private Button exitBtn;

    @FXML
    private ImageView arrowImageView;

    @FXML
    private Button loadMoreBtn;
    @FXML
    private Button leftArrowBtn;
    @FXML
    private Button rightArrowBtn;

    private static final int PAGE_SIZE = 20;
    private int currentPage = 0;
    private ObservableList<String> restaurantNames = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        loadRestaurantsPage(0);

        try {
            Image arrowImage = new Image(getClass().getResourceAsStream("/images/left-arrow.png"));
            arrowImageView.setImage(arrowImage);
            ColorAdjust whiteEffect = new ColorAdjust();
            whiteEffect.setBrightness(1.0);
            arrowImageView.setEffect(whiteEffect);

        } catch (Exception e) {
            e.printStackTrace();
        }

        leftArrowBtn.setOnAction(event -> loadPreviousPage());
        rightArrowBtn.setOnAction(event -> loadNextPage());
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
            resetRestaurants();
        }
    }

    private void resetRestaurants() {
        currentPage = 0;
        restaurantNames.clear();
        loadRestaurantsPage(currentPage);
    }

    private void loadRestaurantsPage(int page) {
        List<Restaurant> allRestaurants = UserSession.getInstance().getOwnedRestaurants();
        int start = page * PAGE_SIZE;
        int end = Math.min(start + PAGE_SIZE, allRestaurants.size());

        if (start >= allRestaurants.size()) {
            return; // No more restaurants to load
        }

        for (int i = start; i < end; i++) {
            restaurantNames.add(allRestaurants.get(i).getName());
        }

        restaurantListView.setItems(restaurantNames);
        currentPage = page;
    }

    private void loadMoreRestaurants() {
        loadRestaurantsPage(currentPage + 1);
    }

    private void loadPreviousPage() {
        if (currentPage > 0) {
            loadRestaurantsPage(currentPage - 1);
        }
    }

    private void loadNextPage() {
        List<Restaurant> allRestaurants = UserSession.getInstance().getOwnedRestaurants();
        int maxPage = (allRestaurants.size() - 1) / PAGE_SIZE;
        if (currentPage < maxPage) {
            loadRestaurantsPage(currentPage + 1);
        }
    }

    @FXML
    private void handleExitAction() {
        javafx.application.Platform.exit();
        System.exit(0);
    }

    @FXML
    private void handleGoHome() {
        // TODO: Implement navigation to home/dashboard page
        System.out.println("Go Home button clicked");
    }
}
