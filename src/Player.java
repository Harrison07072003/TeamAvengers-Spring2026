import java.util.ArrayList;

public class Player extends Character {
    private final ArrayList<Item> inventory;
    private int coins;
    private String currentRoom;

    public Player(String roomId){
        super("player", 100, 10, 5);
        this.currentRoom = roomId;
        this.inventory = new ArrayList<>();
        this.coins = 0;
    }

    public String explorePuzzle() {
        String result = "";
        if (this.currentRoom.hasPuzzle()) {
            Puzzle puzzle = this.currentRoom.getPuzzle();
            result += puzzle.getPuzzleName() + ": " + puzzle.getQuestion() + " Would you like to solve or ignore?";
            return "no puzzle detected";
        }
        return result;
    }


    public String solvePuzzle(String answer){
            Puzzle puzzle = this.currentRoom.getPuzzle();
            String result = "";
            if (puzzle.checkSolution(answer)){
                result += puzzle.getSuccessMessage();
                if(puzzle.getCoins()>0) {
                    coins +=puzzle.getCoins();
                    result+= puzzle.getCoins() + " coins earned.";
                }
                if(puzzle.getInventory().size()>0) {
                    for (int i=0; i<puzzle.getInventory().size(); i++){
                        result+= puzzle.getInventory().get(i).getItemName() + " dropped.";
                    }
                }
                return result;
            }
            return puzzle.getFailureMessage();
        }
    }
    public Room getCurrentRoom(){

    }

