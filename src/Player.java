import java.util.ArrayList;

public class Player extends Character {
    private final ArrayList<Item> inventory;
    private int coins;

    public Player() {
        super("PLAYER", 100, 10, 5);
        this.inventory = new ArrayList<>();
        this.coins = 0;
    }

    public String explorePuzzle(Puzzle puzzle) {
        if (puzzle == null) {
            return "No puzzle found.";
        }
        if (puzzle.isSolved()) {
            return "This puzzle was already solved.";
        }
        return "Puzzle: " + puzzle.getPuzzleName() + "\nQuestion: " + puzzle.accessPuzzle();
    }

    public String ignorePuzzle() {
        return "You leave the puzzle for later.";
    }

    public boolean solvePuzzle(Puzzle puzzle, String answer) {
        if (puzzle == null) {
            return false;
        }
        boolean solved = puzzle.checkSolution(answer);
        if (solved) {
            acceptRewards(puzzle.dropRewards());
            addCoins(puzzle.dropCoins());
        }
        return solved;
    }

    public void acceptRewards(ArrayList<Item> rewards) {
        if (rewards != null) {
            inventory.addAll(rewards);
        }
    }

    public void addCoins(int amount) {
        if (amount > 0) {
            coins += amount;
        }
    }

    public int getCoins() {
        return coins;
    }

    public String getInventoryString() {
        if (inventory.isEmpty()) {
            return "No rewards collected yet.";
        }

        StringBuilder items = new StringBuilder();
        for (int i = 0; i < inventory.size(); i++) {
            items.append(inventory.get(i).getItemName());
            if (i < inventory.size() - 1) {
                items.append(", ");
            }
        }
        return items.toString();
    }
}