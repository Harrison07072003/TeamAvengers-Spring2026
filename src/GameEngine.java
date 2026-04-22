public class GameEngine {
    private final Puzzle puzzle;

    public GameEngine() {
        this.puzzle = new Puzzle(
                "P1",
                "Puzzle 1",
                "What is the liquid form of frozen H2O?",
                "Water",
                "Nice job. You solved the puzzle.",
                "That is not correct. Try again.",
                "Batteries and Office Key",
                2
        );
    }

    public String handleCommand(String command) {
        if (command == null || command.isBlank()) {
            return "Invalid command.";
        }

        String cleaned = command.trim().toLowerCase();

        switch (cleaned) {
            case "help":
                return """
                       Commands:
                       explore puzzle
                       solve puzzle
                       ignore puzzle
                       status
                       quit
                       """;

            case "explore puzzle":
                if (puzzle.isSolved()) {
                    return "This puzzle was already solved.";
                }
                return "Puzzle: " + puzzle.getPuzzleName() + "\nQuestion: " + puzzle.accessPuzzle();

            case "ignore puzzle":
                return "You leave the puzzle for later.";

            case "status":
                return "Solved: " + puzzle.isSolved()
                        + "\nReward: " + puzzle.getReward()
                        + "\nCoins: " + puzzle.getCoins();

            default:
                return "Invalid command. Type help to see commands.";
        }
    }

    public String solvePuzzle(String answer) {
        if (puzzle.isSolved()) {
            return "This puzzle was already solved.";
        }

        boolean solved = puzzle.checkSolution(answer);

        if (!solved) {
            return puzzle.getFailureMessage();
        }

        return puzzle.getSuccessMessage()
                + "\nReward earned: " + puzzle.getReward()
                + "\nCoins earned: " + puzzle.getCoins();
    }
}