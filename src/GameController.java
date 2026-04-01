import java.util.Scanner;

public class GameController {
    //fields
    Character A;
    View v;
    Scanner input;
    //constructor
    GameController() {
        A = new Character("Player", 100, 10, 5);
        v = new View();
        input = new Scanner(System.in);
    }
    //methods
    public void run(){
        while(true){
            String command = input.nextLine();
            if(command.equals("quit"))
                break;
            else
                v.display(A.getCharaterID());
        }
    }
}
