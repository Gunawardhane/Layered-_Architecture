package lk.ijse.upcycled.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.upcycled.model.CustomerOrdersModel;
import lk.ijse.upcycled.to.CustomerOrder;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerOrdersController {
    public AnchorPane topBar;
    public Label lblHeader;
    public Label lblName;
    public TableView tblOrders;
    public TableColumn colOrderID;
    public TableColumn colDate;
    public TableColumn colCustomerID;

    public void initialize(){
        colOrderID.setCellValueFactory(new PropertyValueFactory<>("CustomerOrderID"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
        colCustomerID.setCellValueFactory(new PropertyValueFactory<>("CustomerID"));


        loadAll();



    }

    private void loadAll(){
        try {
            ArrayList<CustomerOrder> all = CustomerOrdersModel.getAll();
            ObservableList<CustomerOrder> obList = FXCollections.observableArrayList();
            for (CustomerOrder customerOrder :
                    all) {
                obList.add(new CustomerOrder(customerOrder.getCustomerOrderID(),customerOrder.getDate(), customerOrder.getCustomerID()));
            }
            tblOrders.setItems(obList);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
