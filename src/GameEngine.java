public class GameEngine {
    private final RoomMap school;
    private final Player player;

    public GameEngine() {
        this.school = new RoomMap();
        this.player = new Player();
        this.school.generateRooms();
        this.school.loadPuzzles("puzzles.txt");
    }

    public void test() {

    }

    public String navCommand(String command) {
        if (command == null) {
            return "Invalid Command\n-----------------------------------";
        }

        String cleaned = command.trim();

        if (cleaned.equalsIgnoreCase("status")) {
            return "Coins: " + player.getCoins() + "\n-----------------------------------";
        }

        if (cleaned.equalsIgnoreCase("inventory")) {
            return "Inventory: " + player.getInventory() + "\n-----------------------------------";
        }

        Room room = school.getRoom(cleaned);
        if (room != null) {
            player.enterRoom(cleaned, school);
            return "You entered room " + room.getRoomId() + "\n-----------------------------------";
        }

        return "Invalid Command\n-----------------------------------";
    }

    public String battleCommand(String command) {
        return "Battle system not used in puzzle feature.";
    }

    public void resetCombat() {
    }

    public boolean battleEnded() {
        return true;
    }

    public boolean playerAlive() {
        return true;
    }

    public boolean monsterAlive() {
        return false;
    }

    public int getTurn() {
        return 0;
    }

    public Player getPlayer() {
        return player;
    }

    public String getRoomName() {
        Room room = player.getCurrentRoom(school);
        if (room == null) {
            return "No room selected";
        }
        return room.getRoomId();
    }

    public int getPlayerState() {
        return 0;
    }

    public String getPlayerBuilding() {
        return "";
    }

    public String getPlayerHealth() {
        return "";
    }

    public String getMonsterName() {
        return "";
    }

    public String getMonsterHealth() {
        return "";
    }
}