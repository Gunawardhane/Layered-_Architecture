package lk.ijse.upcycled.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class PaymentController {
    public AnchorPane context;
    public AnchorPane topBar;
    public Label lblHeader;
    public Label lblName;
    public TableView tblPayments;
    public TableColumn colPaymentID;
    public TableColumn colOrderID;
    public TableColumn colDate;
    public TableColumn colTotalAmount;
    public TableColumn colTime;
    public TextField txtSearchReturnItems;

    public void btnAddReturnItemsOnAction(ActionEvent actionEvent) {
    }

    public void btnUpdateReturnItemOnAction(ActionEvent actionEvent) {
    }

    public void btnDeleteReturnItemOnAction(ActionEvent actionEvent) {
    }

    public void btnRefreshOnAction(ActionEvent actionEvent) {
    }
}
