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
    //private Puzzle puzzle;
    //private VendingMachine vendingMachine;
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
      //  this.puzzle = null;
      //  this.vendingMachine = null;
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

    public HashMap<String, String> getExits() {
        return exits;
    }

    public String getExit(String input) {
        if (exits.containsKey((input).substring(0,1))) {
            return exits.get(input.substring(0,1));
        }else if(exits.containsValue(input)){
            return input;
        }
        return null;
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public ArrayList<Monster> getMonster() {
        return monsters;
    }

    /*public Puzzle getPuzzle() {
        return puzzle;
    }
*/
    public boolean requiresValidFlashlight() {
        return requiresValidFlashlight;
    }

  *  public VendingMachine getVendingMachine() {
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
        return !inventory.isEmpty();
    }

    public boolean hasMonster() {
        return !monsters.isEmpty();
    }

    public boolean hasPuzzle() {
        return puzzle != null;
    }

    public boolean hasVendingMachine() {
        return vendingMachine != null;
    }


    public void addItem(Item item) {
        inventory.add(item);
    }

    public void removeItem(Item item) {
        inventory.remove(item);
    }

    public void addMonster(Monster monster) {
        monsters.add(monster);
    }

    public void removeMonster(Monster monster) {
        monsters.remove(monster);
    }

    public void removePuzzle() {
        puzzle = null;
    }

    public void addExit(String direction, String roomId){
        exits.put(direction, roomId);
    }
}