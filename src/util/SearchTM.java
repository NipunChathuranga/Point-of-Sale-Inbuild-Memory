package util;

import java.text.DateFormat;
import java.time.LocalDate;

public class SearchTM {
    private String orderid;
    private LocalDate date;
    private double total;
    private String customID;
    private String name;

    public SearchTM() {
    }

    public SearchTM(String orderid, LocalDate date, double total, String customID, String name) {
        this.orderid = orderid;
        this.date = date;
        this.total = total;
        this.customID = customID;
        this.name = name;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getCustomID() {
        return customID;
    }

    public void setCustomID(String customID) {
        this.customID = customID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "SearchTM{" +
                "orderid='" + orderid + '\'' +
                ", date=" + date +
                ", total=" + total +
                ", customID='" + customID + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
