import java.util.ArrayList;

public abstract class Character {
    //fields
    private String charaterID;
    private int maxHP;
    private int currentHP;
    private int attack;
    private int defense;
    private ArrayList<Item> inventory;
    private boolean alive;
    private int coins;
    //constructor
    public Character(String id, int maxHP, int attack, int defense, int coins) {
        this.charaterID = id;
        this.maxHP = maxHP;
        this.currentHP = maxHP;
        this.attack = attack;
        this.defense = defense;
        this.inventory = new ArrayList<Item>();
        this.alive = true;
        this.coins = coins;
    }
    //methods
    //getters and setters
    public String getCharaterID() {
        return this.charaterID;
    }
    public int getAttack() {
        return this.attack;
    }
    public int getDefense() {
        return this.defense;
    }
    public int getCurrentHP() {
        return currentHP;
    }
    public int getMaxHP(){
        return this.maxHP;
    }
    public String getHealth() {
        return this.currentHP + "/" + this.maxHP;
    }
    public ArrayList<Item> getInventory() {
        return this.inventory;
    }
    public int getCoins() {
        return this.coins;
    }
    public void setCoins(int coins) {
        this.coins = coins;
    }
    public String getInventoryString() {
        String inventoryString = "";
        if (this.inventory.isEmpty()) {
            return "Empty";
        }
        for (Item item : this.getInventory()) {
            inventoryString += item.getItemName() + ", ";
        }
        return inventoryString.substring(0, inventoryString.length() - 2);
    }
    public boolean isAlive() {
        return this.alive;
    }
    public void setAlive(boolean status) {
        this.alive = status;
    }
    public void setCurrentHP(int hp) {
        this.currentHP = hp;
    }

}
