import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class RoomMap {
    // fields
    private ArrayList<Room> rooms;

    private String roomsFile;
    private String puzzlesFile;
    private String monstersFile;
    private String itemsFile;
    private String saveFile;

    public RoomMap() {
        rooms = new ArrayList<Room>();

        roomsFile = "rooms.txt";
        puzzlesFile = "puzzles.txt";
        monstersFile = "monsters.txt";
        itemsFile = "items.txt";
        saveFile = "saveGame.txt";
    }

    public void generateRooms() {
        ArrayList<String> exitData = new ArrayList<String>();

        try {
            Scanner input = new Scanner(new File(roomsFile));

            while (input.hasNextLine()) {
                String line = input.nextLine().trim();

                if (line.length() == 0 || line.startsWith("#")) {
                    continue;
                }

                // format:
                // roomId|roomName|description|exit1,exit2,exit3
                String[] parts = line.split("\\|");

                if (parts.length < 4) {
                    System.out.println("Skipping bad room line: " + line);
                    continue;
                }

                String roomId = parts[0].trim();
                String roomName = parts[1].trim();
                String description = parts[2].trim();
                String exits = parts[3].trim();

                // assumes Room constructor is Room(String id, String name, String description)
                Room room = new Room(roomId, roomName, description);
                rooms.add(room);

                // save exit ids temporarily so we can connect rooms after all rooms are created
                exitData.add(roomId + "|" + exits);
            }

            input.close();

            // second pass: connect exits
            for (int i = 0; i < exitData.size(); i++) {
                String[] parts = exitData.get(i).split("\\|");

                String roomId = parts[0].trim();
                String exitsPart = parts[1].trim();

                Room currentRoom = getRoom(roomId);

                ArrayList<Room> exitRooms = new ArrayList<Room>();

                if (exitsPart.length() > 0) {
                    String[] exitIds = exitsPart.split(",");

                    for (int j = 0; j < exitIds.length; j++) {
                        String exitId = exitIds[j].trim();
                        Room exitRoom = getRoom(exitId);

                        if (exitRoom != null) {
                            exitRooms.add(exitRoom);
                        }
                    }
                }

                // assumes Room has setExits(ArrayList<Room>)
                currentRoom.setExits(exitRooms);
            }

            System.out.println("Rooms loaded successfully.");
        }
        catch (FileNotFoundException e) {
            System.out.println("Error: rooms.txt file not found.");
        }
    }

    public void loadPuzzles() {
        try {
            Scanner input = new Scanner(new File(puzzlesFile));

            while (input.hasNextLine()) {
                String line = input.nextLine().trim();

                if (line.length() == 0 || line.startsWith("#") || line.startsWith("puzzleId")) {
                    continue;
                }

                // format:
                // puzzleId|puzzleName|question|solution|roomId|hint|attemptsRemaining|isSolved|rewards
                String[] parts = line.split("\\|");

                if (parts.length < 9) {
                    System.out.println("Skipping bad puzzle line: " + line);
                    continue;
                }

                String puzzleId = parts[0].trim();
                String puzzleName = parts[1].trim();
                String question = parts[2].trim();
                String solution = parts[3].trim();
                String roomId = parts[4].trim();
                String hint = parts[5].trim();
                int attemptsRemaining = Integer.parseInt(parts[6].trim());
                boolean isSolved = Boolean.parseBoolean(parts[7].trim());

                // rewards are stored as ids for now
                ArrayList<String> rewards = new ArrayList<String>();
                String[] rewardParts = parts[8].split(",");

                for (int i = 0; i < rewardParts.length; i++) {
                    rewards.add(rewardParts[i].trim());
                }

                // assumes Puzzle constructor like this
                Puzzle puzzle = new Puzzle(
                        puzzleId,
                        puzzleName,
                        question,
                        solution,
                        roomId,
                        hint,
                        attemptsRemaining,
                        isSolved,
                        rewards
                );

                Room room = getRoom(roomId);

                if (room != null) {
                    // assumes Room has setPuzzle(Puzzle)
                    room.setPuzzle(puzzle);
                }
            }

            input.close();
            System.out.println("Puzzles loaded successfully.");
        }
        catch (FileNotFoundException e) {
            System.out.println("puzzles.txt file not found.");
        }
    }

    public void spawnMonsters() {
        try {
            Scanner input = new Scanner(new File(monstersFile));

            while (input.hasNextLine()) {
                String line = input.nextLine().trim();

                if (line.length() == 0 || line.startsWith("#") || line.startsWith("(ID")) {
                    continue;
                }

                // format:
                // ID,Name,Description,HP,Attack,Defense,Coins,Room
                String[] parts = line.split(",");

                if (parts.length < 8) {
                    System.out.println("Skipping bad monster line: " + line);
                    continue;
                }

                String monsterId = parts[0].trim();
                String monsterName = parts[1].trim();
                String description = parts[2].trim();
                int hp = Integer.parseInt(parts[3].trim());
                int attack = Integer.parseInt(parts[4].trim());
                int defense = Integer.parseInt(parts[5].trim());
                int coins = Integer.parseInt(parts[6].trim());
                String roomId = parts[7].trim();

                // assumes Monster constructor like this
                Monster monster = new Monster(
                        monsterId,
                        monsterName,
                        description,
                        hp,
                        attack,
                        defense,
                        coins,
                        roomId
                );

                Room room = getRoom(roomId);

                if (room != null) {
                    // assumes Room has setMonster(Monster)
                    room.setMonster(monster);
                }
            }

            input.close();
            System.out.println("Monsters loaded successfully.");
        }
        catch (FileNotFoundException e) {
            System.out.println("monsters.txt file not found.");
        }
    }

    public void loadItems() {
        try {
            Scanner input = new Scanner(new File(itemsFile));

            while (input.hasNextLine()) {
                String line = input.nextLine().trim();

                if (line.length() == 0 || line.startsWith("#")) {
                    continue;
                }

                // format:
                // itemId|itemName|category|description|attackBonus|healValue|roomId
                String[] parts = line.split("\\|");

                if (parts.length < 7) {
                    System.out.println("Skipping bad item line: " + line);
                    continue;
                }

                String itemId = parts[0].trim();
                String itemName = parts[1].trim();
                String category = parts[2].trim();
                String description = parts[3].trim();
                int attackBonus = Integer.parseInt(parts[4].trim());
                int healValue = Integer.parseInt(parts[5].trim());
                String roomId = parts[6].trim();

                Item item = new Item(itemId, itemName, category, description, attackBonus, healValue);

                Room room = getRoom(roomId);

                if (room != null) {
                    room.addItem(item);
                } else {
                    System.out.println("Room not found for item: " + itemId);
                }
            }

            input.close();
            System.out.println("Items loaded successfully.");
        }
        catch (FileNotFoundException e) {
            System.out.println("items.txt file not found.");
        }
    }

    public Room getRoom(String roomId) {
        for (int i = 0; i < rooms.size(); i++) {
            if (rooms.get(i).getRoomId().equalsIgnoreCase(roomId)) {
                return rooms.get(i);
            }
        }

        return null;
    }

    public void saveGame(Player player) {
        try {
            PrintWriter output = new PrintWriter(saveFile);

            // PLAYER
            output.println("PLAYER");
            output.println(player.getCharaterID() + "," +
                    player.getCurrentHP() + "," +
                    player.getAttack() + "," +
                    player.getDefense() + "," +
                    player.getVialCount() + "," +
                    player.getCurrentRoom());

            // INVENTORY
            output.println("INVENTORY");
            for (Item item : player.getInventory()) {
                output.println(item.toFileString());
            }

            // ROOMS
            output.println("ROOMS");

            for (Room room : rooms) {
                output.println(room.getRoomId());

                // puzzle
                if (room.getPuzzle() != null) {
                    output.println("PUZZLE|" + room.getPuzzle().getPuzzleId() + "|" + room.getPuzzle().isSolved());
                } else {
                    output.println("PUZZLE|none");
                }

                // monster
                if (room.getMonster() != null) {
                    output.println("MONSTER|" + room.getMonster().getCharacterId());
                } else {
                    output.println("MONSTER|none");
                }

                // items
                for (Item item : room.getItems()) {
                    output.println("ITEM|" + item.toFileString());
                }

                output.println("ENDROOM");
            }

            output.close();
            System.out.println("Game saved successfully.");
        } catch (Exception e) {
            System.out.println("Error saving game.");
        }
    }



    public void loadGame(Player player) {
        try {
            Scanner input = new Scanner(new File(saveFile));

            player.clearInventory();

            for (Room r : rooms) {
                r.clearItems();
            }

            String section = "";

            while (input.hasNextLine()) {
                String line = input.nextLine().trim();

                if (line.equals("PLAYER") || line.equals("INVENTORY") || line.equals("ROOMS")) {
                    section = line;
                    continue;
                }

                if (section.equals("PLAYER")) {
                    String[] parts = line.split(",");
                    player.setCharacterID(parts[0]);
                    player.setCurrentHP(Integer.parseInt(parts[1]));
                    player.setAttack(Integer.parseInt(parts[2]));
                    player.setDefense(Integer.parseInt(parts[3]));
                    player.setVialCount(Integer.parseInt(parts[4]));
                    player.setCurrentRoom(parts[5]);
                }

                else if (section.equals("INVENTORY")) {
                    player.addItem(Item.fromFileString(line));
                }

                else if (section.equals("ROOMS")) {
                    if (line.equals("ENDROOM")) continue;

                    Room room = getRoom(line);

                    while (input.hasNextLine()) {
                        String inner = input.nextLine().trim();

                        if (inner.equals("ENDROOM")) break;

                        if (inner.startsWith("PUZZLE")) {
                            if (!inner.contains("none") && room.getPuzzle() != null) {
                                String[] p = inner.split("\\|");
                                room.getPuzzle().setSolved(Boolean.parseBoolean(p[2]));
                            }
                        }

                        if (inner.startsWith("ITEM")) {
                            String data = inner.substring(5);
                            room.addItem(Item.fromFileString(data));
                        }
                    }
                }
            }

            input.close();
            System.out.println("Game loaded successfully.");

        } catch (Exception e) {
            System.out.println("No saved game found.");
        }
    }

    public void checkpoint(Player player) {
        saveGame(player);
        System.out.println("Checkpoint created successfully.");
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }
}