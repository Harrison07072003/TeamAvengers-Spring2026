public class Weapon extends Item {
    //fields
    //constructor
    public Weapon(String itemId, String itemName, String category, String description, int value,String roomLocation, String location,int price) {
        super(itemId, itemName, category, description, value,roomLocation, location,price);
    }
    //methods
    public int getAttackBonus(){
        return this.getValue();
    }
}