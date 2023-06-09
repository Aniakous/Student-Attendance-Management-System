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
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.*;

import static sample.ConnexionMySQL.connectDb;

public class ModuleController {

    @FXML
    private TableColumn<Module, Integer> ColFieldID;

    @FXML
    private TableColumn<Module, Integer> ColModID;

    @FXML
    private TableColumn<Module, String> colName;

    @FXML
    private Button Exit;

    @FXML
    private TextField FieldID;

    @FXML
    private TextField ModID;

    @FXML
    private TextField ModName;

    @FXML
    private TableView<Module> ModTab;

    @FXML
    private Button home;

    @FXML
    private TextField search;

    @FXML
    private Button searchBtn;

    public void initialize() {
        Platform.runLater(() -> ModID.requestFocus());
        initializeTableColumns();
        displayModules();
    }
    private ObservableList<Module> moduleList;
    private int index = -1;
    private Connection conn = null;
    private ResultSet rs = null;
    private PreparedStatement pst = null;


    private void initializeTableColumns() {
        ColModID.setCellValueFactory(new PropertyValueFactory<>("ModID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        ColFieldID.setCellValueFactory(new PropertyValueFactory<>("FieldID"));
    }

    private void displayModules() {
        ObservableList<Module> modules = getAllModules();
        ModTab.setItems(modules);
    }

    public static ObservableList<Module> getAllModules() {
        Connection conn = connectDb();
        ObservableList<Module> list = FXCollections.observableArrayList();

        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM module");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Module(
                        Integer.parseInt(rs.getString("Module_ID")),
                        rs.getString("Name"),
                        Integer.parseInt(rs.getString("Field_ID"))
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
        ModID.clear();
        ModName.clear();
        FieldID.clear();
    }
    @FXML
    void OnActionAddBtn(ActionEvent event) {
        conn = connectDb();
        String sql = "INSERT INTO Module(Name, Field_ID) VALUES (?,?)";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, ModName.getText());
            pst.setString(2, FieldID.getText());
            pst.execute();

            JOptionPane.showMessageDialog(null, "Module added");
            clearFields();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }



    @FXML
    void OnActionDeleteBtn(ActionEvent event) {
        conn = connectDb();
        String sql = "DELETE FROM Module WHERE Module_ID = ?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, ModID.getText());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Module deleted");
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
    void OnActionModTab(ActionEvent event) {
    }



    @FXML
    void OnActionSearchBtn(ActionEvent event) {
        conn = connectDb();
        String searchText = search.getText();

        try {
            String sql = "SELECT * FROM module WHERE Name LIKE ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, "%" + searchText + "%");
            rs = pst.executeQuery();

            moduleList = FXCollections.observableArrayList();

            while (rs.next()) {
                int modID = rs.getInt("Module_ID");
                String name = rs.getString("Name");
                int fieldID = rs.getInt("Field_ID");

                Module module = new Module(modID, name, fieldID);
                moduleList.add(module);
            }

            ModTab.setItems(moduleList);
            clearFields();

            ModTab.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    Module selectedModule = newSelection;
                    ModID.setText(String.valueOf(selectedModule.getModID()));
                    ModName.setText(selectedModule.getName());
                    FieldID.setText(String.valueOf(selectedModule.getFieldID()));
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
        try {
            conn = ConnexionMySQL.connectDb();
            String value1 = ModID.getText();
            String value2 = ModName.getText();
            String value3 = FieldID.getText();


            String sql = "update Module set Module_ID='" + value1 + "',Name = '" + value2 + "', Field_ID = '" + value3  + "' WHERE Module_ID = '" + value1 + "'";

            pst = conn.prepareStatement(sql);
            pst.execute();

            JOptionPane.showMessageDialog(null, "Module updated");
            clearFields();
        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, e);

        }
    }

    }
