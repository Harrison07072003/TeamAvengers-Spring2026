import java.util.ArrayList;

public class Puzzle {
    private final String puzzleId;
    private final String puzzleName;
    private final String question;
    private final String solution;
    private final String roomId;
    private final String successMessage;
    private final String failureMessage;
    private boolean solved;
    private final ArrayList<Item> rewards;
    private int coins;

    public Puzzle(String puzzleId, String puzzleName, String question, String solution,
                  String roomId, String successMessage, String failureMessage,
                  ArrayList<Item> rewards, int coins) {
        this.puzzleId = puzzleId;
        this.puzzleName = puzzleName;
        this.question = question;
        this.solution = solution;
        this.roomId = roomId;
        this.successMessage = successMessage;
        this.failureMessage = failureMessage;
        this.solved = false;
        this.rewards = (rewards == null) ? new ArrayList<>() : new ArrayList<>(rewards);
        this.coins = coins;
    }

    public String accessPuzzle() {
        return question;
    }

    public boolean checkSolution(String answer) {
        if (solved || answer == null) {
            return false;
        }
        if (answer.trim().equalsIgnoreCase(solution.trim())) {
            solved = true;
            return true;
        }
        return false;
    }

    public ArrayList<Item> dropRewards() {
        ArrayList<Item> droppedRewards = new ArrayList<>(rewards);
        rewards.clear();
        return droppedRewards;
    }

    public int dropCoins() {
        int droppedCoins = coins;
        coins = 0;
        return droppedCoins;
    }

    public String getPuzzleId() { return puzzleId; }
    public String getPuzzleName() { return puzzleName; }
    public String getQuestion() { return question; }
    public String getSolution() { return solution; }
    public String getRoomId() { return roomId; }
    public String getSuccessMessage() { return successMessage; }
    public String getFailureMessage() { return failureMessage; }
    public boolean isSolved() { return solved; }
    public ArrayList<Item> getRewards() { return new ArrayList<>(rewards); }
    public int getCoins() { return coins; }
    public void setSolved(boolean solved) { this.solved = solved; }
}
