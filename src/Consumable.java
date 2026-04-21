public class Consumable extends Item {

    public Consumable(String item_Id, String item_Name, String item_type, String item_Description, String item_location,int value) {
        super(item_Id, item_Name, "Consumable", item_Description, item_location, value);;
    }

    public int getHpRestore() {
        return getValue();
    }
}