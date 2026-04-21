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
        setCoins(15);

        this.equippedWeapon = null;
        this.inventoryCapacity = 5;
        this.currentRoom = "";
        this.officeUnlocked = false;
        this.plagueCured = false;
    }

    // ================= INVENTORY =================

    public boolean addItem(Item item) {
        if (item == null || getInventory().size() >= inventoryCapacity) {
            return false;
        }
        return getInventory().add(item);
    }

    public boolean removeItem(Item item) {
        if (item == null) return false;

        boolean removed = getInventory().remove(item);

        if (!removed) return false;

        if (item == equippedWeapon) {
            setATK(getATK() - equippedWeapon.getAtkIncrease());
            equippedWeapon = null;
        }

        if ("backpack".equalsIgnoreCase(item.getName()) && inventoryCapacity > 5) {
            inventoryCapacity = 5;
        }

        return true;
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

    public boolean hasItem(String itemName) {
        return getItem(itemName) != null;
    }

    public boolean pickUpItem(Item item) {
        return addItem(item);
    }

    public Item dropItem(String itemName) {
        Item item = getItem(itemName);
        if (item != null) {
            removeItem(item);
            return item;
        }
        return null;
    }

    // ================= QUEST ITEMS ONLY =================

    public boolean useItem(Item item) {
        if (item instanceof QuestItem) {
            return ((QuestItem) item).use(this);
        }
        return false;
    }

    // ================= WEAPONS =================

    public boolean equipWeapon(Weapon weapon) {
        if (weapon == null || !hasItem(weapon.getName())) {
            return false;
        }

        if (equippedWeapon != null) {
            setATK(getATK() - equippedWeapon.getAtkIncrease());
        }

        equippedWeapon = weapon;
        setATK(getATK() + weapon.getAtkIncrease());

        return true;
    }

    public Weapon getEquippedWeapon() {
        return equippedWeapon;
    }

    // CONSUMABLES

    public boolean consumeFood() {
        for (Item item : getInventory()) {
            if (item instanceof Consumable) {
                ((Consumable) item).getHpRestore();
                removeItem(item);
                return true;
            }
        }
        return false;
    }

    // VENDING

    public boolean buyFood(VendingMachine vendingMachine, String itemName) {
        if (vendingMachine == null) return false;

        if (getCoins() < vendingMachine.getCost()) return false;

        Consumable item = vendingMachine.dispenseItem(itemName);
        if (item == null) return false;

        if (!addItem(item)) return false;

        setCoins(getCoins() - vendingMachine.getCost());
        return true;
    }

    // COMBINING

    public Tool combineItems(String itemName1, String itemName2) {

        Item firstItem = getItem(itemName1);
        Item secondItem = getItem(itemName2);

        if (firstItem == null || secondItem == null) return null;

        boolean isFlashlightCombo =
                ("flashlight".equalsIgnoreCase(firstItem.getItem_Name()) &&
                        "batteries".equalsIgnoreCase(secondItem.getItem_Name())) ||
                        ("flashlight".equalsIgnoreCase(secondItem.getItem_Name()) &&
                                "batteries".equalsIgnoreCase(firstItem.getItem_Name()));

        if (isFlashlightCombo) {

            removeItem(firstItem);
            removeItem(secondItem);

            Tool poweredFlashlight = new Tool(
                    "combined_id",
                    "Powered Flashlight",
                    "tool",
                    "A flashlight with batteries, ready to use.",
                    "R17",
                    0,
                    "Light" );
            addItem(poweredFlashlight);
            return poweredFlashlight;
        }

        return null;
    }

    // GAME STATE

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

    public String getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(String currentRoom) {
        this.currentRoom = currentRoom;
    }
}
