package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.ObservableList;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import static sample.ConnexionMySQL.connectDb;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;
import javafx.scene.control.ComboBox;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;





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
    private TableColumn<Attendance, String> ColTchrCIN;

    @FXML
    private TableColumn<Attendance, ComboBox<String>> Colstts;

    @FXML
    private TextField ModID;

    @FXML
    private TextField TchrCIN;

    @FXML
    private TextField search;

    @FXML
    private Label DateLab;

    @FXML
    private TextField stdID;

    @FXML
    private Button savebtn;

    @FXML
    private ComboBox<Integer> modCombo;

    private SimpleDateFormat dateFormat;

    private void updateDateTime() {
        Date now = new Date();
        String formattedDate = dateFormat.format(now);
        Platform.runLater(() -> {
            DateLab.setText(formattedDate); // Set the formatted date to the DateLab label
        });
    }


    private void displayAttendance() {
        AttendanceList = getAllAttendance();
        AttendanceTab.setItems(AttendanceList);
        //DateLab.setText(dateFormat.format(new Date()));

    }

    public void initialize() {
        Platform.runLater(() -> AttendanceID.requestFocus());
        initializeTableColumns();
        displayAttendance();
        AttendanceTab.setItems(AttendanceList);
        dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        updateDateTime();
        DateLab.setText(LocalDate.now().toString());



    }



    private void initializeTableColumns() {
        ColAttID.setCellValueFactory(new PropertyValueFactory<Attendance, Integer>("attId"));
        ColStdID.setCellValueFactory(new PropertyValueFactory<Attendance, Integer>("stdID"));
        ColTchrCIN.setCellValueFactory(new PropertyValueFactory<Attendance, String>("TchrCIN"));
        ColModID.setCellValueFactory(new PropertyValueFactory<Attendance, Integer>("modId"));

        Colstts.setCellValueFactory(cellData -> {
            Attendance attendance = cellData.getValue();
            ComboBox<String> comboBox = new ComboBox<>();
            comboBox.getItems().addAll("Present", "Absent");
            comboBox.setValue(attendance.getStts());

            comboBox.setOnAction((event) -> {
                String newValue = comboBox.getValue();
                attendance.setStts(newValue);
            });

            return new SimpleObjectProperty<>(comboBox);
        });
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
                int attendanceId = rs.getInt("Attendance_ID");
                int stdID = rs.getInt("Student_ID");
                String TchrCIN = rs.getString("Teacher_CIN");
                int ModID = rs.getInt("Module_ID");
                String stts = rs.getString("Status");

                list.add(new Attendance(attendanceId, stdID, TchrCIN, ModID, stts));
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



    @FXML
    void OnActionSaveBtn(ActionEvent event) {
        try {
            conn = connectDb();
            LocalDate currentDate = LocalDate.parse(DateLab.getText(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            LocalDate todayDate = LocalDate.now();

            String checkSql = "SELECT * FROM DailyAttendance WHERE Date = ?";
            pst = conn.prepareStatement(checkSql);
            pst.setString(1, DateLab.getText());
            rs = pst.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "Attendance has already been saved for today.");
            } else {
                ObservableList<Attendance> data = AttendanceTab.getItems();

                // Save all students for a new date
                String insertSql = "INSERT INTO DailyAttendance(Student_ID, Teacher_CIN, Module_ID, Status, Date) " +
                        "VALUES (?, ?, ?, ?, ?)";
                pst = conn.prepareStatement(insertSql);

                for (Attendance item : data) {
                    pst.setInt(1, item.getStdID());
                    pst.setString(2, item.getTchrCIN());
                    pst.setInt(3, item.getModId());
                    pst.setString(4, item.getStts());
                    pst.setString(5, DateLab.getText());
                    pst.addBatch();
                }

                pst.executeBatch();

                JOptionPane.showMessageDialog(null, "Daily Attendance saved");
                clearFields();
                updatetable();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }



    @FXML
    void OnActionUpdateBtn(ActionEvent event) {
        try {
            conn = ConnexionMySQL.connectDb();
            String value1 = AttendanceID.getText();
            String value2 = stdID.getText();
            String value3 = TchrCIN.getText();
            String value4 = ModID.getText();

            String sql = "UPDATE Attendance SET Student_ID = '" + value2 + "', Teacher_CIN = '" + value3 + "', Module_ID = '" + value4 + "' WHERE Attendance_ID = '" + value1 + "'";

            pst = conn.prepareStatement(sql);
            pst.execute();

            JOptionPane.showMessageDialog(null, "Attendance updated");
            clearFields();
            updatetable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }




    @FXML
    void GetSelected(MouseEvent event) {
        index = AttendanceTab.getSelectionModel().getSelectedIndex();

        if (index <= -1) {
            return;
        }

        AttendanceID.setText(ColAttID.getCellData(index).toString());
        stdID.setText(ColStdID.getCellData(index).toString());
        TchrCIN.setText(String.valueOf(ColTchrCIN.getCellData(index)));
        ModID.setText(ColModID.getCellData(index).toString());
    }

    private void clearFields() {
        AttendanceID.clear();
        stdID.clear();
        TchrCIN.clear();
        ModID.clear();
    }


    public void updatetable() {
        ColAttID.setCellValueFactory(new PropertyValueFactory<>("attId"));
        ColStdID.setCellValueFactory(new PropertyValueFactory<>("stdID"));
        ColTchrCIN.setCellValueFactory(new PropertyValueFactory<>("TchrCIN"));
        ColModID.setCellValueFactory(new PropertyValueFactory<>("modId"));

        AttendanceList = getAllAttendance();
        AttendanceTab.setItems(AttendanceList);
    }

    @FXML
    void OnActionAddBtn(ActionEvent event) {
        conn = connectDb();
        String sql = "INSERT INTO Attendance(Student_ID, Teacher_CIN, Module_ID, Status) VALUES (?,?,?,?)";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, stdID.getText());
            pst.setString(2, TchrCIN.getText());
            pst.setString(3, ModID.getText());
            pst.setString(4, "Absent");
           // pst.setString(5, DateLab.getText()); // Get the date from the DateLab label

            pst.execute();

            JOptionPane.showMessageDialog(null, "Attendance added");
            clearFields();
            updatetable();
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
            updatetable();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    @FXML
    void OnActionExit(MouseEvent event) {
        Platform.exit();
    }



    @FXML
    void OnActionHomee(MouseEvent event) throws IOException {
        JFxUtils.changeScene(Main.stage, "Home.fxml");

    }




    @FXML
    void OnActionSearchBtn(ActionEvent event) {
        conn = connectDb();
        String searchText = search.getText();
        try {
            String sql = "SELECT * FROM Attendance WHERE Attendance_ID LIKE ? OR Student_ID LIKE ? OR Teacher_CIN LIKE ? OR Module_ID LIKE ? OR Status LIKE ?";
            pst = conn.prepareStatement(sql);
            for (int i = 1; i <= 5; i++) {
                pst.setString(i, "%" + searchText + "%");
            }
            rs = pst.executeQuery();
            AttendanceList = FXCollections.observableArrayList();
            while (rs.next()) {
                int attId = rs.getInt("Attendance_ID");
                int stdID = rs.getInt("Student_ID");
                String tchrCIN = rs.getString("Teacher_CIN");
                int modId = rs.getInt("Module_ID");
                String stts = rs.getString("Status");

                AttendanceList.add(new Attendance(attId, stdID, tchrCIN, modId, stts));
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
                } else {
                    clearFields();
                }
            });
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }


    @FXML
    void modhandle(ActionEvent event) {

    }



    @FXML
    void handleKeyPress(KeyEvent event) {
        KeyCode keyCode = event.getCode();
        if (keyCode == KeyCode.UP) {
            focusPreviousField();
        } else if (keyCode == KeyCode.DOWN) {
            focusNextField();
        }
    }

    private void focusPreviousField() {
        if (AttendanceID.isFocused()) {
            ModID.requestFocus();
        } else if (stdID.isFocused()) {
            AttendanceID.requestFocus();
        } else if (TchrCIN.isFocused()) {
            stdID.requestFocus();
        } else if (ModID.isFocused()) {
            TchrCIN.requestFocus();
        }
    }

    private void focusNextField() {
        if (AttendanceID.isFocused()) {
            stdID.requestFocus();
        } else if (stdID.isFocused()) {
            TchrCIN.requestFocus();
        } else if (TchrCIN.isFocused()) {
            ModID.requestFocus();
        } else if (ModID.isFocused()) {
            AttendanceID.requestFocus();
        }
    }
}