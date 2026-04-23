public class Weapon extends Item {

    public Weapon(String itemId, String itemName, String description,
                  String location, String roomLocation, int value, int price) {
        super(itemId, itemName, "Weapon", description, location, roomLocation, value, price);
    }

    public int getAtkIncrease() {
        return getValue();
    }
}