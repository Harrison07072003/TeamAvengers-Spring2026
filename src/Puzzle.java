import java.util.ArrayList;

public class Puzzle {
    //fields
    private final String puzzleId;
    private final String puzzleName;
    private final String question;
    private final String solution;
    private final String successMessage;
    private final String failureMessage;
    private final ArrayList<Item> rewards;
    private  String roomId;
    private int coins;
    private boolean solved;

    //constructor
    public Puzzle(String puzzleId, String puzzleName, String question, String solution,String roomId,
                  String successMessage, String failureMessage, int coins) {
        this.puzzleId = puzzleId;
        this.puzzleName = puzzleName;
        this.question = question;
        this.solution = solution;
        this.roomId = roomId;
        this.successMessage = successMessage;
        this.failureMessage = failureMessage;
        this.rewards = new ArrayList<>();
        this.coins = coins;
        this.solved = false;
    }
    // getters
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

    public ArrayList<Item> getRewards() {
        return this.rewards;
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
    public boolean checkSolution(String answer) {
        if (answer == null || this.solved) {
            return false;
        }

        if (answer.trim().equalsIgnoreCase(this.solution)) {
            this.solved = true;
            return true;
        }

        return false;
    }

    public boolean isSolved() {
        return this.solved;
    }
    public String toFileString() {
        return this.puzzleId + ";" + this.puzzleName + ";" + this.question + ";" + this.solution + ";" + this.roomId + ";" + this.successMessage + ";" + this.failureMessage + ";" + this.coins + ";" + this.solved;
    }
    public String getRewardsFileString() {
        return this.rewards.get(0).toFileString();
    }

}