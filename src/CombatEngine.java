public class CombatEngine {
    //fields
    private final
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
    public boolean isBattleOver(){
        return !player.isAlive() || !enemy.isAlive() || retreated;
    }
    public int getTurns(){
        return this.turns;
    }
    public void resetEngine(){
        this.player.setState(2);
        this.enemy = player.getMonster();
        this.turns = 1;
        this.retreated = false;
    }
    public String getMonsterHealth(){
        return this.enemy.getHealth();
    }
    public boolean getMonsterAlive(){
        return this.enemy.isAlive();
    }
    public String action(String command){
        String result = "";
        boolean defending = (turns % 3 == 0);
        if(command.equals("attack")){
            if(defending)
                result += enemy.getName() + " is defending! Your attacks will be less effective.\n";
            enemy.setDefending(defending);
            player.attack(enemy);
            result += "You did " + player.getDamage(enemy,false) + " points of damage\n";
            if(!defending) {
                enemy.attack(player);
                if(enemy.isAlive())
                    result += enemy.getName() + " launched forward! They did " + enemy.getDamage(player) + " points of damage\n";
            }
        }
        else if(command.equals("heavy attack")){
            if(defending)
                result += enemy.getName() + " is defending! Your attacks will be less effective.\n";
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
                result += enemy.getName() + " landed a blow dealing " + enemy.getDamage(player) + " points of damage\n";
            }
            else
                result += "Your heavy attack missed!\n";
        }
        else if(command.equals("defend")){
            player.setDefending(true);
            result += "You brace yourself for the next attack.\n";
            if(!defending) {
                enemy.attack(player);
                result += enemy.getName() + " struck and knocked off " + enemy.getDamage(player) + " points of HP\n";
            }
            else
                result += "Both you and the enemy prepared yourselves and defended!\n";
            player.setDefending(false);
        }
        else if(command.equals("dodge")){
            double dodgeChance = (Math.random()*100);
            if(dodgeChance >= 50 && !defending){
                result += enemy.getName() + " missed!\n";
            }
            else if(defending){
                result += "The dodge was wasted " + enemy.getName() +" didn't strike.\n";
            }
            else{
                result += "You failed to dodge the enemy's strike.\n";
                enemy.attack(player);
                result += enemy.getName() + " did " + enemy.getDamage(player) + " points of HP\n";
            }
        }
        else if(command.equals("retreat")){
            result += "You retreat from the battle, to the last room you were in.\n";
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
