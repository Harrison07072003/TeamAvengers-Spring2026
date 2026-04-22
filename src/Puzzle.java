import java.util.ArrayList;

public class Puzzle {
    private String puzzleId;
    private String puzzleName;
    private String question;
    private String solution;
    private String roomId;
    private String successMessage;
    private String failureMessage;
    private boolean solved;
    private ArrayList<Item> rewards;
    private int coins;

    public Puzzle(String puzzleId, String puzzleName, String question, String solution, String roomId, String
                  successMessage, String failureMessage, int coins) {
        this.puzzleId = puzzleId;
        this.puzzleName = puzzleName;
        this.question = question;
        this.solution = solution;
        this.roomId = roomId;
        this.solved = false;
        this.coins = coins;
        this.successMessage = successMessage;
        this.failureMessage = failureMessage;
        this.rewards = new ArrayList<>();
    }

    public String getPuzzleId() {
        return puzzleId;
    }

    public String getPuzzleName() {
        return puzzleName;
    }

    public String getQuestion() {
        return question;
    }

    public String getSolution() {
        return solution;
    }

    public String getRoomId() {
        return roomId;
    }


    public boolean isSolved() {
        return solved;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }

    public ArrayList<Item> getRewards() {
        return this.rewards;
    }
    public String toFileString() {
        return this.puzzleId + "," + this.puzzleName + "," + this.question + "," + this.solution + "," + this.roomId + "," + this.successMessage + "," + this.failureMessage + "," + this.coins;
    }
    public String getRewardsFileString() {
        return this.rewards.get(0).toFileString();
    }
}