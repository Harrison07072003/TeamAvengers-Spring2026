public class Weapon extends Item {

    public Weapon(String Id, String Name, String type, String Description, String location,String roomLocation, int value, int price) {
        super(Id, Name,"Weapon", Description, location, roomLocation,value,price);

    }
    public int getAtkIncrease() {
        return getValue();
    }

}
