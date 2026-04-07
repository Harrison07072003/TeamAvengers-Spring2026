public class Monster extends Character{
    //fields
    private String monsterDescription;
    private String RoomD;
    //constructor
    public Monster(String id,String description, int maxHP, int attack, int defense,int coins, String roomID) {
        super(id, maxHP, attack, defense,coins);
        this.monsterDescription = description;
        this.RoomD = roomID;
    }
    //methods
    //getters and setters
    public String getMonsterDescription() {
        return this.monsterDescription;
    }
    public void attack(Character player,boolean defending){
        if(this.isAlive()) {
            double damage = this.getAttack() - player.getDefense();
            if(defending){damage =damage*(0.6);}
            if (damage <= 0) {damage = 1;}
            player.setCurrentHP(player.getCurrentHP() - (int)damage);
            if (player.getCurrentHP() <= 0)
                player.setAlive(false);
        }
    }
    public Item dropItem(){
        if(!this.isAlive() && !this.getInventory().isEmpty())
            return this.getInventory().get(0);
        else
            return null;
    }
    public int dropCoins(){
        return this.getCoins();
    }
}
