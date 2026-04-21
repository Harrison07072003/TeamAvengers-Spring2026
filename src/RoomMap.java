import java.io.File;
import java.io.FileNotFoundException;
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
    private String checkpointFile;

    public RoomMap(String roomsf,String puzzles, String monsters, String items) {
        rooms = new ArrayList<Room>();
        roomsFile = roomsf;
        puzzlesFile = puzzles;
        monstersFile = monsters;
        itemsFile = items;
        saveFile = "saveGame.txt";
        checkpointFile = "checkpoint.txt";
    }

    public void generateRooms() {
        Scanner input;
        String roomId;
        String roomName;
        String roomDescription;
        String building;
        String[] exits;
        boolean requiresFlashlight;
        try {
            input = new Scanner(new File(roomsFile));
            if(input.hasNextLine()){ input.nextLine();}
            while(input.hasNextLine()){
                String line = input.nextLine();
                if(line.length() == 0){ continue; }
                String[] values = line.split("\\|");
                roomId = values[0];
                roomName = values[1];
                roomDescription = values[2];
                building = values[3];
                exits = values[4].split(",");
                requiresFlashlight = Boolean.parseBoolean(values[5]);
                Room room = new Room(roomId, roomName, roomDescription, building, requiresFlashlight);
                String[] directions = {"North", "East", "South", "West"};
                for(int i = 0; i < exits.length; i++){
                    String exitRoom = exits[i].trim();
                    if(!exitRoom.equals("0")){
                        room.addExit(directions[i], exitRoom);
                    }
                }
                rooms.add(room);
            }
            input.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error: rooms.txt file not found.");
        }
    }


    public void spawnMonsters() {
        Scanner input;
        String monsterId;
        String monsterName;
        String monsterDescription;
        int hp;
        int attack;
        int defense;
        int coins;
        String roomId;

        try {
            input = new Scanner(new File(monstersFile));
            if(input.hasNextLine()){ input.nextLine();}
            while(input.hasNextLine()){
                String line = input.nextLine();
                if(line.length() == 0){ continue; }
                String[] values = line.split(",");
                monsterId = values[0];
                monsterName = values[1];
                monsterDescription = values[2];
                hp = Integer.parseInt(values[3]);
                attack = Integer.parseInt(values[4]);
                defense = Integer.parseInt(values[5]);
                coins = Integer.parseInt(values[6]);
                roomId = values[7];
                Monster villan = new Monster(monsterId, monsterName, monsterDescription, hp, attack, defense, coins, roomId);
                rooms.get(Integer.parseInt(roomId.substring(1))-1).addMonster(villan);
            }
            input.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error: monsters.txt file not found.");
        }
    }



    public void loadPuzzles() {
        Scanner input;
        String puzzleId;
        String puzzleName;
        String question;
        String answer;
        String roomId;
        String successMessage;
        String failureMessage;
        int coins;
        try{
            input = new Scanner(new File(puzzlesFile));
            if(input.hasNextLine()){ input.nextLine();}
            while(input.hasNextLine()){
                String line = input.nextLine();
                if(line.length() == 0){ continue; }
                String[] values = line.split("\\|");
                puzzleId = values[0];
                puzzleName = values[1];
                question = values[2];
                answer = values[3];
                roomId = values[4];
                successMessage = values[5];
                failureMessage = values[6];
                coins = Integer.parseInt(values[7]);
                Puzzle riddle = new Puzzle(puzzleId, puzzleName, question, answer, roomId, successMessage, failureMessage, coins);
                rooms.get(Integer.parseInt(roomId.substring(1))-1).setPuzzle(riddle);
            }
            input.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error: puzzles.txt file not found.");
        }

    }



    public void loadItems() {
        // need to separate per type of item
    }
    public ArrayList<Room> getRooms(){
        return this.rooms;
    }
    public Room getRoom(String num){
        int roomNumber = Integer.parseInt(num.substring(1));
        return rooms.get(roomNumber-1);
    }







   /* public void saveGame(Player player) {
        try {
            PrintWriter output = new PrintWriter(saveFile);
            // PLAYER
            output.println("PLAYER");
            output.println(
                    player.getCharaterID() + "," +
                    player.getCurrentHP() + "," +
                    player.getAttack() + "," +
                    player.getDefense() + "," +
                    player.getCoins() + "," +
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
                output.println("ROOM|" + room.getRoomId());

                // monster
                for (Monster monster : room.getMonster()) {
                    output.println(
                            "MONSTER|" +
                                    monster.getCharaterID() + "," +
                                    monster.getCurrentHP() + "," +
                                    monster.isAlive()
                    );
                }


                // puzzle

                // items

                output.println("ENDROOM");
            }
            output.close();
            System.out.println("Game saved successfully.");
        } catch (Exception e) {
            System.out.println("Error saving game.");
        }
    }



    public void loadGame(Player player) {
        Scanner input;
        String section = "";
        Room currentRoom = null;
        try {
            File save = new File(saveFile);
            File checkpoint = new File(checkpointFile);
            if (!save.exists() && !checkpoint.exists()) {
                System.out.println("There are no saved games or checkpoints found.");
                return;
            }
            File fileToLoad;

            if (save.exists() && checkpoint.exists()) {
                if (save.lastModified() >= checkpoint.lastModified()) {
                    fileToLoad = save;
                } else {
                    fileToLoad = checkpoint;
                }
            } else if (save.exists()) {
                fileToLoad = save;
            } else {
                fileToLoad = checkpoint;
            }
            input = new Scanner(fileToLoad);
            while (input.hasNextLine()) {
                String line = input.nextLine().trim();
                if (line.length() == 0) {
                    continue;
                }
                if (line.equals("PLAYER") || line.equals("INVENTORY") || line.equals("ROOMS")) {
                    section = line;
                    continue;
                }
                if (section.equals("PLAYER")) {
                    String[] values = line.split(",");
                    player.setCurrentHP(Integer.parseInt(values[1].trim()));
                    player.setAttack(Integer.parseInt(values[2].trim()));
                    player.setDefense(Integer.parseInt(values[3].trim()));
                    player.setCoins(Integer.parseInt(values[4].trim()));
                    player.setVialCount(Integer.parseInt(values[5].trim()));
                    player.setCurrentRoom(values[6].trim());
                }

                else if (section.equals("INVENTORY")) {
                    // add inventory loading here  when Item loading is finalized
                }

                else if (section.equals("ROOMS")) {
                    if (line.startsWith("ROOM|")) {
                        String roomId = line.split("\\|")[1].trim();
                        currentRoom = getRoom(roomId);
                    }

                    else if (line.startsWith("MONSTER|") && currentRoom != null) {
                        String[] values = line.split("\\|")[1].split(",");

                        String monsterId = values[0].trim();
                        int monsterHP = Integer.parseInt(values[1].trim());
                        boolean alive = Boolean.parseBoolean(values[2].trim());

                        Monster monster = getMonster(currentRoom, monsterId);

                        if (monster != null) {
                            monster.setCurrentHP(monsterHP);
                            monster.setAlive(alive);
                        }
                    }

                    else if (line.equals("ENDROOM")) {
                        currentRoom = null;
                    }
                }
            }

            input.close();
            System.out.println("Game loaded successfully.");

        } catch (FileNotFoundException e) {
            System.out.println("There are no saved games or checkpoints found.");
        }
    }


    public void saveCheckpoint(Player player) {
        try {
            PrintWriter output = new PrintWriter(checkpointFile);

            // PLAYER
            output.println("PLAYER");
            output.println(
                    player.getCharaterID() + "," +
                            player.getCurrentHP() + "," +
                            player.getAttack() + "," +
                            player.getDefense() + "," +
                            player.getCoins() + "," +
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
                output.println("ROOM|" + room.getRoomId());

                // monster
                for (Monster monster : room.getMonster()) {
                    output.println(
                            "MONSTER|" +
                                    monster.getCharaterID() + "," +
                                    monster.getCurrentHP() + "," +
                                    monster.isAlive()
                    );
                }

                // puzzle

                // items

                output.println("ENDROOM");
            }
            output.close();
            System.out.println("Checkpoint saved successfully.");
        } catch (Exception e) {
            System.out.println("Error saving checkpoint.");
        }
    }


    public void loadCheckpoint(Player player) {
        Scanner input;
        String section = "";
        Room currentRoom = null;

        try {
            File checkpoint = new File(checkpointFile);

            if (!checkpoint.exists()) {
                System.out.println("There are no checkpoints found.");
                return;
            }

            input = new Scanner(checkpoint);

            while (input.hasNextLine()) {
                String line = input.nextLine().trim();

                if (line.length() == 0) {
                    continue;
                }

                if (line.equals("PLAYER") || line.equals("INVENTORY") || line.equals("ROOMS")) {
                    section = line;
                    continue;
                }

                if (section.equals("PLAYER")) {
                    String[] values = line.split(",");

                    player.setCurrentHP(Integer.parseInt(values[1].trim()));
                    player.setAttack(Integer.parseInt(values[2].trim()));
                    player.setDefense(Integer.parseInt(values[3].trim()));
                    player.setCoins(Integer.parseInt(values[4].trim()));
                    player.setVialCount(Integer.parseInt(values[5].trim()));
                    player.setCurrentRoom(values[6].trim());
                }

                else if (section.equals("INVENTORY")) {
                    // add inventory loading here later
                }

                else if (section.equals("ROOMS")) {
                    if (line.startsWith("ROOM|")) {
                        String roomId = line.split("\\|")[1].trim();
                        currentRoom = getRoom(roomId);
                    }

                    else if (line.startsWith("MONSTER|") && currentRoom != null) {
                        String[] values = line.split("\\|")[1].split(",");

                        String monsterId = values[0].trim();
                        int monsterHP = Integer.parseInt(values[1].trim());
                        boolean alive = Boolean.parseBoolean(values[2].trim());

                        Monster monster = getMonster(currentRoom, monsterId);

                        if (monster != null) {
                            monster.setCurrentHP(monsterHP);
                            monster.setAlive(alive);
                        }
                    }

                    else if (line.equals("ENDROOM")) {
                        currentRoom = null;
                    }
                }
            }
            input.close();
            System.out.println("Checkpoint loaded successfully.");
        } catch (FileNotFoundException e) {
            System.out.println("There are no checkpoints found.");
        }
    }
   }

    */

}