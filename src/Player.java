import java.util.ArrayList;

public class Player {
    private String currentRoom;
    private GameMap map;
    private ArrayList<Item> inventory;

    public Player(String startingRoom, GameMap map) {
        this.currentRoom = startingRoom;
        this.map = map;
        this.inventory = new ArrayList<>();
    }

    //Command: Enter Room (move player)
    public String enterRoom(String input) {
        if(input == null||input.trim().isEmpty()){
            return "There is no entrance in that direction. Please enter a valid Direction or Room ID.";
        }
        Room current = map.getRoom(currentRoom);
        String userInput = input.trim().toUpperCase();
        String nextRoom = current.getExit(userInput);
        if (nextRoom == null) {
            return "There is no entrance in that direction. Please enter a valid Direction or Room ID.";
        }
        currentRoom = nextRoom;
        return "You have entered " + currentRoom;
    }

    public String exploreRoom() {
        Room current = map.getRoom(currentRoom);
        if(current.requiresValidFlashlight()&& !inventory.contains(getItem("Powered Flashlight"))){
            return "Functioning flashlight with batteries is needed to explore this room.";
        }
        else {
            String result = "You are currently in the " + current.getRoomName() + ": " + current.getRoomDescription();
            if (current.hasItem()) {
                if (current.getInventory().size() == 1) {
                    result += "\nThere is an item in this room: " + current.getInventory().get(0).getItem_Name();
                }
                if (current.getInventory().size() > 1) {
                    result += "\nThere are items in this room: ";
                    for (Item item : current.getInventory()) {
                        result += "\n- " + item.getItem_Name();
                    }
                }
            }
            if (current.hasVendingMachine()) {
                result += "\nThere is a vending machine in this room.";
            }
            if (current.hasMonster()) {
                if (current.getMonster().size() == 1) {
                    result += "\nThere is a monster in this room: " + current.getMonster().get(0).getMonsterName();
                }
                if (current.getMonster().size() > 1) {
                    result += "\nThere are monsters in this room: ";
                    for (Monster monster : current.getMonster()) {
                        result += "\n- " + monster.getMonsterName();
                    }
                }
            }

            if (current.hasPuzzle()) {
                result += "\nThere is a puzzle in this room";
            }
            return result;
        }
    }

    public Item getItem(String itemName) {
        for (Item item : inventory) {
            if (item.getItem_Name().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;
    }
}