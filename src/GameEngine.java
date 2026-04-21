public class GameEngine {
    //fields
    private final Player A;
    private final CombatEngine combatEngine;
    private final RoomMap school;
    private final GameResult result;
    //constructor
    public GameEngine(){
        this.school = new RoomMap();
        this.A = new Player("Player", 100, 18, 5, 10,"R1",school);
        this.combatEngine = new CombatEngine(A);
        this.result = new GameResult();
    }
    //methods
    //loads test data
    public void test(){
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
        Item s = new Item("IO1","Sword", "A sharp sword",5);
        Item s2 = new Item("IO2","Shield", "A sturdy shield",100);
        Item s3 = new Item("IO3","Revolver","Crimson case",20);
        m.getInventory().add(s);
        m2.getInventory().add(s2);
        m3.getInventory().add(s3);
    }

    //navigation
    public String navCommand(String command){
        result.resetMessage();
        result.resetStatus();
        if(command.equals("status")) {
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
        else if(command.equals("inventory"))
            this.result.setMessage(A.getInventoryString()+"\n");
        else if(command.equals("inspect"))
            this.result.setMessage(A.inspectMonster()+"\n");
        else if(command.startsWith("equip ")) {
            String itemName = command.substring(6);
            boolean success = A.equipWeapon(itemName);
            if(success)
                this.result.setMessage("You have equipped the " + itemName + "!\n");
            else
                this.result.setMessage("You don't have a " + itemName + " in your inventory.\n");
        }
        else if(command.startsWith("examine "))
            result.setMessage(A.examineItem(command.substring(8)) + "\n");
        else if(command.startsWith("enter "))
            result.setMessage(A.enterRoom(command.substring(6)) + "\n");
        else if(command.equals("explore"))
            result.setMessage(A.exploreRoom() + "\n");
        else
            this.result.setMessage("Invalid Command\n");
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
    public boolean playerAlive(){
        return A.isAlive();
    }
    public boolean monsterAlive(){
        return combatEngine.getMonsterAlive();
    }
    public int getTurn(){
        return this.combatEngine.getTurns();
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
    public String getPlayerBuilding(){
        return this.getPlayer().getBuilding();
    }
    public String getPlayerHealth(){
        return A.getHealth();
    }
    public String getMonsterName(){
        return A.getMonsterName();
    }
    public String getMonsterHealth(){
        return combatEngine.getMonsterHealth();
    }
}
