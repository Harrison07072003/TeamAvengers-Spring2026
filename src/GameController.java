import java.util.Scanner;

public class GameController {
    //fields
    private PlayerT A;
    private View v;
    private Scanner input;
    private boolean isRunning;
    //constructor
    GameController() {
        A = new PlayerT("Player", 100, 10, 5, new RoomT());
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
            if(command.equals("quit")){
                isRunning = false;
            }
                else if(command.equals("status")){
                    v.displayStatus(A.getHealth(), A.getAttack(), A.getDefense());
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
                else{
                    v.display("Invalid command. Try again.");
                }
        }
    }
}
