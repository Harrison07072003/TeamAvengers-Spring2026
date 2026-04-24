
import java.util.ArrayList;
public class Player extends Character {
    //fields
    private String previousRoom;
    private String currentRoom;
    private final RoomMap Map;
    private Weapon equippedWeapon;
    private final Weapon standardWeapon = new Weapon("I0", "Fists", "Weapon", "Your own two fists, not very effective but better than nothing.", 0, "", "", 0);
    private int vials;
    private int currentState; //lets the game know what state the player is in 1=navigation,2=battle,3=puzzle,4=finished
    private Item pickedUp; //need for item methods.- j
    private int capacity;


    //constructor
    public Player(String id, int maxHP, int attack, int defense, int coins, String roomID, RoomMap map) {
        super(id, maxHP, attack, defense, coins);
        this.currentRoom = roomID;
        this.Map = map;
        this.equippedWeapon = standardWeapon;
        this.currentState = 1;
        this.vials = 0;
        this.previousRoom = roomID;
        this.pickedUp = null;
        this.capacity = 5;
    }

    //getters and setters
    public int getDamage(Monster enemy, boolean heavyDamage) {
        double damage;
        if (heavyDamage && enemy.isDefending())
            damage = (((this.getAttack() + this.equippedWeapon.getAttackBonus() * 1.3) - enemy.getDefense()));
        else if (heavyDamage)
            damage = (this.getAttack() + this.equippedWeapon.getAttackBonus()) * 1.3;
        else if (enemy.isDefending())
            damage = ((this.getAttack() + this.equippedWeapon.getAttackBonus()) * 0.6 - enemy.getDefense());
        else
            damage = this.getAttack() + this.equippedWeapon.getAttackBonus() - enemy.getDefense();
        if (damage < 1)
            return 1;
        else
            return (int) damage;
    }

    public String getRoomName() {
        return this.getCurrentRoom(currentRoom).getRoomName();
    }

    public String getMonsterName() {
        return this.getMonster().getMonsterName();
    }

    public Monster getMonster() {
        return this.getCurrentRoom(this.getRoomID()).getMonsters().get(0);
    }

    public int getCurrentState() {
        return this.currentState;
    }

    public int getAttackBonus() {
        return this.equippedWeapon.getAttackBonus();
    }

    public String getBuilding() {
        return this.getCurrentRoom(currentRoom).getBuilding();
    }

    public String getEquippedWeaponName() {
        return this.equippedWeapon.getItemName();
    }

    public int getVials() {
        return this.vials;
    }

    public void setVials(int vials) {
        this.vials = vials;
    }

    public Room getCurrentRoom(String roomID) {
        return this.Map.getRoom(roomID);
    }

    public String getRoomID() {
        return this.currentRoom;
    }

    public Item getPickedUp() {
        return this.pickedUp;
    }

    public void setPickedUp(Item item) {
        this.pickedUp = item;
    }

    public void setState(int state) {
        this.currentState = state;
    }

    public void setCurrentRoom(String roomID) {
        this.currentRoom = roomID;
    }

    public String getPreviousRoom() {
        return this.previousRoom;
    }

    public void setPreviousRoom(String previousRoom) {
        this.previousRoom = previousRoom;
    }
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }



    //reset
    public void resetPlayer() {
        this.setCurrentRoom("R1");
        this.setPreviousRoom("R1");
        this.pickedUp = null;
        this.capacity = 5;
        this.setAlive(true);
        this.setCurrentHP(this.getMaxHP());
        this.setVials(0);
        this.setStandardWeapon();
        this.getInventory().clear();
    }

    private void setStandardWeapon() {
        this.equippedWeapon = standardWeapon;
    }

    //methods
    //monster methods
    public void attack(Monster monster) {
        if (monster.isAlive()) {
            double damage = this.getAttack() + this.equippedWeapon.getAttackBonus() - monster.getDefense();
            if (monster.isDefending()) {
                damage = damage * (0.6);
            }
            if (damage <= 0) {
                damage = 1;
            }
            monster.setCurrentHP(monster.getCurrentHP() - (int) damage);
            if (monster.getCurrentHP() <= 0) {
                monster.setAlive(false);
                if (!monster.getInventory().isEmpty()) {
                    Item dropped = monster.getInventory().get(0);
                    this.getInventory().add(dropped);
                    this.addVial(dropped);
                }
                this.collectCoins(monster.getCoins());
            }
        }
    }

    public boolean heavyAttack(Monster monster) {
        if (monster.isAlive()) {
            double heavyDamage = ((this.getAttack() + this.equippedWeapon.getAttackBonus()) * 1.3);
            if (monster.isDefending()) {
                heavyDamage -= monster.getDefense();
            }
            if (heavyDamage <= 0) {
                heavyDamage = 1;
            }
            int chance = (int) (Math.random() * 100);
            if (chance > 40) {
                monster.setCurrentHP(monster.getCurrentHP() - (int) heavyDamage);
                if (monster.getCurrentHP() <= 0) {
                    monster.setAlive(false);
                    if (!monster.getInventory().isEmpty()) {
                        Item dropped = monster.getInventory().get(0);
                        this.getInventory().add(dropped);
                        this.addVial(dropped);
                    }
                    this.collectCoins(monster.getCoins());
                }
                return true;
            }
        }
        return false;
    }

    public void collectCoins(int coins) {
        this.setCoins(this.getCoins() + coins);
    }

    public String inspectMonster() {
        if (this.getCurrentRoom(currentRoom).hasMonsters()) {
            Monster monster = this.getMonster();
            if (monster.isAlive()) {
                return monster.getMonsterName() + ": " + monster.getMonsterDescription() + "\nHP: " + monster.getCurrentHP() + "/" + monster.getMaxHP() + "\nAttack: "
                        + monster.getAttack() + "\nDefense: " + monster.getDefense();
            }
        }
        return "No Monsters detected";
    }

    public void retreat() {
        this.enterRoom(this.previousRoom);
    }

    public boolean engageMonster() {
        return this.getCurrentRoom(currentRoom).getMonsters().get(0).isAlive();
    }


    //item methods
    //command: PickUp Item (on the srs, ik its sorta irrelevant, but they wanted it)
    public String pickUp(String itemName) {
        if (itemName.isBlank()) {
            return "Please enter an item to pick up.";
        }
        Room room = this.getCurrentRoom(currentRoom);
        ArrayList<String> items = new ArrayList<>();
        for (int i = 0; i < room.getInventory().size(); i++) {
            items.add(room.getInventory().get(i).getItemName());
        }
        if (items.contains(itemName)) {
            Item item = room.getInventory().get(items.indexOf(itemName));
            room.removeItem(item);
            setPickedUp(item);
            return "You have picked up " + itemName;
        }
        return "There is no item with that name in this room.";
    }

    //command: Store item - srs-> depends on "Pickup" command.
    public String storeItem() {
        if (this.pickedUp == null) {
            return "You have not picked up an item to store.";
        }
        if (this.getInventory().size() >= this.capacity) {// *FIX ME* inventory size limit yadaya..
            return "Your inventory is full. Please drop an item before storing a new one.";
        }
        this.getInventory().add(this.pickedUp);
        String itemName = this.pickedUp.getItemName();
        this.addVial(pickedUp);
        setPickedUp(null);
        return "You have stored " + itemName + " in your inventory.";
    }

    //Command: Drop item
    public String leave(String itemName) {
            if (itemName.isBlank()) {
                return "Please enter an item to drop.";
            }
            if(pickedUp == null){
                return "You have not picked up an item to drop.";
            }
            if (pickedUp.getItemName().equalsIgnoreCase(itemName)) {
                Item item;
                if (pickedUp.getItemName().equalsIgnoreCase(itemName)) {
                    item = pickedUp;
                    setPickedUp(null);
                } else {
                    return "Item entered not found. Please enter an item you've picked up or have in your inventory.";
                }
                this.getCurrentRoom(currentRoom).addItem(item);
                return "You have dropped " + item.getItemName() + " in the room.";
            }
        return "Item entered not found. Please enter an item you've picked up or have in your inventory.";
    }
    
    //Command: Use Item - "for key item only"-analysis doc...
    public boolean useItem(String itemName){
        if(itemName.isBlank()){
            return false;
        }
        Item item = getItem(itemName);
        if(item == null){
            return false;
        }
        if(item.getItemName().equalsIgnoreCase("Office Key")){
            if(currentRoom.equals("R1") || currentRoom.equals("R20") || currentRoom.equals("R16")){
                this.getCurrentRoom("R17").setLocked(false);
                return true;
            }
        }
        return false;
    }


    //Command: Combine Items - for cure vials, only in r16 chem lab (and flashlight?)



    public String dropItem(String item) {
        for (int i = 0; i < this.getInventory().size(); i++) {
            if (this.getInventory().get(i).getItemName().equalsIgnoreCase(item)) {
                Item dropped = this.getInventory().get(i);
                this.getCurrentRoom(currentRoom).addItem(dropped);
                this.getInventory().remove(dropped);
                this.removeVial(dropped);
                return "You have dropped " + dropped.getItemName() + " in the room.";
            }
        }
        return null;
    }
    public String combineItems(){
        if(this.getInventory().contains(getItem("Flashlight")) && this.getInventory().contains(getItem("Batteries"))){
            this.getInventory().remove(getItem("Flashlight"));
            this.getInventory().remove(getItem("Batteries"));
            Item poweredFlashlight = new Tool("I9", "Powered Flashlight", "Tool", "A flashlight with batteries, can be used to explore dark rooms.", 0, "", "", 0);
            this.getInventory().add(poweredFlashlight);
            return "You have combined the Flashlight and Batteries to create a Powered Flashlight!";
        }
        else if(this.getInventory().contains(getItem("Cure Vial 1")) && this.getInventory().contains(getItem("Cure Vial 2")) &&
                this.getInventory().contains(getItem("Cure Vial 3")) && this.getInventory().contains(getItem("Cure Vial 4")) &&
                this.getInventory().contains(getItem("Cure Vial 5")) && this.currentRoom.equals("R16")){
            this.getInventory().remove(getItem("Cure Vial 1"));
            this.getInventory().remove(getItem("Cure Vial 2"));
            this.getInventory().remove(getItem("Cure Vial 3"));
            this.getInventory().remove(getItem("Cure Vial 4"));
            this.getInventory().remove(getItem("Cure Vial 5"));
            Item Cure = new QuestItem("A13","Cure","QuestItem","Cure for the plague",0,"","",0);
            this.getInventory().add(Cure);
            return "You have combined the vials and created the Cure for the plague";

        }
        return "You don't have the necessary items to combine.";
    }

    public boolean combineFlashlightMessage(){
        return this.getInventory().contains(getItem("Flashlight")) && this.getInventory().contains(getItem("Batteries"));
    }
    public boolean combineCureMessage(){
        return this.getInventory().contains(getItem("Cure Vial 1")) && this.getInventory().contains(getItem("Cure Vial 2")) &&
                this.getInventory().contains(getItem("Cure Vial 3")) && this.getInventory().contains(getItem("Cure Vial 4")) &&
                this.getInventory().contains(getItem("Cure Vial 5"));
    }
    public void addVial(Item item){
        if(item.getItemName().startsWith("Cure Vial"))
            this.vials++;
    }
    public void removeVial(Item item) {
        if (item.getItemName().startsWith("Cure Vial"))
            this.vials--;
    }

    public String checkWeapon(){
        if(this.equippedWeapon.getItemName().equals("Fists"))
            return "There is no weapon equipped.";
        return this.equippedWeapon.toString();
    }

    public boolean equipWeapon(String weapon) {
        for (int i = 0; i < this.getInventory().size(); i++) {
            if (this.getInventory().get(i).getItemName().equalsIgnoreCase(weapon)) {
                this.equippedWeapon = (Weapon) this.getInventory().get(i);
                return true;
            }
        }
        return false;
    }

    public String examineItem(String item) {
        for (int i = 0; i < this.getInventory().size(); i++) {
            if (item.equalsIgnoreCase(this.getInventory().get(i).getItemName()))
                return this.getInventory().get(i).toString();
        }
        return "You don't have that item";
    }


    //Command: Enter Room (move player)
    //room methods
    public String enterRoom(String input) {
        if (input == null || input.trim().isEmpty()) {
            return "There is no entrance in that direction. Please enter a valid Direction or Room ID.";
        }
        Room current = this.getCurrentRoom(this.currentRoom);
        String userInput = input.trim().toUpperCase();
        String nextRoom = current.getExit(userInput);
        if (nextRoom == null) {
            return "There is no entrance in that direction. Please enter a valid Direction or Room ID.";
        }
        if(this.getCurrentRoom(nextRoom).isLocked()){
            return "This room is locked. You must use a key item to enter this room.";
        }
        this.previousRoom = this.currentRoom;
        this.currentRoom = nextRoom;
        return "You have entered " + this.getCurrentRoom(this.currentRoom).getRoomName() + ".";
    }

    public String exploreRoom() {
        Room current = this.getCurrentRoom(currentRoom);
        if (current.requiresValidFlashlight() && !this.getInventory().contains(getItem("Powered Flashlight"))) {
            return "Functioning flashlight with batteries is needed to explore this room.";
        } else {
            String result = "You are currently in the " + current.getRoomName() + ": " + current.getRoomDescription();
            if (current.hasItem()) {
                if (current.getInventory().size() == 1) {
                    result += "\nThere is an item in this room: " + current.getInventory().get(0).getItemName();
                }
                if (current.getInventory().size() > 1) {
                    result += "\nThere are items in this room: ";
                    for (Item item : current.getInventory()) {
                        result += "\n- " + item.getItemName();
                    }
                }
            }
            if (current.hasVendingMachine()) {
                result += "\nThere is a vending machine in this room.";
            }
            if (current.hasMonsters()) {
                if (current.getMonster().size() == 1) {
                    result += "\nThere is a monster in this room: " + current.getMonster().get(0).getMonsterName();
                }
                if (current.getMonster().size() > 1) {
                    result += "\nThere are monsters in this room: ";
                    for (Monster monster : current.getMonster()) {
                        result += "\n- " + monster.getMonsterName();
                    }
                }
            }

            if (current.hasPuzzle()) {
                result += "\nThere is a puzzle in this room";
            }

            result += "\nExits: " + current.getExits();
            return result;
        }
    }

    public Item getItem(String itemName) {
        for (Item item : this.getInventory()) {
            if (item.getItemName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;
    }

    //puzzle methods
    public boolean escapeGame() {
        if (currentRoom.equals("R20") && this.getInventory().contains(getItem("Cure")))
            return true;
        return false;
    }

    public Weapon getEquippedWeapon() {
        return this.equippedWeapon;
    }

    public void setEquippedWeapon(Weapon equippedWeapon) {
        this.equippedWeapon = equippedWeapon;
    }

    public String explorePuzzle() {
        String result = "";
        if (this.getCurrentRoom(currentRoom).hasPuzzle()) {
            if(this.getCurrentRoom(currentRoom).getPuzzle().isSolved()){
                return "You have already solved this puzzle.";
            }
            Puzzle puzzle = this.getCurrentRoom(currentRoom).getPuzzle();
            result += puzzle.getPuzzleName() + ": " + puzzle.getQuestion();
        } else {
            return "no puzzle detected";
        }
        return result;
    }

    public String solvePuzzle(String answer) {
        Puzzle puzzle = this.getCurrentRoom(currentRoom).getPuzzle();
        String result = "";
        if (puzzle.checkSolution(answer)) {
            result += puzzle.getSuccessMessage();
            if (puzzle.getCoins() > 0) {
                this.setCoins(this.getCoins() + puzzle.getCoins());
                result += puzzle.getCoins() + " coins earned.";
            }
                if (puzzle.getRewards().size() > 0) {
                    for (int i = 0; i < puzzle.getRewards().size(); i++) {
                        this.getInventory().add(puzzle.getRewards().get(i));
                        this.addVial(puzzle.getRewards().get(i));
                        result += puzzle.getRewards().get(i).getItemName() + " dropped.";
                    }
                }
                return result;
            }
            return puzzle.getFailureMessage();
        }
}
