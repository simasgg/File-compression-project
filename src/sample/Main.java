package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Locale locale = new Locale("en_LT");
        ResourceBundle bundle = ResourceBundle.getBundle("resources.translations", locale);
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"), bundle);
        primaryStage.setTitle("Compression");
        Scene scene = new Scene(root, 1000, 850);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}