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

    private void setActionMessage(String message) {
        this.lastActionMessage = message;
    }

    private boolean hasInventorySpace() {
        return getInventory().size() < inventoryCapacity;
    }

    // Name lookup is the common bridge from text commands to concrete inventory items.
    public Item getItem(String itemName) {
        if (itemName == null) {
            return null;
        }

        for (Item item : getInventory()) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;
    }

    // Adds an item to inventory and rewrites its container metadata to match the player.
    public boolean addItem(Item item) {
        if (item == null || !hasInventorySpace()) {
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

    private Room getCurrentRoomObject() {
        if (roomMap == null || currentRoom == null || currentRoom.isEmpty()) {
            return null;
        }
        return roomMap.getRoom(currentRoom);
    }

    private Room requireCurrentRoom() {
        Room room = getCurrentRoomObject();
        if (room == null) {
            setActionMessage("Room not found.");
        }
        return room;
    }

    private Item requireOwnedItem(Item item) {
        if (item == null || !getInventory().contains(item)) {
            setActionMessage("You don't have that item.");
            return null;
        }
        return item;
    }

    private Consumable getFirstConsumable() {
        for (Item item : getInventory()) {
            if (item instanceof Consumable) {
                return (Consumable) item;
            }
        }
        return null;
    }

    private Consumable getFirstFoodForSale(VendingMachine vendingMachine) {
        for (Map.Entry<Item, Integer> entry : vendingMachine.getItemsForSale().entrySet()) {
            if (entry.getKey() instanceof Consumable) {
                return (Consumable) entry.getKey();
            }
        }
        return null;
    }

    private boolean isFlashlightRecipe(Item firstItem, Item secondItem) {
        return firstItem != null && secondItem != null &&
                (("flashlight".equalsIgnoreCase(firstItem.getName()) &&
                        "batteries".equalsIgnoreCase(secondItem.getName())) ||
                        ("flashlight".equalsIgnoreCase(secondItem.getName()) &&
                                "batteries".equalsIgnoreCase(firstItem.getName())));
    }

    // Picks up an item from the player's current room using the exact API shape requested.
    public void pickUpItem(String itemName) {
        if (itemName == null || itemName.isEmpty()) {
            setActionMessage("Specify item to pick up.");
            return;
        }

        Room room = requireCurrentRoom();
        if (room == null) {
            return;
        }

        Item item = room.findItem(itemName);
        if (item == null) {
            setActionMessage("Item not found.");
            return;
        }

        if (!addItem(item)) {
            setActionMessage("Inventory full.");
            return;
        }

        room.removeItem(item);
        setActionMessage("Picked up " + item.getName() + ".");
    }

    // Drops an item back into the current room and returns the dropped object for callers that need it.
    public Item dropItem(String itemName) {
        if (itemName == null || itemName.isEmpty()) {
            setActionMessage("Specify item to drop.");
            return null;
        }

        Room room = requireCurrentRoom();
        if (room == null) {
            return null;
        }

        Item item = getItem(itemName);
        if (item == null) {
            setActionMessage("You don't have that item.");
            return null;
        }

        removeItem(item);
        room.addItem(item);
        setActionMessage("Dropped " + item.getName() + ".");
        return item;
    }

    // Inventory display stays in the player so the controller only prints the result.
    public String showInventory() {
        return inventoryToString();
    }

    // Quest item handling happens from the player side, matching the current project rule.
    public void useItem(Item item) {
        if (item == null) {
            setActionMessage("Item not found.");
            return;
        }

        if (!(item instanceof Quest) || !item.canUse()) {
            setActionMessage("You can't use that item.");
            return;
        }

        Quest questItem = (Quest) item;
        switch (questItem.getQuestType()) {
            case "office_key":
                if (officeUnlocked) {
                    setActionMessage("Nothing happened.");
                    return;
                }
                officeUnlocked = true;
                break;
            case "cure":
                if (plagueCured) {
                    setActionMessage("Nothing happened.");
                    return;
                }
                plagueCured = true;
                break;
            case "cure_vital":
                break;
            default:
                setActionMessage("Nothing happened.");
                return;
        }

        removeItem(item);
        setActionMessage("Used " + item.getName() + ".");
    }

    // Stores an owned item in the current room's vending machine using the item's current price.
    public boolean storeItem(Item item) {
        Room room = requireCurrentRoom();
        if (room == null) {
            return false;
        }
        if (!room.hasVendingMachine()) {
            setActionMessage("No vending machine here.");
            return false;
        }

        Item ownedItem = requireOwnedItem(item);
        if (ownedItem == null) {
            return false;
        }

        removeItem(ownedItem);
        room.getVendingMachine().addItem(ownedItem, ownedItem.getPrice());
        ownedItem.setRoomLocation(currentRoom);
        setActionMessage("Stored " + ownedItem.getName() + ".");
        return true;
    }

    // Equipping always replaces the previous weapon bonus before applying the new one.
    public void equipWeapon(Weapon weapon) {
        if (weapon == null || !getInventory().contains(weapon)) {
            setActionMessage("Not a weapon.");
            return;
        }

        if (equippedWeapon != null) {
            setATK(getATK() - equippedWeapon.getAtkIncrease());
        }

        equippedWeapon = weapon;
        setATK(getATK() + weapon.getAtkIncrease());
        setActionMessage("Equipped " + weapon.getName() + ".");
    }

    // Buys the first consumable currently stocked in the room's vending machine.
    public boolean buyFood() {
        Room room = requireCurrentRoom();
        if (room == null) {
            return false;
        }
        if (!room.hasVendingMachine()) {
            setActionMessage("No vending machine here.");
            return false;
        }
        if (!hasInventorySpace()) {
            setActionMessage("Inventory full.");
            return false;
        }

        VendingMachine vendingMachine = room.getVendingMachine();
        Consumable food = getFirstFoodForSale(vendingMachine);
        if (food == null) {
            setActionMessage("Food not found.");
            return false;
        }
        if (getCoins() < food.getPrice()) {
            setActionMessage("Not enough coins.");
            return false;
        }

        Item boughtItem = vendingMachine.dispenseItem(food.getName());
        if (boughtItem == null || !addItem(boughtItem)) {
            setActionMessage("Purchase failed.");
            return false;
        }

        setCoins(getCoins() - food.getPrice());
        setActionMessage("Bought " + boughtItem.getName() + ".");
        return true;
    }

    // Consumes the first consumable in inventory because the required method shape has no parameters.
    public void consumeFood() {
        Consumable food = getFirstConsumable();
        if (food == null) {
            setActionMessage("Not consumable.");
            return;
        }

        int healed = food.getHpRestore();
        setHP(getHP() + healed);
        removeItem(food);
        setActionMessage("Consumed " + food.getName() + " +" + healed + " HP");
    }

    // Combines the hard-coded flashlight recipe required by the no-argument method signature.
    public boolean combineItems() {
        Item firstItem = getItem("flashlight");
        Item secondItem = getItem("batteries");
        if (!isFlashlightRecipe(firstItem, secondItem)) {
            setActionMessage("Cannot combine those items.");
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
        setActionMessage("Created " + poweredFlashlight.getName() + ".");
        return true;
    }
}
