import java.util.Scanner;

public class PuzzleView {
    private Scanner scanner;

    public PuzzleView() {
        scanner = new Scanner(System.in);
    }

    public void puzzleUI() {
        System.out.println("\n********************************");
        System.out.println("[Explore puzzles.txt] - Read the riddle");
        System.out.println("[Solve]          - Input an answer");
        System.out.println("[Ignore puzzles.txt]  - Leave for later");
        System.out.println("********************************");
    }

    public void puzzleUI(Room room) {
        if (room != null && room.getPuzzle() != null) {
            System.out.println("\npuzzles.txt detected in room " + room.getRoomId());
        }
        puzzleUI();
    }

    public void displayPuzzle(Puzzle puzzle) {
        System.out.println("\n==============================");
        System.out.println("PUZZLE: " + puzzle.getPuzzleName());
        System.out.println("==============================");
        System.out.println("Question: " + puzzle.getQuestion());
        System.out.println("==============================");
    }

    public String getPlayerAnswer() {
        System.out.print("Enter your answer: ");
        return scanner.nextLine();
    }

    public String getInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public void showRoom(Room room) {
        if (room == null) {
            showMessage("No room selected.");
            return;
        }
        showMessage("You entered room: " + room.getRoomId());
    }

    public void showCorrectMessage() {
        System.out.println("Correct! You solved the puzzle.");
    }

    public void showIncorrectMessage(int attemptsRemaining) {
        System.out.println("Incorrect answer.");
        System.out.println("Attempts remaining: " + attemptsRemaining);
    }

    public void showPuzzleFailed() {
        System.out.println("You failed the puzzle. No attempts left.");
    }

    public void showReward(String reward) {
        System.out.println("Reward dropped: " + reward);
    }

    public void showMessage(String message) {
        System.out.println(message);
    }
}