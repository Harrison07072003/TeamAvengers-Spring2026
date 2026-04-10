public class Monster extends Character{
    //fields
    private String monsterDescription;
    private String RoomID;
    private String name;
    //constructor
    public Monster(String id,String Name,String description, int maxHP, int attack, int defense,int coins, String roomID) {
        super(id, maxHP, attack, defense,coins);
        this.name = Name;
        this.monsterDescription = description;
        this.RoomID = roomID;
    }
    //methods
    //getters and setters
    public String getMonsterDescription() {
        return this.monsterDescription;
    }
    public void attack(Character player){
        if(this.isAlive()) {
            double damage = this.getAttack() - player.getDefense();
            if(player.isDefending()){damage =damage*(0.6);}
            if (damage <= 0) {damage = 1;}
            player.setCurrentHP(player.getCurrentHP() - (int)damage);
            if (player.getCurrentHP() <= 0)
                player.setAlive(false);
        }
    }
    public Item dropItem(String item){
        if(!this.isAlive() && !this.getInventory().isEmpty())
            return this.getInventory().get(0);
        else
            return null;
    }
    public String getName(){
        return this.name;
    }
}
