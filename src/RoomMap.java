import java.util.ArrayList;

public class RoomMap {
    private Room currentRoom;

    public RoomMap() {
        currentRoom = new Room("Starting Room");
        VendingMachine vendingMachine =
                new VendingMachine("VM1", "Vending Machine", "Dispenses food for coins.", "tool", 0);
        vendingMachine.setVendingMachine(currentRoom);

        try {
            ArrayList<Item> items = Item.loadItemsFromDefaultFile();
            for (Item item : items) {
                currentRoom.addItem(item);
            }
        } catch (Exception e) {
            throw new IllegalStateException("Unable to load the starting room items from Item.txt: " + e.getMessage(), e);
        }
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }
}
