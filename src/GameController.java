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
        school.putVendingMachines();
        school.loadItems();
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
            else if(command.equals("examine")){
                if(A.getCurrentRoomData().getInventory().isEmpty())
                    v.display("No items in this room.");
                else
                    v.display("Item: " + A.getCurrentRoomData().getInventory().get(0).getItemName());
            }
            else if(command.equals("monsters")){
                if(A.getCurrentRoomData().getMonster().isEmpty())
                    v.display("No monsters in this room.");
                else
                    v.display("Monster items: " + A.getCurrentRoomData().getMonster().getFirst().getInventory().getFirst().getItemName());
            }
            else if(command.equals("puzzles")){
                if(A.getCurrentRoomData().getPuzzle() == null)
                    v.display("No puzzle in this room.");
                else
                    v.display("Puzzle item: " + A.getCurrentRoomData().getPuzzle().getRewards().get(0).getItemName());
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
            else if(command.equals("vending")){
                if(A.getCurrentRoomData().getVendingMachine() == null)
                    v.display("No vending machine in this room.");
                else
                    v.display("Vending Machine items: " + A.getCurrentRoomData().getVendingMachine().getItemList());
            }
            else if(command.equals("v")){
                if(A.getCurrentRoomData().getVendingMachine() == null)
                    v.display("No vending machine in this room.");
                else
                    v.display("Vending Machine items: " + A.getCurrentRoomData().getVendingMachine().getLocation());
            }
            else if(command.equals("quit"))
                isRunning = false;
            else
                v.display("Invalid command. Try again.");
        }
    }
}