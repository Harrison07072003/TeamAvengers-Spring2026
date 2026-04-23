// Weapons treat the shared Item value field as an attack bonus.
public class Weapon extends Item {

    public Weapon(String itemId, String itemName, String description,
                  String location, String roomLocation, int value, int price) {
        super(itemId, itemName, "Weapon", description, location, roomLocation, value, price);
    }

    // Weapon value maps directly to the attack increase granted when equipped.
    public int getAtkIncrease() {
        return getValue();
    }
}
