import java.util.Scanner;

public class GameController {
    //fields
    private final Player A;
    private final View v;
    private final Scanner input;
    private boolean isRunning;
    private final CombatEngine combatEngine;
    private final RoomMap school;
    //constructor
    GameController(View v) {
        this.school = new RoomMap();
        this.A = new Player("Player", 100, 18, 5, 10,"R1",school);
        this.v = v;
        this.input = new Scanner(System.in);
        this.isRunning = true;
        this.combatEngine = new CombatEngine(A);
    }
    //methods
    public void run(){
        school.getRooms().add(new Room("R1","Dining Hall","Trays of food are scattered around, and some tables and chairs have been scattered.",new int[]{1,2,3,4},"Building E"));
        school.getRooms().add(new Room("R2","Library","A quiet library filled with books.",new int[]{0,5,6,7},"Library"));
        school.getRooms().add(new Room("R3","Chem Lab","A science lab that has chemicals to quell plague",new int[]{8,9,10,11},"Building H"));
        Monster m = new Monster("M1","Masked Man" ,"A scary monster", 75, 10, 3,2,"R1");
        Monster m2 = new Monster("M2","Porky" ,"A scarier monster", 100, 15, 5,5,"R2");
        Monster m3 = new Monster("M3","Takaya","Revolver wielding maniac",95,20,7,10,"R3");
        school.getRooms().get(0).getMonsters().add(m);
        school.getRooms().get(1).getMonsters().add(m2);
        school.getRooms().get(2).getMonsters().add(m3);
        Item s = new Item("IO1","Sword", "A sharp sword",5);
        Item s2 = new Item("IO2","Shield", "A sturdy shield",100);
        Item s3 = new Item("IO3","Revolver","Crimson case",20);
        m.getInventory().add(s);
        m2.getInventory().add(s2);
        m3.getInventory().add(s3);
        while(isRunning) {
            v.display(A.getRoomName());
            v.display("Commands Available: Status, Inventory, Map, Inspect, Equip, Quit, Help");
            v.display("Enter a command:");
            String command = input.nextLine();
            if(command.equals("quit")){
                isRunning = false;
            }
                else if(command.equals("status")){
                    v.displayStatus(A.getHealth(), A.getAttack(),A.getAttackBonus(), A.getDefense(),0,A.getCoins(),A.getEquippedWeaponName());
                }
                else if(command.equals("map")){
                    v.showMap(A.getBuilding());
                }
                else if(command.equals("help")){
                    v.navHelp(A.getCurrentState());
                }
                else if(command.equals("inventory")){
                    v.display(A.getInventoryString());
                }
                else if(command.equals("inspect")){
                    v.display(A.inspectMonster());
                    if(A.inspectMonster().equals("No Monsters detected"))
                        continue;
                    v.display("Do you want to engage or ignore monster? Type in 'engage' to fight" +
                            " or press any other key to ignore.");
                    command = input.nextLine();
                    if(command.equals("engage")){
                        boolean engage = A.engageMonster();
                        if(engage){
                            v.display("You have engaged battle with the monster! Prepare for battle!");
                            battle();
                        }
                        else{
                            v.display("There is no monster to engage in this room.");
                        }
                    }
                    else{
                        v.display("You decided not to engage");
                    }
                }
                else if(command.equals("move")) {
                    A.setCurrentRoom("R2");
                }
                else if(command.equals("back")){
                    A.setCurrentRoom("R1");
                }
                else if(command.equals("third")){
                    A.setCurrentRoom("R3");
                }
                else if(command.startsWith("equip ")){
                    String itemName = command.substring(6);
                    boolean eq = A.equipWeapon(itemName);
                    if(eq){
                        v.display("You have equipped the " + itemName + "!");
                    }
                    else{
                        v.display("You don't have a " + itemName + " in your inventory.");
                    }
                }
                else{
                    v.display("Invalid command. Try again.");
                }
                v.display("-----------------------------------");
        }
    }
    public void battle(){
        combatEngine.resetEngine();
        while(!combatEngine.isBattleOver()){
            v.display("Turn: " + combatEngine.getTurns());
            v.monsterUI(A.getHealth(),A.getMonsterName(), combatEngine.getMonsterHealth());
            String command = input.nextLine();
            if(command.equals("check weapon")){
                v.display(A.checkWeapon());
                continue;
            }
            if(command.equals("help")){
                v.navHelp(A.getCurrentState());
                continue;
            }
            String result = combatEngine.action(command);
            v.display(result + "------------------------------");
        }
        if(!combatEngine.getMonsterAlive()){
            v.display("You won the battle!");
            v.display("You found a " + A.getInventory().get(A.getInventory().size() - 1).getItemName() + " on " + A.getMonsterName() + "! You also found " + A.getMonster().getCoins() +" coins");
        }
        else if(!A.isAlive()){
            v.display("You were defeated...");
            isRunning = false;
        }
        A.setState(1);
    }
    public void puzzle(){

    }
    public void startGame(){
        // New simple, bold console title screen (GGC PLAGUE)
        String[] title = new String[]{
            "====================================================",
            "  ____   ____   ____    ____    _      _   ____  _____",
            " / ___| / ___| / ___|  / ___|  / \\    | | / ___|| ____|",
            "| |  _ | |  _ | |  _  | |  _  / _ \\   | || |  _ |  _|  ",
            "| |_| || |_| || |_| | | |_| |/ ___ \\  | || |_| || |___ ",
            " \\____| \\____| \\____|  \\____/_/   \\_\\_| \\____||_____|",
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
                    v.display("Goodbye!");
                    System.exit(0);
                    break;
                default:
                    v.display("Invalid option. Please enter 1, 2, or 3 (or start/load/quit). ");
            }
        }
    }
}
