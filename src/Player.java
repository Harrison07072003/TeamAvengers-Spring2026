public class Player extends Character{
    //fields
    private String currentRoom;
    private GameMap Map;
    private Item equippedWeapon;
    //constructor
    public Player(String id, int maxHP, int attack, int defense, int coins,String roomID,GameMap map) {
        super(id, maxHP, attack, defense,coins);
        this.currentRoom = roomID;
        this.Map = map;
        this.equippedWeapon = new Item("I0","Fists","test,",0);
    }


    //methods
    public void attack(Monster monster){
        if(monster.isAlive()) {
            double damage = this.getAttack() + this.equippedWeapon.getAttackBonus() - monster.getDefense();
            if(monster.isDefending()){damage = damage*(0.6);}
            if (damage <= 0) {damage = 1;}
            monster.setCurrentHP(monster.getCurrentHP() - (int)damage);
            if (monster.getCurrentHP() <= 0) {
                monster.setAlive(false);
                this.getInventory().add(monster.dropItem(""));
                this.collectCoins(monster.getCoins());
            }
        }
    }
    public void collectCoins(int coins){
        this.setCoins(this.getCoins() + coins);
    }
    public String inspectMonster(){
        Monster monster = this.getMonster();
        if(monster.isAlive()) {
            return monster.getName() + ": " + monster.getMonsterDescription() + "\nHP: " + monster.getCurrentHP() + "/" + monster.getMaxHP() + "\nAttack: "
                    + monster.getAttack() + "\nDefense: " + monster.getDefense() + "\n------------------------------";
        }
        else{
            return "No Monsters detected";
        }
    }
    public boolean heavyAttack(Monster monster){
        if(monster.isAlive()){
            double heavyDamage = ((this.getAttack() + this.equippedWeapon.getAttackBonus()) * 1.4 - monster.getDefense());
            if(monster.isDefending()){heavyDamage = heavyDamage*(0.6);}
            if(heavyDamage <= 0){heavyDamage = 1;}
            int chance = (int)(Math.random() * 100);
            if(chance > 40){
                monster.setCurrentHP(monster.getCurrentHP() - (int)heavyDamage);
                if (monster.getCurrentHP() <= 0) {
                    monster.setAlive(false);
                    this.getInventory().add(monster.dropItem(""));
                    this.collectCoins(monster.getCoins());
                }
                return true;
            }
        }
        return false;
    }
    public void retreat(){

    }
    public boolean engageMonster(){
        if(this.getCurrentRoom(currentRoom).getMonsters().get(0).isAlive()){
            return true;
        }
        return false;
    }
    public Item dropItem(String item){
        return null;
    }
    public void defend(){
        this.setDefending(true);
    }
    public Room getCurrentRoom(String roomID){
         return this.Map.getRoom(roomID);
    }
    public String getRoomID(){
        return this.currentRoom;
    }
     public void setCurrentRoom(String roomID){
        this.currentRoom = roomID;
    }
    public String checkWeapon(){
        return this.equippedWeapon.toString();
    }
    public int getAttackBonus(){
        return this.equippedWeapon.getAttackBonus();
    }
    public String getBuilding(){
        return this.getCurrentRoom(currentRoom).getBuilding();
    }
    public boolean equipWeapon(String weapon){
        for(int i = 0; i < this.getInventory().size(); i++){
            if(this.getInventory().get(i).getItemName().equalsIgnoreCase(weapon)){
                this.equippedWeapon = this.getInventory().get(i);
                return true;
            }
        }
        return false;
    }
    public String getRoomName(){
        return this.getCurrentRoom(currentRoom).getName();
    }
    public String getMonsterName(){
        return this.getMonster().getName();
    }
    public Monster getMonster(){
        return this.getCurrentRoom(this.getRoomID()).getMonsters().get(0);
    }

}
