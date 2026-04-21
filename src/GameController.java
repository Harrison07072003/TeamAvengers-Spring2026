import java.util.ArrayList;
import java.util.Scanner;

public class GameController {
    private final RoomMap map;
    private final Player player;
    private final View view;
    private final Scanner input;
    private boolean isRunning;
    private Room currentRoom;

    public GameController() {
        map = new RoomMap();
        player = new Player();
        view = new View();
        input = new Scanner(System.in);
        isRunning = true;
        currentRoom = null;
    }

    public void run() {
        map.generateRooms();

        if (!map.loadPuzzles("puzzles.txt")) {
            view.display("Could not load puzzles.txt.");
            view.display("Make sure puzzles.txt is inside your project folder.");
            return;
        }

        view.display("GGC Plague Puzzle Demo");
        view.display("Type a room ID with a puzzle: R1, R2, R4, R5, R8, R12, R13");
        view.display("Type quit to stop.");
        view.display("");

        while (isRunning) {
            view.display("Enter room ID:");
            String roomId = input.nextLine().trim();

            if (roomId.equalsIgnoreCase("quit")) {
                isRunning = false;
                view.display("Game ended.");
                break;
            }

            if (!player.enterRoom(roomId, map)) {
                view.display("Room not found.");
                view.display("");
                continue;
            }

            currentRoom = player.getCurrentRoom(map);
            if (currentRoom == null) {
                view.display("");
                continue;
            }

            view.showRoomEntered(currentRoom.getRoomId());

            if (!currentRoom.checkPuzzle()) {
                if (currentRoom.getPuzzle() == null) {
                    view.display("No puzzle in this room.");
                } else {
                    view.display("This puzzle was already solved.");
                }
                view.display("");
                continue;
            }

            puzzle();
            view.display("Player coins: " + player.getCoins());
            view.display("");
        }
    }

    public void puzzle() {
        boolean puzzleMenuRunning = true;

        while (puzzleMenuRunning) {
            view.puzzleUI(currentRoom.getRoomId());
            String action = input.nextLine().trim().toLowerCase();

            switch (action) {
                case "explore":
                case "explore puzzle":
                    view.showPuzzle(currentRoom.getPuzzle().getPuzzleName(), player.explorePuzzle(currentRoom));
                    view.display("Type Solve Puzzle to answer or Ignore Puzzle to leave it for later.");
                    break;

                case "solve":
                case "solve puzzle":
                    view.showPuzzle(currentRoom.getPuzzle().getPuzzleName(), currentRoom.getPuzzle().getQuestion());
                    view.display("Enter your answer:");
                    String answer = input.nextLine().trim();

                    if (player.solvePuzzle(currentRoom, answer)) {
                        view.display(currentRoom.getPuzzle().getSuccessMessage());
                        givePuzzleRewards();
                        puzzleMenuRunning = false;
                    } else {
                        view.display(currentRoom.getPuzzle().getFailureMessage());
                        view.display("Type Solve Puzzle to try again or Ignore Puzzle to leave it for later.");
                    }
                    break;

                case "ignore":
                case "ignore puzzle":
                    view.display(player.ignorePuzzle(currentRoom));
                    puzzleMenuRunning = false;
                    break;

                default:
                    view.display("Invalid puzzle command.");
            }
        }
    }

    private void givePuzzleRewards() {
        ArrayList<Item> droppedRewards = currentRoom.getPuzzle().dropRewards();

        for (Item item : droppedRewards) {
            if (player.acceptReward(item)) {
                view.showReward(item.getItem_Name());
            }
        }

        int droppedCoins = currentRoom.getPuzzle().dropCoins();
        if (droppedCoins > 0) {
            player.addCoins(droppedCoins);
            view.showCoinsEarned(droppedCoins);
        }
    }
}
