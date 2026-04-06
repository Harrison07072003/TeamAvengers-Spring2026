public class Puzzle {

    // Stores the unique ID of the puzzle
    private String puzzleId;

    // Stores the name/title of the puzzle
    private String name;

    // Stores the puzzle question or riddle shown to the player
    private String question;

    // Stores the correct answer to the puzzle
    private String answer;

    // Stores the hint that can help the player solve the puzzle
    private String hint;

    // Stores the reward the player gets for solving the puzzle
    private String reward;

    // Keeps track of whether the puzzle has already been solved
    private boolean solved;

    // Counts how many times the player has attempted the puzzle
    private int attempts;

    // Constructor used to create a Puzzle object with its starting values
    public Puzzle(String puzzleId, String name, String question, String answer,
                  String hint, String reward) {

        // Assign the parameter values to the object's fields
        this.puzzleId = puzzleId;
        this.name = name;
        this.question = question;
        this.answer = answer;
        this.hint = hint;
        this.reward = reward;

        // When a puzzle is first created, it has not been solved yet
        this.solved = false;

        // When a puzzle is first created, the player has made 0 attempts
        this.attempts = 0;
    }

    // Checks whether the player's answer matches the correct answer
    public boolean checkAnswer(String playerAnswer) {

        // Increase the attempt counter every time the player tries to answer
        attempts++;

        // Compare the player's answer to the correct answer, ignoring uppercase/lowercase differences
        if (playerAnswer.equalsIgnoreCase(answer)) {

            // If the answer is correct, mark the puzzle as solved
            solved = true;

            // Return true to show the answer was correct
            return true;
        }

        // Return false if the answer was incorrect
        return false;
    }

    // Returns the puzzle question so it can be displayed to the player
    public String getQuestion() {
        return question;
    }

    // Returns whether the puzzle has already been solved
    public boolean isSolved() {
        return solved;
    }

    // Returns the hint for the puzzle
    public String getHint() {
        return hint;
    }

    // Returns the reward for solving the puzzle
    public String getReward() {
        return reward;
    }

    // Returns the number of attempts the player has made
    public int getAttempts() {
        return attempts;
    }
}