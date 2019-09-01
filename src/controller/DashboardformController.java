package controller;

import javafx.animation.ScaleTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;

public class DashboardformController {


    //public AnchorPane root;
    public AnchorPane DashBoardAnchorPane;

    public void btnCustomer_playMouseEnterAnimation(MouseEvent mouseEvent) {
        playMouseEnterAnimation(mouseEvent);

    }

    public void btnCustomer_playMouseExitAnimation(MouseEvent mouseEvent) {
        playMouseExitAnimation(mouseEvent);
    }


    public void playMouseExitAnimation(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) mouseEvent.getSource();
            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1);
            scaleT.setToY(1);
            scaleT.play();
        }
    }

    public void playMouseEnterAnimation(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) mouseEvent.getSource();

            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1.2);
            scaleT.setToY(1.2);
            scaleT.play();

            DropShadow glow = new DropShadow();
            glow.setColor(Color.CORNFLOWERBLUE);
            glow.setWidth(20);
            glow.setHeight(20);
            glow.setRadius(20);
            icon.setEffect(glow);
        }

    }


    public void btnManageCustomers_OnAction(MouseEvent mouseEvent) throws IOException {
        URL resource = this.getClass().getResource("/view/ManageCustomersform.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) (this.DashBoardAnchorPane.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
    }

    public void btnMngItems_playMouseEnterAnimation(MouseEvent mouseEvent) {
        playMouseEnterAnimation(mouseEvent);
    }

    public void btnMngItems_playMouseExitAnimation(MouseEvent mouseEvent) {
        playMouseExitAnimation(mouseEvent);
    }

    public void btnPlaceOrders_playMouseEnterAnimation(MouseEvent mouseEvent) {
        playMouseEnterAnimation(mouseEvent);
    }

    public void btnPlaceOrders_playMouseExitAnimation(MouseEvent mouseEvent) {
        playMouseExitAnimation(mouseEvent);
    }

    public void btnSearchOrders_playMouseEnterAnimation(MouseEvent mouseEvent) {
        playMouseEnterAnimation(mouseEvent);
    }

    public void btnSearchOrders_playMouseExitAnimation(MouseEvent mouseEvent) {
        playMouseExitAnimation(mouseEvent);
    }

    public void btnPlaceOrders_OnAction(MouseEvent mouseEvent) throws IOException {
        URL resource = this.getClass().getResource("/view/PlaceOrderform.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) (this.DashBoardAnchorPane.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
    }

    public void btnManageitems_OnAction(MouseEvent mouseEvent) throws IOException {
        URL resource = this.getClass().getResource("/view/ManageItemsform.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) (this.DashBoardAnchorPane.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
    }

    public void btnSearchOrders_OnAction(MouseEvent mouseEvent) throws IOException {
        URL resource = this.getClass().getResource("/view/SearchOrderform.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) (this.DashBoardAnchorPane.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
    }
}
