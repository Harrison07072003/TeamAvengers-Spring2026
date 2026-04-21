public class Monster extends Character{
    //fields
    private String monsterDescription;
    private String RoomID;
    private String monsterName;
    //constructor
    public Monster(String id,String Name,String description, int maxHP, int attack, int defense,int coins, String roomID) {
        super(id, maxHP, attack, defense,coins);
        this.monsterName = Name;
        this.monsterDescription = description;
        this.RoomID = roomID;
    }
    //methods
    //getters and setters
    public String getMonsterDescription() {
        return this.monsterDescription;
    }
    public String getMonsterName(){
        return this.monsterName;
    }

}