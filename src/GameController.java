public class GameController {
    private RoomMap map;
    private Player player;
    private PuzzleView view;
    private boolean isRunning;

    public GameController() {
        map = new RoomMap();
        player = new Player();
        view = new PuzzleView();
        isRunning = true;
    }

    public void run() {
        map.generateRooms();
        map.loadPuzzles("puzzles.txt");

        view.showMessage("GGC Plague puzzles.txt Demo");
        view.showMessage("Type a room ID with a puzzle: R1, R2, R4, R5, R8, R12, R13");
        view.showMessage("Type quit to stop.\n");

        while (isRunning) {
            String roomId = view.getInput("Enter room ID: ");

            if (roomId.equalsIgnoreCase("quit")) {
                isRunning = false;
                view.showMessage("Game ended.");
                break;
            }

            if (!player.enterRoom(roomId, map)) {
                view.showMessage("Room not found.\n");
                continue;
            }

            Room currentRoom = player.getCurrentRoom(map);
            view.showRoom(currentRoom);

            if (currentRoom.getPuzzle() == null) {
                view.showMessage("No puzzle in this room.\n");
                continue;
            }

            if (currentRoom.getPuzzle().isSolved()) {
                view.showMessage("This puzzle was already solved.\n");
                continue;
            }

            boolean puzzleMenuRunning = true;

            while (puzzleMenuRunning) {
                view.puzzleUI(currentRoom);
                String action = view.getInput("Action: ");

                switch (action.toLowerCase()) {
                    case "explore":
                    case "explore puzzle":
                        player.explorePuzzle(currentRoom, view);
                        break;

                    case "solve":
                    case "solve puzzle":
                        player.solvePuzzle(currentRoom, view);

                        if (currentRoom.getPuzzle().isSolved()) {
                            puzzleMenuRunning = false;
                        }
                        break;

                    case "ignore":
                    case "ignore puzzle":
                        player.ignorePuzzle(view);
                        puzzleMenuRunning = false;
                        break;

                    default:
                        view.showMessage("Invalid puzzle command.");
                }
            }

            if (!currentRoom.getItems().isEmpty()) {
                view.showMessage("Items currently in room: " + currentRoom.getItems());
            }

            view.showMessage("");
        }
    }
}