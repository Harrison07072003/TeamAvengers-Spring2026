import java.util.Scanner;
import java.util.ArrayList; //delete after test

public class Player extends Character {
    private int vialCount;
    private int capacity;
    private String currentRoom;
    private Weapon equippedWeapon;
    private GameMap map;
    private ArrayList<Item> inventory; // delete after test

    public Player(String id, int maxHP, int attack, int defense, GameMap map) {
        super(id, maxHP, attack, defense);
        this.vialCount = 0;
        this.capacity = 5;
        this.currentRoom = "R1";
        this.equippedWeapon = null;
        this.map = map;
        this.inventory = new ArrayList<>(); // delete after test
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }// delete after test

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

    public GameMap getMap() {
        return map;
    }

    public void setMap(GameMap map) {
        this.map = map;
    }

    //  Start Game
    public void startGame() {
        vialCount = 0;
        capacity = 5;
        currentRoom = "R1";
        equippedWeapon = null;

        if (map != null) {
            map.generateRooms();
            map.loadPuzzles();
            map.spawnMonsters();
            map.loadItems();
        }

        System.out.println("Welcome to GGC Plague.");
        System.out.println("You wake up in the Chemistry Lab.");
        System.out.println("Your goal is to collect five cure vials, create the cure, and escape through the parking lot.");
    }

    // Restart
    public void restartGame() {
        vialCount = 0;
        capacity = 5;
        currentRoom = "R1";
        equippedWeapon = null;
        if (map != null) {
            map.generateRooms();
            map.loadPuzzles();
            map.spawnMonsters();
            map.loadItems();
        }
        System.out.println("Game has been restared.");
    }

    // Quit
    public void quitGame() {
        System.out.println("Exiting game.");
        System.exit(0);

    }

    // Escape Game
    public void escapeGame() {
        if (!currentRoom.equalsIgnoreCase("R20")) {
            System.out.println("You must be in the Parking Lot (R20) to escape.");
            return;
        }

        // player must have all 5 vials / cure requirement before escaping
        if (vialCount >= 5) {
            System.out.println("You successfully escaped GGC with the cure. You win!");
        }
        else {
            System.out.println("You cannot escape yet.");
            System.out.println(vialCount + "/5 vials collected. 5/5 vials are required for the Cure in order to escape.");
        }
    }


    //delete after test
    public void addItem(Item item) {
        if (inventory.size() < capacity) {
            inventory.add(item);
        } else {
            System.out.println("Inventory is full.");
        }
    }

    public Item removeItem(String itemId) {
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).getId().equalsIgnoreCase(itemId)) {
                return inventory.remove(i);
            }
        }
        return null;
    }

    public void clearInventory() {
        inventory.clear();
    }


}