public class Item {
    //fields
    private String ID;
    private String name;
    private String description;
    private int attackBonus;
    //constructor
    Item(String id, String name, String description, int attackBonus){
        this.ID = id;
        this.name = name;
        this.description = description;
        this.attackBonus = attackBonus;
    }
    //methods
     public String getItemName() {
        return this.name;
    }
    public int getAttackBonus(){
        return this.attackBonus;
    }
    public String toString(){
        return this.name + ": Raises damage by " + this.getAttackBonus();
    }
}
