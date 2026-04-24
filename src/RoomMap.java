import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.sql.Timestamp;

public class RoomMap {
    // fields
    private ArrayList<Room> rooms;

    private String roomsFile;
    private String puzzlesFile;
    private String monstersFile;
    private String itemsFile;
    private String vendingMachinesFile;
    private String saveFile;
    private String checkpointFile;
    private String actualLoadFile;

    public RoomMap(String roomsf,String puzzles, String monsters, String items, String vendingMachines) {
        rooms = new ArrayList<>();
        roomsFile = roomsf;
        puzzlesFile = puzzles;
        monstersFile = monsters;
        itemsFile = items;
        vendingMachinesFile = vendingMachines;
        saveFile = "saveFile.txt";
        checkpointFile = "checkpointFile.txt";
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
                if(room.getRoomId().equals("R17")){ //if prof office -jocelin
                    room.setLocked(true);
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
                monsterId = values[0].trim();
                monsterName = values[1].trim();
                monsterDescription = values[2].trim();
                hp = Integer.parseInt(values[3].trim());
                attack = Integer.parseInt(values[4].trim());
                defense = Integer.parseInt(values[5].trim());
                coins = Integer.parseInt(values[6].trim());
                roomId = values[7].trim();
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
                category = values[2];
                itemDescription = values[3];
                value = Integer.parseInt(values[4]);
                roomLocation = values[5];
                location = values[6];
                price = Integer.parseInt(values[7]);
                Item item = returnItem(itemId, itemName, category, itemDescription, value, roomLocation,location,price);
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
    public boolean saveExists() {
        File save = new File(saveFile);
        File checkpoint = new File(checkpointFile);
        return save.exists() || checkpoint.exists();
    }
    public ArrayList<Room> getRooms(){
        return this.rooms;
    }
    public Room getRoom(String num){
        int roomNumber = Integer.parseInt(num.substring(1));
        return rooms.get(roomNumber-1);
    }
    private Item returnItem(String itemId, String itemName, String category, String itemDescription, int value, String roomLocation,String location,int price){
        if(category.equals("Weapon")){
            return new Weapon (itemId, itemName, category, itemDescription, value, roomLocation, location, price);
        }
        else if(category.equals("Tool")){
            return new Tool (itemId, itemName, category, itemDescription, value, roomLocation, location, price);
        }
        else if(category.equals("Consumable")){
            return new Consumable (itemId, itemName, category, itemDescription, value, roomLocation, location, price);
        }
        else if(category.equals("QuestItem")){
            return new QuestItem(itemId, itemName, category, itemDescription, value, roomLocation, location, price);
        }
        return null;
    }

    public String saveGame(Player player) {
        try (PrintWriter output = new PrintWriter(saveFile)) {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            output.println("Current Timestamp: " + timestamp);
            savePlayerSection(output, player);
            savePlayerInventorySection(output, player);
            saveRoomsSection(output);
            return "Game saved successfully.\n";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error saving game." + e.getMessage() + "\n";
        }
    }


    public void setActualLoadFile(String save,String checkpoint) {
        File file1 = new File(save);
        File file2 = new File(checkpoint);
        if(!file1.exists() && !file2.exists()){
            return;
        }
        if(file1.exists() && file2.exists()){
            if(file1.lastModified() > file2.lastModified()){
                actualLoadFile = save;
            }
            else{
                actualLoadFile = checkpoint;
            }
        }
        else if(file1.exists()){
            actualLoadFile = save;
        }
        else{
            actualLoadFile = checkpoint;
        }


    }

    public String loadGame(Player player) {
        this.setActualLoadFile(saveFile,checkpointFile);
        if (actualLoadFile == null || actualLoadFile.isEmpty()) {
            return "No saved game found.\n";
        }
        rooms.clear();
        player.getInventory().clear();
        File save = new File(actualLoadFile);
        if (!save.exists()) {
            return "No saved game found.\n";
        }
        //compare timestamps and load most recent
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
            return "No saved game found\n.";
        }
        return "Game loaded successfully.\n";
    }

    private void savePlayerSection(PrintWriter output, Player player) {
        output.println("PLAYER");
        output.println(player.getCharaterID() + "," +
                player.getCurrentHP() + "," +
                player.getAttack() + "," +
                player.getDefense() + "," +
                player.getCoins() + "," +
                player.getVialCount() + "," +
                player.getRoomID() + "," +
                player.getMaxHP() + "," +
                player.getPreviousRoom());
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

    // java
// in src/RoomMap.java - add helper and replace methods below

    // helper to avoid breaking the '|' separator if field contains '|'
    private String sanitize(String s) {
        if (s == null) return "";
        return s.replace("|", "/"); // replace pipe with safe char
    }

    private void saveRoom(PrintWriter output, Room room) {
        // Use '|' as field delimiter so commas in descriptions/building don't break parsing
        output.println("ROOM|" + sanitize(room.getRoomId()) +
                "|" + sanitize(room.getRoomName()) +
                "|" + sanitize(room.getRoomDescription()) +
                "|" + sanitize(room.getBuilding()) +
                "|" + room.requiresValidFlashlight());
        output.println("EXITS|" + room.getExitsFileString());
        for (Item item : room.getInventory()) {
            output.println("Room ITEM|" + item.toFileString());
        }
        if (room.getPuzzle() != null) {
            output.println("PUZZLE|" + room.getPuzzle().toFileString());
            if (room.getPuzzle().getRewards() == null || room.getPuzzle().getRewards().isEmpty()) {
                output.println("PUZZLE REWARD|none");
            } else {
                output.println("PUZZLE REWARD|" + room.getPuzzle().getRewardsFileString());
            }
        } else {
            output.println("PUZZLE|none");
            output.println("PUZZLE REWARD|none");
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
                    monster.isAlive() + "," +
                    monster.getMaxHP());
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

// in handleRoomsLine(...) replace ROOM parsing branch with:


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
        player.setMaxHP(Integer.parseInt(values[7].trim()));
        player.setPreviousRoom(values[8].trim());
    }

    // --- START: Robust item parsing for load operations ---
    /**
     * Parse an item line coming from a toFileString() representation where the description
     * may contain commas. The format (fields) expected is:
     * id,name,description,category,value,roomLocation,location,price
     * description can contain commas, so this method reconstructs the description by
     * taking the variable-middle part between the fixed head and fixed tail fields.
     */
    // java
    private Item parseItemFromData(String itemData) {
        String[] parts = itemData.split(",");
        if (parts.length < 8) {
            return null; // not enough fields
        }
        int n = parts.length;
        int priceIndex = n - 1;
        int locationIndex = n - 2;
        int roomLocationIndex = n - 3;
        int valueIndex = n - 4;
        int categoryIndex = 2; // as requested: category is the third parameter (index 2)
        int descriptionStart = 3; // description begins at index 3
        int descriptionEnd = valueIndex - 1; // description ends just before the value field

        String itemId = parts[0].trim();
        String itemName = parts[1].trim();
        String category = parts[categoryIndex].trim();

        // Rebuild description which may contain commas
        StringBuilder desc = new StringBuilder();
        for (int i = descriptionStart; i <= descriptionEnd; i++) {
            if (i > descriptionStart) desc.append(",");
            desc.append(parts[i]);
        }
        String itemDescription = desc.toString().trim();

        try {
            int value = Integer.parseInt(parts[valueIndex].trim());
            String roomLocation = parts[roomLocationIndex].trim();
            String location = parts[locationIndex].trim();
            int price = Integer.parseInt(parts[priceIndex].trim());

            return returnItem(itemId, itemName, category, itemDescription, value, roomLocation, location, price);
        } catch (NumberFormatException e) {
            return null; // invalid numeric fields
        }
    }

    // --- END: Robust item parsing for load operations ---

    private void handlePlayerInventoryLine(String line, Player player) {
        if (line.startsWith("Player ITEM|")) {
            String itemData = line.split("\\|",2)[1].trim();
            Item item = parseItemFromData(itemData);
            if (item != null) player.getInventory().add(item);
        } else if (line.startsWith("EQUIPPED|")) {
            String equippedData = line.split("\\|",2)[1].trim();
            if (!equippedData.equals("none")) {
                Item equipped = parseItemFromData(equippedData);
                if (equipped != null && equipped instanceof Weapon) {
                    player.setEquippedWeapon((Weapon) equipped);
                }
            }
        }
    }

    private void handleRoomsLine(String line) {
        if (line.startsWith("ROOM|")) {
            // split into up to 6 parts: "ROOM", id, name, description, building, requiresFlashlight
            String[] roomValues = line.split("\\|", 6);
            if (roomValues.length < 6) {
                System.out.println("Skipping malformed ROOM line: " + line);
                return;
            }
            Room room = new Room(roomValues[1], roomValues[2], roomValues[3], roomValues[4],
                    Boolean.parseBoolean(roomValues[5]));
            rooms.add(room);
        } else if(line.startsWith("EXITS|")) {
            String exitsData = line.split("\\|",2)[1].trim();
            String[] exits = exitsData.split(",");
            // iterate in pairs: direction, targetRoom
            for(int i = 0; i + 1 < exits.length; i += 2){
                rooms.get(rooms.size() - 1).addExit(exits[i].trim(), exits[i+1].trim());
            }
        }
        else if (line.startsWith("Room ITEM|")) {
            String itemData = line.split("\\|",2)[1].trim();
            Item item = parseItemFromData(itemData);
            if (item != null) rooms.get(rooms.size() - 1).addItem(item);
        } else if (line.startsWith("PUZZLE|")) {
            String puzzleData = line.split("\\|",2)[1].trim();
            if (!puzzleData.equals("none")) {
                String[] puzzleValues = puzzleData.split(";");
                Puzzle puzzle = new Puzzle(puzzleValues[0], puzzleValues[1], puzzleValues[2], puzzleValues[3],
                        puzzleValues[4], puzzleValues[5], puzzleValues[6], Integer.parseInt(puzzleValues[7]));
                puzzle.setSolved(Boolean.parseBoolean(puzzleValues[8]));
                rooms.get(rooms.size() - 1).setPuzzle(puzzle);
            }
        } else if (line.startsWith("PUZZLE REWARD|")) {
            String rewardsData = line.split("\\|",2)[1].trim();
            if (rewardsData.equals("none")) {
                // nothing to add
                return;
            }
            String[] rewards = rewardsData.split(";");
            // ensure puzzle exists before attempting to add rewards
            Puzzle roomPuzzle = rooms.get(rooms.size() - 1).getPuzzle();
            if (roomPuzzle == null) return;
            for (int i = 0; i < rewards.length; i++) {
                Item reward = parseItemFromData(rewards[i].trim());
                if (reward != null) roomPuzzle.getRewards().add(reward);
            }
        } else if (line.startsWith("MONSTER|")) {
            String monsterData = line.split("\\|",2)[1].trim();
            String[] monsterValues = monsterData.split(",");
            Monster monster = new Monster(monsterValues[0], monsterValues[1], monsterValues[2],
                    Integer.parseInt(monsterValues[3]), Integer.parseInt(monsterValues[4]),
                    Integer.parseInt(monsterValues[5]), Integer.parseInt(monsterValues[6]), monsterValues[7]);
            monster.setAlive(Boolean.parseBoolean(monsterValues[8]));
            monster.setMaxHP(Integer.parseInt(monsterValues[9]));
            rooms.get(rooms.size() - 1).addMonster(monster);
        } else if (line.startsWith("VENDING MACHINE|")) {
            String vendingData = line.split("\\|",2)[1].trim();
            if (!vendingData.equals("none")) {
                VendingMachine vm = new VendingMachine(vendingData, rooms.get(rooms.size() - 1).getRoomId());
                rooms.get(rooms.size() - 1).setVendingMachine(vm);
            }
        } else if (line.startsWith("Monster ITEM|")) {
            // format: Monster ITEM|<monsterId>|<item toFileString>
            String[] split = line.split("\\|",3);
            String monsterId = split[1].trim();
            String itemData = split[2].trim();
            Item item = parseItemFromData(itemData);
            if (item != null) {
                for (Room room : rooms) {
                    for (Monster monster : room.getMonster()) {
                        if (monster.getCharaterID().equals(monsterId)) {
                            monster.getInventory().add(item);
                        }
                    }
                }
            }
        } else if (line.startsWith("VendingMachine ITEM|")) {
            String itemData = line.split("\\|",2)[1].trim();
            Item item = parseItemFromData(itemData);
            if (item != null && rooms.get(rooms.size() - 1).getVendingMachine() != null) {
                rooms.get(rooms.size() - 1).getVendingMachine().addItem(item, item.getPrice());
            }
        } else if (line.equals("ENDROOM")) {
            // nothing to do here - room already completed
        }
    }

    public String checkpoint(Player player) {
        try (PrintWriter output = new PrintWriter(checkpointFile)) {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            output.println("Current Timestamp: " + timestamp);
            savePlayerSection(output, player);
            savePlayerInventorySection(output, player);
            saveRoomsSection(output);
            return "Checkpoint saved successfully.";
        } catch (Exception e) {
            System.out.println("Error saving checkpoint.");
        }
        return "Error saving checkpoint.";
    }

}
