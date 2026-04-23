public class Consumable extends Item {

    public Consumable(String itemId, String itemName, String description,
                      String location, String roomLocation, int value, int price) {
        super(itemId, itemName, "Consumable", description, location, roomLocation, value, price);
    }

    public int getHpRestore() {
        return getValue();
    }
}