//Gethsemane
// Consumables are inventory items whose numeric value is treated as healing.
public class Consumable extends Item {

    public Consumable(String itemId, String itemName, String category,String description,
                      int value, String roomLocation,String location,int price) {
        super(itemId, itemName, category, description,value, roomLocation, location, price);
    }

    // The shared Item value field is interpreted as HP restored for food/medicine.
    public int getHpRestore() {
        return this.getValue();
    }
    public String toString(){
        return this.getItemName() + "| " + this.getDescription() + ". Gives you +" + this.getValue() + " healing";
    }
}
