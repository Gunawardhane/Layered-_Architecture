//package lk.ijse.upcycled.controller;
//
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.scene.control.Label;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.scene.layout.AnchorPane;
//import lk.ijse.upcycled.model.CustomerModel;
//import lk.ijse.upcycled.model.CustomerOrderModel;
//import lk.ijse.upcycled.to.Customer;
//import lk.ijse.upcycled.to.CustomerOrder;
//
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.regex.Pattern;
//
//public class OrderController {
//    public AnchorPane topBar;
//    public Label lblHeader;
//    public Label lblName;
//    public TableView tblOrders;
//    public TableColumn orderID;
//    public TableColumn Date;
//    public TableColumn customerID;
//    public TableColumn colOrderID;
//    public TableColumn colDate;
//    public TableColumn colCustomerID;
//
//    public void initialize(){
//        colOrderID.setCellValueFactory(new PropertyValueFactory<>("CusId"));
//        colDate.setCellValueFactory(new PropertyValueFactory<>("Name"));
//        colCustomerID.setCellValueFactory(new PropertyValueFactory<>("Email"));
//
//
//        loadAll();
//
//
//
//    }
//
//    private void loadAll(){
//        try {
//            ArrayList<CustomerOrder> all = CustomerOrderModel.getAll();
//            ObservableList<CustomerOrder> obList = FXCollections.observableArrayList();
//            for (CustomerOrder customerOrder :
//                    all) {
//                obList.add(new CustomerOrder(customerOrder.getCustomerOrderID(),customerOrder.getDate(), customerOrder.getCustomerID()));
//            }
//            tblOrders.setItems(obList);
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//}
