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


    private ObservableList<Module> moduleList;
    private int index = -1;
    private Connection conn = null;
    private ResultSet rs = null;
    private PreparedStatement pst = null;

    public void initialize() {
        Platform.runLater(() -> ModID.requestFocus());
        initializeTableColumns();
        displayModules();
    }

    private void initializeTableColumns() {
        ColModID.setCellValueFactory(new PropertyValueFactory<>("ModID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        ColFieldID.setCellValueFactory(new PropertyValueFactory<>("FieldID"));
    }

    private void displayModules() {
        moduleList = getAllModules();
        ModTab.setItems(moduleList);
    }

    public static ObservableList<Module> getAllModules() {
        Connection conn = connectDb();
        ObservableList<Module> list = FXCollections.observableArrayList();

        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Module");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Module(
                        rs.getInt("Module_ID"),
                        rs.getString("Name"),
                        rs.getInt("Field_ID")
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
        Module selectedModule = ModTab.getSelectionModel().getSelectedItem();

        if (selectedModule != null) {
            int moduleId = selectedModule.getModID();
            String sql = "DELETE FROM Module WHERE ModID = ?";

            try {
                conn = connectDb();
                pst = conn.prepareStatement(sql);
                pst.setInt(1, moduleId);
                pst.execute();

                JOptionPane.showMessageDialog(null, "Module deleted");
                displayModules();
                clearFields();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select a module to delete");
        }
    }



    @FXML
    void OnActionExitBtn(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void OnActionHome(ActionEvent event) {

    }

    @FXML
    void OnActionModTab(ActionEvent event) {

    }

    @FXML
    void OnActionSearchBtn(ActionEvent event) {
        conn = connectDb();
        String searchText = search.getText();

        try {
            String sql = "SELECT * FROM Module WHERE Name LIKE ?";
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
            Module selectedModule = ModTab.getSelectionModel().getSelectedItem();

            if (selectedModule != null) {
                try {
                    conn = connectDb();
                    String modID = ModID.getText();
                    String name = ModName.getText();
                    String fieldID = FieldID.getText();

                    String sql = "UPDATE Module SET Name = ?, FieldID = ? WHERE ModID = ?";
                    pst = conn.prepareStatement(sql);
                    pst.setString(1, name);
                    pst.setString(2, fieldID);
                    pst.setString(3, modID);
                    pst.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Module updated");
                    displayModules();
                    clearFields();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please select a module to update");
            }
        }

    }
