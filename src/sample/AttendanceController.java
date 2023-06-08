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
    private TableColumn<Attendance, Integer> ColTchrID;

    @FXML
    private TableColumn<Attendance, String> ColDate;

    @FXML
    private TableColumn<Attendance, String> Colsmstr;

    @FXML
    private TableColumn<Attendance, String> Colstts;

    @FXML
    private TextField Date;

    @FXML
    private TextField ModID;

    @FXML
    private TextField Semester;

    @FXML
    private TextField Status;

    @FXML
    private TextField TchrID;

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
        ColTchrID.setCellValueFactory(new PropertyValueFactory<>("tchrId"));
        Colsmstr.setCellValueFactory(new PropertyValueFactory<>("sem"));
        ColModID.setCellValueFactory(new PropertyValueFactory<>("modId"));
        Colstts.setCellValueFactory(new PropertyValueFactory<>("stts"));
        ColDate.setCellValueFactory(new PropertyValueFactory<>("date"));

    }

    private void displayAttendance() {
        ObservableList<Attendance> attendance = getAllAttendance();
        AttendanceTab.setItems(attendance);
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
                        Integer.parseInt(rs.getString("Attendance_ID")),
                        Integer.parseInt(rs.getString("Student_ID")),
                        Integer.parseInt(rs.getString("Teacher_ID")),
                        rs.getString("Semester"),
                        Integer.parseInt(rs.getString("Module_ID")),
                        rs.getString("Status"),
                        rs.getString("Date")));
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
        TchrID.clear();
        Semester.clear();
        ModID.clear();
        Status.clear();
        Date.clear();

    }


    @FXML
    void OnActionAddBtn(ActionEvent event) {

        conn = connectDb();
        String sql = "INSERT INTO Attendance(Attendance_ID, Student_ID, Teacher_ID, Semester, Module_ID, Status, Date ) VALUES (?,?,?,?,?,?,?)";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, AttendanceID.getText());
            pst.setString(2, stdID.getText());
            pst.setString(3, TchrID.getText());
            pst.setString(4, Semester.getText());
            pst.setString(5, ModID.getText());
            pst.setString(6, Status.getText());
            pst.setString(7, Date.getText());
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
                int tchrId = rs.getInt("Teacher_ID");
                String sem = rs.getString("Semester");
                int modId = rs.getInt("Module_ID");
                String stts = rs.getString("Status");
                String date = rs.getString("Date");

                Attendance attendance = new Attendance(attId, stdID, tchrId, sem, modId, stts, date);
                AttendanceList.add(attendance);
            }
            AttendanceTab.setItems(AttendanceList);
            clearFields();
            AttendanceTab.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    Attendance selectedAttendance = newSelection;

                    AttendanceID.setText(String.valueOf(selectedAttendance.getAttId()));
                    stdID.setText(String.valueOf(selectedAttendance.getStdID()));
                    TchrID.setText(String.valueOf(selectedAttendance.getTchrId()));
                    Semester.setText(selectedAttendance.getSem());
                    ModID.setText(String.valueOf(selectedAttendance.getModId()));
                    Status.setText(selectedAttendance.getStts());
                    Date.setText(selectedAttendance.getDate());

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
            String value3 = TchrID.getText();
            String value4 = Semester.getText();
            String value5 = ModID.getText();
            String value6 = Status.getText();
            String value7 = Date.getText();

            String sql = "update Attendance set Attendance_ID='"+value1+"',Student_ID = '"+value2+"', Teacher_ID = '"+value3+"', Semester = '"+value4+"' , Module_ID = '"+value5+"', Status = '"+value6+"', Date = '"+value7+"' WHERE Attendance_ID = '"+value1+"'";

            pst = conn.prepareStatement(sql);
            pst.execute();

            JOptionPane.showMessageDialog(null,"Attendance updated");
            clearFields();
        }catch (Exception e){

            JOptionPane.showMessageDialog(null,e);

        }
    }

}
