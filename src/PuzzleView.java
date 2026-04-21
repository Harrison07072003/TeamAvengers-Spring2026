import java.util.ArrayList;
import java.util.List;

public class PuzzleView {

    public void puzzleUI(String roomId) {
        System.out.println();
        System.out.println("************************************");
        System.out.println("? PUZZLE DETECTED IN [" + roomId + "] ?");
        System.out.println("************************************");
        System.out.println("[Explore Puzzle] - Read the riddle");
        System.out.println("[Solve]          - Input an answer");
        System.out.println("[Ignore Puzzle]  - Leave for later");
        System.out.println("************************************");
        System.out.print("Action: ");
    }

    public void displayPuzzle(Puzzle puzzle) {
        if (puzzle == null) {
            showMessage("No puzzle in this room.");
            return;
        }

        System.out.println();
        System.out.println("==============================");
        System.out.println("PUZZLE: " + puzzle.getPuzzleName());
        System.out.println("==============================");
        System.out.println("Question: " + puzzle.getQuestion());
        System.out.println("==============================");
    }

    public void showRoom(Room room) {
        if (room == null) {
            showMessage("No room selected.");
            return;
        }
        showMessage("You entered room: " + room.getRoomId());
    }

    public void showCorrectMessage(String message) {
        System.out.println(message);
    }

    public void showIncorrectMessage(String message) {
        System.out.println(message);
    }

    public void showReward(String rewardId) {
        System.out.println("Reward dropped in room: " + getItemDisplayName(rewardId));
    }

    public void showCoinsEarned(int coins) {
        System.out.println("Coins earned: " + coins);
    }

    public void showRoomItems(List<String> itemIds) {
        if (itemIds == null || itemIds.isEmpty()) {
            return;
        }

        ArrayList<String> itemNames = new ArrayList<>();

        for (String itemId : itemIds) {
            itemNames.add(getItemDisplayName(itemId));
        }

        System.out.println("Items currently in room: " + itemNames);
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    private String getItemDisplayName(String itemId) {
        if (itemId == null) {
            return "Unknown Item";
        }

        switch (itemId.trim().toUpperCase()) {
            case "A1":
                return "Flashlight";
            case "A2":
                return "Batteries";
            case "A3":
                return "Cure Vial";
            case "A4":
                return "Vending Machine Food";
            case "A5":
                return "Normal Food";
            case "A6":
                return "Coins";
            case "A7":
                return "Kitchen Knife";
            case "A8":
                return "Fork";
            case "A9":
                return "Book";
            case "A10":
                return "Backpack";
            case "A11":
                return "Broom";
            case "A12":
                return "Office Key";
            case "A13":
                return "Cure";
            default:
                return itemId;
        }
    }
}