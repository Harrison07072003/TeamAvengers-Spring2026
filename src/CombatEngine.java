public class CombatEngine {
    //fields
    Player player;
    Monster enemy;
    private int turns;
    private boolean retreated;
    //constructor
    public CombatEngine(Player player) {
        this.player = player;
        this.turns = 1;
        this.retreated = false;
    }
    //methods
    //battle conditions
    public boolean isBattleOver(){
        return !player.isAlive() || !enemy.isAlive() || retreated;
    }
    public void resetEngine(){
        this.player.setState(2);
        this.enemy = player.getMonster();
        this.turns = 1;
        this.retreated = false;
    }
    //getters
    public int getTurns(){
        return this.turns;
    }
    public String getMonsterHealth(){
        return this.enemy.getHealth();
    }
    public boolean getMonsterAlive(){
        return this.enemy.isAlive();
    }
    public boolean getRetreated(){
        return this.retreated;
    }
    //battle command
    public String action(String command){
        String result = "";
        boolean defending = (turns % 3 == 0);
        if(command.equalsIgnoreCase("check weapon")) {
            result += player.checkWeapon() + "\n";
            turns--;
        }
        else if(command.equalsIgnoreCase("attack")){
            if(defending)
                result += enemy.getMonsterName() + " is defending! Your attacks will be less effective.\n";
            enemy.setDefending(defending);
            player.attack(enemy);
            result += "You did " + player.getDamage(enemy,false) + " points of damage\n";
            if(!defending) {
                enemy.attack(player);
                if(enemy.isAlive())
                    result += enemy.getMonsterName() + " launched forward! They did " + enemy.getDamage(player) + " points of damage\n";
            }
        }
        else if(command.equalsIgnoreCase("heavy attack")){
            if(defending)
                result += enemy.getMonsterName() + " is defending! Your attacks will be less effective.\n";
            enemy.setDefending(defending);
            boolean success = player.heavyAttack(enemy);
            if(success && !defending){
                result += "You did a lot of damage with your heavy attack! You did "
                        + player.getDamage(enemy,true) + " points of damage\n";
                enemy.attack(player);
            }
            else if(success && defending){
                result += "Your heavy attack was less effective against the defending enemy, but you still did a lot of damage!\n";
                result += "You did " + player.getDamage(enemy,true) + " points of damage\n";
            }
            else if(!success && !defending){
                result += "Your heavy attack missed!\n";
                enemy.attack(player);
                result += enemy.getMonsterName() + " landed a blow dealing " + enemy.getDamage(player) + " points of damage\n";
            }
            else
                result += "Your heavy attack missed!\n";
        }
        else if(command.equalsIgnoreCase("defend")){
            player.setDefending(true);
            result += "You brace yourself for the next attack.\n";
            if(!defending) {
                enemy.attack(player);
                result += enemy.getMonsterName() + " struck and knocked off " + enemy.getDamage(player) + " points of HP\n";
            }
            else
                result += "Both you and the enemy prepared yourselves and defended!\n";
            player.setDefending(false);
        }
        else if(command.equalsIgnoreCase("dodge")){
            double dodgeChance = (Math.random()*100);
            if(dodgeChance >= 50 && !defending){
                result += enemy.getMonsterName() + " missed!\n";
            }
            else if(defending){
                result += "The dodge was wasted " + enemy.getMonsterName() +" didn't strike.\n";
            }
            else{
                result += "You failed to dodge the enemy's strike.\n";
                enemy.attack(player);
                result += enemy.getMonsterName() + " did " + enemy.getDamage(player) + " points of HP\n";
            }
        }
        else if(command.equalsIgnoreCase("retreat")){
            result += "You retreated from battle to fight another day!\n";
            retreated = true;
        }
        else{
            result += "Invalid command. Try again.\n";
            turns--;
        }
        turns++;
        return result;
    }

}
