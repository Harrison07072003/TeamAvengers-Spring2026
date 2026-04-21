public class Weapon extends Item {
    private int atkIncrease;

    public Weapon(String item_Id, String item_Name, String item_type, String item_Description, String item_Location, int value) {
        super(item_Id, item_Name, item_Description, item_type, item_Location, value);
        this.atkIncrease = value;
    }

    public int getAtkIncrease() {
        return atkIncrease;
    }

}
