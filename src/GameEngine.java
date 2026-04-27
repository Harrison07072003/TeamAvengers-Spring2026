public class GameEngine {
    //fields
    private final Player A;
    private final CombatEngine combatEngine;
    private final RoomMap school;
    private final GameResult result;
    //constructor
    public GameEngine(){
        this.school = new RoomMap("Rooms.txt","puzzles.txt","Monsters.txt","Item.txt","vendingmachines.txt");
        this.A = new Player("Player", 100, 16, 7, 15,"R1",school);
        this.combatEngine = new CombatEngine(A);
        this.result = new GameResult();
    }
    //methods
    //navigation
    public String navCommand(String command){
        result.resetMessage();
        result.resetStatus();
        if(command.equalsIgnoreCase("status")) {
            String health = A.getHealth();
            int attack = A.getAttack();
            int bonus = A.getAttackBonus();
            int defense = A.getDefense();
            int vials = A.getVials();
            int coins = A.getCoins();
            String weapon = A.getEquippedWeaponName();
            result.setMessage("HP: " + health + "  \n" +
                    "Attack: " + attack + " | ATK Bonus: (+" + bonus +") | Defense: " + defense + "\n" +
                    "Vials: " + vials + "/5 | Coins: " + coins + "\nCurrent Weapon: " + weapon +"\n");
        }
        else if(command.equals("test")){
            A.getInventory().add(new Tool("104,","Powered Flashlight","Tool","A flashlight that can be used to explore dark rooms",0,"R1","P1",0));
            A.getInventory().add(new QuestItem("A13","Cure","QuestItem","Cure for the plague",0,"","",0));
            A.setCurrentRoom("R14");
        }
        else if(command.equalsIgnoreCase("inventory"))
            this.result.setMessage(A.getInventoryString()+"\n");
        else if(command.equalsIgnoreCase("inspect"))
            this.result.setMessage(A.inspectMonster()+"\n");
        else if(command.equalsIgnoreCase("unequip")){
            this.result.setMessage(A.unequipWeapon()+"\n");
        }
        else if(command.startsWith("equip ")) {
            String itemName = command.substring(6);
            boolean success = A.equipWeapon(itemName);
            if(success)
                this.result.setMessage("You have equipped the " + itemName + "!\n");
            else
                this.result.setMessage("You don't have a " + itemName + " in your inventory.\n");
            if(itemName.isBlank()){
                this.result.setMessage("Please enter a name of a weapon.\n");
            }
        }
        else if(command.startsWith("examine "))
            result.setMessage(A.examineItem(command.substring(8)) + "\n");
        else if(command.startsWith("enter "))
            result.setMessage(A.enterRoom(command.substring(6)) + "\n");
        else if(command.equalsIgnoreCase("explore room"))
            result.setMessage(A.exploreRoom() + "\n");
        else if(command.equalsIgnoreCase("explore puzzle"))
            result.setMessage(A.explorePuzzle() + "\n");
        else if(command.startsWith("pickup "))
            result.setMessage(A.pickUp(command.substring(7)) + "\n");
        else if(command.equals("collect coins")){
            result.setMessage(A.collectCoinsinRoom() + "\n");
        }
        else if(command.equalsIgnoreCase("buy food"))
            result.setMessage(A.buyFood() + "\n");
        else if(command.startsWith("consume "))
            result.setMessage(A.consumeFood(command.substring(8)) + "\n");
        else if(command.startsWith("drop "))
            result.setMessage(A.dropItem(command.substring(5)) + "\n");
        else if(command.startsWith("use ")) {
            if(A.useItem(command.substring(4))){
                result.setMessage("You used the " + command.substring(4) + "!\n");
            }
            else{
                result.setMessage("You don't have a " + command.substring(4) + " in your inventory.\n");
            }
        }
       else if(command.equalsIgnoreCase("combine"))
            result.setMessage(A.combineItems() + "\n");
        else if(command.equalsIgnoreCase("save game"))
            result.setMessage(this.saveGame());
        else if(command.equalsIgnoreCase("load game"))
            result.setMessage(this.loadGame());
        else if(command.equalsIgnoreCase("checkpoint")){
            result.setMessage(school.checkpoint(A));
        }
        else if(command.equalsIgnoreCase("escape")){
            if(A.escapeGame()){
                result.setMessage("Congratulations! You have escaped the school and won the game!\n");
            }
            else{
                result.setMessage("You cannot escape yet. You need to collect all 5 vials to escape.\n"
                 + "You have collected " + A.getVials() + "/5 vials ,after you obtain 5/5 vials, combine them in Chem Lab 2 to form a cure in order to escape\n");
            }
            if(A.containsCure() && !A.escapeGame())
                result.setMessage("You have the cure. Now head to the Parking Lot to escape and win the game.\n");
        }
        else
            this.result.setMessage("Invalid Command\n");
        if(A.combineFlashlightMessage())
            this.result.setMessage(this.result.getMessage() + "You can combine the batteries and the Flashlight\n");
        if(A.combineCureMessage())
            this.result.setMessage(this.result.getMessage() + "You can combine the vials in Chemistry Lab 2\n");
        this.result.setMessage(this.result.getMessage() + "-----------------------------------");
        return result.getMessage();

    }
    //battle
    public String battleCommand(String command){
        return this.combatEngine.action(command);
    }
    public void resetCombat(){
        this.combatEngine.resetEngine();
    }
    public boolean battleEnded(){
        return this.combatEngine.isBattleOver();
    }
    public boolean retreated(){
        return this.combatEngine.getRetreated();
    }
    public String enter(String direction){
        return this.getPlayer().enterRoom(direction);
    }
    public boolean playerAlive(){
        return A.isAlive();
    }
    public boolean monsterAlive(){
        return combatEngine.getMonsterAlive();
    }
    public int getTurn(){
        return this.combatEngine.getTurns();
    }
    public String getMonsterName(){
        return A.getMonsterName();
    }
    public String getMonsterHealth(){
        return combatEngine.getMonsterHealth();
    }
    //puzzle
    public String solvePuzzle(String reply){
        return this.A.solvePuzzle(reply);
    }
    public boolean getPuzzleStatus(){
        return A.getCurrentRoom(A.getRoomID()).getPuzzle().getSolved();
    }
    //getters and setters
    public Player getPlayer(){
        return this.A;
    }
    public String getRoomName(){
        result.setMessage(A.getRoomName());
        return result.getMessage();
    }

    public int getPlayerState(){
        return this.getPlayer().getCurrentState();
    }
    public void setPlayerState(int state){
        this.A.setState(state);
    }
    public String getPlayerBuilding(){
        return this.getPlayer().getBuilding();
    }
    public String getPlayerHealth(){
        return A.getHealth();
    }
    //game manager methods
    public String resetGame() {
        A.resetPlayer();
        school.generateRooms();
        school.spawnMonsters();
        school.loadPuzzles();
        school.putVendingMachines();
        school.loadItems();
        return "You have just woken up and see that you are in GGC in a Chem Lab";
    }
    public String saveGame(){
        return school.saveGame(A);
    }
    public String loadGame(){
        return school.loadGame(A);
    }
    public boolean saveExists(){
        return school.saveExists();
    }
    public void destoryMonster(){
        this.getPlayer().getCurrentRoom(A.getRoomID()).removeMonster(A.getMonster());
    }
    //copy detroy monster for puzze
    public void destroyPuzzle(){
        this.getPlayer().getCurrentRoom(A.getRoomID()).removePuzzle();
    }

    //loads test data
    /*public void test(){
        school.getRooms().add(new Room("R1","Dining Hall","Trays of food are scattered around, and some tables and chairs have been scattered.","Building E",true));
        school.getRooms().add(new Room("R2","Library","A quiet library filled with books.","Library",true));
        school.getRooms().add(new Room("R3","Chem Lab","A science lab that has chemicals to quell plague","Building H",true));
        school.getRooms().add(new Room("R4","Parking Lot","The parking lot, where the player can escape from the school.","Parking Lot",false));
        school.getRooms().get(0).getExits().put("east","R2");
        school.getRooms().get(1).getExits().put("west","R1");
        school.getRooms().get(1).getExits().put("east","R3");
        school.getRooms().get(2).getExits().put("west","R2");
        school.getRooms().get(2).getExits().put("east","R4");
        school.getRooms().get(3).getExits().put("west","R3");
        Monster m = new Monster("M1","Masked Man" ,"A scary monster", 75, 10, 3,2,"R1");
        Monster m2 = new Monster("M2","Porky" ,"A scarier monster", 100, 15, 5,5,"R2");
        Monster m3 = new Monster("M3","Takaya","Revolver wielding maniac",95,20,7,10,"R3");
        Monster m4 = new Monster("M4","Final Boss","The final boss of the game",150,25,10,20,"R4");
        school.getRooms().get(0).getMonsters().add(m);
        school.getRooms().get(1).getMonsters().add(m2);
        school.getRooms().get(2).getMonsters().add(m3);
        school.getRooms().get(3).getMonsters().add(m4);
        Item s = new Weapon("IO1","Sword","Weapon" ,"A sharp sword",5,"R1","M0",0);
        Item s2 = new Weapon("IO2","Shield","Weapon" ,"A sturdy shield",100,"R2","M1",0);
        Item s3 = new Weapon("IO3","Revolver","Weapon","Crimson case",20,"R3","M2",0);
        m.getInventory().add(s);
        m2.getInventory().add(s2);
        m3.getInventory().add(s3);
        A.getInventory().add(new Tool("104,","Powered Flashlight","Tool","A flashlight that can be used to explore dark rooms",0,"R1","P1",0));
    }

     */
}
