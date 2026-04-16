import java.util.HashMap;
import java.util.Map;

public class VendingMachine extends Item {
    private Map<String, Consumable> availableItems = new HashMap<>();
    private int cost = 4; // cost for items, e.g., food

    public VendingMachine(String item_Id, String item_Name, String item_Description, String item_type, int value) {
        super(item_Id, item_Name, item_Description, item_type, value);
        // Initialize with some items, e.g., food
        availableItems.put("food", new Consumable("food_id", "Food", "Edible food that restores HP", "consumable", 10));
    }

    public int getCost() {
        return cost;
    }

    public boolean buyItem(String itemName) {
        return availableItems.containsKey(itemName);
    }

    public Consumable dispenseItem(String itemName) {
        return availableItems.get(itemName);
    }
}
