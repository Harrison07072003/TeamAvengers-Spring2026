public class Player extends Character{
    //fields
    private String currentRoom;
    private final GameMap Map;
    private Item equippedWeapon;
    private int vials;
    private int currentState; //lets the game know what state the player is in 1=navigation,2=battle,3=puzzle,4=finished
    //constructor
    public Player(String id, int maxHP, int attack, int defense, int coins,String roomID,GameMap map) {
        super(id, maxHP, attack, defense,coins);
        this.currentRoom = roomID;
        this.Map = map;
        this.equippedWeapon = new Item("I0","Fists","test,",0);
        this.currentState = 1;
        this.vials = 0;
    }


    //methods
    //monster methods
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
    public boolean heavyAttack(Monster monster){
        if(monster.isAlive()){
            double heavyDamage = ((this.getAttack() + this.equippedWeapon.getAttackBonus()) * 1.3);
            if(monster.isDefending()){heavyDamage -= monster.getDefense();}
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
    public void collectCoins(int coins){
        this.setCoins(this.getCoins() + coins);
    }
    public String inspectMonster(){
        if(this.getCurrentRoom(currentRoom).hasMonsters()) {
            Monster monster = this.getMonster();
            if (monster.isAlive()) {
                return monster.getName() + ": " + monster.getMonsterDescription() + "\nHP: " + monster.getCurrentHP() + "/" + monster.getMaxHP() + "\nAttack: "
                        + monster.getAttack() + "\nDefense: " + monster.getDefense();
            }
        }
        return "No Monsters detected";
    }
    public void retreat(){
        //this.move();
        int roomId = Integer.parseInt(this.getRoomID().substring(1)) - 1;
        String room = "R" + roomId;
        if(roomId >= 1)
            this.setCurrentRoom(room);
    }
    public boolean engageMonster() {
        return this.getCurrentRoom(currentRoom).getMonsters().get(0).isAlive();
    }
    //getters and setters
    public int getDamage(Monster enemy,boolean heavyDamage){
        double damage;
        if(heavyDamage && enemy.isDefending())
            damage = (((this.getAttack() + this.equippedWeapon.getAttackBonus()*1.3)  - enemy.getDefense()));
        else if(heavyDamage)
            damage = (this.getAttack() + this.equippedWeapon.getAttackBonus() )*1.3;
        else if(enemy.isDefending())
            damage = ((this.getAttack() + this.equippedWeapon.getAttackBonus())*0.6 - enemy.getDefense());
        else
            damage = this.getAttack() + this.equippedWeapon.getAttackBonus() - enemy.getDefense();
        if(damage < 1)
            return 1;
        else
            return (int) damage;
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
    public int getCurrentState(){
        return this.currentState;
    }
    public void setState(int state){
        this.currentState = state;
    }
    public String getEquippedWeaponName(){
        return this.equippedWeapon.getItemName();
    }
    public int getVials(){
        return this.vials;
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
    public int getAttackBonus(){
        return this.equippedWeapon.getAttackBonus();
    }
    public String getBuilding(){
        return this.getCurrentRoom(currentRoom).getBuilding();
    }
    //item methods
    public Item dropItem(String item){
        return null;
    }
    public String checkWeapon(){
        if(this.equippedWeapon.getItemName().equals("Fists"))
            return "There is no weapon equipped.";
        return this.equippedWeapon.toString();
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

}
