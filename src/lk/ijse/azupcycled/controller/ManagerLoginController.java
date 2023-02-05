package lk.ijse.upcycled.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.upcycled.util.UiStyleUtil;

import java.io.IOException;

public class ManagerLoginController {
    public AnchorPane context;
    public JFXTextField txtUsername;
    public JFXTextField txtPassword;

    public void btnManagerLogin(ActionEvent actionEvent) throws IOException {
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("../view/ManagerDashboard.fxml")));
        UiStyleUtil.setStageStyle(scene, context);
    }

    public void backToHome(MouseEvent mouseEvent) throws IOException {
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("../view/HomeForm.fxml")));
        UiStyleUtil.setStageStyle(scene, context);
    }
}
