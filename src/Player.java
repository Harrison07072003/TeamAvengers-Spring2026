import java.util.ArrayList;

public class Player extends Character {
    private ArrayList<Item> inventory;
    private int coins;
    private Item equippedWeapon;

    public Player(String id, int maxHP, int attack, int defense) {
        super(id, maxHP, attack, defense);
        this.inventory = new ArrayList<>();
        this.coins = 15; // Starting coins
        this.equippedWeapon = null;
        // add capacity start capacity  5 then increase by the backpack to 10
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

    public Item getEquippedWeapon() {
        return equippedWeapon;
    }

    public void setEquippedWeapon(Item equippedWeapon) {
        this.equippedWeapon = equippedWeapon;
    }
}
