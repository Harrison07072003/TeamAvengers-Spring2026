public class Consumable extends Item {

    public Consumable(String Id, String Name, String type, String Description, String location, String roomLocation,int value) {
        super(Id, Name, "Consumable", Description, location, roomLocation, value);;
    }

    public int getHpRestore() {
        return getValue();
    }
}