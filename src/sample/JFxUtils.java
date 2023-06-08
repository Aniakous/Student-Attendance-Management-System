package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class JFxUtils {

        public static Stage loadFxmlToStage(String fxml) {
            Stage stage=new Stage();
            try {

                Parent root = FXMLLoader.load(JFxUtils.class.getResource(fxml));
                Scene sc = new Scene(root);
                stage.setScene(sc);
                return stage;
            } catch (IOException e) {
                throw new IllegalStateException("cannot load FXML screen", e);
            }
        }
        public static void changeScene(Stage stg, String newFXML) throws IOException {
            Parent root = FXMLLoader.load(JFxUtils.class.getResource(newFXML));
            stg.getScene().setRoot(root);
        }
}
