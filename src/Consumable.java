public class Consumable extends Item {
    private int hpRestore;

    public Consumable(String item_Id, String item_Name,String item_type,String item_Description,String item_location,int value) {
        super(item_Id, item_Name, item_Description, item_type,item_location,value);
        this.hpRestore = value;
    }

    public int getHpRestore() {
        return hpRestore;
    }
}