package util;

public class ItemTM implements Cloneable {

    String itemcode;
    String description;
    int qtyonhand;
    int unitprice;

    public ItemTM() {


    }


    public ItemTM(String itemcode, String description, int qtyonhand, int unitprice) {
        this.itemcode = itemcode;
        this.description = description;
        this.qtyonhand = qtyonhand;
        this.unitprice = unitprice;
    }
    public ItemTM clone(){

        return new ItemTM(this.itemcode, this.description, this.qtyonhand, this.unitprice);
    }

    public String getItemcode() {
        return itemcode;
    }

    public void setItemcode(String itemcode) {
        this.itemcode = itemcode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQtyonhand() {
        return qtyonhand;
    }

    public void setQtyonhand(int qtyonhand) {
        this.qtyonhand = qtyonhand;
    }

    public int getUnitprice() {
        return unitprice;
    }

    public void setUnitprice(int unitprice) {
        this.unitprice = unitprice;
    }

    @Override
    public String toString() {
        return "ItemTM{" +
                "itemcode='" + itemcode + '\'' +
                ", description='" + description + '\'' +
                ", qtyonhand=" + qtyonhand +
                ", unitprice=" + unitprice +
                '}';
    }
}
