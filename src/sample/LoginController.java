package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import static sample.ConnexionMySQL.connectDb;

public class LoginController {

    @FXML
    private PasswordField lblpss;

    @FXML
    private TextField lbluser;

    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;




    @FXML
    void btnlogin(MouseEvent event) throws IOException {

        String username = lbluser.getText();
        String password = lblpss.getText();

        try {
            conn = connectDb();
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?  AND (role='Teacher' OR role='Administration')";
            pst = conn.prepareStatement(sql);
            pst.setString(1, username);
            pst.setString(2, password);

            rs = pst.executeQuery();

            if (rs.next()) {
                String role = rs.getString("role");

                if (role.equals("Administration")) {
                    JFxUtils.changeScene(Main.stage, "Home.fxml");
                } else if (role.equals("Teacher")) {
                    JFxUtils.changeScene(Main.stage, "Attendance.fxml");
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid username or password!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


        @FXML
    void btnsignup(MouseEvent event)throws IOException{
            JFxUtils.changeScene(Main.stage, "Registration.fxml");

    }



}
