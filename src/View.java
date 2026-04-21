public class View {
    public View() {
    }

    public void display(String str) {
        System.out.println(str);
    }

    public void navUI(String roomName) {
        System.out.println(roomName + "\nCommands Available: N, E, S, W, Status, Inventory, Inspect, Equip <weapon>, Map, Quit, Help\nEnter a command:");
    }

    public void monsterUI(String health, String monster, String monsterHealth) {
        System.out.println("Your HP: " + health + "\n" +
                monster + "'s HP: " + monsterHealth + "\n" +
                "Commands: Attack, Heavy Attack, Defend, Dodge, Check Weapon, Retreat\n" +
                "--------------------------------\nAction:");
    }

    public void puzzleUI() {
        System.out.println("Commands: Explore Puzzle, Solve Puzzle, Ignore Puzzle");
        System.out.println("Action:");
    }

    public void showPuzzlePrompt() {
        System.out.println("There is a puzzle in this room. Type 'puzzle' to interact with it or continue exploring.");
    }

    public void showPuzzleQuestion(String title, String question, int attemptsRemaining) {
        System.out.println("Puzzle: " + title);
        System.out.println(question);
        System.out.println("Attempts remaining: " + attemptsRemaining);
    }

    public void showRewardDrop(String rewardName) {
        System.out.println("Reward added: " + rewardName);
    }

    public void showCoinsEarned(int coins) {
        System.out.println("Coins earned: " + coins);
    }

    public void navHelp(int state) {
        if (state == 1) {
            System.out.println("Commands:\nStatus\nInventory\nInspect\nMap\nN, E, S, W\nEquip <weapon>\nPuzzle\nQuit");
        } else if (state == 2) {
            System.out.println("Commands:\nAttack\nHeavy Attack\nDefend\nDodge\nCheck Weapon\nRetreat");
        } else if (state == 3) {
            System.out.println("Commands:\nExplore Puzzle\nSolve Puzzle\nIgnore Puzzle");
        }
    }

    public void showMap(String building) {
        if (building.equals("E Building")) {
            System.out.println(
                    "                         |Building E|\n" +
                    " [Game Room] -------- [Kitchen] -------- [Dining Hall]\n" +
                    "      |                    |                   |\n" +
                    " [Meditation Room] --- [Student Lounge] --- [Campus Cafe] -----> Library"
            );
        } else if (building.equals("Library")) {
            System.out.println(
                    "Building E <------        |Library|\n" +
                    "  [Study Room]                              -----[Server Room]-----> Parking Lot\n" +
                    "         |                                  |            |\n" +
                    "         -------[Library Lobby]----[Computer Classroom]  |\n" +
                    "                          |                 |            |\n" +
                    "[Electric Control Room]----                 ----[Malfunctioning Elevators]"
            );
        } else if (building.equals("H Building")) {
            System.out.println(
                    "Library<-----             |H Building|\n" +
                    " [Chemistry Lab] ---- [Professor Office] ---- [Chemistry Lab 2]\n" +
                    "        |                    |                      |\n" +
                    "        |                    |            [Digital Media Lab]\n" +
                    "        |                    |                      |\n" +
                    " [Health Lab] -------- [Bathroom] -------- [Janitor's Closet]\n" +
                    "        |\n" +
                    "        |\n" +
                    "Parking Lot"
            );
        } else if (building.equals("Parking Lot")) {
            System.out.println(
                    "                        H Building\n" +
                    "                             ^\n" +
                    "                             |\n\n" +
                    "Library<------     |Parking Lot|"
            );
        } else {
            System.out.println("Map unavailable.");
        }
    }

    public void showCredits() {
        System.out.println("Teams Involved\n----------------\nCreative Team: Team Cobra\nDevelopment Team: Team Avengers\n" +
                "\nDevelopers\n----------------\nTeam Manager: Jocelin Mendoza\nTechnical Lead: Harrison Allen\nTesting Lead: José Muñoz-Suastegui" +
                "\nData Lead: Asliee Pena Cabrera\nRequirements & Documentation Lead: Gethsemane Gonzalez Cirilo");
    }
}
