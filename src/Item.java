public class Item {
    private final String item_Id;
    private final String item_Name;

    public Item(String item_Id, String item_Name) {
        this.item_Id = item_Id;
        this.item_Name = item_Name;
    }

    public String getItem_Id() {
        return item_Id;
    }

    public String getItem_Name() {
        return item_Name;
    }

    @Override
    public String toString() {
        return item_Name;
    }
}
