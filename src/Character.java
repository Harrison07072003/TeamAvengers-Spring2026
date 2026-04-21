import java.util.ArrayList;

public class Character {
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
    public Character(String id, int maxHP, int attack, int defense,int coins) {
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
    public String getCharaterID() {
        return this.charaterID;
    }
    public int getMaxHP() {
        return this.maxHP;
    }
    public int getCurrentHP() {
        return this.currentHP;
    }
    public int getAttack() {
        return this.attack;
    }
    public int getDefense() {
        return this.defense;
    }
    public ArrayList<Item> getInventory() {
        return this.inventory;
    }
    public boolean isAlive() {
        return this.alive;
    }
}