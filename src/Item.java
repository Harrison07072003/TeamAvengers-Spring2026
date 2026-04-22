public abstract class Item {
    private String Id;
    private String Name;
    private String Description;
    private String Type;
    private String location;
    private String roomLocation;
    private int value; // HP for consumables, attack for weapons, etc.

    public Item(String Id, String Name, String Type,String Description,String location, String roomLocation,int value) {
        this.Id = Id;
        this.Name = Name;
        this.Type = Type;
        this.Description = Description;
        this.location = location;
        this.roomLocation = roomLocation;
        this.value = value;
    }

    public String getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public String getDescription() {
        return Description;
    }

    public String getType() {
        return Type;
    }
    public String getlocation() {
        return location;
    }
    public String getRoomLocation() {
        return roomLocation;
    }
    public int getValue() {
        return value;
    }
}
