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


public class FieldController {

    @FXML
    private Button Exit;

    @FXML
    private TextField FieldID;

    @FXML
    private TextField FldName;

    @FXML
    private TableView<Field> FldTab;

    @FXML
    private TableColumn<Field, Integer> ColFldID;

    @FXML
    private TableColumn<Field, String > colName;

    @FXML
    private Button home;

    @FXML
    private TextField search;

    @FXML
    private Button searchBtn;


    private ObservableList<Field> fieldList;

    private PreparedStatement pst;

    private Connection conn;


    public void initialize() {
        Platform.runLater(() -> FieldID.requestFocus());
        initializeTableColumns();
        displayFields();
    }

    private void initializeTableColumns() {
        ColFldID.setCellValueFactory(new PropertyValueFactory<>("FieldIDProperty"));
        colName.setCellValueFactory(new PropertyValueFactory<>("NameProperty"));
    }

    private void displayFields() {
        ObservableList<Field> fieldList = getAllFields();
        FldTab.setItems(fieldList);
    }

    private ObservableList<Field> getAllFields() {
        Connection conn = connectDb();
        ObservableList<Field> list = FXCollections.observableArrayList();

        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Field");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Field(
                        Integer.parseInt(rs.getString("FieldID")),
                        rs.getString("Name")
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
        FieldID.clear();
        FldName.clear();
    }

    public void setFieldList(ObservableList<Field> fieldList) {
        this.fieldList = fieldList;
    }

    @FXML
    void OnActionAddBtn(ActionEvent event) {
        conn = connectDb();
        String sql = "INSERT INTO Module Name VALUES (?)";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, FldName.getText());
            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "Field added");
            clearFields();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }



    @FXML
    void OnActionDeleteBtn(ActionEvent event) {

    }

    @FXML
    void OnActionExitBtn(ActionEvent event) {

        Platform.exit();
    }

    @FXML
    void OnActionHome(ActionEvent event) {

    }

    @FXML
    void OnActionFldTab(ActionEvent event) {

    }

    @FXML
    void OnActionSearchBtn(ActionEvent event) {

    }

    @FXML
    void OnActionUpdateBtn(ActionEvent event) {

    }

}
