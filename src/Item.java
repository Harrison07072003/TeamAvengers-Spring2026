public abstract class Item {
    private String itemId;
    private String itemName;
    private String category;
    private String description;
    private int value;
    private String roomLocation;
    private String location;
    private int price;

    public Item(String itemId, String itemName, String category,
                String description, int value, String roomLocation,String location,int price) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.category = category;
        this.description = description;
        this.value = value;
        this.roomLocation = roomLocation;
        this.location = location;
        this.price = price;
    }

    public String getId() {
        return itemId;
    }

    public String getItemId() {
        return itemId;
    }

    public String getItemName() {
        return this.itemName;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public int getValue(){
        return this.value;
    }
    public String getRoomLocation(){
        return this.roomLocation;
    }
    public String getLocation() {
        return location;
    }
    public int getPrice() {
        return price;
    }
    public String toFileString() {
        return this.itemId + "," + this.itemName + "," + this.category + "," + this.description + "," + this.value + "," + this.roomLocation + "," + this.location + "," + this.price;
    }



    public String toString() {
        return itemId + " - " + itemName + " (" + category + ")";
    }
}