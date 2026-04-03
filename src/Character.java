import java.util.ArrayList;

public class Character {
    //fields
    private String charaterID;
    private int maxHP;
    private int currentHP;
    private int attack;
    private int defense;
    //private ArrayList<Item> inventory;
    private boolean alive;
    //constructor
    public Character(String id, int maxHP, int attack, int defense) {
        this.charaterID = id;
        this.maxHP = maxHP;
        this.currentHP = maxHP;
        this.attack = attack;
        this.defense = defense;
        //this.inventory = new ArrayList<Item>();
        this.alive = true;
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
