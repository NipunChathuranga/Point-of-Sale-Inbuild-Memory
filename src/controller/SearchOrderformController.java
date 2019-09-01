package controller;

import com.jfoenix.controls.JFXTextField;
import com.sun.org.apache.xpath.internal.operations.Or;
import db.DB;
import db.Order;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class SearchOrderformController {

    public AnchorPane anchorPaneSrchOrd;
    public JFXTextField txtSeacrchItems;
    public TableView<Order> tblViewSearch;
    public TableColumn clmnID;
    public TableColumn clmnDate;
    public TableColumn clmnTotal;
    public TableColumn clmnCustID;
    public TableColumn clmnName;


    public void initialize() {


        tblViewSearch.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("orderId"));
        tblViewSearch.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        tblViewSearch.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("total"));
        tblViewSearch.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("customerId"));
        tblViewSearch.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("custname"));

        ObservableList<Order> orders = FXCollections.observableList(DB.plcOrderList);
        tblViewSearch.setItems(orders);

        ObservableList<Order> orderObservableList = FXCollections.observableArrayList(orders);
        txtSeacrchItems.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String searchText = txtSeacrchItems.getText().toLowerCase();
                ObservableList<Order> temporders = FXCollections.observableArrayList();
                for (Order order : orderObservableList) {
                    if (order.getOrderId().toLowerCase().contains(searchText) || order.getOrderDate().toString().
                            contains(searchText) ||
                            order.getCustomerId().toLowerCase().contains(searchText) || order.getCustname().toLowerCase().contains(searchText)) {

                        temporders.add(order);

                    }

                    tblViewSearch.setItems(temporders);
                }


            }
        });


//        ObservableList<Order> orders = FXCollections.observableList(DB.plcOrderList);
//        tblViewSearch.setItems(orders);
//
//        FilteredList<Order> filteredList = new FilteredList<>(orders, b -> true);
//
//
//        txtSeacrchItems.textProperty().addListener((observable, oldValue, newValue) -> {
//            filteredList.setPredicate(order -> {
//                if (newValue == null || newValue.isEmpty()) {
//                    return true;
//                }
//
//                String lowerCaseFilter = newValue.toLowerCase();
//
//                if (order.getOrderId().toLowerCase().indexOf(lowerCaseFilter) != -1) {
//                    return true;
//                } else if (String.valueOf(order.getOrderDate()).indexOf(lowerCaseFilter) != -1) {
//                    return true;
//                } else if (String.valueOf(order.getTotal()).indexOf(lowerCaseFilter) != -1) {
//                    return true;
//                } else if (order.getCustomerId().toLowerCase().indexOf(lowerCaseFilter) != -1) {
//                    return true;
//                } else if (order.getCustname().toLowerCase().indexOf(lowerCaseFilter) != -1) {
//                    return true;
//                } else
//                    return false;
//            });
//        });
//
//        SortedList<Order> sortedList = new SortedList<>(filteredList);
//        sortedList.comparatorProperty().bind(tblViewSearch.comparatorProperty());
//        tblViewSearch.setItems(sortedList);

    }


    public void btnBacktoHome_OnClicked(MouseEvent mouseEvent) throws IOException {
        URL resource = this.getClass().getResource("/view/DashBoardform.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) (this.anchorPaneSrchOrd.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();

    }

    public void tblSearchOnMouseClicked(MouseEvent mouseEvent) throws IOException {


        if(mouseEvent.getClickCount()==2) {
            URL resource = this.getClass().getResource("/view/PlaceOrderform.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(resource);
            Parent root = fxmlLoader.load();
            Scene orderFormScene = new Scene(root);
            Stage secondaryStage = new Stage();
            secondaryStage.setScene(orderFormScene);
            secondaryStage.centerOnScreen();
            secondaryStage.setTitle("View Orders");
            secondaryStage.setResizable(false);

            PlaceOrderformController ofctrl = fxmlLoader.getController();
            Order selectedOrder = tblViewSearch.getSelectionModel().getSelectedItem();
            ofctrl.initializeForSearchOrderForm(selectedOrder.getOrderId());
            secondaryStage.show();


        }



    }


}

