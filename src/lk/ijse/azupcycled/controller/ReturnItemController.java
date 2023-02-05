package lk.ijse.upcycled.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class ReturnItemController {
    public AnchorPane context;
    public AnchorPane topBar;
    public Label lblHeader;
    public Label lblName;
    public TableView tblReturnItems;
    public TableColumn colReturnItemID;
    public TableColumn colReturnItemOrderID;
    public TableColumn colReturnItemAmount;
    public TableColumn colReturnItemReason;
    public TableColumn colReturnItemQty;
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
