public class Consumable extends Item {
    //fields
    //constructor
    public Consumable(String itemId, String itemName, String category, String description, int value, String roomLocation, String location,int price) {
        super(itemId, itemName, category, description, value,roomLocation, location,price);
    }
    //methods
     public String toFileString() {
        return getItemId() + "|" + getItemName() + "|" + getCategory() + "|" + getDescription() + "|" + getValue();
    }
}
