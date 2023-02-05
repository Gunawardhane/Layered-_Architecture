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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.upcycled.model.ItemModel;
import lk.ijse.upcycled.model.SupplierModel;
import lk.ijse.upcycled.to.Item;
import lk.ijse.upcycled.to.Supplier;
import lk.ijse.upcycled.util.ValidationUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class AdminSupplierController {
    public AnchorPane context;
    public AnchorPane topBar;
    public Label lblHeader;
    public Label lblName;
    public TableView tblItem;
    public TableColumn colSupplierID;
    public TableColumn colSupplierName;
    public TableColumn colSupplierAddress;
    public TableColumn colSupplierPN;
    public TextField txtSearchSuppliers;
    public JFXTextField txtSupplierID;
    public JFXTextField txtSupplierName;
    public JFXTextField txtSupplierAddress;
    public JFXTextField txtSupplierPhoneNumber;
    public JFXButton btnSave;
    public TableView tblSuppliers;
    LinkedHashMap<JFXTextField, Pattern> map = new LinkedHashMap<>();

    private void fillData(Supplier supplier){
        txtSupplierID.setText(supplier.getSupplierID());
        txtSupplierName.setText(supplier.getSupplierName());
        txtSupplierAddress.setText(supplier.getSupplierAddress());
        txtSupplierPhoneNumber.setText(supplier.getSupplierPhoneNumber());
    }

    public void initialize(){
        colSupplierID.setCellValueFactory(new PropertyValueFactory<>("SupplierID"));
        colSupplierName.setCellValueFactory(new PropertyValueFactory<>("SupplierName"));
        colSupplierAddress.setCellValueFactory(new PropertyValueFactory<>("SupplierAddress"));
        colSupplierPN.setCellValueFactory(new PropertyValueFactory<>("SupplierPhoneNumber"));

        loadAll();

        Pattern idPattern = Pattern.compile("^(S)[0-9]{3}$");
        Pattern namePattern = Pattern.compile("^[A-z0-9 ,-/]{3,20}$");
        Pattern address = Pattern.compile("^\\d+\\s[A-z]+\\s[A-z]+");
        Pattern phoneNumber = Pattern.compile("^0\\d{9}$");

        map.put(txtSupplierID, idPattern);
        map.put(txtSupplierName, namePattern);
        map.put(txtSupplierAddress, namePattern);
        map.put(txtSupplierPhoneNumber, phoneNumber);



    }


    private void loadAll(){
        try {
            ArrayList<Supplier> all = SupplierModel.getAll();
            ObservableList<Supplier> obList = FXCollections.observableArrayList();
            for (Supplier supplier :
                    all) {
                obList.add(new Supplier(supplier.getSupplierID(),supplier.getSupplierName(), supplier.getSupplierAddress(), supplier.getSupplierPhoneNumber()));
            }
            tblSuppliers.setItems(obList);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void btnAddSuppliersOnAction(ActionEvent actionEvent) {
saveSupplier();
    }

    private void saveSupplier(){
        String supplierID = txtSupplierID.getText();
        String supplierName = txtSupplierName.getText();
        String supplierAddress = txtSupplierAddress.getText();
        String supplierPhoneNumber = txtSupplierPhoneNumber.getText();

        Supplier supplier = new Supplier(supplierID, supplierName, supplierAddress, supplierPhoneNumber);
        try {
            boolean isAdded = SupplierModel.save(supplier);

            if(isAdded) {
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier Added!").show();
                loadAll();
            } else {
                new Alert(Alert.AlertType.WARNING, "Something happened!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public void btnUpdateSuppliersOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String supplierID = txtSupplierID.getText();
        String supplierName = txtSupplierName.getText();
        String supplierAddress = txtSupplierAddress.getText();
        String supplierPhoneNumber = txtSupplierPhoneNumber.getText();

        Supplier supplier =new Supplier(supplierID,supplierName,supplierAddress,supplierPhoneNumber);

        boolean isUpdated = SupplierModel.update(supplier);
        if (isUpdated){
            new Alert(Alert.AlertType.CONFIRMATION,"Supplier Updated").show();
            Supplier clearItem = new Supplier();
            clearMethod(clearItem);
        }else {
            new Alert(Alert.AlertType.WARNING,"Something Happened").show();
        }
    }

    public void btnDeleteSuppliersOnAction(ActionEvent actionEvent) {
        String supplierID= txtSupplierID.getText();
//
        SupplierModel supplierModel = new SupplierModel();

        boolean isDeleted = false;
        try {
            isDeleted = supplierModel.delete(supplierID);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        }

        if(isDeleted) {
            new Alert(Alert.AlertType.CONFIRMATION, "Supplier Delete!").show();
            //emptyTextField(item);
            // getAllData();
        } else {
            new Alert(Alert.AlertType.WARNING, "Something happened!").show();
            //  emptyTextField(item);
        }
    }


    public void textFields_Key_Released(KeyEvent keyEvent) {
        ValidationUtil.validate(map,btnSave);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            Object response = ValidationUtil.validate(map, btnSave);

            if (response instanceof JFXTextField) {
                JFXTextField textField = (JFXTextField) response;
                textField.setStyle("-fx-font-size: 20; -fx-prompt-text-fill: #A7A7A7");
                textField.requestFocus();
            } else if (response instanceof Boolean) {
                System.out.println("Work");
                saveSupplier();
            }
        }
    }

    public void btnSearchSuppliersOnAction(ActionEvent actionEvent) {
        String supplierID = txtSupplierID.getText();
        try {
            Supplier supplier = SupplierModel.search(supplierID);
            if(supplier != null) {
                fillData(supplier);

            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void clearMethod(Supplier clearItem){
        txtSupplierID.clear();
        txtSupplierName.clear();
        txtSupplierAddress.clear();
        txtSupplierPhoneNumber.clear();
    }

    public void refreshThePage(MouseEvent mouseEvent) {
        initialize();
    }
}
