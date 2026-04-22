public class Puzzle {
    private final String puzzleId;
    private final String puzzleName;
    private final String question;
    private final String solution;
    private final String successMessage;
    private final String failureMessage;
    private final String reward;
    private final int coins;
    private boolean solved;

    public Puzzle(String puzzleId, String puzzleName, String question, String solution,
                  String successMessage, String failureMessage, String reward, int coins) {
        this.puzzleId = puzzleId;
        this.puzzleName = puzzleName;
        this.question = question;
        this.solution = solution;
        this.successMessage = successMessage;
        this.failureMessage = failureMessage;
        this.reward = reward;
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

    public String getReward() {
        return reward;
    }

    public int getCoins() {
        return coins;
    }

    public boolean isSolved() {
        return solved;
    }
}