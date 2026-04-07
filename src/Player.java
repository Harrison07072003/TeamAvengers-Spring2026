public class Player extends Character{
    //fields
    private String currentRoom;
    //constructor
    public Player(String id, int maxHP, int attack, int defense, int coins,String room) {
        super(id, maxHP, attack, defense,coins);
        this.currentRoom = room;
    }


    //methods
    public void attack(Monster monster,boolean defending){
        if(monster.isAlive()) {
            double damage = this.getAttack() - monster.getDefense();
            if(defending){damage = damage*(3/5);}
            if (damage <= 0) {damage = 1;}
            monster.setCurrentHP(monster.getCurrentHP() - (int)damage);
            if (monster.getCurrentHP() <= 0) {
                monster.setAlive(false);
                this.getInventory().add(monster.dropItem());
                this.collectCoins(monster.dropCoins());
            }
        }
    }
    public void collectCoins(int coins){
        this.setCoins(this.getCoins() + coins);
    }
    public String inspectMonster(Monster monster){
        return monster.getMonsterDescription() + "\nHP: " + monster.getCurrentHP() + "/" + monster.getMaxHP() + "\nAttack: "
                + monster.getAttack() + "\nDefense: " + monster.getDefense() + "\n------------------------------";
    }
    public boolean heavyAttack(Monster monster,boolean defending){
        if(monster.isAlive()){
            double heavyDamage = (this.getAttack() * 1.4 - monster.getDefense());
            if(defending){heavyDamage = heavyDamage*(3/5);}
            if(heavyDamage <= 0){heavyDamage = 1;}
            int chance = (int)(Math.random() * 100);
            if(chance > 40){
                monster.setCurrentHP(monster.getCurrentHP() - (int)heavyDamage);
                if (monster.getCurrentHP() <= 0) {
                    monster.setAlive(false);
                    this.getInventory().add(monster.dropItem());
                    this.collectCoins(monster.dropCoins());
                }
                return true;
            }
        }
        return false;
    }
    public void retreat(){

    }
}
