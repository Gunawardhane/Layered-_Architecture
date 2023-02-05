package lk.ijse.upcycled.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.upcycled.model.AdminModel;
import lk.ijse.upcycled.util.UiStyleUtil;

import java.io.IOException;
import java.sql.SQLException;

public class  AdminLoginController {
    public AnchorPane context;
    public JFXTextField txtUsername;
    public JFXTextField txtPassword;

    public void btnAdminLogin(ActionEvent actionEvent) throws IOException {
        try {
            if (AdminModel.search(txtUsername.getText(),txtPassword.getText())!=null){
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("../view/AdminDashBoard.fxml")));
                UiStyleUtil.setStageStyle(scene, context);
            }else {
                new Alert(Alert.AlertType.WARNING,"Wrong login details").show();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void backToHomeForm(MouseEvent mouseEvent) throws IOException {
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("../view/HomeForm.fxml")));
        UiStyleUtil.setStageStyle(scene, context);
    }
}
