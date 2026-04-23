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
    public int getDamage(Player p){
        double damage = this.getAttack() - p.getDefense();
        if(p.isDefending())
            damage = damage*0.6;
        if(damage < 1)
            return 1;
        else
            return (int) damage;
    }
    //combat methods
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
        Item drop = this.getInventory().get(0);
        if(!this.isAlive() && !this.getInventory().isEmpty()) {
            return drop;
        }
        else
            return null;
    }
    public String getDropsString(){
        String drop = "";
        if(this.getInventory().isEmpty())
            return "";
        else
            for(int i = 0; i < this.getInventory().size(); i++){
                drop += this.getInventory().get(i).getItemName() + ",";
            }
        return drop.substring(0,drop.length()-1);
    }
   public String fileString(){
        return this.getCharaterID() + "," + this.getMonsterName() + "," + this.getMonsterDescription() + "," + this.getMaxHP() + "," + this.getAttack() + "," + this.getDefense() + "," + this.getCoins() + "," + this.getRoomID();
    }
    public String getRoomID() {
        return this.RoomID;
    }
}
