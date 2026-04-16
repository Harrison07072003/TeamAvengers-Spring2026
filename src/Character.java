import java.util.ArrayList;

public class Character {
    //fields
    private String characterID;
    private String name;
    private int HP;
    private int ATK;
    private int DEF;
    private ArrayList<Item> inventory;
    private boolean isAlive;
    private int coins;
    //constructor
    public Character(String id, int maxHP, int attack, int defense) {
        this.characterID = id;
        this.name = ""; // Default, can be set
        this.HP = maxHP;
        this.ATK = attack;
        this.DEF = defense;
        this.inventory = new ArrayList<>();
        this.isAlive = true;
        this.coins = 0; // Default
    }
    //methods
    public String getCharacterID() {
        return this.characterID;
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

    public void setHP(int HP) {
        this.HP = HP;
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

    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }
}
