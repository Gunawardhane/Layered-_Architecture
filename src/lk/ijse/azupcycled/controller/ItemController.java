package lk.ijse.upcycled.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.upcycled.db.DBConnection;
import lk.ijse.upcycled.model.ItemModel;
import lk.ijse.upcycled.to.Item;
import lk.ijse.upcycled.util.CrudUtil;
import lk.ijse.upcycled.util.ValidationUtil;
import lk.ijse.upcycled.controller.AdminDashboardController;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Pattern;

public class ItemController {
    public AnchorPane context;
    public AnchorPane topBar;
    public Label lblHeader;
    public Label lblName;
    public TableView tblItem;
    public TableColumn colItemID;
    public TableColumn colItemName;
    public TableColumn colBuyingPrice;
    public TableColumn colSellingPrice;
    public TextField txtSearchItem;
    public JFXTextField txtItemID;
    public JFXTextField txtItemName;
    public JFXTextField txtSellingPrice;
    public JFXTextField txtBuyingPrice;
    public JFXButton btnSave;
    public JFXButton refreshButton;
    public TableColumn colQoh;
    public TableColumn countPrice;
    public JFXTextField txtQtyOnHand;
    public JFXTextField txtUnitPrice;
    public TableColumn colUnitPrice;
    public Button itemreport;
    public Button itemreportBtn;
    LinkedHashMap<JFXTextField, Pattern> map = new LinkedHashMap<>();

    private void fillData(Item item){
        txtItemID.setText(item.getItemID());
        txtItemName.setText(item.getName());
        txtUnitPrice.setText(item.getUnitPrice());
        txtQtyOnHand.setText(item.getQOH());
    }


    public void initialize(){
        colItemID.setCellValueFactory(new PropertyValueFactory<>("ItemID"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQoh.setCellValueFactory(new PropertyValueFactory<>("QOH"));

        loadAll();

        Pattern idPattern = Pattern.compile("^(I)[0-9]{3}$");
        Pattern namePattern = Pattern.compile("^[A-z0-9 ,-/]{3,20}$");
        Pattern price = Pattern.compile("^[1-9][0-9]*(.[0-9]{2})?$");

        map.put(txtItemID, idPattern);
        map.put(txtItemName, namePattern);
        map.put(txtUnitPrice, price);
        map.put(txtQtyOnHand, price);

        
        
        
    }

    private void loadAll(){
        try {
            ArrayList<Item> all = ItemModel.getAll();
            ObservableList<Item> obList = FXCollections.observableArrayList();
            for (Item item :
                    all) {
                obList.add(new Item(item.getItemID(),item.getName(), item.getUnitPrice(), item.getQOH()));
            }
            tblItem.setItems(obList);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void btnAddItemOnAction(ActionEvent actionEvent) {
        saveItem();
    }

    private void saveItem(){
        String itemId = txtItemID.getText();
        String name = txtItemName.getText();
        String unitPrice = txtUnitPrice.getText();
        String QOH = txtQtyOnHand.getText();

        Item item = new Item(itemId, name, unitPrice, QOH);
        try {
            boolean isAdded = ItemModel.save(item);

            if(isAdded) {
                new Alert(Alert.AlertType.CONFIRMATION, "Item Added!").show();
                loadAll();
            } else {
                new Alert(Alert.AlertType.WARNING, "Something happened!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
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
                saveItem();
            }
        }
    }


        public void btnUpdateItemOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String itemid = txtItemID.getText();
        String name = txtItemName.getText();
        String unitPrice = txtUnitPrice.getText();
        String QOH = txtQtyOnHand.getText();

        Item item =new Item(itemid,name,unitPrice,QOH);

        boolean isUpdated = ItemModel.update(item);
        if (isUpdated){
            new Alert(Alert.AlertType.CONFIRMATION,"Item Updated").show();
            Item clearItem = new Item();
            clearMethod(clearItem);
            tblItem.refresh();
            loadAll();
        }else {
            new Alert(Alert.AlertType.WARNING,"Something Happened").show();
        }



    }

    public void btnDeleteItemOnAction(ActionEvent actionEvent) {
        String itemId= txtItemID.getText();
//        Item item = new Item(itemId);
        ItemModel itemModel = new ItemModel();

        boolean isDeleted = false;
        try {
            isDeleted = itemModel.delete(itemId);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        }

        if(isDeleted) {
            new Alert(Alert.AlertType.CONFIRMATION, "Item Deleted!").show();
            //emptyTextField(item);
           // getAllData();
        } else {
            new Alert(Alert.AlertType.WARNING, "Something happened!").show();
          //  emptyTextField(item);
        }
    }



    public void btnSearchItemOnAction(ActionEvent actionEvent) {
        String itemId = txtItemID.getText();
        try {
            Item item = ItemModel.search(itemId);
            if(item != null) {
                fillData(item);

            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void clearMethod(Item clearItem){
        txtItemID.clear();
        txtItemName.clear();
        txtSellingPrice.clear();
        txtBuyingPrice.clear();
    }


    public void refreshThePage(MouseEvent mouseEvent) {
        initialize();
    }

    public void showReport(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, JRException {

        InputStream resource = this.getClass().getResourceAsStream("/reports/ItemReport.jrxml");
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(resource);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DBConnection.getDBConnection().getConnection());
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }




    }

    public void generateItemReport(MouseEvent mouseEvent) {
        try {
            JasperReport compiledReport = (JasperReport) JRLoader.loadObject(this.getClass().getResource("/reports/Invoice_1.jrxml"));
            Connection connection = DBConnection.getDBConnection().getConnection();
            JasperPrint jasperPrint = JasperFillManager.fillReport(compiledReport, null, connection);
            JasperViewer.viewReport(jasperPrint, false);


        } catch (JRException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
