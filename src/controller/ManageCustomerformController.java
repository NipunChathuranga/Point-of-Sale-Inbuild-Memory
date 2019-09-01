package controller;

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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.CustomerTable;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

public class ManageCustomerformController {
    public TextField txtField_ID;
    public TextField txtField_Name;
    public TextField txtField_Address;
    public AnchorPane AnchorPaneCust;
    public TableView<CustomerTable> tblViewCustDetails;
    public TableColumn<CustomerTable, String> tblViewIID;
    public TableColumn<CustomerTable, String> tblViewName;
    public TableColumn<CustomerTable, String> tblViewAddress;
    public Button btnSave;
    public Button btnDelete;


    public void initialize() {

        //tblViewCustDetails.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        //tblViewCustDetails.setItems(customerlist);
        txtField_ID.setDisable(true);
        txtField_Name.setDisable(true);
        txtField_Address.setDisable(true);
        btnDelete.setDisable(true);
        btnSave.setDisable(true);


        ObservableList<CustomerTable> customers = FXCollections.observableList(DB.customerlist);
        tblViewCustDetails.setItems(customers);

        tblViewCustDetails.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CustomerTable>() {
            @Override
            public void changed(ObservableValue<? extends CustomerTable> observable, CustomerTable oldValue, CustomerTable newValue) {
                CustomerTable selectedItem = tblViewCustDetails.getSelectionModel().getSelectedItem();

                if (selectedItem == null) {
                    btnSave.setText("Save");
                    btnDelete.setDisable(true);
                    return;
                }

                btnSave.setText("Update");
                btnSave.setDisable(false);
                btnDelete.setDisable(false);
                txtField_Name.setDisable(false);
                txtField_Address.setDisable(false);
                txtField_ID.setText(selectedItem.getId());
                txtField_Name.setText(selectedItem.getName());
                txtField_Address.setText(selectedItem.getAddress());

            }
        });

        tblViewCustDetails.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblViewCustDetails.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblViewCustDetails.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));


//        tblViewIID.setCellValueFactory(new PropertyValueFactory<>("id"));
//        tblViewName.setCellValueFactory(new PropertyValueFactory<>("name"));
//        tblViewAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
//        txtField_ID.setDisable(true);

        //tblViewCustDetails.getSelectionModel().selectedItemProperty().addListener();


        //ObservableList<Customer> custarr = tblViewCustDetails.getItems();


    }


    public void btnHome_OnAction(ActionEvent actionEvent) throws IOException {
        URL resource = this.getClass().getResource("/view/DashBoardform.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) (this.AnchorPaneCust.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();

    }

    public void btnNewCust_OnAction(ActionEvent actionEvent) {
        txtField_ID.clear();
        txtField_Name.clear();
        txtField_Address.clear();
        tblViewCustDetails.getSelectionModel().clearSelection();
        txtField_Name.setDisable(false);
        txtField_Address.setDisable(false);
        txtField_Name.requestFocus();
        btnSave.setDisable(false);
        int maxId = 0;
        for (CustomerTable customer : DB.customerlist) {
            int id = Integer.parseInt(customer.getId().replace("C", ""));
            if (id > maxId) {
                maxId = id;
            }
        }

        maxId = maxId + 1;
        String id = "";
        if (maxId < 10) {
            id = "C00" + maxId;
        } else if (maxId < 100) {
            id = "C0" + maxId;
        } else {
            id = "C" + maxId;
        }
        txtField_ID.setText(id);


    }

    public void btnSave_OnAction(ActionEvent actionEvent) {
//        CustomerTable lastcust = customerlist.get((customerlist.size()) - 1);
//        String a = lastcust.getId().replace("C", "");
//        int num = Integer.parseInt(a);
//        customerlist.add(new CustomerTable("C00" + (num + 1), txtField_Name.getText(), txtField_Address.getText()));
//        System.out.println(num);

        if (btnSave.getText().equals("Save")) {
            ObservableList<CustomerTable> customers = tblViewCustDetails.getItems();
            customers.add(new CustomerTable(txtField_ID.getText(), txtField_Name.getText(),
                    txtField_Address.getText()));
            btnNewCust_OnAction(actionEvent);
        } else {
            CustomerTable selectedItem = tblViewCustDetails.getSelectionModel().getSelectedItem();
            selectedItem.setName(txtField_Name.getText());
            selectedItem.setAddress(txtField_Address.getText());
            tblViewCustDetails.refresh();
            btnNewCust_OnAction(actionEvent);
        }


    }

    public void btnDelete_OnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure whether you want to delete this customer?",
                ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {
            CustomerTable selectedItem = tblViewCustDetails.getSelectionModel().getSelectedItem();
            tblViewCustDetails.getItems().remove(selectedItem);
        }
    }
}
