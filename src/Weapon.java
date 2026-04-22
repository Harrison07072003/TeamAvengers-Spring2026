public class Weapon extends Item {

    public Weapon(String item_Id, String item_Name, String item_type, String item_Description, String location,String roomId, int value) {
        super(item_Id, item_Name,"Weapon", item_Description, location, roomId, value);

    }
    public int getAtkIncrease() {
        return getValue();
    }

}
