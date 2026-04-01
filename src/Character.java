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
    public String getCharaterID() {
        return this.charaterID;
    }
}
