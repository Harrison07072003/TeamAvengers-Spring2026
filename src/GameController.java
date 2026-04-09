import java.util.Scanner;

public class GameController {
    //fields
    private Player A;
    private View v;
    private Scanner input;
    private boolean isRunning;
    private CombatEngine combatEngine;
    private RoomMap school;
    //constructor
    GameController(View v) {
        this.school = new RoomMap();
        this.A = new Player("Player", 100, 18, 5, 10,"R1",school);
        this.v = v;
        this.input = new Scanner(System.in);
        this.isRunning = true;
        this.combatEngine = new CombatEngine(A);
    }
    //methods
    public void run(){
        school.getRooms().add(new Room("R1","Classroom","A typical classroom with desks and a whiteboard.",new int[]{1,2,3,4}));
        school.getRooms().add(new Room("R2","Library","A quiet library filled with books.",new int[]{0,5,6,7}));
        while(isRunning) {
            v.display(A.getRoomID());
            v.display("Commands: status, map, engage,inventory,inspect, quit");
            v.display("Enter a command:");
            String command = input.nextLine();
            Monster m = new Monster("Monster", "A scary monster", 75, 10, 3,2,"R1");
            Monster m2 = new Monster("Monster2", "A scarier monster", 100, 15, 5,5,"R2");
            school.getRooms().get(0).getMonsters().add(m);
            school.getRooms().get(1).getMonsters().add(m2);
            Item s = new Item("IO1","Sword", "A sharp sword",100);
            Item s2 = new Item("IO2","Shield", "A sturdy shield",0);
            m.getInventory().add(s);
            m2.getInventory().add(s2);
            if(command.equals("quit")){
                isRunning = false;
            }
                else if(command.equals("status")){
                    v.displayStatus(A.getHealth(), A.getAttack(),A.getAttackBonus(), A.getDefense(),0,A.getCoins());
                }
                else if(command.equals("map")){
                    v.showMap();
                }
                else if(command.equals("help")){
                    v.navHelp();
                }
                else if(command.equals("inventory")){
                    v.display(A.getInventoryString());
                }
                else if(command.equals("inspect")){
                    v.display(A.inspectMonster());
                }
                else if(command.equals("engage")){
                    boolean engage = A.engageMonster();
                    if(engage){
                        v.display("You have engaged the monster! Prepare for battle!");
                        battle();
                    }
                    else{
                        v.display("There is no monster to engage in this room.");
                    }
                }
                else if(command.equals("move")) {
                    A.setCurrentRoom("R2");
                }
                else if(command.equals("back")){
                    A.setCurrentRoom("R1");
                }
                else if(command.startsWith("equip ")){
                    String itemName = command.substring(6);
                    boolean eq = A.equipWeapon(itemName);
                    if(eq){
                        v.display("You have equipped the " + itemName + "!");
                    }
                    else{
                        v.display("You don't have a " + itemName + " in your inventory.");
                    }
                }
                else{
                    v.display("Invalid command. Try again.");
                }
        }
    }
    public void battle(){
        combatEngine.resetEngine();
        while(!combatEngine.isBattleOver()){
            v.display("Turn: " + combatEngine.getTurns());
            v.monsterUI(A.getHealth(),"Monster", combatEngine.getMonsterHealth());
            String command = input.nextLine();
            if(command.equals("status")){
                v.displayStatus(A.getHealth(), A.getAttack(),A.getAttackBonus(), A.getDefense(),3,100);
                continue;
            }
            String result = combatEngine.action(command);
            v.display(result + "------------------------------");
        }
        if(!combatEngine.getMonsterAlive()){
            v.display("You won the battle!");
            v.display("You found a " + A.getInventory().get(A.getInventory().size() - 1).getItemName() + " on the monster!");
        }
        else if(!A.isAlive()){
            v.display("You were defeated...");
            isRunning = false;
        }
    }
    public void puzzle(){

    }
}
