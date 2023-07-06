package sample;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static sample.ConnexionMySQL.connectDb;

public class RegistrationController {

    @FXML
    private ToggleGroup f;

    @FXML
    private PasswordField lblpss;

    @FXML
    private TextField lbluser;

    @FXML
    private RadioButton raEmployee;

    @FXML
    private RadioButton radmin;


    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;


    @FXML
    void btnsignup(MouseEvent  event) {
        String username = lbluser.getText();
        String password = lblpss.getText();
        String role = "";

        if (radmin.isSelected()) {
            role = "Administration";
        } else if (raEmployee.isSelected()) {
            role = "Teacher";
        }

        if (username.isEmpty() || password.isEmpty() || role.isEmpty()) {
            showAlert(AlertType.ERROR, "Signup Failed", "Please enter a username, password, and select a role.");
            return;
        }

        try {
            conn = connectDb();

            String checkUserSql = "SELECT * FROM users WHERE username = ?";
            pst = conn.prepareStatement(checkUserSql);
            pst.setString(1, username);
            rs = pst.executeQuery();

            if (rs.next()) {
                showAlert(AlertType.ERROR, "Signup Failed", "Username already exists. Please choose a different username.");
                return;
            }

            String insertUserSql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
            pst = conn.prepareStatement(insertUserSql);
            pst.setString(1, username);
            pst.setString(2, password);
            pst.setString(3, role);

            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                showAlert(AlertType.INFORMATION, "Signup Successful", "Account created successfully!");
            } else {
                showAlert(AlertType.ERROR, "Signup Failed", "Failed to create account!");
            }

            pst.close();
        } catch (SQLException e) {
            showAlert(AlertType.ERROR, "Signup Failed", "Failed to create account!");
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
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnlogin(MouseEvent  event) throws IOException {
        JFxUtils.changeScene(Main.stage, "login.fxml");
    }
}
