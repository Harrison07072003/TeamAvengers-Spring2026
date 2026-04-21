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
    public void run(){
        engine.test();
        //navigation loop
        while(isRunning) {
            view.navUI(engine.getRoomName());
            String command = input.nextLine();
            if(command.equals("help"))
                view.navHelp(engine.getPlayerState());
            else if(command.equals("map"))
                view.showMap(engine.getPlayerBuilding());
            else if(command.equals("quit")){
                isRunning = false;
            }
            else {
                String result = engine.navCommand(command);
                view.display(result);
                if (command.equals("inspect") && !(result.substring(0, result.length() - 1).startsWith("No Monsters detected"))) {
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
            }
        }
        view.display("You have left the game");
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
                view.navHelp(engine.getPlayerState());
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
            isRunning = false;
        }
        engine.getPlayer().setState(1);
    }
    //puzzle loop
    public void puzzle(){

    }
    //starts game
    public void startGame(){
        // New simple, bold console title screen (GGC PLAGUE)
        String[] title = new String[]{
            "====================================================",
            "  ____   ____   ____    ____    _      _   ____  _____",
            " / ___| / ___| / ___|  / ___|  / \\    | | / ___|| ____|",
            "| |  _ | |  _ | |  _  | |  _  / _ \\   | || |  _ |  _|  ",
            "| |_| || |_| || |_| | | |_| |/ ___ \\  | || |_| || |___ ",
            " \\____| \\____| \\____|  \\____/_/   \\_\\_| \\____||_____",
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
            view.display("[2] Load Game (not implemented)");
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
                    // enter the main game loop
                    run();
                    menuActive = false; // if run returns, break out
                    break;
                case "2":
                case "load":
                    view.display("Load game selected. (Feature not implemented yet.)");
                    view.display("Press Enter to return to the main menu...");
                    input.nextLine();
                    break;
                case "3":
                case "quit":
                case "q":
                    // Instead of terminating the JVM here, cleanly stop the controller loop
                    view.display("Goodbye!");
                    // signal run() (if running) to stop and return control to caller
                    isRunning = false;
                    menuActive = false;
                    return; // return from startGame and let the caller decide lifecycle
                default:
                    view.display("Invalid option. Please enter 1, 2, or 3 (or start/load/quit). ");
            }
        }
    }
}
