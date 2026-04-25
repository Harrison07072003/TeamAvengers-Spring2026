import java.util.Scanner;

public class GameController {
    //fields
    private final View view;
    private final Scanner input;
    private boolean isRunning;
    private final GameEngine engine;
    //constructor
    GameController() {
        this.view = new View();
        this.input = new Scanner(System.in);
        this.isRunning = true;
        this.engine = new GameEngine();
    }
    //methods
    //navigation state
    public void run(boolean savedgame){
        if(!savedgame)
            engine.resetGame();
        //navigation loop
        while(isRunning) {
            view.navUI(engine.getRoomName());
            String command = input.nextLine();
            if(command.equalsIgnoreCase("help")) {
                view.help(engine.getPlayerState());
            }
            else if(command.equalsIgnoreCase("navHelp")){
                view.navHelp();
            }
            else if(command.equalsIgnoreCase("map"))
                view.showMap(engine.getPlayerBuilding());
            else if(command.equalsIgnoreCase("restart")){
                while(true) {
                    view.display("Do you want to restart the game yes/no");
                    String response = input.nextLine();
                    if(response.equalsIgnoreCase("yes")) {
                        engine.resetGame();
                        break;
                    }
                    else if(response.equalsIgnoreCase("no")) {
                        view.display("You decided not to restart");
                        break;
                    }
                    view.display("I didn't quite get that");
                }

            }
            else if(command.equalsIgnoreCase("quit")){
                this.quitGame();
            }
            else {
                String result = engine.navCommand(command);
                view.display(result);
                if (command.equalsIgnoreCase("inspect") && !(result.substring(0, result.length() - 1).startsWith("No Monsters detected"))) {
                    view.display("Do you want to engage or ignore monster? Type in 'engage' to fight" +
                            " or press any other key to ignore.");
                    String response = input.nextLine();
                    if (response.equals("engage")) {
                        view.display("You have engaged battle with the monster! Prepare for battle!");
                        battle();
                    } else {
                        view.display("You decided not to engage");
                    }

                }
                if(command.equalsIgnoreCase("explore puzzle") && (result.substring(0, result.length() - 1).startsWith("Puzzle"))) {
                    view.display("Do you want to solve or ignore the puzzle?" +
                            " Type in 'solve' to solve or press any other key to ignore.");
                    String response = input.nextLine();
                    if(response.equals("solve"))
                        puzzle();
                    else{
                        view.display("You decided not to ignore the puzzle");
                    }
                }
                if(command.equalsIgnoreCase("escape") && result.startsWith("Congratulations! You have escaped the school and won the game!")){
                    isRunning = false;
                    view.showCredits();
                }
                if(command.startsWith("pickup") && result.startsWith("You have picked up ")){
                    while(true) {
                        view.display("Do you want to store this item- Enter store or drop");
                        String store = input.nextLine();
                        if (store.equalsIgnoreCase("store")) {
                            view.display(engine.getPlayer().storeItem());
                            break;
                        } else if (store.equalsIgnoreCase("drop")) {
                            view.display(engine.getPlayer().leave(engine.getPlayer().getPickedUp().getItemName()));
                            break;
                        }
                        view.display("I didn't quite get that");
                    }
                }
            }
        }
        view.display("Bye! Bye!");
    }
    //battle state
    public void battle(){
        this.engine.resetCombat();
        //battle loop
        while(!engine.battleEnded()){
            view.display("Turn: " + engine.getTurn());
            view.monsterUI(engine.getPlayerHealth(), engine.getMonsterName(), engine.getMonsterHealth());
            String command = input.nextLine();
            if(command.equals("help"))
                view.help(engine.getPlayerState());
            else {
                String result = this.engine.battleCommand(command);
                view.display(result + "------------------------------");
            }
        }
        //after battle
        if(!engine.monsterAlive()){
            view.display("You won the battle!");
            view.display("You found a " + engine.getPlayer().getMonster().getDropsString()
                    + " on " + engine.getPlayer().getMonsterName() + "! You also found " + engine.getPlayer().getMonster().getCoins() +" coins");
        }
        else if(!engine.playerAlive()){
            view.display("You were defeated...");
            if(engine.saveExists())
                engine.loadGame();
            else
                engine.resetGame();
        }
        engine.setPlayerState(2);
    }
    //puzzle state
    public void puzzle(){
        while(!engine.getPuzzleStatus()) {
            view.display("Put in answer");
            String reply = input.nextLine();
            view.display(engine.solvePuzzle(reply));
            if(engine.getPuzzleStatus())
                break;
            view.display("Do you want to try again. If no type in no otherwise any key will allow retry");
            String choice = input.nextLine();
            if(choice.equalsIgnoreCase("no"))
                break;
        }
    }
    //starts game
   public void startGame(){
        // ASCII title for "GGC PLAGUE"
        String[] title = new String[]{
            "====================================================",
            "  ____   ____    ____     ____   _      _   ____  _____ ",
            " / ___| / ___|  |  _ \\   / ___| / \\    | | / ___|| ____|",
            "| |  _ | |  _   | |_) | | |  _ / _ \\   | || |  _ |  _|  ",
            "| |_| || |_| |  |  __/  | |_| / ___ \\  | || |_| || |___ ",
            " \\____| \\____|  |_|      \\____/_/   \\_\\_| \\____||_____|",
            "",
            "                      GGC PLAGUE",
            "===================================================="
        };

        for (String line : title) {
            view.display(line);
        }
        view.display("");
        view.display("Welcome to GGC Plague! Choose an option:");
        boolean menuActive = true;

        while (menuActive) {
            view.display("[1] Start New Game");
            view.display("[2] Load Game");
            view.display("[3] Quit");
            view.display("Enter choice (1-3) or type start/load/quit:");
            String choice = input.nextLine();
            if (choice == null) choice = "";
            choice = choice.trim().toLowerCase();

            switch (choice) {
                case "1":
                case "start":
                case "new":
                    view.display("Starting new game...\n");
                    // start a fresh game; run(false) -> run will call resetGame()
                    run(false);
                    menuActive = false; // if run returns, break out
                    break;
                case "2":
                case "load":
                    // attempt to load via engine
                    String loadResult;
                    try {
                        loadResult = engine.loadGame();
                    } catch (Exception e) {
                        loadResult = "Error while attempting to load game: " + e.getMessage();
                    }
                    view.display(loadResult);
                    // If loaded successfully, enter the main loop preserving loaded state
                    if (loadResult != null && loadResult.toLowerCase().contains("loaded successfully")) {
                        run(true); // don't reset game inside run
                        menuActive = false;
                    } else {
                        view.display("Returning to title menu...");
                    }
                    break;
                case "3":
                case "quit":
                case "q":
                    view.display("Goodbye!");
                    isRunning = false;
                    menuActive = false;
                    return;
                default:
                    view.display("Invalid option. Please enter 1, 2, or 3 (or start/load/quit). ");
            }
        }
    }

    //quits game
    public void quitGame() {
        view.display("Do you want to quit the game? (yes/no)");
        String response = input.nextLine();
        if (response.equalsIgnoreCase("yes")) {
            view.display("Do you want to save the game before quitting? (yes/no)");
            String saveResponse = input.nextLine();
            if (saveResponse.equalsIgnoreCase("yes")) {
                engine.saveGame();
                view.display("Game saved.");
            } else {
                view.display("Game not saved.");
            }
            isRunning = false;
            view.display("Exiting game.");
        } else {
            view.display("Continuing game.");
        }

    }
}