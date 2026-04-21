import java.util.ArrayList;

public class Player extends Character {
    private String currentRoom;
    private final GameMap map;
    private Weapon equippedWeapon;
    private int vials;
    private int currentState;
    private int inventoryCapacity;
    private boolean officeUnlocked;
    private boolean plagueCured;

    public Player(String id, int maxHP, int attack, int defense, int coins, String roomId, GameMap map) {
        super(id, maxHP, attack, defense, coins);
        this.currentRoom = roomId;
        this.map = map;
        this.equippedWeapon = null;
        this.vials = 0;
        this.currentState = 1;
        this.inventoryCapacity = 5;
        this.officeUnlocked = false;
        this.plagueCured = false;
        setName(id);
    }

    public Room getCurrentRoom(String roomId) {
        return map.getRoom(roomId);
    }

    public Room getCurrentRoom() {
        return map.getRoom(currentRoom);
    }

    public String getRoomID() {
        return currentRoom;
    }

    public String getRoomName() {
        Room room = getCurrentRoom();
        return room == null ? "Unknown Room" : room.getRoomName();
    }

    public String getBuilding() {
        Room room = getCurrentRoom();
        return room == null ? "Unknown" : room.getBuilding();
    }

    public int getCurrentState() {
        return currentState;
    }

    public void setState(int state) {
        this.currentState = state;
    }

    public void setCurrentRoom(String roomId) {
        if (roomId != null && map.getRoom(roomId) != null) {
            this.currentRoom = roomId.toUpperCase();
        }
    }

    public int getVials() {
        return vials;
    }

    public int getInventoryCapacity() {
        return inventoryCapacity;
    }

    public boolean isOfficeUnlocked() {
        return officeUnlocked;
    }

    public void setOfficeUnlocked(boolean officeUnlocked) {
        this.officeUnlocked = officeUnlocked;
    }

    public boolean isPlagueCured() {
        return plagueCured;
    }

    public void setPlagueCured(boolean plagueCured) {
        this.plagueCured = plagueCured;
    }

    public boolean addItem(Item item) {
        if (item == null || getInventory().size() >= inventoryCapacity) {
            return false;
        }
        getInventory().add(item);
        if (item.getItemName().toLowerCase().startsWith("cure vital")) {
            vials++;
        }
        if (item instanceof Tool tool && "carry".equalsIgnoreCase(tool.getUtilityType())) {
            inventoryCapacity = 10;
        }
        return true;
    }

    public boolean removeItem(Item item) {
        if (item == null) {
            return false;
        }
        boolean removed = getInventory().remove(item);
        if (removed && item.getItemName().toLowerCase().startsWith("cure vital")) {
            vials = Math.max(0, vials - 1);
        }
        if (removed && item == equippedWeapon) {
            setAttack(getAttack() - equippedWeapon.getAttackBonus());
            equippedWeapon = null;
        }
        return removed;
    }

    public Item getItem(String itemName) {
        for (Item item : getInventory()) {
            if (item.getItemName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;
    }

    public boolean hasItem(String itemName) {
        return getItem(itemName) != null;
    }

    public String enterRoom(String input) {
        Room current = getCurrentRoom();
        if (current == null || input == null || input.isBlank()) {
            return "There is no entrance in that direction. Please enter a valid direction or room ID.";
        }

        String nextRoom = current.getExit(input);
        if (nextRoom == null) {
            return "There is no entrance in that direction. Please enter a valid direction or room ID.";
        }

        currentRoom = nextRoom.toUpperCase();
        return "You have entered " + getRoomName() + ".";
    }

    public String exploreRoom() {
        Room current = getCurrentRoom();
        if (current == null) {
            return "Your current room could not be found.";
        }

        if (current.requiresValidFlashlight() && !hasItem("Powered Flashlight")) {
            return "A functioning flashlight with batteries is needed to explore this room.";
        }

        StringBuilder result = new StringBuilder();
        result.append("You are currently in ")
                .append(current.getRoomName())
                .append(": ")
                .append(current.getRoomDescription());

        if (current.hasItem()) {
            result.append("\nItems in the room:");
            for (Item item : current.getInventory()) {
                result.append("\n- ").append(item.getItemName());
            }
        }

        if (current.hasVendingMachine()) {
            result.append("\nThere is a vending machine in this room.");
        }

        if (current.hasMonsters()) {
            result.append("\nMonsters in the room:");
            for (Monster monster : current.getMonsters()) {
                if (monster.isAlive()) {
                    result.append("\n- ").append(monster.getName());
                }
            }
        }

        if (current.hasActivePuzzle()) {
            result.append("\nThere is a puzzle in this room.");
        }

        return result.toString();
    }

    public boolean hasActivePuzzle() {
        Room room = getCurrentRoom();
        return room != null && room.hasActivePuzzle();
    }

    public String explorePuzzle() {
        Room room = getCurrentRoom();
        if (room == null || !room.hasActivePuzzle()) {
            return "No unsolved puzzle is in this room.";
        }

        currentState = 3;
        Puzzle puzzle = room.getPuzzle();
        return "Puzzle: " + puzzle.getPuzzleName() + "\nQuestion: " + puzzle.getQuestion();
    }

    public String solvePuzzle(String answer) {
        Room room = getCurrentRoom();
        if (room == null || !room.hasActivePuzzle()) {
            return "No unsolved puzzle is in this room.";
        }

        Puzzle puzzle = room.getPuzzle();
        boolean correct = puzzle.checkSolution(answer);
        if (!correct) {
            if (puzzle.getAttemptsRemaining() <= 0) {
                currentState = 1;
                return puzzle.getFailureMessage() + " No attempts remain.";
            }
            return puzzle.getFailureMessage() + " Attempts remaining: " + puzzle.getAttemptsRemaining();
        }

        ArrayList<Item> droppedRewards = puzzle.dropRewards();
        StringBuilder message = new StringBuilder(puzzle.getSuccessMessage());

        for (Item reward : droppedRewards) {
            if (addItem(reward)) {
                message.append("\nReward added to inventory: ").append(reward.getItemName());
            } else {
                room.addItem(reward);
                message.append("\nInventory full. Reward dropped in room: ").append(reward.getItemName());
            }
        }

        int droppedCoins = puzzle.dropCoins();
        if (droppedCoins > 0) {
            setCoins(getCoins() + droppedCoins);
            message.append("\nCoins earned: ").append(droppedCoins);
        }

        currentState = 1;
        return message.toString();
    }

    public String ignorePuzzle() {
        currentState = 1;
        return "You leave the puzzle for later.";
    }

    public String inspectMonster() {
        Room room = getCurrentRoom();
        if (room != null && room.hasMonsters()) {
            Monster monster = room.getMonsters().get(0);
            if (monster.isAlive()) {
                return monster.getName() + ": " + monster.getMonsterDescription() + "\nHP: " + monster.getHealth() + "\nAttack: " + monster.getAttack() + "\nDefense: " + monster.getDefense();
            }
        }
        return "No monsters detected.";
    }

    public boolean engageMonster() {
        Room room = getCurrentRoom();
        return room != null && room.hasMonsters() && room.getMonsters().get(0).isAlive();
    }

    public Monster getMonster() {
        Room room = getCurrentRoom();
        if (room == null || room.getMonsters().isEmpty()) {
            return null;
        }
        return room.getMonsters().get(0);
    }

    public String getMonsterName() {
        Monster monster = getMonster();
        return monster == null ? "No Monster" : monster.getName();
    }

    public int getDamage(Monster enemy, boolean heavyDamage) {
        double damage;
        if (enemy == null) {
            return 0;
        }
        if (heavyDamage && enemy.isDefending()) {
            damage = ((getAttack() + getAttackBonus()) * 1.3) - enemy.getDefense();
        } else if (heavyDamage) {
            damage = (getAttack() + getAttackBonus()) * 1.3;
        } else if (enemy.isDefending()) {
            damage = ((getAttack() + getAttackBonus()) * 0.6) - enemy.getDefense();
        } else {
            damage = getAttack() + getAttackBonus() - enemy.getDefense();
        }
        return Math.max(1, (int) damage);
    }

    public void attack(Monster monster) {
        if (monster == null || !monster.isAlive()) {
            return;
        }
        int damage = Math.max(1, getAttack() + getAttackBonus() - monster.getDefense());
        if (monster.isDefending()) {
            damage = Math.max(1, (int) (damage * 0.6));
        }
        monster.setCurrentHP(monster.getCurrentHP() - damage);
        if (!monster.isAlive()) {
            Item droppedItem = monster.dropItem("");
            if (droppedItem != null) {
                addItem(droppedItem);
            }
            setCoins(getCoins() + monster.getCoins());
        }
    }

    public boolean heavyAttack(Monster monster) {
        if (monster == null || !monster.isAlive()) {
            return false;
        }
        int chance = (int) (Math.random() * 100);
        if (chance <= 40) {
            return false;
        }
        int damage = Math.max(1, (int) ((getAttack() + getAttackBonus()) * 1.3) - monster.getDefense());
        if (monster.isDefending()) {
            damage = Math.max(1, (int) (damage * 0.6));
        }
        monster.setCurrentHP(monster.getCurrentHP() - damage);
        if (!monster.isAlive()) {
            Item droppedItem = monster.dropItem("");
            if (droppedItem != null) {
                addItem(droppedItem);
            }
            setCoins(getCoins() + monster.getCoins());
        }
        return true;
    }

    public void retreat() {
        Room current = getCurrentRoom();
        if (current == null) {
            return;
        }
        String southExit = current.getExits().get("S");
        String westExit = current.getExits().get("W");
        String fallback = southExit != null ? southExit : westExit;
        if (fallback != null) {
            setCurrentRoom(fallback);
        }
    }

    public int getAttackBonus() {
        return equippedWeapon == null ? 0 : equippedWeapon.getAttackBonus();
    }

    public String getEquippedWeaponName() {
        return equippedWeapon == null ? "Fists" : equippedWeapon.getItemName();
    }

    public boolean equipWeapon(String weaponName) {
        Item item = getItem(weaponName);
        if (!(item instanceof Weapon weapon)) {
            return false;
        }

        if (equippedWeapon != null) {
            setAttack(getAttack() - equippedWeapon.getAttackBonus());
        }

        equippedWeapon = weapon;
        setAttack(getAttack() + weapon.getAttackBonus());
        return true;
    }

    public String checkWeapon() {
        if (equippedWeapon == null) {
            return "There is no weapon equipped.";
        }
        return equippedWeapon.toString();
    }

    public String examineItem(String itemName) {
        Item item = getItem(itemName);
        return item == null ? "You don't have that item." : item.toString();
    }

    @Override
    public Item dropItem(String itemName) {
        Item item = getItem(itemName);
        if (item != null) {
            removeItem(item);
        }
        return item;
    }
}
