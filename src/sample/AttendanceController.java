package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.collections.ObservableList;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;
import static sample.ConnexionMySQL.connectDb;

public class AttendanceController {

    @FXML
    private TableView<Attendance> AttendanceTab;

    @FXML
    private TextField AttendanceID;

    @FXML
    private TableColumn<Attendance, Integer> ColAttID;

    @FXML
    private TableColumn<Attendance, Integer> ColModID;

    @FXML
    private TableColumn<Attendance, Integer> ColStdID;

    @FXML
    private TableColumn<Attendance, Integer> ColTchrCIN;

    @FXML
    private TableColumn<Attendance, String> Colstts;


    @FXML
    private TextField ModID;

    @FXML
    private TextField Status;

    @FXML
    private TextField TchrCIN;

    @FXML
    private TextField search;

    @FXML
    private TextField stdID;


    public void initialize() {
        Platform.runLater(() -> AttendanceID.requestFocus());
        initializeTableColumns();
        displayAttendance();
    }

    private void initializeTableColumns() {
        ColAttID.setCellValueFactory(new PropertyValueFactory<>("attId"));
        ColStdID.setCellValueFactory(new PropertyValueFactory<>("stdID"));
        ColTchrCIN.setCellValueFactory(new PropertyValueFactory<>("TchrCIN"));
        ColModID.setCellValueFactory(new PropertyValueFactory<>("modId"));
        Colstts.setCellValueFactory(new PropertyValueFactory<>("stts"));
    }

    private void displayAttendance() {
        ObservableList<Attendance> attendance = getAllAttendance();
        AttendanceList = attendance;
        AttendanceTab.setItems(AttendanceList);
    }

    private ObservableList<Attendance> AttendanceList;
    private int index = -1;
    private Connection conn = null;
    private ResultSet rs = null;
    private PreparedStatement pst = null;

    public static ObservableList<Attendance> getAllAttendance() {
        Connection conn = connectDb();
        ObservableList<Attendance> list = FXCollections.observableArrayList();

        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM attendance");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Attendance(
                        rs.getInt("Attendance_ID"),
                        rs.getInt("Student_ID"),
                        rs.getString("Teacher_CIN"),
                        rs.getInt("Module_ID"),
                        rs.getString("Status")
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
        AttendanceID.clear();
        stdID.clear();
        TchrCIN.clear();
        ModID.clear();
        Status.clear();
    }


    @FXML
    void OnActionAddBtn(ActionEvent event) {

        conn = connectDb();
        String sql = "INSERT INTO Attendance(Attendance_ID, Student_ID, Teacher_CIN, Module_ID, Status ) VALUES (?,?,?,?,?)";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, AttendanceID.getText());
            pst.setString(2, stdID.getText());
            pst.setString(3, TchrCIN.getText());
            pst.setString(4, ModID.getText());
            pst.setString(5, Status.getText());
            pst.execute();

            JOptionPane.showMessageDialog(null, "Attendance added");
            clearFields();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    @FXML
    void OnActionDeleteBtn(ActionEvent event) {

        conn = connectDb();
        String sql = "DELETE FROM Attendance WHERE Attendance_ID = ?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, AttendanceID.getText());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Attendance deleted");
            clearFields();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    @FXML
    void OnActionExitBtn(ActionEvent event) {

        Platform.exit();

    }

    public void OnActionHome(ActionEvent actionEvent) throws IOException {


        JFxUtils.changeScene(Main.stage, "Home.fxml");
    }


    @FXML
    void OnActionSearchBtn(ActionEvent event) {

        conn = connectDb();
        String searchText = search.getText();
        try {
            String sql = "SELECT * FROM Attendance WHERE Attendance_ID LIKE ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, "%" + searchText + "%");
            rs = pst.executeQuery();
            AttendanceList = FXCollections.observableArrayList();
            while (rs.next()) {
                int attId = rs.getInt("Attendance_ID");
                int stdID = rs.getInt("Student_ID");
                String TchrCIN = rs.getString("Teacher_CIN");
                int modId = rs.getInt("Module_ID");
                String stts = rs.getString("Status");

                Attendance attendance = new Attendance(attId, stdID, TchrCIN, modId, stts);
                AttendanceList.add(attendance);
            }
            AttendanceTab.setItems(AttendanceList);
            clearFields();
            AttendanceTab.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    Attendance selectedAttendance = newSelection;

                    AttendanceID.setText(String.valueOf(selectedAttendance.getAttId()));
                    stdID.setText(String.valueOf(selectedAttendance.getStdID()));
                    TchrCIN.setText(selectedAttendance.getTchrCIN());
                    ModID.setText(String.valueOf(selectedAttendance.getModId()));
                    Status.setText(selectedAttendance.getStts());

                } else {
                    clearFields();
                }
            });
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }


    @FXML
    void OnActionUpdateBtn(ActionEvent event) {

        try{
            conn = ConnexionMySQL.connectDb();
            String value1 = AttendanceID.getText();
            String value2 = stdID.getText();
            String value3 = TchrCIN.getText();
            String value4 = ModID.getText();
            String value5 = Status.getText();

            String sql = "UPDATE Attendance SET Attendance_ID = '" + value1 + "', Student_ID = '" + value2 + "', Teacher_ID = '" + value3 + "', Module_ID = '" + value4 + "', Status = '" + value5 + "' WHERE Attendance_ID = '" + value1 + "'";

            pst = conn.prepareStatement(sql);
            pst.execute();

            JOptionPane.showMessageDialog(null,"Attendance updated");
            clearFields();
        }catch (Exception e){

            JOptionPane.showMessageDialog(null,e);

        }
    }

}
