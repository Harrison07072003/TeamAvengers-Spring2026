import java.util.ArrayList; //delete after test

public class Player extends Character {
    private int vialCount;
    private int capacity;
    private String currentRoom;
    private Weapon equippedWeapon;
    private RoomMap map;
    private int coins;

    public Player(String id, int maxHP, int attack, int defense,int coins, RoomMap map) {
        super(id, maxHP, attack, defense,coins);
        this.vialCount = 0;
        this.capacity = 5;
        this.currentRoom = "R1";
        this.equippedWeapon = null;
        this.map = map;
    }


    // getters and setters for my fields
    public int getVialCount() {
        return vialCount;
    }

    public void setVialCount(int vialCount) {
        this.vialCount = vialCount;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(String currentRoom) {
        this.currentRoom = currentRoom;
    }

    public Weapon getEquippedWeapon() {
        return equippedWeapon;
    }

    public void setEquippedWeapon(Weapon equippedWeapon) {
        this.equippedWeapon = equippedWeapon;
    }

    public RoomMap getMap() {
        return map;
    }

    public void setMap(RoomMap map) {
        this.map = map;
    }

    public Room getCurrentRoomData(){
        return this.map.getRoom(this.currentRoom);
    }

    //  Start Game



    // Quit
    public void quitGame() {
        System.out.println("Exiting game.");

    }




    //delete after test
    public void addItem(Item item) {
        if (this.getInventory().size() < capacity) {
            this.getInventory().add(item);
        } else {
            System.out.println("Inventory is full.");
        }
    }

    public Item removeItem(String itemId) {
        for (int i = 0; i < this.getInventory().size(); i++) {
            if (this.getInventory().get(i).getId().equalsIgnoreCase(itemId)) {
                return this.getInventory().remove(i);
            }
        }
        return null;
    }

    public void clearInventory() {
        this.getInventory().clear();
    }

    public int getCoins(){
        return coins;
    }


}