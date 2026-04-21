import java.util.ArrayList;
import java.util.Scanner;

public class GameController {
    private final RoomMap map;
    private final Player player;
    private final PuzzleView view;
    private final Scanner input;
    private boolean isRunning;

    public GameController() {
        map = new RoomMap();
        player = new Player();
        view = new PuzzleView();
        input = new Scanner(System.in);
        isRunning = true;
    }

    public void run() {
        map.generateRooms();

        if (!map.loadPuzzles("puzzles.txt")) {
            view.showMessage("Could not load puzzles.txt.");
            view.showMessage("Make sure puzzles.txt is inside your project folder.");
            return;
        }

        view.showMessage("GGC Plague Puzzle Demo");
        view.showMessage("Type a room ID with a puzzle: R1, R2, R4, R5, R8, R12, R13");
        view.showMessage("Type quit to stop.");
        view.showMessage("");

        while (isRunning) {
            view.showMessage("Enter room ID:");
            String roomId = input.nextLine().trim();

            if (roomId.equalsIgnoreCase("quit")) {
                isRunning = false;
                view.showMessage("Game ended.");
                break;
            }

            if (!player.enterRoom(roomId, map)) {
                view.showMessage("Room not found.");
                view.showMessage("");
                continue;
            }

            Room currentRoom = player.getCurrentRoom(map);
            view.showRoom(currentRoom);

            if (currentRoom == null) {
                view.showMessage("");
                continue;
            }

            if (!currentRoom.checkPuzzle()) {
                if (currentRoom.getPuzzle() == null) {
                    view.showMessage("No puzzle in this room.");
                } else {
                    view.showMessage("This puzzle was already solved.");
                }
                view.showMessage("");
                continue;
            }

            puzzle(currentRoom);

            if (!currentRoom.getItems().isEmpty()) {
                view.showRoomItems(currentRoom.getItems());
            }

            view.showMessage("Player coins: " + player.getCoins());
            view.showMessage("");
        }
    }

    public void puzzle(Room currentRoom) {
        Puzzle currentPuzzle = currentRoom.getPuzzle();
        boolean puzzleMenuRunning = true;

        while (puzzleMenuRunning) {
            view.puzzleUI(currentRoom.getRoomId());
            String action = input.nextLine().trim().toLowerCase();

            switch (action) {
                case "explore":
                case "explore puzzle":
                    view.displayPuzzle(currentPuzzle);
                    view.showMessage("Type Solve to answer or Ignore Puzzle to leave it for later.");
                    break;

                case "solve":
                case "solve puzzle":
                    view.displayPuzzle(currentPuzzle);
                    view.showMessage("Enter your answer:");
                    String answer = input.nextLine().trim();

                    if (currentPuzzle.checkSolution(answer)) {
                        view.showCorrectMessage(currentPuzzle.getSuccessMessage());
                        givePuzzleRewards(currentPuzzle, currentRoom);
                        puzzleMenuRunning = false;
                    } else {
                        view.showIncorrectMessage(currentPuzzle.getFailureMessage());
                        view.showMessage("Type Solve to try again or Ignore Puzzle to leave it for later.");
                    }
                    break;

                case "ignore":
                case "ignore puzzle":
                    view.showMessage("You leave the puzzle for later.");
                    puzzleMenuRunning = false;
                    break;

                default:
                    view.showMessage("Invalid puzzle command.");
            }
        }
    }

    private void givePuzzleRewards(Puzzle puzzle, Room currentRoom) {
        ArrayList<String> droppedItems = puzzle.dropItems();

        for (String item : droppedItems) {
            currentRoom.addItem(item);
            view.showReward(item);
        }

        int droppedCoins = puzzle.dropCoins();
        if (droppedCoins > 0) {
            player.addCoins(droppedCoins);
            view.showCoinsEarned(droppedCoins);
        }
    }
}