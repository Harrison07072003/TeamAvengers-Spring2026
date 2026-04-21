import java.util.ArrayList;
import java.util.List;

public class Player {
    private String currentRoomId;
    private int coins;
    private final ArrayList<Item> inventory;
    private final ArrayList<Item> lastAcceptedRewards;
    private int lastCoinsEarned;

    public Player() {
        currentRoomId = null;
        coins = 15;
        inventory = new ArrayList<>();
        lastAcceptedRewards = new ArrayList<>();
        lastCoinsEarned = 0;
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
        lastAcceptedRewards.clear();
        lastCoinsEarned = 0;

        if (room == null || room.getPuzzle() == null) {
            return false;
        }

        Puzzle puzzle = room.getPuzzle();
        boolean solved = puzzle.checkSolution(answer);

        if (solved) {
            ArrayList<Item> droppedItems = puzzle.dropItems();
            for (Item item : droppedItems) {
                acceptReward(item);
                lastAcceptedRewards.add(item);
            }

            lastCoinsEarned = puzzle.dropCoins();
            addCoins(lastCoinsEarned);
        }

        return solved;
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

    public List<String> getLastRewardNames() {
        ArrayList<String> rewardNames = new ArrayList<>();
        for (Item item : lastAcceptedRewards) {
            rewardNames.add(item.getItem_Name());
        }
        return rewardNames;
    }

    public int getLastCoinsEarned() {
        return lastCoinsEarned;
    }
}