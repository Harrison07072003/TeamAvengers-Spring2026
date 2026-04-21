import java.util.ArrayList;

public class VendingMachine {
    private final ArrayList<Consumable> availableItems = new ArrayList<>();
    private final int cost = 4;

    public VendingMachine(String item_Id, String item_Name, String item_type, String item_Description, String item_Location , int value) {
        availableItems.add(new Consumable("food_id", "Food", "consumable","Edible food that restores HP" , "R5, R11, R19",10));
    }

    public int getCost() {
        return cost;
    }

    public boolean buyItem(String itemName) {
        if (itemName == null) {
            return false;
        }

        for (Consumable item : availableItems) {
            if (item.getItem_Name().equalsIgnoreCase(itemName)) {
                return true;
            }
        }

        return false;
    }

    public Consumable dispenseItem(String itemName) {
        if (itemName == null) {
            return null;
        }

        for (Consumable template : availableItems) {
            if (template.getItem_Name().equalsIgnoreCase(itemName)) {
                return new Consumable(
                        template.getItem_Id() + "_" + System.nanoTime(),
                        template.getItem_Name(),
                        template.getItem_type(),
                        template.getItem_Description(),
                        template.getItem_Location(),
                        template.getHpRestore()
                );
            }
        }

        return null;
    }
}

