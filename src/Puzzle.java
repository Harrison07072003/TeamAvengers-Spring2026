import java.util.ArrayList;

public class Puzzle {
    private final String puzzleId;
    private final String puzzleName;
    private final String question;
    private final String solution;
    private final String successMessage;
    private final String failureMessage;
    private final ArrayList<Item> inventory;
    private int coins;
    private boolean solved;

    public Puzzle(String puzzleId, String puzzleName, String question, String solution,
                  String successMessage, String failureMessage,
                  ArrayList<Item> rewards, int coins) {
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

    public String accessPuzzle() {
        return question;
    }

    public boolean checkSolution(String answer) {
        if (answer == null || solved) {
            return false;
        }

        if (answer.trim().equalsIgnoreCase(solution.trim())) {
            solved = true;
            return true;
        }

        return false;
    }

    public ArrayList<Item> dropRewards() {
        ArrayList<Item> droppedRewards = new ArrayList<>(inventory);
        inventory.clear();
        return droppedRewards;
    }

    public int dropCoins() {
        int droppedCoins = coins;
        coins = 0;
        return droppedCoins;
    }

    public String getPuzzleId() {
        return puzzleId;
    }

    public String getPuzzleName() {
        return puzzleName;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public String getFailureMessage() {
        return failureMessage;
    }

    public boolean isSolved() {
        return solved;
    }
}