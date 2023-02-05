//package lk.ijse.upcycled.controller;
//
//import com.jfoenix.controls.JFXButton;
//import com.jfoenix.controls.JFXTextField;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.event.ActionEvent;
//import javafx.scene.control.Alert;
//import javafx.scene.control.Label;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import javafx.scene.input.KeyEvent;
//import javafx.scene.layout.AnchorPane;
//import lk.ijse.upcycled.model.CustomerOrderModel;
//import lk.ijse.upcycled.model.ItemModel;
//import lk.ijse.upcycled.to.CustomerOrder;
//import lk.ijse.upcycled.to.Item;
//
//import java.sql.SQLException;
//import java.util.ArrayList;
//
//public class CustomerOrderController {
//    public AnchorPane context;
//    public AnchorPane topBar;
//    public Label lblHeader;
//    public Label lblName;
//    public JFXButton btnSave;
//    public TableView tblOrders;
//    public TableColumn colOrderID;
//    public TableColumn colOrderAmount;
//    public TableColumn colCustomerID;
//    public JFXTextField txtOrderID;
//    public JFXTextField txtOrderAmount;
//    public JFXTextField txtOrderCustomerID;
//
//    private void loadAll(){
//        try {
//            ArrayList<CustomerOrder> all = CustomerOrderModel.getAll();
//            ObservableList<CustomerOrder> obList = FXCollections.observableArrayList();
//            for (CustomerOrder customerOrder :
//                    all) {
//                obList.add(new CustomerOrder(customerOrder.getCustomerOrderID(),customerOrder.getAmount(), customerOrder.getCustomerID()));
//            }
//            tblOrders.setItems(obList);
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void btnAddOrderOnAction(ActionEvent actionEvent) {
//        saveItem();
//    }
//
//    private void saveItem(){
//        String customerOrderID = txtOrderCustomerID.getText();
//        String amount = txtOrderAmount.getText();
//        String cusID = txtOrderCustomerID.getText();
//
//        CustomerOrder customerOrder = new CustomerOrder(customerOrderID, amount, cusID);
//        try {
//            boolean isAdded = CustomerOrderModel.save(customerOrder);
//
//            if(isAdded) {
//                new Alert(Alert.AlertType.CONFIRMATION, "Order Added!").show();
//                loadAll();
//            } else {
//                new Alert(Alert.AlertType.WARNING, "Something happened!").show();
//            }
//        } catch (SQLException | ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//
//    public void btnSearchOrderOnAction(ActionEvent actionEvent) {
//        String customerOrderID= txtOrderID.getText();
//        try {
//            CustomerOrder customerOrder = CustomerOrderModel.search(customerOrderID);
//            if(customerOrder != null) {
//                fillData(customerOrder);
//
//            }
//        } catch (SQLException | ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public void btnDeleteOrderOnAction(ActionEvent actionEvent) {
//        String customerOrderID= txtOrderID.getText();
//        CustomerOrderModel customerOrderModel = new CustomerOrderModel();
//
//        boolean isDeleted = false;
//        try {
//            isDeleted = customerOrderModel.delete(customerOrderID);
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            System.out.println(e);
//        }
//
//        if(isDeleted) {
//            new Alert(Alert.AlertType.CONFIRMATION, "Order Delete!").show();
//            //emptyTextField(item);
//            // getAllData();
//        } else {
//            new Alert(Alert.AlertType.WARNING, "Something happened!").show();
//            //  emptyTextField(item);
//        }
//    }
//
//    public void btnUpdateOrderOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
//        String customerOrderID = txtOrderID.getText();
//        String amount = txtOrderAmount.getText();
//        String cusID = txtOrderCustomerID.getText();
//
//
//        CustomerOrder customerOrder =new CustomerOrder(customerOrderID,amount,cusID);
//
//        boolean isUpdated = CustomerOrderModel.update(customerOrder);
//        if (isUpdated){
//            new Alert(Alert.AlertType.CONFIRMATION,"Order Updated").show();
//        }else {
//            new Alert(Alert.AlertType.WARNING,"Something Happened").show();
//        }
//    }
//
//    public void textFields_Key_Released(KeyEvent keyEvent) {
//    }
//
//    private void fillData(CustomerOrder customerOrder){
//        txtOrderID.setText(customerOrder.getCustomerOrderID());
//        txtOrderAmount.setText(customerOrder.);
//        txtOrderCustomerID.setText(customerOrder.getCustomerOrderID());
//
//    }
//
//}
