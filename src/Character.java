import java.util.ArrayList;

// Base model for anything in the game that can have stats, coins, and an inventory.
public class Character {
    // Shared identity and combat stats inherited by concrete character types.
    private String characterID;
    private String name;
    private int HP;
    private int ATK;
    private int DEF;
    private ArrayList<Item> inventory;
    private boolean isAlive;
    private int coins;

    // Starts every character with stats and an empty inventory.
    public Character(String id, int maxHP, int attack, int defense) {
        this.characterID = id;
        this.name = "";
        this.HP = maxHP;
        this.ATK = attack;
        this.DEF = defense;
        this.inventory = new ArrayList<>();
        this.isAlive = true;
        this.coins = 0;
    }

    public String getCharacterID() {
        return characterID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHP() {
        return HP;
    }

    // Dropping HP to zero also marks the character as dead.
    public void setHP(int HP) {
        this.HP = HP;
        if (this.HP <= 0) {
            this.HP = 0;
            this.isAlive = false;
        }
    }

    public int getATK() {
        return ATK;
    }

    public void setATK(int ATK) {
        this.ATK = ATK;
    }

    public int getDEF() {
        return DEF;
    }

    public void setDEF(int DEF) {
        this.DEF = DEF;
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public void setInventory(ArrayList<Item> inventory) {
        this.inventory = inventory;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        this.isAlive = alive;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    // Formats the inventory once so the controller/view can print it directly.
    public String inventoryToString() {
        if (inventory == null || inventory.isEmpty()) {
            return "Inventory empty.";
        }

        StringBuilder sb = new StringBuilder("Inventory:\n");
        for (Item item : inventory) {
            sb.append("- ")
                    .append(item.getName())
                    .append(" [")
                    .append(item.getType())
                    .append("]\n");
        }
        return sb.toString().trim();
    }
}
