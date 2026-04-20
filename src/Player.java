import java.util.ArrayList;

public class Player extends Character {
    private Weapon equippedWeapon;
    private int inventoryCapacity;
    private String currentRoom;
    private boolean officeUnlocked;
    private boolean plagueCured;
    private final View view;

    public Player(String id, int maxHP, int attack, int defense, View view) {
        super(id, maxHP, attack, defense);
        setInventory(new ArrayList<>());
        setCoins(15);
        this.equippedWeapon = null;
        this.inventoryCapacity = 5;
        this.currentRoom = "";
        this.officeUnlocked = false;
        this.plagueCured = false;
        this.view = view;
    }

    @Override
    public ArrayList<Item> getInventory() {
        return super.getInventory();
    }

    public int getInventoryCapacity() {
        return inventoryCapacity;
    }

    public void expandInventory(int newCapacity) {
        if (newCapacity > inventoryCapacity) {
            inventoryCapacity = newCapacity;
        }
    }

    public boolean addItem(Item item) {
        if (item == null || getInventory().size() >= inventoryCapacity) {
            return false;
        }

        getInventory().add(item);
        return true;
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

        if ("backpack".equalsIgnoreCase(item.getItem_Name()) && inventoryCapacity > 5) {
            inventoryCapacity = 5;
        }

        return true;
    }

    public boolean hasItem(String itemName) {
        for (Item item : getInventory()) {
            if (item.getItem_Name().equalsIgnoreCase(itemName)) {
                return true;
            }
        }
        return false;
    }

    public Item getItem(String itemName) {
        for (Item item : getInventory()) {
            if (item.getItem_Name().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;
    }

    public Weapon getEquippedWeapon() {
        return equippedWeapon;
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

    public void setEquippedWeapon(Weapon equippedWeapon) {
        this.equippedWeapon = equippedWeapon;
    }

    public void pickUpItem(String itemName) {
        view.display("Picked up " + itemName);
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
        if (item == null) {
            view.display("Item not found.");
            return;
        }

        if (item instanceof Consumable) {
            view.display(((Consumable) item).use(this));
        } else if (item instanceof Tool) {
            view.display(((Tool) item).use(this));
        } else if (item instanceof Weapon) {
            view.display(((Weapon) item).use(this));
        } else if (item instanceof QuestItem) {
            view.display(((QuestItem) item).use(this));
        } else {
            view.display("Cannot use " + item.getItem_Name());
        }
    }

    public boolean storeItem(Item item) {
        if (addItem(item)) {
            view.display("Stored " + item.getItem_Name());
            return true;
        }

        view.display("Inventory full");
        return false;
    }

    public String equipWeapon(Weapon weapon) {
        if (weapon == null) {
            return View.weaponNotFound();
        }

        if (!hasItem(weapon.getItem_Name())) {
            return View.weaponNotInInventory();
        }

        if (equippedWeapon == weapon) {
            return View.weaponAlreadyEquipped(weapon.getItem_Name());
        }

        if (equippedWeapon != null) {
            setATK(getATK() - equippedWeapon.getAtkIncrease());
        }

        setEquippedWeapon(weapon);
        setATK(getATK() + weapon.getAtkIncrease());
        return View.equippedWeapon(weapon.getItem_Name());
    }

    public boolean buyFood(VendingMachine vendingMachine, String itemName) {
        if (vendingMachine == null || !vendingMachine.buyItem(itemName)) {
            view.display("That item is not available.");
            return false;
        }

        if (getCoins() < vendingMachine.getCost()) {
            view.display("Not enough coins");
            return false;
        }

        Consumable item = vendingMachine.dispenseItem(itemName);
        if (item == null) {
            view.display("That item is not available.");
            return false;
        }

        if (!addItem(item)) {
            view.display("Inventory full");
            return false;
        }

        setCoins(getCoins() - vendingMachine.getCost());
        view.display("Bought " + item.getItem_Name());
        return true;
    }

    public void consumeFood() {
        for (int i = 0; i < getInventory().size(); i++) {
            Item item = getInventory().get(i);
            if (item instanceof Consumable) {
                view.display(((Consumable) item).use(this));
                return;
            }
        }

        view.display("No consumable items available.");
    }

    public boolean combineItems() {
        return combineItems("Batteries", "Flashlight") != null;
    }

    public Tool combineItems(String itemNameOne, String itemNameTwo) {
        Item firstItem = getItem(itemNameOne);
        Item secondItem = getItem(itemNameTwo);

        if (firstItem == null || secondItem == null) {
            return null;
        }

        boolean isFlashlightCombo =
                ("batteries".equalsIgnoreCase(firstItem.getItem_Name()) && "flashlight".equalsIgnoreCase(secondItem.getItem_Name())) ||
                ("flashlight".equalsIgnoreCase(firstItem.getItem_Name()) && "batteries".equalsIgnoreCase(secondItem.getItem_Name()));

        if (!isFlashlightCombo) {
            return null;
        }

        removeItem(firstItem);
        removeItem(secondItem);

        Tool poweredFlashlight = new Tool(
                "combined_id",
                "Powered Flashlight",
                "A flashlight with batteries, ready to use.",
                "tool",
                0,
                "light"
        );
        addItem(poweredFlashlight);
        return poweredFlashlight;
    }

    public void viewInventory() {
        view.display("Inventory:");
        for (Item item : getInventory()) {
            view.display("- " + item.getItem_Name());
        }
    }
}
