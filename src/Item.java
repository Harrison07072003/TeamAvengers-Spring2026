public class Item {
    private String item_Id;
    private String item_Name;
    private String item_Description;
    private String item_type;

    public Item(String item_Id, String item_Name, String item_Description, String item_type) {
        this.item_Id = item_Id;
        this.item_Name = item_Name;
        this.item_Description = item_Description;
        this.item_type = item_type;
    }
    public String getItem_Id() {
        return item_Id;
    }
    public void setItem_Id(String item_Id) {
        this.item_Id = item_Id;
    }
    public String getItem_Name() {
        return item_Name;
    }
    public void setItem_Name(String item_Name) {
        this.item_Name = item_Name;
    }
    public String getItem_Description() {
        return item_Description;
    }
    public void setItem_Description(String item_Description) {
        this.item_Description = item_Description;
    }
    public String getItem_type() {
        return item_type;
    }
    public void setItem_type(String item_type) {
        this.item_type = item_type;
    }

}
