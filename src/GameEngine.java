public class GameEngine {
    private final RoomMap school;
    private final Player player;
    private final CombatEngine combatEngine;
    private final GameResult result;

    public GameEngine() {
        this.school = new RoomMap();
        school.loadRooms("Rooms.txt");
        school.loadItems("Item.txt");
        school.loadPuzzles("puzzles.txt");
        school.loadMonsters("Monsters.txt");
        this.player = new Player("Player", 100, 18, 5, 15, "R1", school);
        this.combatEngine = new CombatEngine(player);
        this.result = new GameResult();
    }

    public String navCommand(String command) {
        result.resetMessage();
        result.resetStatus();
        if (command == null || command.isBlank()) {
            return "Enter a command.";
        }

        String trimmed = command.trim();
        String lower = trimmed.toLowerCase();

        if (lower.equals("status")) {
            result.setMessage("HP: " + player.getHealth() + "\nAttack: " + player.getAttack() + " | ATK Bonus: +" + player.getAttackBonus() +
                    "\nDefense: " + player.getDefense() + "\nVials: " + player.getVials() + "/5\nCoins: " + player.getCoins() +
                    "\nCurrent Weapon: " + player.getEquippedWeaponName());
        } else if (lower.equals("inventory")) {
            result.setMessage(player.getInventoryString());
        } else if (lower.equals("inspect") || lower.equals("explore")) {
            result.setMessage(player.exploreRoom());
        } else if (lower.startsWith("equip ")) {
            String itemName = trimmed.substring(6).trim();
            result.setMessage(player.equipWeapon(itemName) ? "You equipped " + itemName + "." : "You do not have that weapon.");
        } else if (lower.startsWith("examine ")) {
            result.setMessage(player.examineItem(trimmed.substring(8).trim()));
        } else if (lower.equals("n") || lower.equals("north") || lower.equals("e") || lower.equals("east")
                || lower.equals("s") || lower.equals("south") || lower.equals("w") || lower.equals("west")
                || lower.matches("r\\d+")) {
            result.setMessage(player.enterRoom(trimmed));
        } else {
            result.setMessage("Invalid command.");
        }
        return result.getMessage();
    }

    public String battleCommand(String command) {
        return combatEngine.action(command);
    }

    public void resetCombat() {
        combatEngine.resetEngine();
    }

    public boolean battleEnded() {
        return combatEngine.isBattleOver();
    }

    public boolean playerAlive() {
        return player.isAlive();
    }

    public boolean monsterAlive() {
        return combatEngine.getMonsterAlive();
    }

    public int getTurn() {
        return combatEngine.getTurns();
    }

    public Player getPlayer() {
        return player;
    }

    public String getRoomName() {
        return player.getRoomName();
    }

    public int getPlayerState() {
        return player.getCurrentState();
    }

    public String getPlayerBuilding() {
        return player.getBuilding();
    }

    public String getPlayerHealth() {
        return player.getHealth();
    }

    public String getMonsterName() {
        return player.getMonsterName();
    }

    public String getMonsterHealth() {
        return combatEngine.getMonsterHealth();
    }

    public boolean hasActivePuzzle() {
        return player.hasActivePuzzle();
    }

    public boolean hasActiveMonster() {
        return player.engageMonster();
    }

    public String explorePuzzle() {
        return player.explorePuzzle();
    }

    public String solvePuzzle(String answer) {
        return player.solvePuzzle(answer);
    }

    public String ignorePuzzle() {
        return player.ignorePuzzle();
    }
}
