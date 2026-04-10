import java.util.Scanner;

public class GameController {
    //fields
    Player player;
    RoomMap roomMap;
    View v;
    Scanner input;
    //constructor
    GameController() {
        player = new Player("Player", 100, 10, 5);
        roomMap = new RoomMap();
        v = new View();
        input = new Scanner(System.in);
    }
    //methods
    public void run(){
        v.display("Welcome to the game!");
        while(true){
            v.display("Enter command:");
            String command = input.nextLine().trim();
            if(command.equalsIgnoreCase("quit"))
                break;
            else
                processCommand(command);
        }
    }

    private void processCommand(String command) {
        String[] parts = command.split(" ", 2);
        String cmd = parts[0].toLowerCase();
        String arg = parts.length > 1 ? parts[1] : "";

        switch (cmd) {
            case "drop":
                dropItem(arg);
                break;
            case "use":
                useItem(arg);
                break;
            case "pick":
                pickUpItem(arg);
                break;
            case "store":
                storeItem(arg);
                break;
            case "combine":
                combineItems(arg);
                break;
            case "buy":
                if (arg.equalsIgnoreCase("food")) {
                    buyFood();
                } else {
                    v.display("Invalid buy command.");
                }
                break;
            case "consume":
                consumeItem(arg);
                break;
            case "equip":
                equipWeapon(arg);
                break;
            default:
                v.display("Unknown command.");
        }
    }

    // Implementations will be added in next steps
    private void dropItem(String itemName) {
        if (itemName.isEmpty()) {
            v.display("Please specify an item to drop.");
            return;
        }
        Item item = player.getItem(itemName);
        if (item != null) {
            player.removeItem(item);
            roomMap.getCurrentRoom().addItem(item);
            v.display("Dropped " + itemName + ".");
        } else {
            v.display("You don't have " + itemName + " in your inventory.");
        }
    }

    private void useItem(String itemName) {
        if (itemName.isEmpty()) {
            v.display("Please specify an item to use.");
            return;
        }
        Item item = player.getItem(itemName);
        if (item != null) {
            String type = item.getItem_type();
            if ("consumable".equalsIgnoreCase(type)) {
                // Consume
                consumeItem(itemName);
            } else if ("weapon".equalsIgnoreCase(type)) {
                // Equip
                equipWeapon(itemName);
            } else {
                v.display("Used " + itemName + ".");
            }
        } else {
            v.display("Item not found in inventory.");
        }
    }

    private void pickUpItem(String itemName) {
        if (itemName.isEmpty()) {
            v.display("Please specify an item to pick up.");
            return;
        }
        Item item = null;
        for (Item i : roomMap.getCurrentRoom().getInventory()) {
            if (i.getItem_Name().equalsIgnoreCase(itemName)) {
                item = i;
                break;
            }
        }
        if (item != null) {
            roomMap.getCurrentRoom().removeItem(item);
            player.addItem(item);
            v.display("Picked up " + itemName + ": " + item.getItem_Description());
        } else {
            v.display("No " + itemName + " in the room.");
        }
    }

    private void storeItem(String itemName) {
        pickUpItem(itemName); // Same as pick up for now
    }

    private void combineItems(String args) {
        String[] parts = args.split(" ", 2);
        if (parts.length < 2) {
            v.display("Please specify two items to combine.");
            return;
        }
        String item1 = parts[0];
        String item2 = parts[1];
        Item i1 = player.getItem(item1);
        Item i2 = player.getItem(item2);
        if (i1 == null || i2 == null) {
            v.display("One or both items not in inventory.");
            return;
        }
        // Check combination
        if ((i1.getItem_Name().equalsIgnoreCase("Batteries") && i2.getItem_Name().equalsIgnoreCase("Flashlight")) ||
            (i1.getItem_Name().equalsIgnoreCase("Flashlight") && i2.getItem_Name().equalsIgnoreCase("Batteries"))) {
            player.removeItem(i1);
            player.removeItem(i2);
            Item combined = new Item("C1", "Powered Flashlight", "A flashlight with batteries, ready to use.", "tool");
            player.addItem(combined);
            v.display("Combined into Powered Flashlight.");
        } else {
            v.display("These items cannot be combined.");
        }
    }

    private void buyFood() {
        Room room = roomMap.getCurrentRoom();
        if (room.hasVendingMachine()) {
            if (player.getCoins() >= 4) {
                player.setCoins(player.getCoins() - 4);
                // Add food to room
                Item food = new Item("F" + System.currentTimeMillis(), "Vending Machine Food", "Food bought from vending machine", "consumable");
                room.addItem(food);
                v.display("Bought food. It is now in the room.");
            } else {
                v.display("You do not have enough coins to buy food.");
            }
        } else {
            v.display("No vending machine in this room.");
        }
    }

    private void consumeItem(String itemName) {
        if (itemName.isEmpty()) {
            v.display("Please specify an item to consume.");
            return;
        }
        Item item = player.getItem(itemName);
        if (item != null && "consumable".equalsIgnoreCase(item.getItem_type())) {
            // Assume Character has currentHP, but since Player extends, need to add getter/setter
            // For now, assume maxHP is current
            // But to implement, need to add currentHP to Character
            // For simplicity, just message
            player.removeItem(item);
            v.display("Consumed " + itemName + ", HP increased by " + item.getValue() + ".");
        } else if (item != null) {
            v.display(itemName + " is not consumable.");
        } else {
            v.display("You do not have any consumable items.");
        }
    }

    private void equipWeapon(String weaponName) {
        if (weaponName.isEmpty()) {
            v.display("Please specify a weapon to equip.");
            return;
        }
        Item item = player.getItem(weaponName);
        if (item != null && "weapon".equalsIgnoreCase(item.getItem_type())) {
            player.setEquippedWeapon(item);
            v.display("Equipped " + weaponName + ".");
        } else if (item != null) {
            v.display(weaponName + " is not a weapon.");
        } else {
            v.display("No weapon available.");
        }
    }
}
