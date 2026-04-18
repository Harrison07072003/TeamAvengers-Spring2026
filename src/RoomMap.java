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
        for (int i = 1; i <= 13; i++) {
            String roomId = "R" + i;
            rooms.put(roomId, new Room(roomId));
        }
    }

    public void loadPuzzles(String fileName) {
        Path filePath = Paths.get(fileName);
        if (!Files.exists(filePath)) {
            filePath = Paths.get(".", fileName);
        }
        if (!Files.exists(filePath)) {
            return;
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

                ArrayList<String> rewards = new ArrayList<>();
                if (!parts[8].trim().isEmpty()) {
                    String[] rewardParts = parts[8].split(",");
                    for (String reward : rewardParts) {
                        String trimmed = reward.trim();
                        if (!trimmed.isEmpty()) {
                            rewards.add(trimmed);
                        }
                    }
                }

                int attemptsRemaining;
                try {
                    attemptsRemaining = Integer.parseInt(parts[6].trim());
                } catch (NumberFormatException ex) {
                    attemptsRemaining = 3;
                }

                boolean isSolved = Boolean.parseBoolean(parts[7].trim());

                Puzzle puzzle = new Puzzle(
                        parts[0].trim(),
                        parts[1].trim(),
                        parts[2].trim(),
                        parts[3].trim(),
                        parts[4].trim(),
                        parts[5].trim(),
                        attemptsRemaining,
                        isSolved,
                        rewards
                );

                Room room = rooms.get(puzzle.getRoomId());
                if (room != null) {
                    room.setPuzzle(puzzle);
                }
            }
        } catch (IOException ex) {
            // Keep game running even if puzzle file cannot be read.
        }
    }

    public Room getRoom(String roomId) {
        return rooms.get(roomId);
    }
}
