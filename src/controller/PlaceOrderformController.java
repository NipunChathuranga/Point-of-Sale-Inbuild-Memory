package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import db.DB;
import db.Order;
import db.OrderDetail;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.CartTM;
import util.CustomerTable;
import util.ItemTM;

import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;

public class PlaceOrderformController {
    public AnchorPane PlcOrderAnchorPane;
    public TextField txtFieldOrderID;
    public TextField txtFieldDate;
    public TextField txtFieldName;
    public JFXComboBox<String> cmbCustID;
    public JFXComboBox<String> cmbItemCode;
    public Button btnPlaceOrder;
    public TextField txtFieldTotal;
    public TextField txtFieldDescription;
    public TextField txtFieldQtyonHand;
    public TextField txtFieldUnitPrice;
    public TextField txtFieldQty;
    public Button btnSave;
    public TableView<CartTM> tblPlaceOrderView;
    public TableColumn<CartTM, String> clmnCode;
    public TableColumn<CartTM, String> clmnDescription;
    public TableColumn<CartTM, Integer> clmnQty;
    public TableColumn<CartTM, Integer> clmnUnitPrice;
    public TableColumn<CartTM, Double> clmnTotal;
    public TableColumn clmnDelete;
    public JFXButton btnNewOrder;

    private ArrayList<ItemTM> tempItems = new ArrayList<>();


    public void initialize() {

        // Let's map columns with table model
        tblPlaceOrderView.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("itemcode"));
        tblPlaceOrderView.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("desc"));
        tblPlaceOrderView.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("qty"));
        tblPlaceOrderView.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("unitprice"));
        tblPlaceOrderView.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("tot"));
        tblPlaceOrderView.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("delete"));

        // Load all customer ids
        ObservableList<String> customers = cmbCustID.getItems();
        for (CustomerTable customer : DB.customerlist) {
            customers.add(customer.getId());
        }

        // Load all item codes
        ObservableList<String> items = cmbItemCode.getItems();
        for (ItemTM item : DB.itemlist) {
            items.add(item.getItemcode());
        }

        // Create temporary item list
        tempItems = new ArrayList<>();
        for (ItemTM item : DB.itemlist) {
            tempItems.add(item.clone());
        }

        // When customer id is selected
        cmbCustID.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String selectedCustomerID = cmbCustID.getSelectionModel().getSelectedItem();
                enablePlaceOrderButton();

                for (CustomerTable customer : DB.customerlist) {
                    if (customer.getId().equals(selectedCustomerID)) {
                        txtFieldName.setText(customer.getName());
                        break;
                    }
                }
            }
        });

        cmbItemCode.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String selectedItemCode = cmbItemCode.getSelectionModel().getSelectedItem();

                if (selectedItemCode == null) {
                    txtFieldQtyonHand.clear();
                    txtFieldDescription.clear();
                    txtFieldUnitPrice.clear();
                    txtFieldQty.clear();
                    txtFieldQty.setEditable(false);
                    btnSave.setDisable(true);
                    return;
                }

                txtFieldQty.setEditable(true);
                btnSave.setDisable(false);
                for (ItemTM item : tempItems) {
                    if (item.getItemcode().equals(selectedItemCode)) {
                        txtFieldDescription.setText(item.getDescription());
                        txtFieldUnitPrice.setText(item.getUnitprice() + "");
                        txtFieldQtyonHand.setText(item.getQtyonhand() + "");
                    }
                }
            }
        });

        // When a table row is selected
        tblPlaceOrderView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CartTM>() {
            @Override
            public void changed(ObservableValue<? extends CartTM> observable, CartTM oldValue, CartTM newValue) {

                CartTM selectedOrderDetail = tblPlaceOrderView.getSelectionModel().getSelectedItem();
                if (selectedOrderDetail == null) {
                    btnSave.setText("Add");
                    return;
                }
                for (ItemTM tempItem : tempItems) {
                    if (tempItem.getItemcode().equals(selectedOrderDetail.getItemcode())) {
                        for (ItemTM item : DB.itemlist) {
                            if (item.getItemcode().equals(selectedOrderDetail.getItemcode())) {
                                tempItem.setQtyonhand(item.getQtyonhand());
                                break;
                            }
                        }
                    }
                }
                cmbItemCode.getSelectionModel().select(selectedOrderDetail.getItemcode());
                txtFieldQty.setText(selectedOrderDetail.getQty() + "");
                // Don't think about this now...!
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        txtFieldQty.requestFocus();
                        txtFieldQty.selectAll();
                    }
                });
                btnSave.setText("Update");
            }
        });

        reset();


    }

    public void btnSave_OnAction(ActionEvent actionEvent) {
        try {

            int qty = Integer.parseInt(txtFieldQty.getText());
            int qtyonhand = Integer.parseInt(txtFieldQtyonHand.getText());
            int price = Integer.parseInt(txtFieldUnitPrice.getText());


            if (qty <= 0 || qty > qtyonhand) {
                new Alert(Alert.AlertType.ERROR, "Invalid Qty", ButtonType.OK).show();
                txtFieldQty.requestFocus();
                txtFieldQty.selectAll();
                return;
            }

            String selectedItemCode = cmbItemCode.getSelectionModel().getSelectedItem();
            ObservableList<CartTM> details = tblPlaceOrderView.getItems();


            boolean isExists = false;
            for (CartTM detail : tblPlaceOrderView.getItems()) {
                if (detail.getItemcode().equals(selectedItemCode)) {
                    isExists = true;

                    if (btnSave.getText().equals("Update")) {
                        detail.setQty(qty);
                    } else {
                        detail.setQty(detail.getQty() + qty);
                    }
                    detail.setTot(detail.getQty() * detail.getUnitprice());
                    tblPlaceOrderView.refresh();
                    break;
                }
            }

            if (!isExists) {
                Button btnDelete = new Button("Delete");
                CartTM detailTM = new CartTM(cmbItemCode.getSelectionModel().getSelectedItem(),
                        txtFieldDescription.getText(),
                        qty,
                        price,
                        qty * price,
                        btnDelete
                );
                btnDelete.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        for (ItemTM tempItem : tempItems) {
                            if (tempItem.getItemcode().equals(detailTM.getItemcode())) {
                                // Let's restore the qty
                                int qtyOnHand = tempItem.getQtyonhand() + detailTM.getQty();
                                tempItem.setQtyonhand(qtyOnHand);
                                break;
                            }
                        }
                        tblPlaceOrderView.getItems().remove(detailTM);
                        calculateTotal();
                        enablePlaceOrderButton();
                        cmbItemCode.requestFocus();
                        cmbItemCode.getSelectionModel().clearSelection();
                        tblPlaceOrderView.getSelectionModel().clearSelection();
                    }
                });
                details.add(detailTM);
            }

            updateQty(selectedItemCode, qty);
            // Calculate the grand total
            calculateTotal();
            enablePlaceOrderButton();
            cmbItemCode.requestFocus();
            cmbItemCode.getSelectionModel().clearSelection();
            tblPlaceOrderView.getSelectionModel().clearSelection();
        } catch (NumberFormatException e) {
            System.out.println("Enter a valid Quantity.");

        }


    }

    private void enablePlaceOrderButton() {
        String selectedCustomer = cmbCustID.getSelectionModel().getSelectedItem();
        int size = tblPlaceOrderView.getItems().size();
        if (selectedCustomer == null || size == 0) {
            btnPlaceOrder.setDisable(true);
        } else {
            btnPlaceOrder.setDisable(false);
        }
    }

    public void txtQty_OnAction(ActionEvent actionEvent) {
        if (!btnSave.isDisable()) {
            // btnNewOrder_OnAction(actionEvent);
            btnSave_OnAction(actionEvent);
        }
    }


    public void btnPlaceOrder_OnAction(ActionEvent actionEvent) {


        ArrayList<OrderDetail> details = new ArrayList<>();

        for (CartTM item : tblPlaceOrderView.getItems()) {
            details.add(new OrderDetail(item.getItemcode(), item.getQty(), item.getUnitprice()));
        }

        Order newOrder = new Order(txtFieldOrderID.getText(), LocalDate.now(),
                cmbCustID.getSelectionModel().getSelectedItem(),
                Double.parseDouble(txtFieldTotal.getText()), txtFieldName.getText(),
                details);


        DB.plcOrderList.add(newOrder);
        DB.itemlist = tempItems;
        System.out.println(newOrder);
        reset();


    }


    public void btnNewOrder_OnAction(ActionEvent actionEvent) {

        reset();
        tempItems = new ArrayList<>();
        for (ItemTM item : DB.itemlist) {
            tempItems.add(item.clone());
        }

    }

    private void reset() {
        // Initialize controls to their default states
        txtFieldDate.setText(LocalDate.now() + "");

        btnPlaceOrder.setDisable(true);
        btnSave.setDisable(true);
        txtFieldName.setEditable(false);
        txtFieldName.clear();
        txtFieldDescription.setEditable(false);
        txtFieldUnitPrice.setEditable(false);
        txtFieldQtyonHand.setEditable(false);
        txtFieldQty.setEditable(false);
        cmbCustID.getSelectionModel().clearSelection();
        cmbItemCode.getSelectionModel().clearSelection();
        tblPlaceOrderView.getItems().clear();
        txtFieldTotal.setText("Total : 0.00");

        // Generate the new order id
        int maxOrderId = 0;
        for (Order order : DB.plcOrderList) {
            int orderId = Integer.parseInt(order.getOrderId().replace("OD", ""));
            if (orderId > maxOrderId) {
                maxOrderId = orderId;
            }
        }
        maxOrderId++;
        if (maxOrderId < 10) {
            txtFieldOrderID.setText("OD00" + maxOrderId);
        } else if (maxOrderId < 100) {
            txtFieldOrderID.setText("OD0" + maxOrderId);
        } else {
            txtFieldOrderID.setText("OD" + maxOrderId);
        }
    }


    private void updateQty(String selectedItemCode, int qty) {
        for (ItemTM item : tempItems) {
            if (item.getItemcode().equals(selectedItemCode)) {
                item.setQtyonhand(item.getQtyonhand() - qty);
                break;
            }
        }
    }

    private void calculateTotal() {
        ObservableList<CartTM> details = tblPlaceOrderView.getItems();

        double total = 0;
        for (CartTM detail : details) {
            total += detail.getTot();
        }

        // Let's format the total
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        nf.setMinimumFractionDigits(2);
        nf.setGroupingUsed(false);

        txtFieldTotal.setText(String.valueOf(total));
    }


    public void btnBacktoHome_OnClicked(MouseEvent mouseEvent) throws IOException {
        URL resource = this.getClass().getResource("/view/DashBoardform.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) (this.PlcOrderAnchorPane.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
    }

    public void initializeForSearchOrderForm(String orderId) {
        txtFieldOrderID.setText(orderId);
        btnSave.setDisable(true);
        btnPlaceOrder.setDisable(true);
        btnNewOrder.setDisable(true);

        for (Order order : DB.plcOrderList) {
            if (order.getOrderId().equals(orderId)) {
                txtFieldDate.setText(order.getOrderDate() + "");
                cmbCustID.getSelectionModel().select(order.getCustomerId());
                txtFieldTotal.setText(order.getTotal() + "");


                ObservableList<CartTM> orderDetails = tblPlaceOrderView.getItems();
                for (OrderDetail od : order.getOrderItems()) {
                    String itemDescription = null;
                    for (ItemTM item : DB.itemlist) {
                        if (item.getItemcode().equals(od.getItemCode())) {

                            itemDescription = item.getDescription();
                            break;

                        }


                    }
                    orderDetails.add(new CartTM(od.getItemCode(), itemDescription, od.getQty(), od.getUnitPrice(), od.getQty() * od.getUnitPrice()));

                }


                break;
            }


        }


    }
}
