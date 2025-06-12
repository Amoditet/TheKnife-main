package theknife;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;
import javafx.stage.StageStyle;

import theknife.HomeController;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/home.fxml"));

        Parent root = loader.load();

        HomeController controller = loader.getController();

        controller.setPrimaryStage(primaryStage);

        primaryStage.initStyle(StageStyle.UNDECORATED);

        primaryStage.setTitle("TheKnife");

        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        primaryStage.setMaximized(true);
        primaryStage.setFullScreenExitHint("");
        primaryStage.setFullScreenExitKeyCombination(javafx.scene.input.KeyCombination.NO_MATCH);
        primaryStage.setFullScreen(true);

        primaryStage.setOnCloseRequest(event -> {
            System.exit(0);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
