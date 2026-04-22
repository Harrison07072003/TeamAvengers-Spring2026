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
    private String checkpointFile;

    public RoomMap(String roomsf,String puzzles, String monsters, String items) {
        rooms = new ArrayList<>();
        roomsFile = roomsf;
        puzzlesFile = puzzles;
        monstersFile = monsters;
        itemsFile = items;
        saveFile = "savegame.txt";
        checkpointFile = "";
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

    public void putVendingMachines() {
        Scanner input;
        String vendingId;
        String roomId;
        try{
            input = new Scanner(new File("vendingmachines.txt"));
            if(input.hasNextLine()){ input.nextLine();}
            while(input.hasNextLine()){
                String line = input.nextLine();
                if(line.length() == 0){ continue; }
                String[] values = line.split(",");
                vendingId = values[0];
                roomId = values[1];
                VendingMachine vm = new VendingMachine(vendingId,roomId);
                rooms.get(Integer.parseInt(roomId.substring(1))-1).setVendingMachine(vm);
            }
            input.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error: vendingmachines.txt file not found.");
        }
    }
    public void loadItems() {
        // need to separate per type of item
        Scanner input;
        String itemId;
        String itemName;
        String itemDescription;
        String category;
        String roomLocation;
        String location;
        int value;
        int price;
        try{
            input = new Scanner(new File(itemsFile));
            if(input.hasNextLine()){ input.nextLine();}
            while(input.hasNextLine()) {
                String line = input.nextLine();
                if (line.length() == 0) {
                    continue;
                }
                String[] values = line.split("\\|");
                itemId = values[0];
                itemName = values[1];
                itemDescription = values[2];
                category = values[3];
                value = Integer.parseInt(values[4]);
                roomLocation = values[5];
                location = values[6];
                price = Integer.parseInt(values[7]);
                Item item = returnItem(itemId, itemName, itemDescription, category, value, roomLocation,location,price);
                if(location.startsWith("R")){
                    rooms.get(Integer.parseInt(location.substring(1))-1).addItem(item);
                }
                if(location.startsWith("M")) {
                    rooms.get(Integer.parseInt(roomLocation.substring(1))-1).getMonster().getFirst().getInventory().add(item);
                    }
                if(location.startsWith("P")){
                    rooms.get(Integer.parseInt(roomLocation.substring(1))-1).getPuzzle().getRewards().add(item);
                }
                if(location.startsWith("V")){
                    rooms.get(Integer.parseInt(roomLocation.substring(1))-1).getVendingMachine().addItem(item,price);
                }
            }
            input.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error: items.txt file not found.");
        }
    }

    public ArrayList<Room> getRooms(){
        return this.rooms;
    }
    public Room getRoom(String num){
        int roomNumber = Integer.parseInt(num.substring(1));
        return rooms.get(roomNumber-1);
    }
    private Item returnItem(String itemId, String itemName, String itemDescription, String category, int value, String roomLocation,String location,int price){
        if(category.equals("Weapon")){
            return new Weapon (itemId, itemName, itemDescription, category, value, roomLocation, location, price);
        }
        else if(category.equals("Tool")){
            return new Tool (itemId, itemName, itemDescription, category, value, roomLocation, location, price);
        }
        else if(category.equals("Consumable")){
            return new Consumable (itemId, itemName, itemDescription, category, value, roomLocation, location, price);
        }
        else if(category.equals("QuestItem")){
            return new QuestItem(itemId, itemName, itemDescription, category, value, roomLocation, location, price);
        }
        return null;
    }







   public void saveGame(Player player){
        PrintWriter output;
        try {
            output = new PrintWriter(saveFile);
            // PLAYER
            output.println("PLAYER");
            output.println(player.getCharaterID() + "," +
                    player.getCurrentHP() + "," +
                    player.getAttack() + "," +
                    player.getDefense() + "," +
                    player.getCoins() + "," +
                    player.getVialCount() + "," +
                    player.getCurrentRoom());
            output.println("ROOMS");
            for(Room room : rooms) {
                output.println("ROOM|" + room.getRoomId());
                for(Monster monster : room.getMonster()) {
                    output.println("MONSTER|" + monster.getCharaterID() + "," + monster.getCurrentHP() + "," + monster.isAlive());
                }
                output.println("ENDROOM");
            }
            output.close();
        }catch (Exception e) {
            System.out.println("Error saving game.");
        }
   }

   public void loadGame(Player player){
        Scanner input;
        String section = "";
        try {
            File save = new File(saveFile);
            if (!save.exists()) {
                System.out.println("No saved game found.");
                return;
            }
            input = new Scanner(save);
            while (input.hasNextLine()) {
                String line = input.nextLine().trim();
                if (line.length() == 0) {
                    continue;
                }
                if (line.equals("PLAYER")) {
                    section = "PLAYER";
                    continue;
                }
                else if (line.equals("ROOMS")) {
                    section = "ROOMS";
                    continue;
                }
                if (section.equals("PLAYER")) {
                    String[] values = line.split(",");
                    player.setCharacterID(values[0].trim());
                    player.setCurrentHP(Integer.parseInt(values[1].trim()));
                    player.setAttack(Integer.parseInt(values[2].trim()));
                    player.setDefense(Integer.parseInt(values[3].trim()));
                    player.setCoins(Integer.parseInt(values[4].trim()));
                    player.setVialCount(Integer.parseInt(values[5].trim()));
                    player.setCurrentRoom(values[6].trim());
                }
                else if (section.equals("ROOMS")) {
                    if (line.startsWith("ROOM|")) {
                        String roomId = line.split("\\|")[1].trim();
                    }
                    else if (line.startsWith("MONSTER|")) {
                        String[] values = line.split("\\|")[1].split(",");
                        String monsterId = values[0].trim();
                        int monsterHP = Integer.parseInt(values[1].trim());
                        boolean alive = Boolean.parseBoolean(values[2].trim());
                        for(Room room : rooms) {
                            for(Monster monster : room.getMonster()) {
                                if(monster.getCharaterID().equals(monsterId)) {
                                    monster.setCurrentHP(monsterHP);
                                    monster.setAlive(alive);
                                }
                            }
                        }
                    }
                }
            }
            input.close();
        } catch (FileNotFoundException e) {
            System.out.println("No saved game found.");
        }
   }

/*
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