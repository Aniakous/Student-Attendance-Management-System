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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;

import javafx.collections.FXCollections;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.cell.PropertyValueFactory;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.beans.property.SimpleStringProperty;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javafx.scene.control.TableView;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import java.sql.SQLException;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Label;
import com.mysql.jdbc.Driver;


public class StudentController {

    @FXML
    private TableView<Student> StdTab;



    public void initialize() {
        Platform.runLater(() -> stdID.requestFocus());
        initializeTableColumns();
        displayStudents();
    }

    private void initializeTableColumns() {

        colstdID.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colBirth.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        colFieldID.setCellValueFactory(new PropertyValueFactory<>("fieldID"));
        colMail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
    }



    private ObservableList<Student> getAllUsers() {

        return null;
    }

    private void displayStudents() {
        ObservableList<Student> students = getAllUsers();
        StdTab.setItems(students);
    }

    @FXML
    private TextField stdID;


    @FXML
    private TextField birth;

    @FXML
    private TableColumn<Student, String> colBirth;


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

    private ObservableList<Student> studentList;

    private int index = -1;

    private Connection conn = null;
    private ResultSet rs = null;
    private PreparedStatement pst = null;



    @FXML
    void OnActionAddBtn(ActionEvent event) {
        conn = ConnexionMySQL.connectDb();
        String sql = "INSERT INTO Student(Full_Name, Date_of_Birth, Field_ID, Email, Phone_Number) VALUES (?,?,?,?,?)";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, fullname.getText());

            // Convert birth date format before inserting
            String birthDateString = birth.getText();
            SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date birthDate = inputDateFormat.parse(birthDateString);
            SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String formattedBirthDate = outputDateFormat.format(birthDate);

            pst.setString(2, formattedBirthDate);
            pst.setString(3, fieldID.getText());
            pst.setString(4, mail.getText());
            pst.setString(5, phone.getText());
            pst.execute();

            JOptionPane.showMessageDialog(null, "Student added");
            clearFields();
        } catch (SQLException | ParseException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }



    @FXML
    void OnActionDeleteBtn(ActionEvent event) {
        conn = ConnexionMySQL.connectDb();
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
            conn=ConnexionMySQL.connectDb();

            String value1=stdID.getText();
            String value2=fullname.getText();
            String value3=birth.getText();
            String value4=fieldID.getText();
            String value5=mail.getText();
            String value6=phone.getText();

            String sql="updateStudentsetStudent_ID='"+value1+"',Full_Name='"+value2+"',Date_of_Birth='"+value3+"',Field_ID='"+value4+"',Email='"+value5+"',Phone_Number='"+value6+"'WHEREStudent_ID='"+value1+"'";

            pst=conn.prepareStatement(sql);
            pst.execute();

            JOptionPane.showMessageDialog(null,"Student updated");

        }catch(Exception e){

            JOptionPane.showMessageDialog(null,e);


        }

    }

    @FXML
    void OnActionSearchBtn(ActionEvent event) {
        conn = ConnexionMySQL.connectDb();
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
                String birthDate = rs.getString("Date_of_Birth");
                int fieldID = rs.getInt("Field_ID");
                String email = rs.getString("Email");
                int phoneNumber = rs.getInt("Phone_Number");

                Student student = new Student(studentID, fullName, birthDate, fieldID, email, phoneNumber);
                studentList.add(student);
            }

            StdTab.setItems(studentList);
            clearFields(); // Clear the text fields initially

            StdTab.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    Student selectedStudent = newSelection;
                    stdID.setText(String.valueOf(selectedStudent.getStudentId()));
                    fullname.setText(selectedStudent.getFullName());
                    birth.setText(selectedStudent.getBirth());
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


    @FXML
    void OnActionStdTab(ActionEvent event) {

    }

    private void clearFields() {
        stdID.clear();
        fullname.clear();
        birth.clear();
        fieldID.clear();
        mail.clear();
        phone.clear();
    }

    public void setStudentList(ObservableList<Student> studentList) {
        this.studentList = studentList;
    }


    @FXML
    void OnActionStudent(ActionEvent event) {

    }

    public void OnActionExitBtn(ActionEvent actionEvent) {
    }

    public void OnActionHome(ActionEvent actionEvent) {
    }



}