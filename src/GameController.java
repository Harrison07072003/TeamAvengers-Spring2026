import java.util.Scanner;

public class GameController {
    //fields
    private Player A;
    private View v;
    private Scanner input;
    private boolean isRunning;
    //constructor
    GameController() {
        A = new Player("Player", 100, 5, 5, 10,"R12");
        v = new View();
        input = new Scanner(System.in);
        isRunning = true;
    }
    //methods
    public void run(){
        while(isRunning) {
            v.display("Enter a command:");
            v.display("Commands: status, map, refresh, quit");
            String command = input.nextLine();
            Monster m = new Monster("Monster", "A scary monster", 50, 10, 3,2,"Room1");
            Item s = new Item("IO1","Sword", "A sharp sword");
            m.getInventory().add(s);
            if(command.equals("quit")){
                isRunning = false;
            }
                else if(command.equals("status")){
                    v.displayStatus(A.getHealth(), A.getAttack(), A.getDefense(),0,A.getCoins());
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
                    v.display(A.inspectMonster(m));
                }
                else if(command.equals("engage")){
                    battle(m);
                }
                else{
                    v.display("Invalid command. Try again.");
                }
        }
    }
    public void battle(Monster m){
        int turns = 1;
        boolean defending = false;
        while(A.isAlive() && m.isAlive()){
            if(turns % 3 == 0)
                defending = true;
            else
                defending = false;
            v.display("Turn: " + turns);
            v.monsterUI(A.getHealth(),"Monster",m.getHealth());
            String command = input.nextLine();
            if(command.equals("status")){
                v.displayStatus(A.getHealth(), A.getAttack(), A.getDefense(),3,100);
                turns--;
            }
            else if(command.equals("attack")){
               if(defending){
                     v.display("The monster is defending! Your attack is less effective.");
                     A.attack(m,defending);
                }
               else{
                 A.attack(m,defending);
                 m.attack(A,false);
               }
            }
            else if(command.equals("heavy attack")){
                if(defending){
                    v.display("The monster is defending! Your attack is less effective.");
                    boolean result = A.heavyAttack(m,defending);
                    if(result){
                        v.display("Your heavy attack was successful!");
                    }
                    else{
                        v.display("Your heavy attack missed!");
                    }
                }
                else{
                    boolean result = A.heavyAttack(m,defending);
                    if(result){
                        v.display("Your heavy attack was successful!");
                    }
                    else{
                        v.display("Your heavy attack missed!");
                    }
                    m.attack(A,false);
                }
            }
            else if(command.equals("defend")){
                if (defending) {
                    v.display("Both you and the monster are defending! No damage dealt.");
                }
                else
                    m.attack(A, true);
            }
            else if(command.equals("retreat")){
                A.retreat();
                break;
            }
            else {
                v.display("Invalid");
                turns--;
                }
            turns++;
        }
        if(!m.isAlive()) {
            v.display("The monster has been defeated! You found a " + m.dropItem().getItemName() + " and collected " + m.dropCoins() + " coins.");
        }
        else if(!A.isAlive()){
            v.display("You have been defeated. Game over.");
            isRunning = false;
        }
        else
            v.display("You retreated from battle.");
    }
    public void puzzle(){

    }
}
