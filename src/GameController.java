import java.util.Scanner;

public class GameController {
    //fields
    Player A;
    View v;
    Scanner input;
    boolean isRunning;
    RoomMap school;
    //constructor
    GameController() {
        v = new View();
        input = new Scanner(System.in);
        isRunning = true;
        school = new RoomMap("rooms.txt", "puzzles.txt", "monsters.txt", "items.txt");
        A = new Player("Player", 100, 10, 5,10,school);
    }
    //methods
    public void run(){
        school.generateRooms();
        school.spawnMonsters();
        school.loadPuzzles();
        while(isRunning){
            v.display("Current Room: " + A.getCurrentRoom());
            v.display("Enter a command (type 'quit' to exit): ");
            String command = input.nextLine();
            if(command.equals("move")){
                A.setCurrentRoom("R2");
            }
            else if(command.equals("back")){
                A.setCurrentRoom("R1");
            }
            else if(command.equals("explore")){
                v.display(A.getMap().getRoom(A.getCurrentRoom()).getDescription());
            }
            else if(command.equals("monsters")){
                v.display("Available monsters: " + A.getCurrentRoomData().getMonster().size());
            }
            else if(command.equals("inspect")){
                if(A.getCurrentRoomData().getMonster().isEmpty())
                    v.display("No monsters in this room.");
                else
                    v.display(A.getCurrentRoomData().getMonster().getFirst().getMonsterDescription());
            }
            else if(command.equals("puzzle")){
                if(A.getCurrentRoomData().getPuzzle() == null)
                    v.display("No puzzle in this room.");
                else
                    v.display(A.getCurrentRoomData().getPuzzle().getQuestion());
            }
            else if(command.equals("quit"))
                isRunning = false;
            else
                v.display("Invalid command. Try again.");
        }
    }
}