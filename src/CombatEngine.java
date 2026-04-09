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
        this.enemy = player.getCurrentRoom(player.getRoomID()).getMonsters().get(0);
        this.turns = 1;
        retreated = false;
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
            if(defending){
                result += "The monster is defending! Your attack is less effective.\n";
            }
            enemy.setDefending(defending);
            player.attack(enemy);
            if(!defending) {
                enemy.attack(player);
            }
        }
        else if(command.equals("heavy attack")){
            if(defending){
                result += "The monster is defending!\n";
            }
            enemy.setDefending(defending);
            boolean success = player.heavyAttack(enemy);
            if(success && !defending){
                result += "You did a lot of damage with your heavy attack!\n";
                enemy.attack(player);
            }
            else if(success && defending){
                result += "Your heavy attack was less effective against the defending monster, but you still did a lot of damage!\n";
            }
            else if(!success){
                result += "Your heavy attack missed!\n";
                enemy.attack(player);
            }
        }
        else if(command.equals("defend")){
            player.defend();
            result += "You brace yourself for the next attack.\n";
            enemy.attack(player);
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
