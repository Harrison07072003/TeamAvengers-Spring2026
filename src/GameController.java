import java.util.Scanner;

public class GameController {
    private final View view;
    private final Scanner input;
    private boolean isRunning;
    private final GameEngine engine;

    GameController() {
        this.view = new View();
        this.input = new Scanner(System.in);
        this.isRunning = true;
        this.engine = new GameEngine();
    }

    public void run() {
        while (isRunning) {
            view.navUI(engine.getRoomName());
            String command = input.nextLine().trim();
            String lower = command.toLowerCase();

            if (lower.equals("help")) {
                view.navHelp(engine.getPlayerState());
            } else if (lower.equals("map")) {
                view.showMap(engine.getPlayerBuilding());
            } else if (lower.equals("quit")) {
                isRunning = false;
            } else if (lower.equals("puzzle")) {
                if (engine.hasActivePuzzle()) {
                    puzzle();
                } else {
                    view.display("No active puzzle is in this room.");
                }
            } else {
                String result = engine.navCommand(command);
                view.display(result);

                if (engine.hasActivePuzzle() && (lower.equals("inspect") || lower.equals("explore"))) {
                    view.showPuzzlePrompt();
                }

                if (engine.hasActiveMonster() && lower.equals("inspect")) {
                    view.display("A monster is here. Type 'engage' to fight or press Enter to ignore.");
                    String response = input.nextLine().trim();
                    if (response.equalsIgnoreCase("engage")) {
                        battle();
                    } else {
                        view.display("You decided not to engage.");
                    }
                }
            }
        }
        view.display("You have left the game.");
    }

    public void battle() {
        engine.resetCombat();
        while (!engine.battleEnded()) {
            view.display("Turn: " + engine.getTurn());
            view.monsterUI(engine.getPlayerHealth(), engine.getMonsterName(), engine.getMonsterHealth());
            String command = input.nextLine().trim();
            if (command.equalsIgnoreCase("help")) {
                view.navHelp(2);
            } else {
                view.display(engine.battleCommand(command));
            }
        }

        if (!engine.monsterAlive()) {
            view.display("You won the battle.");
        } else if (!engine.playerAlive()) {
            view.display("You were defeated.");
            isRunning = false;
        }
        engine.getPlayer().setState(1);
    }

    public void puzzle() {
        while (isRunning && engine.hasActivePuzzle()) {
            view.puzzleUI();
            String action = input.nextLine().trim().toLowerCase();

            switch (action) {
                case "explore":
                case "explore puzzle":
                    view.display(engine.explorePuzzle());
                    break;
                case "solve":
                case "solve puzzle":
                    view.display("Enter your answer:");
                    String answer = input.nextLine().trim();
                    view.display(engine.solvePuzzle(answer));
                    if (!engine.hasActivePuzzle()) {
                        return;
                    }
                    break;
                case "ignore":
                case "ignore puzzle":
                    view.display(engine.ignorePuzzle());
                    return;
                case "help":
                    view.navHelp(3);
                    break;
                default:
                    view.display("Invalid puzzle command.");
            }
        }
    }

    public void startGame() {
        String[] title = new String[]{
                "============================================",
                "               GGC PLAGUE",
                "    Escape the campus. Solve puzzles.",
                "============================================"
        };

        for (String line : title) {
            view.display(line);
        }
        view.display("");
        view.display("Welcome to GGC Plague. Choose an option:");

        boolean menuActive = true;
        while (menuActive) {
            view.display("[1] Start New Game");
            view.display("[2] Credits");
            view.display("[3] Quit");
            view.display("Enter choice:");
            String choice = input.nextLine().trim().toLowerCase();

            switch (choice) {
                case "1":
                case "start":
                case "new":
                    run();
                    menuActive = false;
                    break;
                case "2":
                case "credits":
                    view.showCredits();
                    break;
                case "3":
                case "quit":
                    view.display("Goodbye.");
                    menuActive = false;
                    isRunning = false;
                    break;
                default:
                    view.display("Invalid option.");
            }
        }
    }
}
