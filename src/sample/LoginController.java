package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private Button logBtn;

    @FXML
    private TextField name;

    @FXML
    private TextField pw;

    @FXML
    private ComboBox<LoginForm> role;

    @FXML
    void logBtnOnAction(ActionEvent event) {

    }

}
