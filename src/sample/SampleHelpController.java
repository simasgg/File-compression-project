package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SampleHelpController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void backButtonPressed(ActionEvent actionEvent) throws IOException {
        Parent help = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene sceneHelp = new Scene(help, 1000, 700);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(sceneHelp);
        window.show();

    }
}
