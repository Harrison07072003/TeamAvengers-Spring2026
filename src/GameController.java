import java.util.Scanner;

public class GameController {
    private final GameEngine engine;
    private final View view;
    private final Scanner input;
    private boolean running;

    public GameController() {
        this.engine = new GameEngine();
        this.view = new View();
        this.input = new Scanner(System.in);
        this.running = true;
    }

    public void startGame() {
        view.showTitle();
        view.showHelp();

        while (running) {
            view.showPrompt();
            String command = input.nextLine().trim();

            if (command.equalsIgnoreCase("quit")) {
                running = false;
                view.display("You have left the puzzle demo.");
                break;
            }

            if (command.equalsIgnoreCase("solve puzzle")) {
                view.display("Enter your answer:");
                String answer = input.nextLine().trim();
                String result = engine.solvePuzzle(answer);
                view.display(result);
                continue;
            }

            String result = engine.handleCommand(command);
            view.display(result);
        }
    }
}