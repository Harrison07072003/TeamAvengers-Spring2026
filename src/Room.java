import java.util.ArrayList;
import java.util.HashMap;

public class Room {
    //fields
    private String roomId;
    private String roomName;
    private String roomDescription;
    private String building;
    private HashMap<String, String> exits;
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
        this.exits = new HashMap<>();
        this.inventory = new ArrayList<>();
        this.monsters = new ArrayList<>();
        this.puzzle = null;
        this.vendingMachine = null;
        this.requiresValidFlashlight = requiresValidFlashlight;
    }

    //getters
    public String getRoomId() {
        return this.roomId;
    }

    public String getRoomName() {
        return this.roomName;
    }

    public String getRoomDescription() {
        return this.roomDescription;
    }

    public String getBuilding() {
        return this.building;
    }

    public HashMap<String, String> getExits() {
        return this.exits;
    }

    public String getExit(String input) {
        if (this.exits.containsKey((input).substring(0,1))) {
            return this.exits.get(input.substring(0,1));
        }else if(this.exits.containsValue(input)){
            return input;
        }
        return null;
    }

    public ArrayList<Item> getInventory() {
        return this.inventory;
    }

    public ArrayList<Monster> getMonster() {
        return this.monsters;
    }

    public Puzzle getPuzzle() {
        return puzzle;
    }

    public boolean requiresValidFlashlight() {
        return this.requiresValidFlashlight;
    }

  public VendingMachine getVendingMachine() {
        return vendingMachine;
    }

    //setters
    public void setPuzzle(Puzzle puzzle) {
        this.puzzle = puzzle;
    }

    public void setVendingMachine(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }




    //methods
    public boolean hasItem() {
        return !this.inventory.isEmpty();
    }

    public boolean hasMonsters() {
        return !this.monsters.isEmpty();
    }
    public ArrayList<Monster> getMonsters(){
        return this.monsters;
    }

    public boolean hasPuzzle() {
        return puzzle != null;
    }

    public boolean hasVendingMachine() {
        return vendingMachine != null;
    }




    public void addItem(Item item) {
        this.inventory.add(item);
    }

    public void removeItem(Item item) {
        this.inventory.remove(item);
    }

    public void addMonster(Monster monster) {
        this.monsters.add(monster);
    }

    public void removeMonster(Monster monster) {
        this.monsters.remove(monster);
    }

    public void removePuzzle() {
        puzzle = null;
    }

    public void addExit(String direction, String roomId){
        exits.put(direction, roomId);
    }
    public void addPuzzle(Puzzle puzzle){
        this.puzzle = puzzle;
    }

    public String getExitsFileString() {
        String exitString = "";
        for (String key : exits.keySet()) {
            exitString += key + "," + exits.get(key) + ".";
        }
        return exitString.substring(0, exitString.length() - 1);
    }

}