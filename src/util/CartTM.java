package util;

import javafx.scene.control.Button;

public class CartTM {
    private String itemcode;
    private String desc;
    private int qty;
//    private int qtyonhand;
    private double unitprice;
    private double tot;
    private Button delete;

    public CartTM() {

    }



    public CartTM(String itemcode, String desc, int qty, double unitprice, double tot, Button delete) {
        this.itemcode = itemcode;
        this.desc = desc;
        this.qty = qty;

        this.unitprice = unitprice;
        this.tot = tot;
        this.delete = delete;
    }

    public CartTM(String itemCode, String itemDescription, int qty, double unitPrice) {
        this.itemcode=itemCode;
        this.desc=itemDescription;
        this.qty=qty;
        this.unitprice = unitPrice;
    }

    public CartTM(String itemcode, String desc, int qty, double unitprice, double tot) {
        this.itemcode = itemcode;
        this.desc = desc;
        this.qty = qty;
        this.unitprice = unitprice;
        this.tot = tot;
    }

    public Button getDelete() {
        return delete;
    }

    public void setDelete(Button delete) {
        this.delete = delete;
    }

    public double getUnitprice() {
        return unitprice;
    }

    public void setUnitprice(double unitprice) {
        this.unitprice = unitprice;
    }

    public String getItemcode() {
        return itemcode;
    }

    public void setItemcode(String itemcode) {
        this.itemcode = itemcode;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getTot() {
        return tot;
    }

    public void setTot(double tot) {
        this.tot = tot;
    }

    @Override
    public String toString() {
        return "CartTM{" +
                "itemcode='" + itemcode + '\'' +
                ", desc='" + desc + '\'' +
                ", qty=" + qty +
                ", unitprice=" + unitprice +
                ", tot=" + tot +
                ", delete=" + delete +
                '}';
    }
}
