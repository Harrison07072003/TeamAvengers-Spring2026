import java.util.Scanner;

public class Player extends Character {
    private int vialCount;
    private int capacity;
    private String currentRoom;
    private Weapon equippedWeapon;
    private GameMap map;

    public Player(String id, int maxHP, int attack, int defense, GameMap map) {
        super(id, maxHP, attack, defense);
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
        Scanner input = new Scanner(System.in);

        System.out.print("Are you sure you want to restart the game? (yes/no): ");
        String answer = input.nextLine();

        if (answer.equalsIgnoreCase("yes")) {
            if (map != null) {
                map = new GameMap();
            }

            startGame();
            System.out.println("The game has been restarted.");
        }
        else {
            System.out.println("Restart cancelled.");
        }
    }

    // Quit
    public void quitGame() {
        Scanner input = new Scanner(System.in);

        System.out.print("Do you want to save the game before quitting? (yes/no): ");
        String answer = input.nextLine();

        if (answer.equalsIgnoreCase("yes")) {
            if (map != null) {
                map.saveGame(this);
            }
            System.out.println("Game saved. Exiting game.");
        }
        else {
            System.out.println("Exiting game without saving.");
        }

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

    // Inventory
    public void viewInventory() {
        System.out.println("Inventory:");

        // This is a temporary placeholder until the real Inventory structure is added
        System.out.println("Weapons: ");
        System.out.println("Items: ");
        System.out.println("Quest Items: ");
        System.out.println("Tools: ");

        if (equippedWeapon != null) {
            System.out.println("Equipped Weapon: " + equippedWeapon);
        }
        else {
            System.out.println("Equipped Weapon: none");
        }

        System.out.println("Available space: " + capacity);
    }
}