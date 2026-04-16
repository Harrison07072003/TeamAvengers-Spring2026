import java.util.ArrayList;

public class Player extends Character {
    private ArrayList<Item> inventory;
    private int coins;
    private Weapon equippedWeapon; // Changed to Weapon
    private int inventoryCapacity;
    private int vialCount;
    private int capacity; // Perhaps same as inventoryCapacity
    private String currentRoom;
    private GameMap gameMap; // Assuming GameMap class exists

    public Player(String id, int maxHP, int attack, int defense) {
        super(id, maxHP, attack, defense);
        this.inventory = new ArrayList<>();
        this.coins = 15; // Starting coins
        this.equippedWeapon = null;
        this.inventoryCapacity = 5; // add capacity start capacity  5 then increase by the backpack to 10
        // backpack can be dropped decrases back to 5
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public void addItem(Item item) {
        inventory.add(item);
    }

    public void removeItem(Item item) {
        inventory.remove(item);
    }

    public boolean hasItem(String itemName) {
        for (Item item : inventory) {
            if (item.getItem_Name().equalsIgnoreCase(itemName)) {
                return true;
            }
        }
        return false;
    }

    public Item getItem(String itemName) {
        for (Item item : inventory) {
            if (item.getItem_Name().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public Weapon getEquippedWeapon() {
        return equippedWeapon;
    }

    public void setEquippedWeapon(Weapon equippedWeapon) {
        this.equippedWeapon = equippedWeapon;
    }

    public void pickUpItem(String itemName) {
        // Placeholder: Assume item is available and add to inventory
        System.out.println("Picked up " + itemName);
        // In a full implementation, this would interact with the room
    }

    public Item dropItem(String itemName) {
        Item item = getItem(itemName);
        if (item != null) {
            removeItem(item);
            System.out.println("Dropped " + itemName);
            return item;
        }
        return null;
    }

    public void useItem(Item item) {
        if (item instanceof Consumable) {
            Consumable consumable = (Consumable) item;
            consumable.use();
            setHP(getHP() + consumable.getHpRestore());
            removeItem(item);
        } else if (item instanceof Tool) {
            Tool tool = (Tool) item;
            tool.use(this);
        } else if (item instanceof Weapon) {
            equipWeapon((Weapon) item);
        } else {
            System.out.println("Cannot use " + item.getItem_Name());
        }
    }

    public boolean storeItem(Item item) {
        if (inventory.size() < inventoryCapacity) {
            addItem(item);
            System.out.println("Stored " + item.getItem_Name());
            return true;
        } else {
            System.out.println("Inventory full");
            return false;
        }
    }

    public void equipWeapon(Weapon weapon) {
        if (hasItem(weapon.getItem_Name())) {
            setEquippedWeapon(weapon);
            setATK(getATK() + weapon.getAtkIncrease());
            System.out.println("Equipped " + weapon.getItem_Name());
        } else {
            System.out.println("Weapon not in inventory");
        }
    }

    public boolean buyFood() {
        if (coins >= 4) {
            coins -= 4;
            Consumable food = new Consumable("food_id", "Food", "Edible food", "consumable", 10);
            addItem(food);
            System.out.println("Bought food");
            return true;
        } else {
            System.out.println("Not enough coins");
            return false;
        }
    }

    public void consumeFood() {
        for (Item item : inventory) {
            if (item instanceof Consumable) {
                useItem(item);
                break;
            }
        }
    }

    public boolean combineItems() {
        Item batteries = getItem("Batteries");
        Item flashlight = getItem("Flashlight");
        if (batteries != null && flashlight != null) {
            removeItem(batteries);
            removeItem(flashlight);
            Tool poweredFlashlight = new Tool("combined_id", "Powered Flashlight", "Flashlight with batteries", "tool", 0, "light");
            addItem(poweredFlashlight);
            System.out.println("Combined Batteries and Flashlight into Powered Flashlight");
            return true;
        }
        return false;
    }

    public boolean enterRoom(String room) {
        // Placeholder
        System.out.println("Entering room " + room);
        currentRoom = room;
        return true;
    }

    public String exploreRoom(Room room) {
        // Placeholder
        return "Exploring room";
    }

    public void startGame() {
        // Placeholder
        System.out.println("Game started");
    }

    public void quitGame() {
        // Placeholder
        System.out.println("Game quit");
    }

    public void viewInventory() {
        System.out.println("Inventory:");
        for (Item item : inventory) {
            System.out.println("- " + item.getItem_Name());
        }
    }

    public void attack(Monster enemy, boolean defending) {
        // Placeholder
        System.out.println("Attacking enemy");
    }

    public void heavyAttack(Monster enemy, boolean defending) {
        // Placeholder
        System.out.println("Heavy attacking enemy");
    }

    public void defend() {
        // Placeholder
        System.out.println("Defending");
    }

    public void dodge() {
        // Placeholder
        System.out.println("Dodging");
    }

    public void retreat() {
        // Placeholder
        System.out.println("Retreating");
    }

    public String inspectMonster(Monster enemy) {
        // Placeholder
        return "Inspecting monster";
    }

    public void ignoreMonster() {
        // Placeholder
        System.out.println("Ignoring monster");
    }

    public void engageMonster() {
        // Placeholder
        System.out.println("Engaging monster");
    }

    public void explorePuzzle() {
        // Placeholder
        System.out.println("Exploring puzzle");
    }

    public void solvePuzzle() {
        // Placeholder
        System.out.println("Solving puzzle");
    }

    public void ignorePuzzle() {
        // Placeholder
        System.out.println("Ignoring puzzle");
    }
}
