import java.util.ArrayList;
import java.util.HashMap;

public class Room {
    private final String roomId;
    private final String roomName;
    private final String roomDescription;
    private final String building;
    private final HashMap<String, String> exits;
    private final ArrayList<Item> inventory;
    private final ArrayList<Monster> monsters;
    private Puzzle puzzle;
    private VendingMachine vendingMachine;
    private final boolean requiresValidFlashlight;

    public Room(String roomId, String roomName, String roomDescription, String building, boolean requiresValidFlashlight) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.roomDescription = roomDescription;
        this.building = building;
        this.exits = new HashMap<>();
        this.inventory = new ArrayList<>();
        this.monsters = new ArrayList<>();
        this.requiresValidFlashlight = requiresValidFlashlight;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getRoomID() {
        return roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public String getName() {
        return roomName;
    }

    public String getRoomDescription() {
        return roomDescription;
    }

    public String getBuilding() {
        return building;
    }

    public HashMap<String, String> getExits() {
        return exits;
    }

    public String getExit(String input) {
        if (input == null || input.isBlank()) {
            return null;
        }

        String normalized = input.trim().toUpperCase();
        if (normalized.length() > 0 && exits.containsKey(normalized.substring(0, 1))) {
            return exits.get(normalized.substring(0, 1));
        }
        if (exits.containsValue(normalized)) {
            return normalized;
        }
        return null;
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public ArrayList<Monster> getMonsters() {
        return monsters;
    }

    public ArrayList<Monster> getMonster() {
        return monsters;
    }

    public Puzzle getPuzzle() {
        return puzzle;
    }

    public boolean requiresValidFlashlight() {
        return requiresValidFlashlight;
    }

    public VendingMachine getVendingMachine() {
        return vendingMachine;
    }

    public void setPuzzle(Puzzle puzzle) {
        this.puzzle = puzzle;
    }

    public void setVendingMachine(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    public void attachVendingMachine(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    public boolean hasItem() {
        return !inventory.isEmpty();
    }

    public boolean hasMonster() {
        return !monsters.isEmpty();
    }

    public boolean hasMonsters() {
        return !monsters.isEmpty();
    }

    public boolean hasPuzzle() {
        return puzzle != null;
    }

    public boolean hasActivePuzzle() {
        return puzzle != null && !puzzle.isSolved();
    }

    public boolean hasVendingMachine() {
        return vendingMachine != null;
    }

    public void addItem(Item item) {
        if (item != null) {
            inventory.add(item);
        }
    }

    public void removeItem(Item item) {
        inventory.remove(item);
    }

    public Item findItem(String itemName) {
        for (Item item : inventory) {
            if (item.getItemName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;
    }

    public void addMonster(Monster monster) {
        if (monster != null) {
            monsters.add(monster);
        }
    }

    public void removeMonster(Monster monster) {
        monsters.remove(monster);
    }

    public void removePuzzle() {
        puzzle = null;
    }

    public void addExit(String direction, String roomId) {
        exits.put(direction.toUpperCase(), roomId.toUpperCase());
    }
}
