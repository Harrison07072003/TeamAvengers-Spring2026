import java.util.ArrayList;
public class Player extends Character {
    //fields
    private String previousRoom;
    private String currentRoom;
    private RoomMap Map;
    private Weapon equippedWeapon;
    private final Weapon standardWeapon = new Weapon("W0", "Fists", "Weapon", "Your bare hands. Not very effective, but better than nothing.", 0, "R0", "P1", 0);
    private int vials;
    private int currentState; //lets the game know what state the player is in 1=navigation,2=battle,3=puzzle,4=finished
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
    }


    //methods
    // getters and setters for my fields

    public String getPreviousRoom() {
        return previousRoom;
    }
    public void setPreviousRoom(String previousRoom) {
        this.previousRoom = previousRoom;
    }

    public RoomMap getMap() {
        return Map;
    }

    public void setMap(RoomMap map) {
        this.Map = map;
    }

    public int getVialCount() {
        return this.vials;
    }

    public void setVialCount(int vialCount) {
        this.vials = vialCount;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

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
                if (!monster.getInventory().isEmpty())
                    this.getInventory().add(monster.dropItem(""));
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
                    if (!monster.getInventory().isEmpty())
                        this.getInventory().add(monster.dropItem(""));
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

    public Room getCurrentRoom(String roomID) {
        return this.Map.getRoom(roomID);
    }

    public String getRoomID() {
        return this.currentRoom;
    }

    public void setState(int state) {
        this.currentState = state;
    }

    public void setCurrentRoom(String roomID) {
        this.currentRoom = roomID;
    }

    //item methods
    public Item dropItem(String item) {
        return null;
    }

    public String checkWeapon() {
        if (this.equippedWeapon.getItemName().equals("Fists"))
            return "There is no weapon equipped.";
        return this.equippedWeapon.toString();
    }

    public boolean equipWeapon(String weapon) {
        for (int i = 0; i < this.getInventory().size(); i++) {
            if (this.getInventory().get(i).getItemName().equalsIgnoreCase(weapon)) {
                if (this.getInventory().get(i).getCategory().equalsIgnoreCase("Weapon")) {
                    this.equippedWeapon = (Weapon) this.getInventory().get(i);
                    return true;
                }
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

    //reset
    public void resetPlayer() {
        this.setAlive(true);
        this.setCurrentHP(this.getMaxHP());
        this.setVialCount(0);
        this.setStandardWeapon();
        this.clearInventory();
    }

    private void setStandardWeapon() {
        this.equippedWeapon = standardWeapon;
    }

    //delete after test
    public void addItem(Item item) {
        if (this.getInventory().size() < capacity) {
            this.getInventory().add(item);
        } else {
            System.out.println("Inventory is full.");
        }
    }

    public Item removeItem(String itemId) {
        for (int i = 0; i < this.getInventory().size(); i++) {
            if (this.getInventory().get(i).getId().equalsIgnoreCase(itemId)) {
                return this.getInventory().remove(i);
            }
        }
        return null;
    }

    public void clearInventory() {
        this.getInventory().clear();
    }

    public boolean EscapeGame() {
        if (currentRoom.equals("R2") && this.getInventory().contains(getItem("Cure")))
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
            Puzzle puzzle = this.getCurrentRoom(currentRoom).getPuzzle();
            result += puzzle.getPuzzleName() + ": " + puzzle.getQuestion();
            }
        else {
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
                    result += puzzle.getRewards().get(i).getItemName() + " dropped.";
                }
            }
            return result;
        }
        return puzzle.getFailureMessage();
    }
}


