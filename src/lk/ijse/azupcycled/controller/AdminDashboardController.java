package lk.ijse.upcycled.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import lk.ijse.upcycled.util.UiStyleUtil;

import java.io.IOException;

public class AdminDashboardController {


    public AnchorPane menuContext;
    public AnchorPane backgroundContext;
    public AnchorPane topBar;
    public Label lblHeader;
    public Label lblName;
    public BarChart barChart;
    public Label topProduct1;
    public Label topProduct2;
    public Label topProduct3;
    public JFXButton lblClock;
    public AnchorPane context;

    public void btnDashboardOnAction(ActionEvent actionEvent) throws IOException {

    }

    public void btnItemsOnAction(ActionEvent actionEvent) throws IOException {
       setUiDashboard("ItemForm","Items");
    }

    public void btnSuppliersOnAction(ActionEvent actionEvent) throws IOException {
        setUiDashboard("AdminSupplierForm","Suppliers");
    }

    public void btnCustomersOnAction(ActionEvent actionEvent) throws IOException {
        setUiDashboard("CustomerForm","Customers");
    }

    public void btnReturnItemsOnAction(ActionEvent actionEvent) throws IOException {
        setUiDashboard("ReturnItems","Return Items");
    }

    public void btnPaymentsOnAction(ActionEvent actionEvent) throws IOException {
        setUiDashboard("Payments","Payments");
    }

    public void btnInventoryOnAction(ActionEvent actionEvent) throws IOException {
        setUiDashboard("AdminInventory","Inventory");
    }

    public void btnSalaryOnAction(ActionEvent actionEvent) throws IOException {
        setUiDashboard("AdminSalary","Salary");
    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) throws IOException {
        setUiDashboard("PlaceOrder","Place A order");
    }

    public void btnLogOutOnAction(ActionEvent actionEvent) throws IOException {
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("../view/HomeForm.fxml")));
        UiStyleUtil.setStageStyle(scene, context);
    }

    private void setUiDashboard(String location, String header) throws IOException {
        lblHeader.setText(header);
        backgroundContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/" + location + ".fxml"));
        backgroundContext.getChildren().add(parent);
    }

    public void btnEmployeesOnAction(ActionEvent actionEvent) throws IOException {
        setUiDashboard("Employees","Employees");
    }

    public void btnOrdersOnAction(ActionEvent actionEvent) throws IOException {
        setUiDashboard("Order","Orders");
    }
}
