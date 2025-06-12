package theknife;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.scene.effect.ColorAdjust;
import javafx.application.Platform;

import java.nio.file.*;
import java.util.*;

import javafx.scene.control.Hyperlink;

public class RegisterController {

    @FXML
    private TextField nomeField;
    @FXML
    private TextField cognomeField;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField domicilioField;
    @FXML
    private CheckBox clienteCheckBox;
    @FXML
    private CheckBox ristoratoreCheckBox;
    @FXML
    private Label errorLabel;

    @FXML
    private Hyperlink loginLink;

    @FXML
    private ImageView arrowImageView;

    @FXML
    private void initialize() {
        try {
            Image arrowImage = new Image(getClass().getResourceAsStream("/images/left-arrow.png"));
            arrowImageView.setImage(arrowImage);
            ColorAdjust whiteEffect = new ColorAdjust();
            whiteEffect.setBrightness(1.0);
            arrowImageView.setEffect(whiteEffect);

        } catch (Exception e) {
            e.printStackTrace();
        }

        clienteCheckBox.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
            if (isNowSelected) {
                ristoratoreCheckBox.setSelected(false);
            }
        });
        ristoratoreCheckBox.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
            if (isNowSelected) {
                clienteCheckBox.setSelected(false);
            }
        });

        loginLink.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) loginLink.getScene().getWindow();
                javafx.geometry.Rectangle2D screenBounds = javafx.stage.Screen.getPrimary().getVisualBounds();
                Scene scene = new Scene(root, screenBounds.getWidth(), screenBounds.getHeight());
                stage.setScene(scene);
                stage.setMaximized(true);
                stage.setFullScreenExitHint("");
                stage.setFullScreenExitKeyCombination(javafx.scene.input.KeyCombination.NO_MATCH);
                stage.setFullScreen(true);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
                errorLabel.setText("Error navigating to the login page.");
                errorLabel.setVisible(true);
            }
        });
    }

    @FXML
    private void handleRegister() {
        String nome = nomeField.getText();
        String cognome = cognomeField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();
        String domicilio = domicilioField.getText();

        nome = capitalizeFirstLetter(nome);
        cognome = capitalizeFirstLetter(cognome);
        domicilio = capitalizeFirstLetter(domicilio);

        String ruolo = null;
        if (clienteCheckBox.isSelected()) {
            ruolo = "cliente";
        } else if (ristoratoreCheckBox.isSelected()) {
            ruolo = "ristoratore";
        }

        if (nome.isEmpty() || cognome.isEmpty() || username.isEmpty() || password.isEmpty() || ruolo == null) {
            errorLabel.setText("Please fill in all required fields.");
            errorLabel.setVisible(true);
            return;
        }

        if (!isOnlyLetters(nome)) {
            errorLabel.setText("Requirements not met.");
            errorLabel.setVisible(true);
            return;
        }
        if (!isOnlyLetters(cognome)) {
            errorLabel.setText("Requirements not met.");
            errorLabel.setVisible(true);
            return;
        }
        if (!isOnlyLetters(domicilio)) {
            errorLabel.setText("Requirements not met.");
            errorLabel.setVisible(true);
            return;
        }

        String newUser = String.join(",", nome, cognome, username, password, domicilio, ruolo);

        try {
            Path path = Paths.get("data/utenti.csv");
            // Controlla se username già esiste
            if (Files.exists(path)) {
                List<String> lines = Files.readAllLines(path);
                for (String line : lines) {
                    String[] parts = line.split(",");
                    if (parts.length >= 3 && parts[2].equals(username)) {
                        errorLabel.setText("Username already exists.");
                        errorLabel.setVisible(true);
                        return;
                    }
                }
            }

            String newUserWithNewline = System.lineSeparator() + newUser;
                Files.write(path, Arrays.asList(newUserWithNewline), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                System.out.println("Setting success: Registrazione completata!");
                errorLabel.setText("Registration completed!");
                errorLabel.setVisible(true);
                try {
                    System.out.println("Loading dashboard.fxml");
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/dashboard.fxml"));
                    Parent root = loader.load();
                    // Set user session after registration
                    theknife.UserSession.getInstance().setUser(username, domicilio, ruolo);
                    // Pass location to DashboardController
                    theknife.DashboardController controller = loader.getController();
                    controller.setLocation(domicilio);
                    controller.updateButtonVisibility();
                    Stage stage = (Stage) nomeField.getScene().getWindow();
                    System.out.println("Stage obtained: " + stage);
                    javafx.geometry.Rectangle2D screenBounds = javafx.stage.Screen.getPrimary().getVisualBounds();
                    Scene scene = new Scene(root, screenBounds.getWidth(), screenBounds.getHeight());
                    stage.setScene(scene);
                    stage.setMaximized(true);
                    stage.setFullScreen(true);
                    stage.show();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    errorLabel.setText("Error during navigation.");
                    errorLabel.setVisible(true);
                }
        } catch (Exception e) {
            e.printStackTrace();
            errorLabel.setText("Error during registration.");
            errorLabel.setVisible(true);
        }
    }

    private String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }

    private boolean isOnlyLetters(String input) {
        return input != null && input.matches("[a-zA-Z]+");
    }

    @FXML
    private void handleGoHome() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/home.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) nomeField.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.setFullScreen(true);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            errorLabel.setText("Error navigating to the home page.");
            errorLabel.setVisible(true);
        }
    }

    @FXML
    private void handleExitAction() {
        Platform.exit();
        System.exit(0);
    }
}
