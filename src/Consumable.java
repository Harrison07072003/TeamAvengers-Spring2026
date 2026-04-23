// Consumables are inventory items whose numeric value is treated as healing.
public class Consumable extends Item {

    public Consumable(String itemId, String itemName, String description,
                      String location, String roomLocation, int value, int price) {
        super(itemId, itemName, "Consumable", description, location, roomLocation, value, price);
    }

    // The shared Item value field is interpreted as HP restored for food/medicine.
    public int getHpRestore() {
        return getValue();
    }
}
