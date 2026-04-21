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
    private final ArrayList<String> inventory;
    private int coins;

    public Puzzle(String puzzleId, String puzzleName, String question, String solution,
                  String roomId, String successMessage, String failureMessage,
                  ArrayList<String> inventory, int coins) {
        this.puzzleId = puzzleId;
        this.puzzleName = puzzleName;
        this.question = question;
        this.solution = solution;
        this.roomId = roomId;
        this.successMessage = successMessage;
        this.failureMessage = failureMessage;
        this.solved = false;
        this.inventory = (inventory == null) ? new ArrayList<>() : new ArrayList<>(inventory);
        this.coins = coins;
    }

    public String accessPuzzle() {
        return question;
    }

    public boolean checkSolution(String answer) {
        if (solved || answer == null) {
            return false;
        }

        String cleanedAnswer = normalize(answer);
        String cleanedSolution = normalize(solution);

        if (cleanedAnswer.equals(cleanedSolution)) {
            solved = true;
            return true;
        }

        return false;
    }

    private String normalize(String text) {
        return text.trim().toLowerCase().replaceAll("\\s+", " ");
    }

    public ArrayList<String> dropItems() {
        ArrayList<String> droppedItems = new ArrayList<>(inventory);
        inventory.clear();
        return droppedItems;
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

    public String getQuestion() {
        return question;
    }

    public String getSolution() {
        return solution;
    }

    public String getRoomId() {
        return roomId;
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

    public ArrayList<String> getInventory() {
        return new ArrayList<>(inventory);
    }

    public int getCoins() {
        return coins;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }
}