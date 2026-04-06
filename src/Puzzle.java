public class Puzzle {
    private String puzzleId;
    private String puzzleName;
    private String question;
    private String solution;
    private String roomId;
    private String hint;
    private int attemptsRemaining;
    private boolean isSolved;
    private Inventory puzzleInventory;

    // Creates a puzzle object with all of its starting values
    public Puzzle(String puzzleId, String puzzleName, String question, String solution,
                  String roomId, String hint, int attemptsRemaining, Inventory puzzleInventory) {
        this.puzzleId = puzzleId;
        this.puzzleName = puzzleName;
        this.question = question;
        this.solution = solution;
        this.roomId = roomId;
        this.hint = hint;
        this.attemptsRemaining = attemptsRemaining;
        this.isSolved = false;
        this.puzzleInventory = puzzleInventory;
    }

    // Returns the puzzle question so the player can access it
    public String accessPuzzle() {
        return question;
    }

    // Checks if the player's answer is correct
    public boolean checkSolution(String answer) {
        // Do not allow more attempts if the puzzle is already solved
        // or if there are no attempts left
        if (isSolved || attemptsRemaining <= 0) {
            return false;
        }

        // If the answer matches the solution, mark puzzle as solved
        if (answer.equalsIgnoreCase(solution)) {
            setSolved();
            unlockProgress();
            return true;
        }

        // If wrong, reduce remaining attempts
        decrementAttempts();
        return false;
    }

    // Returns the hint for the puzzle
    public String giveHint() {
        return hint;
    }

    // Lowers the amount of attempts left by 1
    public void decrementAttempts() {
        if (attemptsRemaining > 0) {
            attemptsRemaining--;
        }
    }

    // Handles progress after solving the puzzle
    public void unlockProgress() {
        // Business logic can be expanded here later
        // Example: unlock a room, reveal item, open door
    }

    // Marks the puzzle as solved
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

    public Inventory getPuzzleInventory() {
        return puzzleInventory;
    }
}