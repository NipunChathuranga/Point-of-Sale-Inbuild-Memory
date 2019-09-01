package controller;

import com.jfoenix.controls.JFXButton;
import db.DB;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.ItemTM;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

public class ManageItemsformController {

    public TextField txtFieldItemCode;
    public TextField txtFieldItemDescription;
    public TextField txtFieldQtyonHand;
    public TextField txtFieldUnitPrice;
    public JFXButton btnsave;
    public JFXButton btnDelete;
    public Button btnNewItem;
    public TableView<ItemTM> tblViewItems;
    public TableColumn<ItemTM, String> clmnitemcode;
    public TableColumn<ItemTM, String> clmnItemDescription;
    public TableColumn<ItemTM, Integer> clmnQtyonhand;
    public TableColumn<ItemTM, Integer> clmnUnitPrice;
    public AnchorPane mngItemsAnchorPane;

    public void initialize() {

        txtFieldItemDescription.setDisable(true);
        txtFieldQtyonHand.setDisable(true);
        txtFieldUnitPrice.setDisable(true);
        txtFieldItemCode.setDisable(true);
        btnsave.setDisable(true);
        btnDelete.setDisable(true);
        ObservableList<ItemTM> items = FXCollections.observableList(DB.itemlist);
        tblViewItems.setItems(items);
        tblViewItems.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("itemcode"));
        tblViewItems.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("description"));
        tblViewItems.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("qtyonhand"));
        tblViewItems.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("unitprice"));

        tblViewItems.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ItemTM>() {
            @Override
            public void changed(ObservableValue<? extends ItemTM> observable, ItemTM oldValue, ItemTM newValue) {
                ItemTM selectedItem = tblViewItems.getSelectionModel().getSelectedItem();

                if (selectedItem == null) {
                    btnsave.setText("Save");
                    btnDelete.setDisable(true);
                    return;
                } else {
                    btnsave.setText("Update");
                    btnsave.setDisable(false);
                    btnDelete.setDisable(false);
                    txtFieldItemDescription.setDisable(false);
                    txtFieldUnitPrice.setDisable(false);
                    txtFieldQtyonHand.setDisable(false);
                    txtFieldItemCode.setText(selectedItem.getItemcode());
                    txtFieldItemDescription.setText(selectedItem.getDescription());
                    txtFieldQtyonHand.setText(String.valueOf(selectedItem.getQtyonhand()));
                    txtFieldUnitPrice.setText(String.valueOf(selectedItem.getUnitprice()));
                }

            }
        });

    }

    public void btnsave_OnAction(ActionEvent actionEvent) {

        String desc = txtFieldItemDescription.getText();
        String qtyonHand = txtFieldQtyonHand.getText();
        String unitprice = txtFieldUnitPrice.getText();
        if (desc.matches("^[A-Za-z\\s]+$")) {
            if (qtyonHand.matches("\\b[0-9]+\\b")) {

                if (unitprice.matches("\\b[0-9]+\\b")) {

                    if (btnsave.getText().equals("Save")) {
                        ObservableList<ItemTM> items = tblViewItems.getItems();
                        items.add(new ItemTM(txtFieldItemCode.getText(), txtFieldItemDescription.getText(),
                                Integer.parseInt(txtFieldQtyonHand.getText()), Integer.parseInt(txtFieldUnitPrice.getText())));
                        btnNewItem_OnAction(actionEvent);
                    } else {
                        ItemTM selectedItem = tblViewItems.getSelectionModel().getSelectedItem();
                        selectedItem.setDescription(txtFieldItemDescription.getText());
                        selectedItem.setQtyonhand(Integer.parseInt(txtFieldQtyonHand.getText()));
                        selectedItem.setUnitprice(Integer.parseInt(txtFieldUnitPrice.getText()));
                        tblViewItems.refresh();
                        btnNewItem_OnAction(actionEvent);
                    }


                } else {

                    txtFieldUnitPrice.requestFocus();
                    System.out.println("Please enter a valid unit price.");


                }

            } else {

                txtFieldQtyonHand.requestFocus();
                System.out.println("Please enter a valid quantity on hand.");


            }


        } else {

            txtFieldItemDescription.requestFocus();
            System.out.println("Please enter a valid description.");


        }


    }

    public void btnDelete_OnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure whether you want to delete this item?",
                ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {
            ItemTM selectedItem = tblViewItems.getSelectionModel().getSelectedItem();
            tblViewItems.getItems().remove(selectedItem);
        }
    }

    public void btnNewItem_OnAction(ActionEvent actionEvent) {
        txtFieldItemCode.clear();
        txtFieldItemDescription.clear();
        txtFieldUnitPrice.clear();
        txtFieldQtyonHand.clear();
        tblViewItems.getSelectionModel().clearSelection();
        txtFieldItemDescription.setDisable(false);
        txtFieldQtyonHand.setDisable(false);
        txtFieldUnitPrice.setDisable(false);
        txtFieldItemDescription.requestFocus();
        btnsave.setDisable(false);

        int maxId = 0;
        for (ItemTM items : DB.itemlist) {
            int id = Integer.parseInt(items.getItemcode().replace("I", ""));
            if (id > maxId) {
                maxId = id;
            }
        }

        maxId = maxId + 1;
        String id = "";
        if (maxId < 10) {
            id = "I00" + maxId;
        } else if (maxId < 100) {
            id = "I0" + maxId;
        } else {
            id = "I" + maxId;
        }
        txtFieldItemCode.setText(id);


    }

    public void btnBacktoHome_OnClicked(MouseEvent mouseEvent) throws IOException {
        URL resource = this.getClass().getResource("/view/DashBoardform.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) (this.mngItemsAnchorPane.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();

    }
}
