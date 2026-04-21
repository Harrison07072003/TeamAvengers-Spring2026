import java.util.ArrayList;
import java.util.List;

public class Room {
    private final String roomId;
    private Puzzle puzzle;
    private final ArrayList<String> items;

    public Room(String roomId) {
        this.roomId = roomId;
        this.items = new ArrayList<>();
    }

    public String getRoomId() {
        return roomId;
    }

    public Puzzle getPuzzle() {
        return puzzle;
    }

    public void setPuzzle(Puzzle puzzle) {
        this.puzzle = puzzle;
    }

    public boolean checkPuzzle() {
        return puzzle != null && !puzzle.isSolved();
    }

    public List<String> getItems() {
        return items;
    }

    public void addItem(String item) {
        if (item != null && !item.trim().isEmpty()) {
            items.add(item.trim());
        }
    }
}