// Weapons treat the shared Item value field as an attack bonus.
//Gethsemane
public class Weapon extends Item {

    public Weapon(String itemId, String itemName, String category,
                  String description, int value, String roomLocation,String location,int price) {
        super(itemId, itemName, category, description,value, roomLocation, location, price);
    }

    // Weapon value maps directly to the attack increase granted when equipped.
    public int getAttackBonus() {
        return this.getValue();
    }
    public String toString(){
        return this.getItemName() + "| " + this.getDescription() + ". Gives you +" + this.getAttackBonus() + " boost to your attack";
    }

}
