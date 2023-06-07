package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;
import static sample.ConnexionMySQL.connectDb;

public class TeacherController {

    @FXML
    private Button Exit;

    @FXML
    private TextField TchrID;

    @FXML
    private TableView<Teacher> TchrTab;

    @FXML
    private TableColumn<Teacher, String> colMail;

    @FXML
    private TableColumn<Teacher, String> colName;

    @FXML
    private TableColumn<Teacher, Integer> colTchrID;

    @FXML
    private TextField fullname;

    @FXML
    private Button home;

    @FXML
    private TextField mail;

    @FXML
    private TextField search;

    @FXML
    private Button searchBtn;

    private ObservableList<Teacher> teacherList;
    private int index = -1;
    private Connection conn = null;
    private ResultSet rs = null;
    private PreparedStatement pst = null;

    public void initialize() {
        Platform.runLater(() -> TchrID.requestFocus());
        initializeTableColumns();
        displayTeachers();
    }

    private void initializeTableColumns() {
        colTchrID.setCellValueFactory(new PropertyValueFactory<>("TchrId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colMail.setCellValueFactory(new PropertyValueFactory<>("mail"));
    }

    private void displayTeachers() {
        teacherList = getAllTeachers();
        TchrTab.setItems(teacherList);
    }

    public static ObservableList<Teacher> getAllTeachers() {
        Connection conn = connectDb();
        ObservableList<Teacher> list = FXCollections.observableArrayList();

        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Teacher");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Teacher(
                        Integer.parseInt(rs.getString("teacherId")),
                        rs.getString("fullName"),
                        rs.getString("email")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    private void clearFields() {
        TchrID.clear();
        fullname.clear();
        mail.clear();
    }

    @FXML
    void OnActionAddBtn(ActionEvent event) {
        conn = connectDb();
        String sql = "INSERT INTO Teacher(Full_Name, Email) VALUES (?, ?)";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, fullname.getText());
            pst.setString(2, mail.getText());
            pst.execute();

            JOptionPane.showMessageDialog(null, "Teacher added");
            clearFields();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    @FXML
    void OnActionDeleteBtn(ActionEvent event) {
        Teacher selectedTeacher = TchrTab.getSelectionModel().getSelectedItem();

        if (selectedTeacher != null) {
            int teacherId = selectedTeacher.getTchrID();
            String sql = "DELETE FROM Teacher WHERE teacherId = ?";

            try {
                conn = connectDb();
                pst = conn.prepareStatement(sql);
                pst.setInt(1, teacherId);
                pst.execute();

                JOptionPane.showMessageDialog(null, "Teacher deleted");
                displayTeachers();
                clearFields();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select a teacher to delete");
        }
    }

    @FXML
    void OnActionSearchBtn(ActionEvent event) {

            String searchText = search.getText();

            if (!searchText.isEmpty()) {
                ObservableList<Teacher> filteredList = FXCollections.observableArrayList();

                for (Teacher teacher : teacherList) {
                    if (teacher.getFullName().toLowerCase().contains(searchText.toLowerCase()) ||
                            teacher.getMail().toLowerCase().contains(searchText.toLowerCase())) {
                        filteredList.add(teacher);
                    }
                }

                if (filteredList.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No matching teachers found");
                }

                TchrTab.setItems(filteredList);
            } else {
                JOptionPane.showMessageDialog(null, "Please enter a search query to find teachers");
                displayTeachers();
            }

    }

    @FXML
    void OnActionUpdateBtn(ActionEvent event) {
            Teacher selectedTeacher = TchrTab.getSelectionModel().getSelectedItem();

            if (selectedTeacher != null) {
                int teacherId = selectedTeacher.getTchrID();
                String sql = "UPDATE Teacher SET Full_Name = ?, Email = ? WHERE teacherId = ?";

                try {
                    conn = connectDb();
                    pst = conn.prepareStatement(sql);
                    pst.setString(1, fullname.getText());
                    pst.setString(2, mail.getText());
                    pst.setInt(3, teacherId);
                    pst.execute();

                    JOptionPane.showMessageDialog(null, "Teacher updated");
                    displayTeachers();
                    clearFields();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please select a teacher to update");
            }
        }

    @FXML
    void OnActionExitBtn(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void OnActionHome(ActionEvent event) {

    }

    @FXML
    void OnActionStdTab(ActionEvent event) {

    }

}
