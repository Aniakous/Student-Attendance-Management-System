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


public class StudentController  {

    @FXML
    private TableView<Student> StdTab;

    @FXML
    private TextField stdID;


    @FXML
    private TableColumn<Student, Integer> colFieldID;

    @FXML
    private TableColumn<Student, String> colMail;

    @FXML
    private TableColumn<Student, String> colName;

    @FXML
    private TableColumn<Student, Integer> colPhone;

    @FXML
    private TableColumn<Student, Integer> colstdID;

    @FXML
    private TextField fieldID;

    @FXML
    private TextField fullname;

    @FXML
    private Button home;

    @FXML
    private TextField mail;

    @FXML
    private TextField phone;

    @FXML
    private TextField search;

    @FXML
    private Button Exit;

    @FXML
    private Button student;


    public void initialize() {
        Platform.runLater(() -> stdID.requestFocus());
        initializeTableColumns();
        displayStudents();
    }

    private void initializeTableColumns() {
        colstdID.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colFieldID.setCellValueFactory(new PropertyValueFactory<>("fieldId"));
        colMail.setCellValueFactory(new PropertyValueFactory<>("mail"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
    }



    private void displayStudents() {
        ObservableList<Student> students = getAllUsers();
        StdTab.setItems(students);
    }

    private ObservableList<Student> studentList;
    private int index = -1;
    private Connection conn = null;
    private ResultSet rs = null;
    private PreparedStatement pst = null;



    public static ObservableList<Student> getAllUsers() {
        Connection conn = connectDb();
        ObservableList<Student> list = FXCollections.observableArrayList();

        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM student");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Student(
                        Integer.parseInt(rs.getString("Student_ID")),
                        rs.getString("Full_Name"),
                        Integer.parseInt(rs.getString("Field_ID")),
                        rs.getString("Email"),
                        Integer.parseInt(rs.getString("Phone_Number"))
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
        stdID.clear();
        fullname.clear();
        fieldID.clear();
        mail.clear();
        phone.clear();
    }


    @FXML
    void OnActionAddBtn(ActionEvent event) {
        conn = connectDb();
        String sql = "INSERT INTO Student(Full_Name, Field_ID, Email, Phone_Number) VALUES (?,?,?,?)";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, fullname.getText());
            pst.setString(2, fieldID.getText());
            pst.setString(3, mail.getText());
            pst.setString(4, phone.getText());
            pst.execute();

            JOptionPane.showMessageDialog(null, "Student added");
            clearFields();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }


    @FXML
    void OnActionDeleteBtn(ActionEvent event) {
        conn = connectDb();
        String sql = "DELETE FROM Student WHERE Student_ID = ?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, stdID.getText());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Student deleted");
            clearFields();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    @FXML
    void OnActionUpdateBtn(ActionEvent event) {

        try{
            conn = ConnexionMySQL.connectDb();
            String value1 = stdID.getText();
            String value2 = fullname.getText();
            String value3 = fieldID.getText();
            String value4 = mail.getText();
            String value5 = phone.getText();

            String sql = "update Student set Student_ID='"+value1+"',Full_Name = '"+value2+"', Field_ID = '"+value3+"', Email = '"+value4+"' , Phone_Number = '"+value5+"' WHERE Student_ID = '"+value1+"'";

            pst = conn.prepareStatement(sql);
            pst.execute();

            JOptionPane.showMessageDialog(null,"Student updated");
            clearFields();
        }catch (Exception e){

            JOptionPane.showMessageDialog(null,e);

        }
    }

    @FXML
    void OnActionSearchBtn(ActionEvent event) {
        conn = connectDb();
        String searchText = search.getText();

        try {
            String sql = "SELECT * FROM Student WHERE Full_Name LIKE ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, "%" + searchText + "%");
            rs = pst.executeQuery();

            studentList = FXCollections.observableArrayList();

            while (rs.next()) {
                int studentID = rs.getInt("Student_ID");
                String fullName = rs.getString("Full_Name");
                int fieldID = rs.getInt("Field_ID");
                String email = rs.getString("Email");
                int phoneNumber = rs.getInt("Phone_Number");

                Student student = new Student(studentID, fullName, fieldID, email, phoneNumber);
                studentList.add(student);
            }

            StdTab.setItems(studentList);
            clearFields();

            StdTab.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    Student selectedStudent = newSelection;
                    stdID.setText(String.valueOf(selectedStudent.getStudentId()));
                    fullname.setText(selectedStudent.getFullName());
                    fieldID.setText(String.valueOf(selectedStudent.getFieldId()));
                    mail.setText(selectedStudent.getMail());
                    phone.setText(String.valueOf(selectedStudent.getPhone()));
                } else {
                    clearFields();
                }
            });
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }


    public void OnActionExitBtn(ActionEvent actionEvent) {

        Platform.exit();
    }

    public void OnActionHome(ActionEvent actionEvent) throws IOException {


        JFxUtils.changeScene(Main.stage, "Home.fxml");
    }
}