public class View {
    public View() {
    }

    public void display(String str) {
        System.out.println(str);
    }

    public void puzzleUI(String roomId) {
        System.out.println();
        System.out.println("************************************");
        System.out.println("PUZZLE DETECTED IN [" + roomId + "]");
        System.out.println("************************************");
        System.out.println("[Explore Puzzle] - Read the riddle");
        System.out.println("[Solve Puzzle]   - Input an answer");
        System.out.println("[Ignore Puzzle]  - Leave for later");
        System.out.println("************************************");
        System.out.print("Action: ");
    }

    public void showPuzzle(String puzzleName, String question) {
        System.out.println();
        System.out.println("==============================");
        System.out.println("PUZZLE: " + puzzleName);
        System.out.println("==============================");
        System.out.println("Question: " + question);
        System.out.println("==============================");
    }

    public void showRoomEntered(String roomId) {
        System.out.println("You entered room: " + roomId);
    }

    public void showReward(String rewardText) {
        System.out.println("Rewards added to inventory: " + rewardText);
    }

    public void showCoinsEarned(int coins) {
        System.out.println("Coins earned: " + coins);
    }
}