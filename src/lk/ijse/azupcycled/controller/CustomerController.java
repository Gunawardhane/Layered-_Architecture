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
import lk.ijse.azupcycled.bo.custom.BOFactory;
import lk.ijse.azupcycled.bo.custom.CustomerBO;
import lk.ijse.azupcycled.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.azupcycled.dto.CustomerDTO;
import lk.ijse.azupcycled.entity.Customer;
import lk.ijse.azupcycled.view.tdm.CustomerTM;
import lk.ijse.upcycled.model.CustomerModel;
import lk.ijse.upcycled.model.ItemModel;
import lk.ijse.upcycled.to.Customer;
import lk.ijse.upcycled.to.Item;
import lk.ijse.upcycled.util.ValidationUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class CustomerController {
    public AnchorPane context;
    public AnchorPane topBar;
    public Label lblHeader;
    public Label lblName;
    public TableView tblCustomer;
    public TableColumn colCustomerID;
    public TableColumn colCustomerName;
    public TableColumn colCustomerEmail;
    public TableColumn colCustomerPhoneNumber;
    public TableColumn colCustomerAddress;
    public TextField txtSearchCustomers;
    public JFXTextField txtCusID;
    public JFXTextField txtCusName;
    public JFXTextField txtCusEmail;
    public JFXTextField txtCusPN;
    public JFXTextField txtCusAddress;
    public JFXButton btnSave;
    public TableView<CustomerTM> tblCustomer;
    LinkedHashMap<JFXTextField, Pattern> map = new LinkedHashMap<>();

//    private void fillData(CustomerBO customerBO){
//        txtCusID.setText(customer.getCusId());
//        txtCusName.setText(customer.getName());
//        txtCusEmail.setText(customer.getEmail());
//        txtCusPN.setText(customer.getPn());
//        txtCusAddress.setText(customer.getAddress());
//
//    }

    CustomerBO customerBO = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);

    public void initialize(){
        colCustomerID.setCellValueFactory(new PropertyValueFactory<>("CusId"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colCustomerEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
        colCustomerPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("Pn"));
        colCustomerAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));

        loadAll();

        Pattern idPattern = Pattern.compile("^(C)[0-9]{3}$");
        Pattern namePattern = Pattern.compile("^[A-z0-9 ,-/]{3,20}$");
        Pattern email = Pattern.compile(".+\n");
        Pattern phoneNumber = Pattern.compile("^0\\d{9}$");
        Pattern address = Pattern.compile("^\\d+\\s[A-z]+\\s[A-z]+");

        map.put(txtCusID, idPattern);
        map.put(txtCusName, namePattern);

        map.put(txtCusPN, phoneNumber);
        map.put(txtCusAddress,namePattern);

    }

    private void loadAll(){
        tblCustomer.getItems().clear();
        try {
            ArrayList<CustomerDTO> allCustomers = customerBO.getAllCustomers();
            for (CustomerDTO c : allCustomers) {
                tblCustomer.getItems.add(new CustomerTM(c.getCusId(),c.getName(), c.getEmail(), c.getPn(), c.getAddress()));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void btnAddCustomerOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
     saveCustomer();
    }

    private void saveCustomer() throws SQLException, ClassNotFoundException {
        String cusID = txtCusID.getText();
        String cusName = txtCusName.getText();
        String cusEmail = txtCusEmail.getText();
        String cusPn = txtCusPN.getText();
        String cusAddress = txtCusAddress.getText();

        Customer customer = new Customer(cusID,cusName,cusEmail,cusPn,cusAddress);
        try{
            boolean isAdded = CustomerModel.save(customer);

            if(isAdded){

                new Alert(Alert.AlertType.CONFIRMATION, "Customer Added!").show();
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

    public void btnUpdateCustomerOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String cusID = txtCusID.getText();
        String cusName = txtCusName.getText();
        String cusEmail = txtCusEmail.getText();
        String cusPn = txtCusPN.getText();
        String cusAddress = txtCusAddress.getText();

        Customer customer = new Customer(cusID,cusName,cusEmail,cusPn,cusAddress);

        boolean isUpdated = CustomerModel.update(customer);
        if (isUpdated){
            new Alert(Alert.AlertType.CONFIRMATION,"Customer Updated").show();
            Item clearItem = new Item();
            loadAll();
        }else {
            new Alert(Alert.AlertType.WARNING,"Something Happened").show();
        }

    }

    public void btnDeleteCustomerOnAction(ActionEvent actionEvent) {
        String cusID = txtCusID.getText();

        CustomerModel customerModel = new CustomerModel();

        boolean isDeleted = false;
        try {
            isDeleted = customerModel.delete(cusID);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        }

        if(isDeleted) {
            new Alert(Alert.AlertType.CONFIRMATION, "Customer Deleted!").show();
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

    public void btnSearchCustomerOnAction(ActionEvent actionEvent) {
        String cusID = txtCusID.getText();
        try {
            Customer customer = CustomerModel.search(cusID);
            if(customer != null) {
                fillData(customer);

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
                saveCustomer();
            }
        }
    }
}
