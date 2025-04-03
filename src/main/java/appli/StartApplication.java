package appli;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StartApplication extends Application {
    private static Stage mainStage;

    public static Object getControllerFromStage() {
        return null;
    }

    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("accueil/LoginView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        mainStage.setTitle("Application JavaFX");
        mainStage.setScene(scene);
        mainStage.show();
    }

    public static void changeScene(String nomDuFichierFxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("accueil/" + nomDuFichierFxml + "View.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        mainStage.setScene(scene);
    }

    public static void main(String[] args) {
        launch();
    }
}
