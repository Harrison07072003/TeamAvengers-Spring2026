public abstract class Item {
    //fields
    private String itemId;
    private String itemName;
    private String category;
    private String description;
    private int value;
    private String roomLocation;
    private String location;
    private int price;

    //constructor
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

    //getters
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
    public void setLocation(String location) {
        this.location = location;
    }
    public void setRoomLocation(String roomLocation) {
        this.roomLocation = roomLocation;
    }


    //toString and fileString
    public String toFileString() {
        return this.itemId + "," + this.itemName + "," + this.category + "," + this.description + "," + this.value + "," + this.roomLocation + "," + this.location + "," + this.price;
    }

    public String toString() {
        return this.itemName + "| " +this.description;
    }
  //public String toString(){
   //     return this.name + ": Raises damage by " + this.getAttackBonus();
    //}
}
