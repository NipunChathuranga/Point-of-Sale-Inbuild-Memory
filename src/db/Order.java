package db;

import java.time.LocalDate;
import java.util.ArrayList;

public class Order {
    private String orderId;
    private LocalDate orderDate;
    private String customerId;
    private double total;
    private String custname;
    private ArrayList<OrderDetail> orderItems;

    public Order(String orderId, LocalDate orderDate, String customerId, double total, String custname, ArrayList<OrderDetail> orderItems) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.customerId = customerId;
        this.total = total;
        this.custname = custname;
        this.orderItems = orderItems;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public ArrayList<OrderDetail> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(ArrayList<OrderDetail> orderItems) {
        this.orderItems = orderItems;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getCustname() {
        return custname;
    }

    public void setCustname(String custname) {
        this.custname = custname;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", orderDate=" + orderDate +
                ", customerId='" + customerId + '\'' +
                ", total=" + total +
                ", custname='" + custname + '\'' +
                ", orderItems=" + orderItems +
                '}';
    }
}
