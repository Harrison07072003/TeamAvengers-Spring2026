
import java.util.HashMap;
import java.util.Map;

public class VendingMachine {
    private String location;
    private final Map<Item,Integer> price;

    public VendingMachine(String location) {
        this.location = location;
        this.price = new HashMap<>();
        }
    }
    // ADD ITEM
    public void addItem(Item item, Integer price) {
        if (item == null || price <= 0) return;
        price.put(item,price);
    }
    // GETTERS
    public String getlocation() {
        return location;
    }
    public Map<Item,Integer> getprice() {
        return price;
    }
    //DISPENSE ITEM
    public Item dispenseItem(String itemName) {
        if (itemName == null) {
            return null;
        }
        for (Item item : price.keySet()) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;
    }
}

