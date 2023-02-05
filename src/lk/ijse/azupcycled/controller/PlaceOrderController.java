package lk.ijse.upcycled.controller;


import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.upcycled.model.*;
import lk.ijse.upcycled.to.CartDetails;
import lk.ijse.upcycled.to.Customer;
import lk.ijse.upcycled.to.Item;
import lk.ijse.upcycled.to.PlaceOrder;
import tm.PlaceOrderTM;


import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class PlaceOrderController {

    @FXML
    private TextField txtQty;
    public AnchorPane context;
    public Label lblOrderID;
    public Label lblOrderDate;
    public JFXComboBox cmbCustomerID;
    public Label lblCustomerName;
    public JFXComboBox cmbItemCode;
    public Label lblItemName;
    public Label lblUnitPrice;
    public Label lblQtyOnHand;
    public TableView<PlaceOrderTM> tblOrderCart;
    public TableColumn colItemCode;
    public TableColumn coltemName;
    public TableColumn colQty;
    public TableColumn colUnitPrice;
    public TableColumn colTotal;
    private TableColumn colAction;
    public Label lblTotal;

    private ObservableList<PlaceOrderTM> obList = FXCollections.observableArrayList();

    public void initialize() {
        loadOrderDate();
        loadCustomerIds();
//        loadNextOrderId();
        loadItemCodes();
        try {
            lblOrderID.setText(CustomerOrderModel.generateOrderId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        tblOrderCart.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("code"));
        tblOrderCart.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblOrderCart.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("qty"));
        tblOrderCart.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        tblOrderCart.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("total"));
        tblOrderCart.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("btnDelete"));
    }
    ArrayList<PlaceOrderTM> arrayList = new ArrayList<>();


    public void btnAddToCartOnAction(ActionEvent actionEvent) {
        String code = String.valueOf(cmbItemCode.getValue());
        int qty = Integer.parseInt(txtQty.getText());
        String name = lblItemName.getText();
        double unitPrice = Double.parseDouble(lblUnitPrice.getText());
        double total = unitPrice * qty;
        Button btnDelete = new Button("Delete");

        txtQty.setText("");

//        obList.add(new PlaceOrderTM(code,name,qty,unitPrice,total,btnDelete));


        /* set delete button to some action before it put on obList */
        btnDelete.setOnAction((e) -> {
            ButtonType ok = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("NO", ButtonBar.ButtonData.CANCEL_CLOSE);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure ?", ok, no);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.orElse(no) == ok) {
                PlaceOrderTM placeOrderTM = (PlaceOrderTM) tblOrderCart.getSelectionModel().getSelectedItem();
                /*
                netTot = Double.parseDouble(txtNetTot.getText());
                netTot = netTot - tm.getTotalPrice();
                */

                tblOrderCart.getItems().removeAll(tblOrderCart.getSelectionModel().getSelectedItem());
            }
        });
        obList.add(new PlaceOrderTM(code, name, qty, unitPrice, total, btnDelete));
        tblOrderCart.setItems(obList);
        double val = lblTotal.getText().equals("")?0: Double.parseDouble(lblTotal.getText());
        lblTotal.setText(String.valueOf(val+total));

        arrayList.add(new PlaceOrderTM(code, name, qty, unitPrice, total, btnDelete));
    }


    private void loadOrderDate() {
        lblOrderDate.setText(String.valueOf(LocalDate.now()));
    }

    private void loadCustomerIds() {
        try {
            ObservableList<String> observableList = FXCollections.observableArrayList();
            ArrayList<String> idList = CustomerModel.loadCustomerIds();

            for (String id : idList) {
                observableList.add(id);
            }
            cmbCustomerID.setItems(observableList);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadNextOrderId() {
        try {
            String orderId = OrderModel.generateNextOrderId();
            lblOrderID.setText(orderId);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadItemCodes() {
        try {
            ObservableList<String> observableList = FXCollections.observableArrayList();
            ArrayList<String> itemList = ItemModel.loadItemCodes();

            for (String code : itemList) {
                observableList.add(code);
            }
            cmbItemCode.setItems(observableList);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colItemCode.setCellValueFactory(new PropertyValueFactory("Code"));
        coltemName.setCellValueFactory(new PropertyValueFactory("Name"));
        colQty.setCellValueFactory(new PropertyValueFactory("Qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory("UnitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory("Total"));
        colAction.setCellValueFactory(new PropertyValueFactory("BtnDelete"));
    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) {
        String orderId = lblOrderID.getText();
        String customerId = String.valueOf(cmbCustomerID.getValue());

        ArrayList<CartDetails> cartDetails = new ArrayList<>();

//        /* load all cart items' to orderDetails arrayList */
//        for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
//            /* get each row details to (PlaceOrderTm)tm in each time and add them to the orderDetails */
//            PlaceOrderTM tm = obList.get(i);
//            cartDetails.add(new CartDetails(orderId, tm.getCode(), tm.getQty(), tm.getName(), tm.getUnitPrice()));
//        }
        for (PlaceOrderTM tm : arrayList) {
            cartDetails.add(new CartDetails(orderId,tm.getCode(), tm.getQty(), tm.getName(), tm.getUnitPrice() ));
        }

        PlaceOrder placeOrder = new PlaceOrder(customerId, orderId, cartDetails);
        try {
            boolean isPlaced = PlaceOrderModel.placeOrder(placeOrder);
            if (isPlaced) {
                /* to clear table */
                obList.clear();
                lblOrderID.setText(CustomerOrderModel.generateOrderId());
                new Alert(Alert.AlertType.CONFIRMATION, "Order Placed!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Order Not Placed!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void fillItemFields(Item item) {
        lblItemName.setText(item.getName());
        lblUnitPrice.setText(String.valueOf(item.getUnitPrice()));
        lblQtyOnHand.setText(String.valueOf(item.getQOH()));
    }

    public void txtQtyOnAction(ActionEvent actionEvent) {
        btnAddToCartOnAction(actionEvent);
    }

    public void cmbCusIdOnAction(ActionEvent actionEvent) {
        try {
            Customer cus = CustomerModel.search(cmbCustomerID.getValue().toString());
            if (cus != null) {
                lblCustomerName.setText(cus.getName());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void cmbCodeOnAction(ActionEvent actionEvent) {
        try {
            Item item = ItemModel.search(cmbItemCode.getValue().toString());
            if (item != null) {
                lblItemName.setText(item.getName());
                lblQtyOnHand.setText(item.getQOH());
                lblUnitPrice.setText(item.getUnitPrice());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}


