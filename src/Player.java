import java.util.ArrayList;

public class Player extends Character {
    private Weapon equippedWeapon;
    private int inventoryCapacity;
    private String currentRoom;
    private boolean officeUnlocked;
    private boolean plagueCured;

    public Player(String id, int maxHP, int attack, int defense) {
        super(id, maxHP, attack, defense);
        setInventory(new ArrayList<>());
        this.inventoryCapacity = 5;
        this.currentRoom = "R1";
        this.officeUnlocked = false;
        this.plagueCured = false;
    }

    public String getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(String currentRoom) {
        this.currentRoom = currentRoom;
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

    public Item getItem(String itemName) {
        if (itemName == null) return null;

        for (Item item : getInventory()) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;
    }

    public boolean addItem(Item item) {
        if (item == null || getInventory().size() >= inventoryCapacity) {
            return false;
        }
        item.setLocation("inventory");
        item.setRoomLocation(currentRoom);
        return getInventory().add(item);
    }

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

    public String pickUpItem(String itemName, Room room) {
        if (room == null) {
            return "Room not found.";
        }

        Item item = room.findItem(itemName);
        if (item == null) {
            return "Item not found.";
        }

        if (!addItem(item)) {
            return "Inventory full.";
        }

        room.removeItem(item);
        return "Picked up " + item.getName() + ".";
    }

    public String dropItem(String itemName, Room currentRoom) {
        if (itemName == null || itemName.isEmpty()) {
            return "Specify item to drop.";
        }

        Item item = getItem(itemName);
        if (item == null) {
            return "You don't have that item.";
        }

        removeItem(item);
        if (currentRoom != null) {
            currentRoom.addItem(item);
        }

        return "Dropped " + item.getName() + ".";
    }

    public String showInventory() {
        return inventoryToString();
    }

    public String equipWeapon(String weaponName) {
        Item item = getItem(weaponName);

        if (!(item instanceof Weapon)) {
            return "Not a weapon.";
        }

        if (equippedWeapon != null) {
            setATK(getATK() - equippedWeapon.getAtkIncrease());
        }

        equippedWeapon = (Weapon) item;
        setATK(getATK() + equippedWeapon.getAtkIncrease());
        return "Equipped " + item.getName() + ".";
    }

    public String consumeItem(String itemName) {
        Item item = getItem(itemName);

        if (item == null) {
            return "Item not found.";
        }

        if (!(item instanceof Consumable)) {
            return "Not consumable.";
        }

        int healed = ((Consumable) item).getHpRestore();
        setHP(getHP() + healed);
        removeItem(item);

        return "Consumed " + item.getName() + " +" + healed + " HP";
    }

    public String useItem(String itemName) {
        return useItem(getItem(itemName));
    }

    public String useItem(Item item) {
        if (item == null) {
            return "Item not found.";
        }

        if (!(item instanceof Quest) || !item.canUse()) {
            return "You can't use that item.";
        }

        String type = ((Quest) item).getQuestType();

        switch (type) {
            case "office_key":
                if (officeUnlocked) {
                    return "Nothing happened.";
                }
                officeUnlocked = true;
                removeItem(item);
                return "Used " + item.getName() + ".";

            case "cure":
                if (plagueCured) {
                    return "Nothing happened.";
                }
                plagueCured = true;
                removeItem(item);
                return "Used " + item.getName() + ".";

            case "cure_vital":
                return "Used " + item.getName() + ".";

            default:
                return "Nothing happened.";
        }
    }

    public Tool combineItems(String itemName1, String itemName2) {
        Item firstItem = getItem(itemName1);
        Item secondItem = getItem(itemName2);

        if (firstItem == null || secondItem == null) {
            return null;
        }

        boolean flashlightCombo =
                ("flashlight".equalsIgnoreCase(firstItem.getName()) &&
                        "batteries".equalsIgnoreCase(secondItem.getName())) ||
                        ("flashlight".equalsIgnoreCase(secondItem.getName()) &&
                                "batteries".equalsIgnoreCase(firstItem.getName()));

        if (!flashlightCombo) {
            return null;
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
        return poweredFlashlight;
    }

    public String combineItemsMessage(String itemName1, String itemName2) {
        if (itemName1 == null || itemName1.isEmpty() || itemName2 == null || itemName2.isEmpty()) {
            return "Specify two items.";
        }

        Tool result = combineItems(itemName1, itemName2);
        if (result == null) {
            return "Cannot combine those items.";
        }

        return "Created " + result.getName() + ".";
    }

    public String combineItems(String itemsToCombine) {
        if (itemsToCombine == null || itemsToCombine.isEmpty()) {
            return "Specify two items.";
        }

        String[] itemNames = itemsToCombine.split(" ", 2);
        if (itemNames.length < 2) {
            return "Specify two items.";
        }

        return combineItemsMessage(itemNames[0], itemNames[1]);
    }

    public String buyItem(String itemName, Room room) {
        if (itemName == null || itemName.isEmpty()) {
            return "Specify item to buy.";
        }

        if (room == null || !room.hasVendingMachine()) {
            return "No vending machine here.";
        }

        VendingMachine vendingMachine = room.getVendingMachine();
        int price = vendingMachine.getPrice(itemName);

        if (price < 0) {
            return "Item not found.";
        }

        if (getCoins() < price) {
            return "Not enough coins.";
        }

        if (getInventory().size() >= inventoryCapacity) {
            return "Inventory full.";
        }

        Item item = vendingMachine.dispenseItem(itemName);
        if (item == null) {
            return "Purchase failed.";
        }

        addItem(item);
        setCoins(getCoins() - price);
        return "Bought " + item.getName() + ".";
    }
}
