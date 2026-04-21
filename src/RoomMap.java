import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoomMap {
    private final Map<String, Room> rooms;
    private final boolean puzzlesLoaded;

    public RoomMap() {
        this.rooms = new HashMap<>();
        generateRooms();
        this.puzzlesLoaded = loadPuzzles("puzzles.txt");
    }

    public void generateRooms() {
        rooms.clear();
        for (int i = 1; i <= 20; i++) {
            String roomId = "R" + i;
            rooms.put(roomId, new Room(roomId));
        }
    }

    public boolean isPuzzlesLoaded() {
        return puzzlesLoaded;
    }

    public boolean loadPuzzles(String fileName) {
        Path filePath = Paths.get(fileName);

        if (!Files.exists(filePath)) {
            filePath = Paths.get(".", fileName);
        }

        if (!Files.exists(filePath)) {
            return false;
        }

        try {
            List<String> lines = Files.readAllLines(filePath);

            for (int i = 1; i < lines.size(); i++) {
                String line = lines.get(i).trim();

                if (line.isEmpty()) {
                    continue;
                }

                String[] parts = line.split("\\|", -1);

                if (parts.length < 9) {
                    continue;
                }

                String puzzleId = parts[0].trim();
                String puzzleName = parts[1].trim();
                String question = parts[2].trim();
                String solution = parts[3].trim();
                String roomId = parts[4].trim();
                String successMessage = parts[5].trim();
                String failureMessage = parts[6].trim();

                int coinReward = 0;
                try {
                    coinReward = Integer.parseInt(parts[7].trim());
                } catch (NumberFormatException ignored) {
                    coinReward = 0;
                }

                ArrayList<Item> itemDrops = new ArrayList<>();
                if (!parts[8].trim().isEmpty()) {
                    String[] rewardParts = parts[8].split(",");
                    for (String reward : rewardParts) {
                        String trimmed = reward.trim();
                        if (!trimmed.isEmpty()) {
                            itemDrops.add(new Item(trimmed, getItemName(trimmed)));
                        }
                    }
                }

                Puzzle puzzle = new Puzzle(
                        puzzleId,
                        puzzleName,
                        question,
                        solution,
                        roomId,
                        successMessage,
                        failureMessage,
                        itemDrops,
                        coinReward
                );

                Room room = rooms.get(roomId);
                if (room != null) {
                    room.setPuzzle(puzzle);
                }
            }

            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    public Room getRoom(String roomId) {
        if (roomId == null) {
            return null;
        }
        return rooms.get(roomId.trim().toUpperCase());
    }

    private String getItemName(String itemId) {
        switch (itemId.toUpperCase()) {
            case "A1": return "Flashlight";
            case "A2": return "Batteries";
            case "A3": return "Cure Vial";
            case "A4": return "Vending Machine Food";
            case "A5": return "Normal Food";
            case "A6": return "Coins";
            case "A7": return "Kitchen Knife";
            case "A8": return "Fork";
            case "A9": return "Book";
            case "A10": return "Backpack";
            case "A11": return "Broom";
            case "A12": return "Office Key";
            case "A13": return "Cure";
            default: return itemId;
        }
    }
}