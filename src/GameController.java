import java.util.Scanner;

public class GameController {
    //fields
    private final View v;
    private final Scanner input;
    private boolean isRunning;
    private GameEngine engine;
    //constructor
    GameController(View v) {
        this.v = v;
        this.input = new Scanner(System.in);
        this.isRunning = true;
        this.engine = new GameEngine();
    }
    //methods
    //main navigation loop
    public void run(){
        engine.test();
        while(isRunning) {
            v.display(engine.getRoomName());
            v.display("Commands Available: Status, Inventory, Map, Inspect, Equip, Quit, Help");
            v.display("Enter a command:");
            String command = input.nextLine();
            if(command.equals("help"))
                v.navHelp(engine.getPlayerState());
            else if(command.equals("quit")){
                isRunning = false;
            }
            else {
                String result = engine.navCommand(command);
                v.display(result);
                if (command.equals("inspect") && !(result.substring(0, result.length() - 1).startsWith("No Monsters detected"))) {
                    v.display("Do you want to engage or ignore monster? Type in 'engage' to fight" +
                            " or press any other key to ignore.");
                    String response = input.nextLine();
                    if (response.equals("engage")) {
                        v.display("You have engaged battle with the monster! Prepare for battle!");
                        battle();
                    } else {
                        v.display("You decided not to engage");
                    }

                }
            }
        }
        v.display("You have left the game");
    }
    //battle loop
    public void battle(){
        this.engine.resetCombat();
        //battle loop
        while(!engine.battleEnded()){
            v.display("Turn: " + engine.getTurn());
            v.display(engine.monsterUI());
            String command = input.nextLine();
            if(command.equals("help"))
                v.navHelp(engine.getPlayerState());
            else {
                String result = this.engine.battleCommand(command);
                v.display(result + "------------------------------");
            }
        }
        //after battle
        if(!engine.monsterAlive()){
            v.display("You won the battle!");
            v.display("You found a " + engine.getPlayer().getInventory().get(engine.getPlayer().getInventory().size() - 1).getItemName() + " on " + engine.getPlayer().getMonsterName() + "! You also found " + engine.getPlayer().getMonster().getCoins() +" coins");
        }
        else if(!engine.playerAlive()){
            v.display("You were defeated...");
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
            v.display(line);
        }
        v.display("");
        v.display("Welcome to GGC Plague! Choose an option:");
        boolean menuActive = true;

        while (menuActive) {
            v.display("[1] Start New Game");
            v.display("[2] Load Game (not implemented)");
            v.display("[3] Quit");
            v.display("Enter choice (1-3) or type start/load/quit:");
            String choice = input.nextLine();
            if (choice == null) choice = "";
            choice = choice.trim().toLowerCase();

            switch (choice) {
                case "1":
                case "start":
                case "new":
                    v.display("Starting new game...\n");
                    // enter the main game loop
                    run();
                    menuActive = false; // if run returns, break out
                    break;
                case "2":
                case "load":
                    v.display("Load game selected. (Feature not implemented yet.)");
                    v.display("Press Enter to return to the main menu...");
                    input.nextLine();
                    break;
                case "3":
                case "quit":
                case "q":
                    // Instead of terminating the JVM here, cleanly stop the controller loop
                    v.display("Goodbye!");
                    // signal run() (if running) to stop and return control to caller
                    isRunning = false;
                    menuActive = false;
                    return; // return from startGame and let the caller decide lifecycle
                default:
                    v.display("Invalid option. Please enter 1, 2, or 3 (or start/load/quit). ");
            }
        }
    }
}
