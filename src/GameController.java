import java.util.Scanner;

public class GameController {
    //fields
    RoomMap map;
    Player player;
    View view;
    boolean isRunning;
    Scanner input;
    //constructor
    GameController() {
        view = new View();
        player = new Player("Player", 100, 10, 5, view);
        map = new RoomMap();
        input = new Scanner(System.in);
        isRunning = true;
    }
    //methods
    public void run(){
        view.display("Welcome to the game!");
        while(isRunning){
            view.display("Enter command:");
            String command = input.nextLine().trim();
            if(command.equalsIgnoreCase("quit"))
                isRunning = false;
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
                    view.display("Invalid buy command.");
                }
                break;
            case "consume":
                consumeItem(arg);
                break;
            case "equip":
                equipWeapon(arg);
                break;
            default:
                view.display("Unknown command.");
        }
    }

    // Implementations will be added in next steps
    private void dropItem(String itemName) {
        if (itemName.isEmpty()) {
            view.display("Please specify an item to drop.");
            return;
        }
        Item item = player.getItem(itemName);
        if (item != null) {
            player.removeItem(item);
            map.getCurrentRoom().addItem(item);
            view.display("Dropped " + itemName + ".");
        } else {
            view.display("You don't have " + itemName + " in your inventory.");
        }
    }

    private void useItem(String itemName) {
        if (itemName.isEmpty()) {
            view.display("Please specify an item to use.");
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
                view.display("Used " + itemName + ".");
            }
        } else {
            view.display("Item not found in inventory.");
        }
    }

    private void pickUpItem(String itemName) {
        if (itemName.isEmpty()) {
            view.display("Please specify an item to pick up.");
            return;
        }
        Item item = null;
        for (Item i : map.getCurrentRoom().getInventory()) {
            if (i.getItem_Name().equalsIgnoreCase(itemName)) {
                item = i;
                break;
            }
        }
        if (item != null) {
            map.getCurrentRoom().removeItem(item);
            player.addItem(item);
            view.display("Picked up " + itemName + ": " + item.getItem_Description());
        } else {
            view.display("No " + itemName + " in the room.");
        }
    }

    private void storeItem(String itemName) {
        pickUpItem(itemName); // Same as pick up for now
    }

    private void combineItems(String args) {
        String[] parts = args.split(" ", 2);
        if (parts.length < 2) {
            view.display("Please specify two items to combine.");
            return;
        }
        String item1 = parts[0];
        String item2 = parts[1];
        Item i1 = player.getItem(item1);
        Item i2 = player.getItem(item2);
        if (i1 == null || i2 == null) {
            view.display("One or both items not in inventory.");
            return;
        }
        // Check combination
        if ((i1.getItem_Name().equalsIgnoreCase("Batteries") && i2.getItem_Name().equalsIgnoreCase("Flashlight")) ||
            (i1.getItem_Name().equalsIgnoreCase("Flashlight") && i2.getItem_Name().equalsIgnoreCase("Batteries"))) {
            player.removeItem(i1);
            player.removeItem(i2);
            Item combined = new Item("C1", "Powered Flashlight", "A flashlight with batteries, ready to use.", "tool", 0);
            player.addItem(combined);
            view.display("Combined into Powered Flashlight.");
        } else {
            view.display("These items cannot be combined.");
        }
    }

    private void buyFood() {
        Room room = map.getCurrentRoom();
        if (room.hasVendingMachine()) {
            if (player.getCoins() >= 4) {
                player.setCoins(player.getCoins() - 4);
                // Add food to room
                Item food = new Item("F" + System.currentTimeMillis(), "Vending Machine Food", "Food bought from vending machine", "consumable", 5);
                room.addItem(food);
                view.display("Bought food. It is now in the room.");
            } else {
                view.display("You do not have enough coins to buy food.");
            }
        } else {
            view.display("No vending machine in this room.");
        }
    }

    private void consumeItem(String itemName) {
        if (itemName.isEmpty()) {
            view.display("Please specify an item to consume.");
            return;
        }
        Item item = player.getItem(itemName);
        if (item != null && "consumable".equalsIgnoreCase(item.getItem_type())) {
            player.setHP(player.getHP() + item.getValue());
            player.removeItem(item);
            view.display("Consumed " + itemName + ", HP increased by " + item.getValue() + ".");
        } else if (item != null) {
            view.display(itemName + " is not consumable.");
        } else {
            view.display("You do not have any consumable items.");
        }
    }

    private void equipWeapon(String weaponName) {
        if (weaponName.isEmpty()) {
            view.display("Please specify a weapon to equip.");
            return;
        }
        Item item = player.getItem(weaponName);
        if (item != null && "weapon".equalsIgnoreCase(item.getItem_type())) {
            player.setEquippedWeapon((Weapon) item);
            view.display("Equipped " + weaponName + ".");
        } else if (item != null) {
            view.display(weaponName + " is not a weapon.");
        } else {
            view.display("No weapon available.");
        }
    }

    public void battle() {
        // Placeholder
        view.display("Battle started");
    }

    public void puzzle() {
        // Placeholder
        view.display("Puzzle started");
    }

    public void restartGame() {
        // Placeholder: Reset game state
        view.display("Game restarted");
    }

    public void escapeGame() {
        // Placeholder: End game
        isRunning = false;
        view.display("Game escaped");
    }
}
