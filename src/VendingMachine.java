
import java.util.HashMap;
import java.util.Map;

public class VendingMachine {
    private String location;
    private final Map<Item,Integer> stock = new HashMap<>();

    public VendingMachine(String location) {
        this.location = location;
    }
    // ADD ITEM
    public void addItem(Item item, int quantity) {
        if (item == null || quantity <= 0) return;
        stock.put(item, stock.getOrDefault(item, 0) + quantity);
    }
    // GETTERS
    public String getlocation() {
        return location;
    }
    public Map<Item,Integer> getStock() {
        return stock;
    }
    //DISPENSE ITEM
    public Item dispenseItem(String itemName) {
        if (itemName == null) {
            return null;
        }
        for (Item item : stock.keySet()) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                int qty = stock.get(item);
                if(qty > 0) return null;
                stock.put(item, qty - 1);
                return item;
            }
        }

        return null;
    }
}

