import java.util.ArrayList;

public class Player extends Character {
    private ArrayList<Item> inventory;
    private int coins;
    private Weapon equippedWeapon; // Changed to Weapon
    private int inventoryCapacity;
    private int vialCount;
    private int capacity; // Perhaps same as inventoryCapacity
    private String currentRoom;
    private RoomMap gameMap; // Assuming GameMap class exists
    private View view;

    public Player(String id, int maxHP, int attack, int defense, View view) {
        super(id, maxHP, attack, defense);
        this.inventory = new ArrayList<>();
        this.coins = 15; // Starting coins
        this.equippedWeapon = null;
        this.inventoryCapacity = 5; // add capacity start capacity  5 then increase by the backpack to 10
        // backpack can be dropped decrases back to 5
        this.view = view;
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
        view.display("Picked up " + itemName);
        // In a full implementation, this would interact with the room
    }

    public Item dropItem(String itemName) {
        Item item = getItem(itemName);
        if (item != null) {
            removeItem(item);
            view.display("Dropped " + itemName);
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
            view.display("Consumed " + item.getItem_Name() + ", HP restored by " + consumable.getHpRestore());
        } else if (item instanceof Tool) {
            Tool tool = (Tool) item;
            tool.use(this);
            view.display("Used " + item.getItem_Name() + " (" + tool.getUtilityType() + ") on player");
        } else if (item instanceof Weapon) {
            String msg = equipWeapon((Weapon) item);
            view.display(msg);
        } else {
            view.display("Cannot use " + item.getItem_Name());
        }
    }

    public boolean storeItem(Item item) {
        if (inventory.size() < inventoryCapacity) {
            addItem(item);
            view.display("Stored " + item.getItem_Name());
            return true;
        } else {
            view.display("Inventory full");
            return false;
        }
    }

    public String equipWeapon(Weapon weapon) {
        if (hasItem(weapon.getItem_Name())) {
            setEquippedWeapon(weapon);
            setATK(getATK() + weapon.getAtkIncrease());
            return "Equipped " + weapon.getItem_Name();
        } else {
            return "Weapon not in inventory";
        }
    }

    public boolean buyFood() {
        if (coins >= 4) {
            coins -= 4;
            Consumable food = new Consumable("food_id", "Food", "Edible food", "consumable", 10);
            addItem(food);
            view.display("Bought food");
            return true;
        } else {
            view.display("Not enough coins");
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
            view.display("Combined Batteries and Flashlight into Powered Flashlight");
            return true;
        }
        return false;
    }

    public boolean enterRoom(String room) {
        // Placeholder
        view.display("Entering room " + room);
        currentRoom = room;
        return true;
    }

    public String exploreRoom(Room room) {
        // Placeholder
        return "Exploring room";
    }

    public void startGame() {
        // Placeholder
        view.display("Game started");
    }

    public void quitGame() {
        // Placeholder
        view.display("Game quit");
    }

    public void viewInventory() {
        view.display("Inventory:");
        for (Item item : inventory) {
            view.display("- " + item.getItem_Name());
        }
    }
}
