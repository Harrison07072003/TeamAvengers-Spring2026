import java.util.ArrayList;

public class VendingMachine extends Item {
    private final ArrayList<Consumable> availableItems = new ArrayList<>();
    private final int cost = 4;
    private String vendingMachineLocation;
    private static final String DEFAULT_VENDING_MACHINE_LOCATION = "R5, R11, R19";

    public VendingMachine(String item_Id, String item_Name, String item_Description, String item_type, int value) {
        this(item_Id, item_Name, item_Description, item_type, DEFAULT_VENDING_MACHINE_LOCATION, value);
    }

    public VendingMachine(String item_Id, String item_Name, String item_Description, String item_type, String item_Location, int value) {
        super(item_Id, item_Name, item_Description, item_type, item_Location, value);
        this.vendingMachineLocation = item_Location;
        availableItems.add(new Consumable("food_id", "Food", "Edible food that restores HP", "consumable", 10));
    }

    public int getCost() {
        return cost;
    }

    public String getVendingMachineLocation() {
        return vendingMachineLocation;
    }

    public void setVendingMachineLocation(String vendingMachineLocation) {
        this.vendingMachineLocation = vendingMachineLocation;
    }

    public void setVendingMachine(Room room) {
        if (room != null) {
            room.attachVendingMachine(this);
        }
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
                        template.getItem_Description(),
                        template.getItem_type(),
                        template.getHpRestore()
                );
            }
        }

        return null;
    }
}
