public abstract class Item {
    private String itemId;
    private String name;
    private String type;
    private String description;
    private String location;      // container ID: room, monster, puzzle, vending machine, etc.
    private String roomLocation;  // actual room ID
    private int value;
    private int price;

    public Item(String itemId, String name, String type, String description,
                String location, String roomLocation, int value, int price) {
        this.itemId = itemId;
        this.name = name;
        this.type = type;
        this.description = description;
        this.location = location;
        this.roomLocation = roomLocation;
        this.value = value;
        this.price = price;
    }

    public String getItemId() {
        return itemId;
    }

    public String getName() {
        return name;
    }

    public String getItemName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getRoomLocation() {
        return roomLocation;
    }

    public int getValue() {
        return value;
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

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean canUse() {
        return false;
    }

    @Override
    public String toString() {
        return itemId + " | " + name + " | " + type + " | " + description +
                " | container=" + location +
                " | room=" + roomLocation +
                " | value=" + value +
                " | price=" + price;
    }
}