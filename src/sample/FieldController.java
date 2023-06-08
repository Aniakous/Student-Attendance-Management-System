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

import static com.sun.xml.internal.ws.policy.sourcemodel.wspolicy.XmlToken.Name;
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
    private int index = -1;
    private Connection conn = null;
    private ResultSet rs = null;
    private PreparedStatement pst = null;

    public void initialize() {
        Platform.runLater(() -> FieldID.requestFocus());
        initializeTableColumns();
        displayFields();
    }

    private void initializeTableColumns() {
        ColFldID.setCellValueFactory(new PropertyValueFactory<>("FieldID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("FldName"));
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
                        rs.getString("FldName")
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
        String sql = "INSERT INTO Field (Name) VALUES (?)";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, FldName.getText());
            pst.execute();

            JOptionPane.showMessageDialog(null, "Field added");
            clearFields();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }



    @FXML
    void OnActionDeleteBtn(ActionEvent event) {

        conn = connectDb();
        String sql = "DELETE FROM Field WHERE Field_ID = ?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, FieldID.getText());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Field deleted");
            clearFields();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    @FXML
    void OnActionExitBtn(ActionEvent event) {

        Platform.exit();
    }

    @FXML
    void OnActionHome(ActionEvent event) throws IOException {


        JFxUtils.changeScene(Main.stage, "Home.fxml");
    }

    @FXML
    void OnActionFldTab(ActionEvent event) {

    }

    @FXML
    void OnActionSearchBtn(ActionEvent event) {

        conn = connectDb();
        String searchText = search.getText();

        try {
            String sql = "SELECT * FROM Field WHERE Name LIKE ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, "%" + searchText + "%");
            rs = pst.executeQuery();

            fieldList = FXCollections.observableArrayList();

            while (rs.next()) {
                int FieldID = rs.getInt("Field_ID");
                String Name = rs.getString("Name");

                Field field = new Field(FieldID, Name);
                fieldList.add(field);
            }

            FldTab.setItems(fieldList);
            clearFields();

            FldTab.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    Field selectedField = newSelection;
                    FieldID.setText(String.valueOf(selectedField.getFieldID()));
                    FldName.setText(selectedField.getName());
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
            String value1 = FieldID.getText();
            String value2 = FldName.getText();

            String sql = "update Field set Field_ID='"+value1+"',Name = '"+value2+ "' WHERE Field_ID = '"+value1+"'";

            pst = conn.prepareStatement(sql);
            pst.execute();

            JOptionPane.showMessageDialog(null,"Field updated");
            clearFields();
        }catch (Exception e){

            JOptionPane.showMessageDialog(null,e);

        }
    }

}
