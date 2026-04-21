import java.util.ArrayList;
import java.util.List;

public class Player {
    private String currentRoomId;
    private int coins;
    private final ArrayList<Item> inventory;

    public Player() {
        currentRoomId = null;
        coins = 15;
        inventory = new ArrayList<>();
    }

    public boolean enterRoom(String roomId, RoomMap map) {
        if (roomId == null || map == null) {
            return false;
        }

        Room room = map.getRoom(roomId.trim().toUpperCase());
        if (room == null) {
            return false;
        }

        currentRoomId = room.getRoomId();
        return true;
    }

    public Room getCurrentRoom(RoomMap map) {
        if (map == null || currentRoomId == null) {
            return null;
        }
        return map.getRoom(currentRoomId);
    }

    public String explorePuzzle(Room room) {
        if (room == null || room.getPuzzle() == null) {
            return "No puzzle in this room.";
        }
        if (room.getPuzzle().isSolved()) {
            return "This puzzle was already solved.";
        }
        return room.getPuzzle().accessPuzzle();
    }

    public String ignorePuzzle(Room room) {
        if (room == null || room.getPuzzle() == null) {
            return "No puzzle in this room.";
        }
        return "You leave the puzzle for later.";
    }

    public boolean solvePuzzle(Room room, String answer) {
        if (room == null || room.getPuzzle() == null) {
            return false;
        }
        return room.getPuzzle().checkSolution(answer);
    }

    public boolean acceptReward(Item item) {
        if (item == null) {
            return false;
        }
        inventory.add(item);
        return true;
    }

    public void addCoins(int amount) {
        if (amount > 0) {
            coins += amount;
        }
    }

    public int getCoins() {
        return coins;
    }

    public List<Item> getInventory() {
        return new ArrayList<>(inventory);
    }
}
