import java.util.ArrayList;

public class Player extends Character {

    private Weapon equippedWeapon;
    private int inventoryCapacity = 5;
    private String currentRoom;
    private boolean officeUnlocked;
    private boolean plagueCured;

    public Player(String id, int maxHP, int attack, int defense) {
        super(id, maxHP, attack, defense);
        setInventory(new ArrayList<>());
    }

    //  INVENTORY

    public boolean addItem(Item item) {
        if (item == null || getInventory().size() >= inventoryCapacity) {
            return false;
        }
        return getInventory().add(item);
    }
    public Item getItem(String itemName) {
        for (Item i : getInventory()) {
            if (i.getName().equalsIgnoreCase(itemName)) {
                return i;
            }
        }
        return null;
    }
    public Item storeItem(Item item) {
        if (item == null || getInventory().size() >= inventoryCapacity) {
            
        }

    }
    public Item dropItem(String itemName) {

        if (itemName == null || itemName.isEmpty()) {
            return null;
        }

        Item item = getItem(itemName);

        if (item == null) {
            return null;
        }

        removeItem(item);
        return item;
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



    public boolean hasItem(String itemName) {
        return getItem(itemName) != null;
    }

    public boolean pickUpItem(Item item) {
        Item foundItem = currentRoom.getName(item);
        if (foundItem != null) {
            currentRoom.removeItem(item);
            addToInventory(foundItem);
            System.out.println(item + " has been picked up and added to your inventory");
        } else {
            System.out.println("item is not available in this room");
        }
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
        if (vendingMachine == null || itemName == null || itemName.isEmpty()) {
            return false;
        }
        if (itemName == null) {
            System.out.println("Item not available in vending machine.");
            return false;
        }

        if (getCoins() < vendingMachine.getprice()) {
            System.out.println("Not enough coins to buy " + itemName);
            return false;
        }

        setCoins(getCoins() - food.getCost());
        addItem(food);
        System.out.println("You bought " + itemName + " for " + food.getCost() + " coins.");
        return true;
    }

    // COMBINING

    public Tool combineItems(String itemName1, String itemName2) {

        Item firstItem = getItem(itemName1);
        Item secondItem = getItem(itemName2);

        if (firstItem == null || secondItem == null) return null;

        boolean isFlashlightCombo =
                ("flashlight".equalsIgnoreCase(firstItem.getName()) &&
                        "batteries".equalsIgnoreCase(secondItem.getName())) ||
                        ("flashlight".equalsIgnoreCase(secondItem.getName()) &&
                                "batteries".equalsIgnoreCase(firstItem.getName()));

        if (isFlashlightCombo) {

            removeItem(firstItem);
            removeItem(secondItem);

            Tool poweredFlashlight = new Tool(
                    "combined_id",
                    "Powered Flashlight",
                    "tool",
                    "A flashlight with batteries, ready to use.",
                    "R17",
                    getroomLocation(),
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

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(String currentRoom) {
        this.currentRoom = currentRoom;
    }
}
