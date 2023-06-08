package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.io.IOException;


public class HomeController {

    @FXML
    void OnActionAttedance(ActionEvent event)throws IOException {


        JFxUtils.changeScene(Main.stage, "Attendance.fxml");
    }

    @FXML
    void OnActionField(ActionEvent event)throws IOException {


        JFxUtils.changeScene(Main.stage, "Field.fxml");
    }

    @FXML
    void OnActionModule(ActionEvent event) throws IOException {


        JFxUtils.changeScene(Main.stage, "Module.fxml");
    }

    @FXML
    void OnActionStudent(ActionEvent event ) throws IOException {


        JFxUtils.changeScene(Main.stage, "Student.fxml");
    }

    @FXML
    void OnActionTeacher(ActionEvent event)throws IOException {


        JFxUtils.changeScene(Main.stage, "Teacher.fxml");
    }

    @FXML
    void OnActionExitBtn(ActionEvent event) {

        Platform.exit();
    }
}
