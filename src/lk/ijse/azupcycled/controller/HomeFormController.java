package lk.ijse.upcycled.controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.upcycled.util.UiStyleUtil;

import java.io.IOException;

public class HomeFormController {
    public Circle c1;
    public Circle c2;
    public Circle c3;
    public Circle c4;
    public Circle c5;
    public Circle c6;
    public Circle c7;
    public Circle c8;
    public FontAwesomeIconView btnClose;
    public FontAwesomeIconView btnMin;
    public AnchorPane context;

    public void initialize() {
        setAnimation(c1, 900);
        setAnimation(c2, 1100);
        setAnimation(c3, 1200);
        setAnimation(c4, 1300);
        setAnimation(c5, 1400);
        setAnimation(c6, 1500);
        setAnimation(c7, 1600);
        setAnimation(c8, 1700);
    }

    private void setAnimation(Circle c, int seconds) {
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setNode(c);
        translateTransition.setDuration(Duration.millis(seconds));
        translateTransition.setCycleCount(TranslateTransition.INDEFINITE);
        translateTransition.setByY(-30);
        translateTransition.setAutoReverse(true);
        translateTransition.play();
    }


    public void btnManagerLogin(ActionEvent actionEvent) throws IOException {
        setUi("ManagerLoginForm");
    }

    public void btnAdminLogin(ActionEvent actionEvent) throws IOException {
        setUi("AdminLogin");
    }

    private void setUi(String location) throws IOException {
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("../view/" + location + ".fxml")));
        UiStyleUtil.setStageStyle(scene, context);
    }

    public void closeOnAction(MouseEvent mouseEvent) {
        System.exit(0);
    }

    public void btnMinusOnAction(MouseEvent mouseEvent) {
        Stage stage = (Stage) btnMin.getScene().getWindow();
        stage.setIconified(true);
    }
}
