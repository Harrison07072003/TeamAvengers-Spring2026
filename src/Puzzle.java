import java.util.ArrayList;

public class Puzzle {
    private String puzzleId;
    private String puzzleName;
    private String question;
    private String solution;
    private String roomId;
    private String hint;
    private int attemptsRemaining;
    private boolean isSolved;
    private ArrayList<String> rewards;

    public Puzzle(String puzzleId, String puzzleName, String question, String solution,
                  String roomId, String hint, int attemptsRemaining,
                  boolean isSolved, ArrayList<String> rewards) {
        this.puzzleId = puzzleId;
        this.puzzleName = puzzleName;
        this.question = question;
        this.solution = solution;
        this.roomId = roomId;
        this.hint = hint;
        this.attemptsRemaining = attemptsRemaining;
        this.isSolved = isSolved;
        this.rewards = (rewards != null) ? new ArrayList<>(rewards) : new ArrayList<>();
    }

    public String accessPuzzle() {
        return question;
    }

    public boolean checkSolution(String answer) {
        if (isSolved || attemptsRemaining <= 0 || answer == null) {
            return false;
        }

        if (solution.equalsIgnoreCase(answer.trim())) {
            setSolved();
            unlockProgress();
            return true;
        }

        decrementAttempts();
        return false;
    }

    public String giveHint() {
        return hint;
    }

    public void decrementAttempts() {
        if (attemptsRemaining > 0) {
            attemptsRemaining--;
        }
    }

    public void unlockProgress() {
        // Placeholder for future puzzle progression logic
    }

    public void setSolved() {
        isSolved = true;
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

    public String getHint() {
        return hint;
    }

    public int getAttemptsRemaining() {
        return attemptsRemaining;
    }

    public boolean isSolved() {
        return isSolved;
    }

    public ArrayList<String> getRewards() {
        return new ArrayList<>(rewards);
    }
}