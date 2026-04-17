import java.util.List;

public class RoomMap {
    private Room currentRoom;

    public RoomMap() {
        currentRoom = new Room("Starting Room");
        currentRoom.setHasVendingMachine(true); // Add vending machine
        // Load items from file
        try {
            List<Item> items = Item.loadItemsFromFile("Item.txt");
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
