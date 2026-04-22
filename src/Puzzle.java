import java.util.ArrayList;

public class Puzzle {
    //fields
    private final String puzzleId;
    private final String puzzleName;
    private final String question;
    private final String solution;
    private final String successMessage;
    private final String failureMessage;
    private final ArrayList<Item> inventory;
    private int coins;
    private boolean solved;

    //constructor
    public Puzzle(String puzzleId, String puzzleName, String question, String solution,
                  String successMessage, String failureMessage, int coins) {
        this.puzzleId = puzzleId;
        this.puzzleName = puzzleName;
        this.question = question;
        this.solution = solution;
        this.successMessage = successMessage;
        this.failureMessage = failureMessage;
        this.inventory = new ArrayList<>();
        this.coins = coins;
        this.solved = false;
    }
    // getters
    public String getPuzzleId() {
        return puzzleId;

    }
    public String getPuzzleName() {
        return puzzleName;
    }

    public String getQuestion(){
        return question;
    }

    public String getSolution(){
        return solution;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public String getFailureMessage() {
        return failureMessage;
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public int getCoins() {
        return coins;
    }

    public boolean getSolved() {
        return solved;
    }

    //setter
    public void setSolved(boolean solved) {
        this.solved = solved;
    }

    //solved
    //methods
    public ArrayList<Item> dropItems() {
        ArrayList <Item> items = new ArrayList<>();
        if(this.solved) {
            for(int i = 0; i < inventory.size(); i++) {
                items.add(inventory.get(i));
            }
        }
        return items;
    }


    public boolean checkSolution(String answer) {
        if (answer == null || solved) {
            return false;
        }

        if (answer.trim().equalsIgnoreCase(solution)) {
            return true;
        }

        return false;
    }

    public boolean isSolved() {
        return solved;
    }

}