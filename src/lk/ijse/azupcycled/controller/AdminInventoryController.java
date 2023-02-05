package lk.ijse.upcycled.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.upcycled.model.CustomerModel;
import lk.ijse.upcycled.model.InventoryModel;
import lk.ijse.upcycled.to.Customer;
import lk.ijse.upcycled.to.Inventory;
import lk.ijse.upcycled.to.Item;
import lk.ijse.upcycled.util.ValidationUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class AdminInventoryController {
    public AnchorPane context;
    public AnchorPane topBar;
    public Label lblHeader;
    public Label lblName;
    public TableView tblInventory;
    public TableColumn colInventoryID;
    public TableColumn colSupplierID;
    public TableColumn colInventoryItemID;
    public TableColumn colInventoryPlacedTime;
    public TableColumn colInventoryQtyOnHand;
    public TextField txtSearchInventory;
    public JFXButton btnSave;
    public JFXTextField txtQtyOnHand;
    public JFXTextField txtInventoryID;
    public JFXTextField txtSupplierID;
    public JFXTextField txtItemID;
    LinkedHashMap<JFXTextField, Pattern> map = new LinkedHashMap<>();

    private void fillData(Inventory inventory){
        txtInventoryID.setText(inventory.getIvID());
        txtQtyOnHand.setText(inventory.getQOH());
        txtSupplierID.setText(inventory.getSupID());
        txtItemID.setText(inventory.getItemID());

    }

    public void initialize(){
        colInventoryID.setCellValueFactory(new PropertyValueFactory<>("IvID"));
        colInventoryQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("QOH"));
        colSupplierID.setCellValueFactory(new PropertyValueFactory<>("SupID"));
        colInventoryItemID.setCellValueFactory(new PropertyValueFactory<>("ItemID"));

        loadAll();

        Pattern idPattern = Pattern.compile("^(IV)[0-9]{3}$");
        Pattern supplierID = Pattern.compile("^(S)[0-9]{3}$");
        Pattern itemID = Pattern.compile("^(I)[0-9]{3}$");

        map.put(txtInventoryID, idPattern);
        map.put(txtSupplierID, supplierID);
        map.put(txtItemID, itemID);

    }

    private void loadAll(){
        try {
            ArrayList<Inventory> all = InventoryModel.getAll();
            ObservableList<Inventory> obList = FXCollections.observableArrayList();
            for (Inventory inventory :
                    all) {
                obList.add(new Inventory(inventory.getIvID(),inventory.getQOH(), inventory.getSupID(), inventory.getItemID()));
            }
            tblInventory.setItems(obList);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void saveInventory() throws SQLException, ClassNotFoundException {
        String inventoryID = txtInventoryID.getText();
        String QOH = txtQtyOnHand.getText();
        String supplierID = txtSupplierID.getText();
        String itemID = txtItemID.getText();


        Inventory inventory = new Inventory(inventoryID,QOH,supplierID,itemID);
        try{
            boolean isAdded = InventoryModel.save(inventory);

            if(isAdded){

                new Alert(Alert.AlertType.CONFIRMATION, "inventory Added!").show();
                loadAll();
            } else {
                new Alert(Alert.AlertType.WARNING, "Something happened!").show();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void btnAddInventoryOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        saveInventory();
    }

    public void btnUpdateInventoryOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String inventoryID = txtInventoryID.getText();
        String QOH = txtQtyOnHand.getText();
        String supplierID = txtSupplierID.getText();
        String itemID = txtItemID.getText();

        Inventory inventory = new Inventory(inventoryID,QOH,supplierID,itemID);

        boolean isUpdated = InventoryModel.update(inventory);
        if (isUpdated){
            new Alert(Alert.AlertType.CONFIRMATION,"Inventory Updated").show();
            Item clearItem = new Item();
            loadAll();
        }else {
            new Alert(Alert.AlertType.WARNING,"Something Happened").show();
        }
    }

    public void btnDeleteInventoryOnAction(ActionEvent actionEvent) {
        String inventoryID = txtInventoryID.getText();

        InventoryModel inventoryModel = new InventoryModel();

        boolean isDeleted = false;
        try {
            isDeleted = inventoryModel.delete(inventoryID);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        }

        if(isDeleted) {
            new Alert(Alert.AlertType.CONFIRMATION, "Inventory Deleted!").show();
            //emptyTextField(item);
            // getAllData();
        } else {
            new Alert(Alert.AlertType.WARNING, "Something happened!").show();
            //  emptyTextField(item);
        }
    }

    public void btnRefreshOnAction(ActionEvent actionEvent) {
initialize();
    }

    public void btnSearchInventoryOnAction(ActionEvent actionEvent) {
        String inventoryID = txtInventoryID.getText();
        try {
            Inventory inventory = InventoryModel.search(inventoryID);
            if(inventory != null) {
                fillData(inventory);

            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void textFields_Key_Released(KeyEvent keyEvent) throws SQLException, ClassNotFoundException {
        ValidationUtil.validate(map,btnSave);
        if (keyEvent.getCode() == KeyCode.ENTER) {
            Object response = ValidationUtil.validate(map, btnSave);

            if (response instanceof JFXTextField) {
                JFXTextField textField = (JFXTextField) response;
                textField.setStyle("-fx-font-size: 20; -fx-prompt-text-fill: #A7A7A7");
                textField.requestFocus();
            } else if (response instanceof Boolean) {
                System.out.println("Work");
                saveInventory();
            }
        }
    }
}
