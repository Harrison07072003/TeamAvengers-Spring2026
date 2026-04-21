import java.util.ArrayList;

public class Puzzle {
    private final String puzzleId;
    private final String puzzleName;
    private final String question;
    private final String solution;
    private final String roomId;
    private final String successMessage;
    private final String failureMessage;
    private final ArrayList<Item> rewards;
    private final int coinReward;
    private int attemptsRemaining;
    private boolean solved;

    public Puzzle(String puzzleId, String puzzleName, String question, String solution, String roomId,
                  String successMessage, String failureMessage, ArrayList<Item> rewards, int coinReward) {
        this(puzzleId, puzzleName, question, solution, roomId, successMessage, failureMessage, rewards, coinReward, 3);
    }

    public Puzzle(String puzzleId, String puzzleName, String question, String solution, String roomId,
                  String successMessage, String failureMessage, ArrayList<Item> rewards, int coinReward, int attemptsRemaining) {
        this.puzzleId = puzzleId;
        this.puzzleName = puzzleName;
        this.question = question;
        this.solution = solution;
        this.roomId = roomId;
        this.successMessage = successMessage;
        this.failureMessage = failureMessage;
        this.rewards = rewards == null ? new ArrayList<>() : new ArrayList<>(rewards);
        this.coinReward = Math.max(0, coinReward);
        this.attemptsRemaining = Math.max(1, attemptsRemaining);
        this.solved = false;
    }

    public String accessPuzzle() {
        return question;
    }

    public boolean checkSolution(String answer) {
        if (solved || answer == null || attemptsRemaining <= 0) {
            return false;
        }

        if (answer.trim().equalsIgnoreCase(solution.trim())) {
            solved = true;
            return true;
        }

        attemptsRemaining--;
        return false;
    }

    public ArrayList<Item> dropRewards() {
        ArrayList<Item> droppedRewards = new ArrayList<>(rewards);
        rewards.clear();
        return droppedRewards;
    }

    public int dropCoins() {
        return coinReward;
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

    public int getAttemptsRemaining() {
        return attemptsRemaining;
    }

    public ArrayList<Item> getRewards() {
        return new ArrayList<>(rewards);
    }
}
