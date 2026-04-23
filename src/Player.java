import java.util.ArrayList;
import java.util.Map;

// Concrete player model that owns inventory actions, item usage, and weapon state.
public class Player extends Character {
    // Player-specific state that sits on top of the shared Character stats.
    private Weapon equippedWeapon;
    private int inventoryCapacity;
    private String currentRoom;
    private boolean officeUnlocked;
    private boolean plagueCured;
    private RoomMap roomMap;
    // Controllers read the most recent outcome from here after void/boolean API calls.
    private String lastActionMessage;

    // Starts the player in a default room with a small inventory cap and no quest progress.
    public Player(String id, int maxHP, int attack, int defense) {
        super(id, maxHP, attack, defense);
        setInventory(new ArrayList<>());
        this.inventoryCapacity = 5;
        this.currentRoom = "R1";
        this.officeUnlocked = false;
        this.plagueCured = false;
        this.lastActionMessage = "";
    }

    public String getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(String currentRoom) {
        this.currentRoom = currentRoom;
    }

    // The room map lets the player resolve its current room without the controller passing Room around.
    public void setRoomMap(RoomMap roomMap) {
        this.roomMap = roomMap;
    }

    public boolean isOfficeUnlocked() {
        return officeUnlocked;
    }

    public void setOfficeUnlocked(boolean officeUnlocked) {
        this.officeUnlocked = officeUnlocked;
    }

    public boolean isPlagueCured() {
        return plagueCured;
    }

    public void setPlagueCured(boolean plagueCured) {
        this.plagueCured = plagueCured;
    }

    public Weapon getEquippedWeapon() {
        return equippedWeapon;
    }

    public String getLastActionMessage() {
        return lastActionMessage;
    }

    // Name lookup is the common bridge from text commands to concrete inventory items.
    public Item getItem(String itemName) {
        if (itemName == null) return null;

        for (Item item : getInventory()) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;
    }

    // Adds an item to inventory and rewrites its container metadata to match the player.
    public boolean addItem(Item item) {
        if (item == null || getInventory().size() >= inventoryCapacity) {
            return false;
        }
        item.setLocation("inventory");
        item.setRoomLocation(currentRoom);
        return getInventory().add(item);
    }

    // Centralized removal keeps equipped-weapon cleanup in one place.
    public boolean removeItem(Item item) {
        if (item == null) {
            return false;
        }

        boolean removed = getInventory().remove(item);
        if (!removed) {
            return false;
        }

        if (item == equippedWeapon) {
            setATK(getATK() - equippedWeapon.getAtkIncrease());
            equippedWeapon = null;
        }

        if ("backpack".equalsIgnoreCase(item.getName()) && inventoryCapacity > 5) {
            inventoryCapacity = 5;
        }

        return true;
    }

    // Picks up an item from the player's current room using the exact API shape requested.
    public void pickUpItem(String itemName) {
        if (itemName == null || itemName.isEmpty()) {
            lastActionMessage = "Specify item to pick up.";
            return;
        }

        Room room = getCurrentRoomObject();
        if (room == null) {
            lastActionMessage = "Room not found.";
            return;
        }

        Item item = room.findItem(itemName);
        if (item == null) {
            lastActionMessage = "Item not found.";
            return;
        }

        if (!addItem(item)) {
            lastActionMessage = "Inventory full.";
            return;
        }

        room.removeItem(item);
        lastActionMessage = "Picked up " + item.getName() + ".";
    }

    // Drops an item back into the current room and returns the dropped object for callers that need it.
    public Item dropItem(String itemName) {
        if (itemName == null || itemName.isEmpty()) {
            lastActionMessage = "Specify item to drop.";
            return null;
        }

        Room room = getCurrentRoomObject();
        if (room == null) {
            lastActionMessage = "Room not found.";
            return null;
        }

        Item item = getItem(itemName);
        if (item == null) {
            lastActionMessage = "You don't have that item.";
            return null;
        }

        removeItem(item);
        room.addItem(item);
        lastActionMessage = "Dropped " + item.getName() + ".";
        return item;
    }

    // Inventory display stays in the player so the controller only prints the result.
    public String showInventory() {
        return inventoryToString();
    }

    // Quest item handling happens from the player side, matching the current project rule.
    public void useItem(Item item) {
        if (item == null) {
            lastActionMessage = "Item not found.";
            return;
        }

        if (!(item instanceof Quest) || !item.canUse()) {
            lastActionMessage = "You can't use that item.";
            return;
        }

        String type = ((Quest) item).getQuestType();

        switch (type) {
            case "office_key":
                if (officeUnlocked) {
                    lastActionMessage = "Nothing happened.";
                    return;
                }
                officeUnlocked = true;
                removeItem(item);
                lastActionMessage = "Used " + item.getName() + ".";
                return;

            case "cure":
                if (plagueCured) {
                    lastActionMessage = "Nothing happened.";
                    return;
                }
                plagueCured = true;
                removeItem(item);
                lastActionMessage = "Used " + item.getName() + ".";
                return;

            case "cure_vital":
                lastActionMessage = "Used " + item.getName() + ".";
                return;

            default:
                lastActionMessage = "Nothing happened.";
        }
    }

    // Stores an owned item in the current room's vending machine using the item's current price.
    public boolean storeItem(Item item) {
        Room room = getCurrentRoomObject();
        if (room == null || !room.hasVendingMachine()) {
            lastActionMessage = "No vending machine here.";
            return false;
        }
        if (item == null || !getInventory().contains(item)) {
            lastActionMessage = "You don't have that item.";
            return false;
        }

        removeItem(item);
        room.getVendingMachine().addItem(item, item.getPrice());
        item.setRoomLocation(currentRoom);
        lastActionMessage = "Stored " + item.getName() + ".";
        return true;
    }

    // Equipping always replaces the previous weapon bonus before applying the new one.
    public void equipWeapon(Weapon weapon) {
        if (weapon == null || !getInventory().contains(weapon)) {
            lastActionMessage = "Not a weapon.";
            return;
        }

        if (equippedWeapon != null) {
            setATK(getATK() - equippedWeapon.getAtkIncrease());
        }

        equippedWeapon = weapon;
        setATK(getATK() + equippedWeapon.getAtkIncrease());
        lastActionMessage = "Equipped " + weapon.getName() + ".";
    }

    // Buys the first consumable currently stocked in the room's vending machine.
    public boolean buyFood() {
        Room room = getCurrentRoomObject();
        if (room == null || !room.hasVendingMachine()) {
            lastActionMessage = "No vending machine here.";
            return false;
        }
        if (getInventory().size() >= inventoryCapacity) {
            lastActionMessage = "Inventory full.";
            return false;
        }

        VendingMachine vendingMachine = room.getVendingMachine();
        Item food = null;
        for (Map.Entry<Item, Integer> entry : vendingMachine.getItemsForSale().entrySet()) {
            if (entry.getKey() instanceof Consumable) {
                food = entry.getKey();
                break;
            }
        }

        if (food == null) {
            lastActionMessage = "Food not found.";
            return false;
        }
        if (getCoins() < food.getPrice()) {
            lastActionMessage = "Not enough coins.";
            return false;
        }

        Item boughtItem = vendingMachine.dispenseItem(food.getName());
        if (boughtItem == null || !addItem(boughtItem)) {
            lastActionMessage = "Purchase failed.";
            return false;
        }

        setCoins(getCoins() - food.getPrice());
        lastActionMessage = "Bought " + boughtItem.getName() + ".";
        return true;
    }

    // Consumes the first consumable in inventory because the required method shape has no parameters.
    public void consumeFood() {
        for (Item item : new ArrayList<>(getInventory())) {
            if (item instanceof Consumable) {
                int healed = ((Consumable) item).getHpRestore();
                setHP(getHP() + healed);
                removeItem(item);
                lastActionMessage = "Consumed " + item.getName() + " +" + healed + " HP";
                return;
            }
        }

        lastActionMessage = "Not consumable.";
    }

    // Combines the hard-coded flashlight recipe required by the no-argument method signature.
    public boolean combineItems() {
        Item firstItem = getItem("flashlight");
        Item secondItem = getItem("batteries");
        boolean flashlightCombo =
                firstItem != null && secondItem != null &&
                        (("flashlight".equalsIgnoreCase(firstItem.getName()) &&
                                "batteries".equalsIgnoreCase(secondItem.getName())) ||
                                ("flashlight".equalsIgnoreCase(secondItem.getName()) &&
                                        "batteries".equalsIgnoreCase(firstItem.getName())));

        if (!flashlightCombo) {
            lastActionMessage = "Cannot combine those items.";
            return false;
        }

        removeItem(firstItem);
        removeItem(secondItem);

        Tool poweredFlashlight = new Tool(
                "A1P",
                "Powered Flashlight",
                "A flashlight with batteries, ready to use.",
                "inventory",
                currentRoom,
                0,
                0,
                "light"
        );

        addItem(poweredFlashlight);
        lastActionMessage = "Created " + poweredFlashlight.getName() + ".";
        return true;
    }

    // Internal helper used by the API methods above to resolve the active room safely.
    private Room getCurrentRoomObject() {
        if (roomMap == null || currentRoom == null || currentRoom.isEmpty()) {
            return null;
        }
        return roomMap.getRoom(currentRoom);
    }
}
