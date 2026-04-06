import java.util.ArrayList;

public class Room {
    //fields
    private String roomId;
    private String roomName;
    private String roomDescription;
    private ArrayList<String> exits;
    private ArrayList<Item> inventory;
    //sum for keep track puzzle
    //sum for keep track monster??

    //constructor
    public Room(String roomId, String roomName, String roomDescription) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.roomDescription = roomDescription;
        this.exits = new ArrayList<>();
        this.inventory = new ArrayList<>();
    }

    //getters
    public String getRoomId() {
        return roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public String getRoomDescription() {
        return roomDescription;
    }

    //methods
    public void addExit(String exit) {
        exits.add(exit);
    }

    public void addItem(Item item) {
        inventory.add(item);
    }

    public void removeItem(Item item) {
        inventory.remove(item);
    }


}
