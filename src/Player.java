public class Player {
    private String currentRoomId;

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

    public void explorePuzzle(Room room, PuzzleView view) {
        if (room == null || view == null) {
            return;
        }

        Puzzle puzzle = room.getPuzzle();
        if (puzzle == null) {
            view.showMessage("No puzzle in this room.");
            return;
        }

        view.displayPuzzle(puzzle);
    }

    public void solvePuzzle(Room room, PuzzleView view) {
        if (room == null || view == null) {
            return;
        }

        Puzzle puzzle = room.getPuzzle();
        if (puzzle == null) {
            view.showMessage("No puzzle in this room.");
            return;
        }

        String answer = view.getPlayerAnswer();
        boolean solved = puzzle.checkSolution(answer);

        if (solved) {
            view.showCorrectMessage();

            for (String reward : puzzle.getRewards()) {
                room.addItem(reward);
                view.showReward(reward);
            }
            return;
        }

        if (puzzle.getAttemptsRemaining() > 0) {
            view.showIncorrectMessage(puzzle.getAttemptsRemaining());
        } else {
            view.showPuzzleFailed();
        }
    }

    public void ignorePuzzle(PuzzleView view) {
        if (view != null) {
            view.showMessage("You leave the puzzle for later.");
        }
    }
}