public class Weapon extends Item {

    public Weapon(String item_Id, String item_Name, String item_type, String item_Description, String item_Location, int value) {
        super(item_Id, item_Name,"Weapon", item_Description, item_Location, value);

    }
    public int getAtkIncrease() {
        return getValue();
    }

}
