import java.util.HashMap;

public class VendingMachine {
    //fields
    String vendingID;
    String location;
    HashMap<Item,Integer> itemsForSale;
    public VendingMachine(String id,String location) {
        this.vendingID = id;
        this.location = location;
        this.itemsForSale = new HashMap<>();
    }
    public void addItemForSale(Item item, int price) {
        itemsForSale.put(item, price);
    }
    public String getLocation() {
        return location;
    }
    public void addItem(Item item,int price) {
        itemsForSale.put(item,price);
    }
    public String getItemList() {
        StringBuilder sb = new StringBuilder();
        for (Item item : itemsForSale.keySet()) {
            sb.append(item.getItemName()).append(" - ").append(itemsForSale.get(item)).append(" coins\n");
        }
        return sb.toString();
    }
}
