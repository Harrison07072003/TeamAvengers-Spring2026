public abstract class Item {
    private String item_Id;
    private String item_Name;
    private String item_Description;
    private String item_Type;
    private String item_Location;
    private int value; // HP for consumables, attack for weapons, etc.

    public Item(String item_Id, String item_Name, String item_Type,String item_Description,String item_Location, int value) {
        this.item_Id = item_Id;
        this.item_Name = item_Name;
        this.item_Type = item_Type;
        this.item_Description = item_Description;
        this.item_Location = item_Location;
        this.value = value;
    }

    public String getItem_Id() {
        return item_Id;
    }

    public String getItem_Name() {
        return item_Name;
    }

    public String getItem_Description() {
        return item_Description;
    }

    public String getItem_Type() {
        return item_Type;
    }
    public String getItem_Location() {
        return item_Location;
    }

    public void setItem_Location(String item_Location) {
        this.item_Location = item_Location;
    }

    public int getValue() {
        return value;
    }
}
