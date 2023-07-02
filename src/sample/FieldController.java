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
import javafx.scene.input.MouseEvent;
import static sample.ConnexionMySQL.connectDb;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


public class FieldController {

    @FXML
    private TableColumn<Field, String> ColDesc;


    @FXML
    private TableColumn<Field, Integer> ColFldID;

    @FXML
    private TextField FieldID;

    @FXML
    private TextField FldName;

    @FXML
    private TableView<Field> FldTab;

    @FXML
    private TableColumn<Field, String> colName;

    @FXML
    private TextField Description;


    @FXML
    private TextField search;

    @FXML
    private Button searchBtn;


    public void initialize() {
        Platform.runLater(() -> FieldID.requestFocus());
        initializeTableColumns();
        displayFields();

        FieldID.setOnKeyPressed(this::handleKeyPress);
        FldName.setOnKeyPressed(this::handleKeyPress);
        Description.setOnKeyPressed(this::handleKeyPress);



    }
    public void updatetable() {
        ColFldID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        ColDesc.setCellValueFactory(new PropertyValueFactory<>("description"));

        fieldList = FXCollections.observableArrayList();
        fieldList.addAll(getAllFields());
        FldTab.setItems(fieldList);
    }




    private ObservableList<Field> fieldList;
    private int index = -1;
    private Connection conn = null;
    private ResultSet rs = null;
    private PreparedStatement pst = null;



    private void initializeTableColumns() {
        ColFldID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        ColDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
    }


    private void displayFields() {
        ObservableList<Field> fields = getAllFields();
        FldTab.setItems(fields);
    }


    public static ObservableList<Field> getAllFields() {
        Connection conn = connectDb();
        ObservableList<Field> list = FXCollections.observableArrayList();

        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM field");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Field(
                        rs.getInt("Field_ID"),
                        rs.getString("Name"),
                        rs.getString("Description")
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
        Description.clear();
    }

    public void setfieldList(ObservableList<Field> fieldList) {
        this.fieldList = fieldList;
    }



    @FXML
    void OnActionAddBtn(ActionEvent event) {
        conn = connectDb();
        String sql = "INSERT INTO Field(Field_ID, Name, Description) VALUES (?, ?, ?)";
        try {
            pst = conn.prepareStatement(sql);

            pst.setString(1, FieldID.getText());
            pst.setString(2, FldName.getText());
            pst.setString(3, Description.getText());
            pst.execute();

            JOptionPane.showMessageDialog(null, "Field added");
            clearFields();
            updatetable();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }



    @FXML
    void OnActionDeleteBtn(ActionEvent event) {
        conn = connectDb();
        String sql = "DELETE FROM field WHERE Field_ID  = ?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, FieldID.getText());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Field deleted");
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
                String FldName = rs.getString("Name");
                String Description = rs.getString("Description");

                Field field = new Field(FieldID, FldName, Description);
                fieldList.add(field);
            }

            FldTab.setItems(fieldList);
            clearFields();

            FldTab.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    Field selectedField = (Field) newSelection;
                    Description.setText(selectedField.getDescription());
                } else {
                    clearFields();
                }
            });
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }


    @FXML
    void OnActionUpdateBtn(ActionEvent event) {

        try{
            conn = ConnexionMySQL.connectDb();
            String value1 = FieldID.getText();
            String value2 = FldName.getText();
            String value3 = Description.getText();

            String sql = "update Field set Field_ID='"+value1+"',Name = '"+value2+"', Description = '"+value3+"'  WHERE Field_ID = '"+value1+"'";

            pst = conn.prepareStatement(sql);
            pst.execute();

            JOptionPane.showMessageDialog(null,"Field updated");
            clearFields();
            updatetable();
        }catch (Exception e){

            JOptionPane.showMessageDialog(null,e);

        }
    }



    @FXML
    void GetSelected(MouseEvent event) {
        index = FldTab.getSelectionModel().getSelectedIndex();

        if (index >= 0) {
            Field selectedField = FldTab.getItems().get(index);
            FieldID.setText(String.valueOf(selectedField.getId()));
            FldName.setText(selectedField.getName());
            Description.setText(selectedField.getDescription());
        }
    }


    @FXML
    void handleKeyPress(KeyEvent event) {
        KeyCode keyCode = event.getCode();
        if (keyCode == KeyCode.UP) {
            focusPreviousField();
            event.consume();
        } else if (keyCode == KeyCode.DOWN) {
            focusNextField();
            event.consume();
        }
    }

    private void focusPreviousField() {
        if (FldName.isFocused()) {
            FieldID.requestFocus();
        } else if (Description.isFocused()) {
            FldName.requestFocus();
        }
    }

    private void focusNextField() {
        if (FieldID.isFocused()) {
            FldName.requestFocus();
        } else if (FldName.isFocused()) {
            Description.requestFocus();
        }
    }


}