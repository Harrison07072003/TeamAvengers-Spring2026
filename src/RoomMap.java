import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;
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
        saveFile = "saveFile.txt";
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

    public void saveGame(Player player) {
        try (PrintWriter output = new PrintWriter(saveFile)) {
            savePlayerSection(output, player);
            savePlayerInventorySection(output, player);
            saveRoomsSection(output);
            System.out.println("Game saved successfully.");
        } catch (Exception e) {
            System.out.println("Error saving game.");
        }
    }

    public void loadGame(Player player) {
        File save = new File(saveFile);
        if (!save.exists()) {
            System.out.println("No saved game found.");
            return;
        }

        try (Scanner input = new Scanner(save)) {
            String section = "";
            while (input.hasNextLine()) {
                String line = input.nextLine().trim();
                if (line.length() == 0) continue;

                if (line.equals("PLAYER")) { section = "PLAYER"; continue; }
                if (line.equals("ROOMS")) { section = "ROOMS"; continue; }
                if (line.equals("PLAYER INVENTORY")) { section = "PLAYER INVENTORY"; continue; }

                if (section.equals("PLAYER")) {
                    handlePlayerLine(line, player);
                } else if (section.equals("PLAYER INVENTORY")) {
                    handlePlayerInventoryLine(line, player);
                } else if (section.equals("ROOMS")) {
                    handleRoomsLine(line);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No saved game found.");
        }
    }

    private void savePlayerSection(PrintWriter output, Player player) {
        output.println("PLAYER");
        output.println(player.getCharaterID() + "," +
                player.getCurrentHP() + "," +
                player.getMaxHP() + "," +
                player.getAttack() + "," +
                player.getDefense() + "," +
                player.getCoins() + "," +
                player.getCurrentRoom() + "," +
                player.getVialCount());
    }

    private void savePlayerInventorySection(PrintWriter output, Player player) {
        output.println("PLAYER INVENTORY");
        for (Item item : player.getInventory()) {
            output.println("Player ITEM| " + item.toFileString());
        }
        if (player.getEquippedWeapon() != null) {
            output.println("EQUIPPED| " + player.getEquippedWeapon().toFileString());
        } else {
            output.println("EQUIPPED| none");
        }
    }

    private void saveRoomsSection(PrintWriter output) {
        output.println("ROOMS");
        for (Room room : rooms) {
            saveRoom(output, room);
        }
    }

    private void saveRoom(PrintWriter output, Room room) {
        output.println("ROOM|" + room.getRoomId() +
                "," + room.getRoomName() +
                "," + room.getRoomDescription() +
                "," + room.getBuilding() +
                "," + room.requiresValidFlashlight());
        output.println("EXITS|" + room.getExitsFileString());
        for (Item item : room.getInventory()) {
            output.println("Room ITEM|" + item.toFileString());
        }
        if (room.getPuzzle() != null) {
            output.println("PUZZLE|" + room.getPuzzle().toFileString());
            output.println("PUZZLE REWARD|" + room.getPuzzle().getRewardsFileString());
        } else {
            output.println("PUZZLE|none");
        }
        for (Monster monster : room.getMonster()) {
            output.println("MONSTER|" +
                    monster.getCharaterID() + "," +
                    monster.getMonsterName() + "," +
                    monster.getMonsterDescription() + "," +
                    monster.getCurrentHP() + "," +
                    monster.getAttack() + "," +
                    monster.getDefense() + "," +
                    monster.getCoins() + "," +
                    monster.getRoomID() + "," +
                    monster.isAlive());
            for (Item item : monster.getInventory()) {
                output.println("Monster ITEM|" + monster.getCharaterID() + "|" + item.toFileString());
            }
        }
        if (room.getVendingMachine() != null) {
            output.println("VENDING MACHINE|" + room.getVendingMachine().getVendingId());
            for (Item item : room.getVendingMachine().getItemsForSale().keySet()) {
                output.println("VendingMachine ITEM|" + item.toFileString());
            }
        } else {
            output.println("VENDING MACHINE|none");
        }
        output.println("ENDROOM");
    }

    private void handlePlayerLine(String line, Player player) {
        String[] values = line.split(",");
        // preserved original mapping from your code
        player.setCharacterID(values[0].trim());
        player.setCurrentHP(Integer.parseInt(values[1].trim()));
        player.setAttack(Integer.parseInt(values[2].trim()));
        player.setDefense(Integer.parseInt(values[3].trim()));
        player.setCoins(Integer.parseInt(values[4].trim()));
        player.setVialCount(Integer.parseInt(values[5].trim()));
        player.setCurrentRoom(values[6].trim());
    }

    private void handlePlayerInventoryLine(String line, Player player) {
        if (line.startsWith("Player ITEM|")) {
            String itemData = line.split("\\|")[1].trim();
            String[] itemValues = itemData.split(",");
            Item item = returnItem(itemValues[0], itemValues[1], itemValues[2], itemValues[3],
                    Integer.parseInt(itemValues[4]), itemValues[5], itemValues[6], Integer.parseInt(itemValues[7]));
            player.getInventory().add(item);
        } else if (line.startsWith("EQUIPPED|")) {
            String equippedData = line.split("\\|")[1].trim();
            if (!equippedData.equals("none")) {
                String[] equippedValues = equippedData.split(",");
                Weapon equippedWeapon = new Weapon(equippedValues[0], equippedValues[1], equippedValues[2], equippedValues[3],
                        Integer.parseInt(equippedValues[4]), equippedValues[5], equippedValues[6], Integer.parseInt(equippedValues[7]));
                player.setEquippedWeapon(equippedWeapon);
            }
        }
    }

    private void handleRoomsLine(String line) {
        if (line.startsWith("ROOM|")) {
            String roomData = line.split("\\|")[1].trim();
            String[] roomValues = roomData.split(",");
            Room room = new Room(roomValues[0], roomValues[1], roomValues[2], roomValues[3],
                    Boolean.parseBoolean(roomValues[4]));
            rooms.add(room);
        } else if(line.startsWith("EXITS|")) {
            String exitsData = line.split("\\|")[1].trim();
            String[] exits = exitsData.split(",");
            for(int i = 0; i < exits.length/2;i+=2){
                rooms.get(rooms.size() - 1).addExit(exits[i].trim(), exits[i+1].trim());
            }
        }
        else if (line.startsWith("Room ITEM|")) {
            String itemData = line.split("\\|")[1].trim();
            String[] itemValues = itemData.split(",");
            Item item = returnItem(itemValues[0], itemValues[1], itemValues[2], itemValues[3],
                    Integer.parseInt(itemValues[4]), itemValues[5], itemValues[6], Integer.parseInt(itemValues[7]));
            rooms.get(rooms.size() - 1).addItem(item);
        } else if (line.startsWith("PUZZLE|")) {
            String puzzleData = line.split("\\|")[1].trim();
            if (!puzzleData.equals("none")) {
                String[] puzzleValues = puzzleData.split(",");
                Puzzle puzzle = new Puzzle(puzzleValues[0], puzzleValues[1], puzzleValues[2], puzzleValues[3],
                        puzzleValues[4], puzzleValues[5], puzzleValues[6], Integer.parseInt(puzzleValues[7]));
                rooms.get(rooms.size() - 1).setPuzzle(puzzle);
            }
        } else if (line.startsWith("PUZZLE REWARD|")) {
            String rewardsData = line.split("\\|")[1].trim();
            String[] rewards = rewardsData.split(";");
            for (int i = 0; i < rewards.length; i++) {
                String[] rewardValues = rewards[i].split(",");
                Item reward = returnItem(rewardValues[0], rewardValues[1], rewardValues[2], rewardValues[3],
                        Integer.parseInt(rewardValues[4]), rewardValues[5], rewardValues[6], Integer.parseInt(rewardValues[7]));
                rooms.get(rooms.size() - 1).getPuzzle().getRewards().add(reward);
            }
        } else if (line.startsWith("MONSTER|")) {
            String monsterData = line.split("\\|")[1].trim();
            String[] monsterValues = monsterData.split(",");
            Monster monster = new Monster(monsterValues[0], monsterValues[1], monsterValues[2],
                    Integer.parseInt(monsterValues[3]), Integer.parseInt(monsterValues[4]),
                    Integer.parseInt(monsterValues[5]), Integer.parseInt(monsterValues[6]), monsterValues[7]);
            monster.setAlive(Boolean.parseBoolean(monsterValues[8]));
            rooms.get(rooms.size() - 1).addMonster(monster);
        } else if (line.startsWith("VENDING MACHINE|")) {
            String vendingData = line.split("\\|")[1].trim();
            if (!vendingData.equals("none")) {
                VendingMachine vm = new VendingMachine(vendingData, rooms.get(rooms.size() - 1).getRoomId());
                rooms.get(rooms.size() - 1).setVendingMachine(vm);
            }
        } else if (line.startsWith("Monster ITEM|")) {
            String itemData = line.split("\\|")[2].trim();
            String[] itemValues = itemData.split(",");
            Item item = returnItem(itemValues[0], itemValues[1], itemValues[2], itemValues[3],
                    Integer.parseInt(itemValues[4]), itemValues[5], itemValues[6], Integer.parseInt(itemValues[7]));
            String monsterId = line.split("\\|")[1].trim();
            for (Room room : rooms) {
                for (Monster monster : room.getMonster()) {
                    if (monster.getCharaterID().equals(monsterId)) {
                        monster.getInventory().add(item);
                    }
                }
            }
        } else if (line.startsWith("VendingMachine ITEM|")) {
            String itemData = line.split("\\|")[1].trim();
            String[] itemValues = itemData.split(",");
            Item item = returnItem(itemValues[0], itemValues[1], itemValues[2], itemValues[3],
                    Integer.parseInt(itemValues[4]), itemValues[5], itemValues[6], Integer.parseInt(itemValues[7]));
            rooms.get(rooms.size() - 1).getVendingMachine().addItem(item, Integer.parseInt(itemValues[7]));
        } else if (line.equals("ENDROOM")) {
            // nothing to do here - room already completed
        }
    }

    public void checkpoint(Player player) {
        try (PrintWriter output = new PrintWriter(saveFile)) {
            savePlayerSection(output, player);
            savePlayerInventorySection(output, player);
            saveRoomsSection(output);
            System.out.println("Game saved successfully.");
        } catch (Exception e) {
            System.out.println("Error saving game.");
        }
    }

}