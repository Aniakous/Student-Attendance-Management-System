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
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;
import static sample.ConnexionMySQL.connectDb;

public class TeacherController {


    @FXML
    private Button Exit;

    @FXML
    private TextField TchrCIN;

    @FXML
    private TableView<Teacher> TchrTab;

    @FXML
    private TableColumn<Teacher, String> colMail;

    @FXML
    private TableColumn<Teacher, String> colName;

    @FXML
    private TableColumn<Teacher, String> colTchrCIN;

    @FXML
    private TableColumn<Teacher, String> ColPhone;

    @FXML
    private TextField fullname;

    @FXML
    private TextField Phone;

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
        Platform.runLater(() -> TchrCIN.requestFocus());
        initializeTableColumns();
        displayTeachers();
    }

    private void initializeTableColumns() {

        colTchrCIN.setCellValueFactory(new PropertyValueFactory<>("TchrCIN"));
        colName.setCellValueFactory(new PropertyValueFactory<>("fullname"));
        colMail.setCellValueFactory(new PropertyValueFactory<>("mail"));
        ColPhone.setCellValueFactory(new PropertyValueFactory<>("Phone"));
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
                        rs.getString("Teacher_CIN"),
                        rs.getString("Full_Name"),
                        rs.getString("Email"),
                        rs.getString("Phone_Number")
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
        TchrCIN.clear();
        fullname.clear();
        mail.clear();
        Phone.clear();
    }

    @FXML
    void OnActionAddBtn(ActionEvent event) {
        conn = connectDb();
        String sql = "INSERT INTO Teacher(Full_Name, Email, Phone_Number) VALUES (?, ?, ?)";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, fullname.getText());
            pst.setString(2, mail.getText());
            pst.setString(3, Phone.getText());
            pst.execute();

            JOptionPane.showMessageDialog(null, "Teacher added");
            clearFields();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }



    @FXML
    void OnActionDeleteBtn(ActionEvent event) {
        conn = connectDb();
        String sql = "DELETE FROM Teacher WHERE Teacher_CIN = ?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, TchrCIN.getText());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Teacher deleted");
            clearFields();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    @FXML
    void OnActionSearchBtn(ActionEvent event) {

        conn = connectDb();
        String searchText = search.getText();

        try {
            String sql = "SELECT * FROM Teacher WHERE Full_Name LIKE ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, "%" + searchText + "%");
            rs = pst.executeQuery();

            teacherList = FXCollections.observableArrayList();

            while (rs.next()) {
                String TchrCIN = rs.getString("Teacher_CIN");
                String fullName = rs.getString("Full_Name");
                String mail = rs.getString("Email");
                String phone = rs.getString("Phone_Number");

                Teacher teacher = new Teacher(TchrCIN, fullName, mail, phone);
                teacherList.add(teacher);
            }

            TchrTab.setItems(teacherList);
            clearFields();

            TchrTab.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    Teacher selectedTeacher = newSelection;
                    TchrCIN.setText(selectedTeacher.getTchrCIN());
                    fullname.setText(selectedTeacher.getFullName());
                    mail.setText(selectedTeacher.getMail());
                    Phone.setText(String.valueOf(selectedTeacher.getPhone()));
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
            String value1 = TchrCIN.getText();
            String value2 = fullname.getText();
            String value3 = mail.getText();
            String value4 = Phone.getText();

            String sql = "update Teacher set Teacher_CIN='"+value1+"',Full_Name = '"+value2+"', Email = '"+value3+"' , Phone_Number = '"+value4+"' WHERE Teacher_CIN = '"+value1+"'";

            pst = conn.prepareStatement(sql);
            pst.execute();

            JOptionPane.showMessageDialog(null,"Teacher updated");
            clearFields();
        }catch (Exception e){

            JOptionPane.showMessageDialog(null,e);

        }
        }

    @FXML
    void OnActionExitBtn(ActionEvent event)
    {
        Platform.exit();
    }

    public void OnActionHome(ActionEvent actionEvent) throws IOException {


        JFxUtils.changeScene(Main.stage, "Home.fxml");
    }
    @FXML
    void OnActionStdTab(ActionEvent event) {

    }

}
