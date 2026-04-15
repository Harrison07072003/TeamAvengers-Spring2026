import java.util.ArrayList;

public class Room {
    //fields
    private String roomId;
    private String roomName;
    private String roomDescription;
    private String building;
    private ArrayList<String> exits;
    private ArrayList<Item> inventory;
    private ArrayList<Monster> monsters;
    private Puzzle puzzle;
    private VendingMachine vendingMachine;
    private boolean requiresValidFlashlight;

    //constructor
    public Room(String roomId, String roomName, String roomDescription, String building, boolean requiresValidFlashlight) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.roomDescription = roomDescription;
        this.building = building;
        this.exits = new ArrayList<>();
        this.inventory = new ArrayList<>();
        this.monsters = new ArrayList<>();
        this.puzzle = null;
        this.requiresValidFlashlight = requiresValidFlashlight;
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

    public String getBuilding() {
        return building;
    }

    public ArrayList<String> getExits() {
        return exits;
    }

    public ArrayList<Item> getInventory() {
        return inventory;
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


    //setters
    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public void setRoomDescription(String roomDescription) {
        this.roomDescription = roomDescription;
    }

    public void setBuilding(String building) {
        this.building = building;
    }


    public void setExits(ArrayList<String> exits) {
        this.exits = exits;
    }

    public void setInventory(ArrayList<Item> inventory) {
        this.inventory = inventory;
    }

    public void setMonster(ArrayList<Monster> monsters) {
        this.monsters = monsters;
    }

    public void setPuzzle(Puzzle puzzle) {
        this.puzzle = puzzle;
    }

    public void setRequiresValidFlashlight(boolean requiresValidFlashlight) {
        this.requiresValidFlashlight = requiresValidFlashlight;
    }

    public void setVendingMachine(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }


    //methods
    public boolean checkItem() {
        return !inventory.isEmpty();
    }

    public boolean checkMonster() {
        return !monsters.isEmpty();
    }

    public boolean checkPuzzle() {
        return puzzle != null;
    }

    public boolean checkVendingMachine() {
        return vendingMachine != null;
    }

    public void addItem(Item item) {
        inventory.add(item);
    }

    public void removeItem(Item item) {
        inventory.remove(item);
    }

    public void removeMonster(Monster monster) {
        monsters.remove(monster);
    }

    public void removePuzzle() {
        puzzle = null;
    }

    public void addExit(String exit) {
        exits.add(exit);
    }

}