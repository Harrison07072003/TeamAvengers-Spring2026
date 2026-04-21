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

    public RoomMap() {
        this.rooms = new HashMap<>();
    }

    public void generateRooms() {
        rooms.clear();
        for (int i = 1; i <= 20; i++) {
            String roomId = "R" + i;
            rooms.put(roomId, new Room(roomId));
        }
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

                ArrayList<String> itemDrops = new ArrayList<>();
                if (!parts[8].trim().isEmpty()) {
                    String[] rewardParts = parts[8].split(",");
                    for (String reward : rewardParts) {
                        String trimmed = reward.trim();
                        if (!trimmed.isEmpty()) {
                            itemDrops.add(trimmed);
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
}