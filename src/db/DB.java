package db;

import util.CartTM;
import util.CustomerTable;
import util.ItemTM;

import java.util.ArrayList;

public class DB {

    public static ArrayList<CustomerTable> customerlist = new ArrayList<>();
    public static ArrayList<ItemTM> itemlist = new ArrayList<>();
    public static ArrayList<CartTM> tblCart = new ArrayList<>();
    public static ArrayList<Order> plcOrderList= new ArrayList<>();

    //static ObservableList<CustomerTable> customerlist = FXCollections.observableArrayList();
    static {
        customerlist.add(new CustomerTable("C001","Nipun","Kaduwela"));
        customerlist.add(new CustomerTable("C002","Heshan","Kalutara"));
        customerlist.add(new CustomerTable("C003","Chathur","Galle"));

        itemlist.add(new ItemTM("I001","Milk",800,200));
        itemlist.add(new ItemTM("I002","Cheese",200,450));
        itemlist.add(new ItemTM("I003","Biscuits",100,15));

    }




}
