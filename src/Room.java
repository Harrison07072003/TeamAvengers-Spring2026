import java.util.ArrayList;

public class Room {
    //fields
    private String roomId;
    private String roomName;
    private String roomDescription;
    private String building;
    private ArrayList<Room> exits;
    private ArrayList<Item> inventory;
    private Monster monster;
    private Puzzle puzzle;
    private boolean requiresValidFlashlight;

    //constructor
    public Room(String roomId, String roomName, String roomDescription, String building, boolean requiresValidFlashlight) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.roomDescription = roomDescription;
        this.building = building;
        this.exits = new ArrayList<>();
        this.inventory = new ArrayList<>();
        this.monster = null;
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

    public ArrayList<Room> getExits() {
        return exits;
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public Monster getMonster() {
        return monster;
    }

    public Puzzle getPuzzle() {
        return puzzle;
    }

    public boolean requiresValidFlashlight() {
        return requiresValidFlashlight;
    }

    //setters



    //methods
    public boolean checkItem(){
    }

    public boolean checkMonster(){
    }

    public boolean checkPuzzle(){

    }

    public boolean checkVendingMachine(){

    }


    public void addItem(Item item) {
        inventory.add(item);
    }

    public void removeItem(Item item) {
        inventory.remove(item);
    }

    public void removeItem(Item item){
        inventory.remove(item);
    }

    public void removeMonster(){
        monster = null;
    }

    public void removePuzzle(){
        puzzle = null;
    }
    public void addExit(String exit) {
        exits.add(exit);
    }



}
