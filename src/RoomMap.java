import java.util.ArrayList;

public class Room {
    private String name;
    private ArrayList<Item> inventory;
    private boolean hasVendingMachine;

    public Room(String name) {
        this.name = name;
        this.inventory = new ArrayList<>();
        this.hasVendingMachine = false;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public void addItem(Item item) {
        inventory.add(item);
    }

    public void removeItem(Item item) {
        inventory.remove(item);
    }

    public boolean hasVendingMachine() {
        return hasVendingMachine;
    }

    public void setHasVendingMachine(boolean hasVendingMachine) {
        this.hasVendingMachine = hasVendingMachine;
    }
}

public class RoomMap {
    private Room currentRoom;

    public RoomMap() {
        currentRoom = new Room("Starting Room");
        currentRoom.setHasVendingMachine(true); // Add vending machine
        // Load items from file
        try {
            ArrayList<Item> items = Item.loadItemsFromFile("../Item.txt");
            for (Item item : items) {
                currentRoom.addItem(item);
            }
        } catch (Exception e) {
            // Handle error
        }
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }
}
