import java.util.ArrayList;

public class GameEngine {
    private final Puzzle puzzle;
    private final Player player;

    public GameEngine() {
        this.player = new Player();

        ArrayList<Item> rewards = new ArrayList<>();
        rewards.add(new Item("A2", "Batteries", "Powers a flashlight"));
        rewards.add(new Item("A12", "Office Key", "Opens a locked office"));

        this.puzzle = new Puzzle(
                "P1",
                "Puzzle 1",
                "What is the liquid form of frozen H2O?",
                "Water",
                "Nice job. You solved the puzzle.",
                "That is not correct. Try again.",
                rewards,
                2
        );
    }

    public GameResult puzzleCommand(String command) {
        if (command == null || command.isBlank()) {
            return new GameResult("Invalid command.", false);
        }

        String cleaned = command.trim();

        if (cleaned.equalsIgnoreCase("help")) {
            return new GameResult(
                    "Commands:\n" +
                            "explore puzzle\n" +
                            "solve puzzle <answer>\n" +
                            "ignore puzzle\n" +
                            "status\n" +
                            "quit",
                    false
            );
        }

        if (cleaned.equalsIgnoreCase("explore puzzle")) {
            return new GameResult(player.explorePuzzle(puzzle), false);
        }

        if (cleaned.equalsIgnoreCase("ignore puzzle")) {
            return new GameResult(player.ignorePuzzle(), true);
        }

        if (cleaned.equalsIgnoreCase("status")) {
            return new GameResult(
                    "Solved: " + puzzle.isSolved() +
                            "\nInventory: " + player.getInventoryString() +
                            "\nCoins: " + player.getCoins(),
                    false
            );
        }

        if (cleaned.toLowerCase().startsWith("solve puzzle ")) {
            boolean solved = player.solvePuzzle(puzzle, cleaned.substring(13).trim());

            if (!solved) {
                return new GameResult(puzzle.getFailureMessage(), false);
            }

            return new GameResult(
                    puzzle.getSuccessMessage() +
                            "\nInventory: " + player.getInventoryString() +
                            "\nCoins: " + player.getCoins(),
                    true
            );
        }

        return new GameResult("Invalid command. Type help to see commands.", false);
    }
}