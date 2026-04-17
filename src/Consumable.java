public class Consumable extends Item {
    private int hpRestore;

    public Consumable(String item_Id, String item_Name, String item_Description, String item_type, int value) {
        super(item_Id, item_Name, item_Description, item_type, value);
        this.hpRestore = value;
    }

    public int getHpRestore() {
        return hpRestore;
    }

    public void use() {
        // Logic to consume the item and restore HP would go here
        // For now, just a placeholder
    }
}
