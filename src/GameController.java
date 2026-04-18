import java.util.Scanner;

public class GameController {
    private final RoomMap map;
    private final Player player;
    private final View view;
    private boolean isRunning;
    private final Scanner input;

    public GameController() {
        view = new View();
        player = new Player("Player", 100, 10, 5, view);
        map = new RoomMap();
        input = new Scanner(System.in);
        isRunning = true;
    }

    public void run() {
        view.display("Welcome to the game!");
        showHelp();
        showRoom();
        while (isRunning) {
            view.display("Enter command:");
            String command = input.nextLine().trim();
            if (command.equalsIgnoreCase("quit")) {
                isRunning = false;
            } else {
                processCommand(command);
            }
        }

        input.close();
    }

    private void processCommand(String command) {
        if (command.isEmpty()) {
            view.display("Enter a command.");
            return;
        }

        String[] parts = command.split(" ", 2);
        String cmd = parts[0].toLowerCase();
        String arg = parts.length > 1 ? parts[1] : "";

        switch (cmd) {
            case "look":
                showRoom();
                break;
            case "inventory":
                showInventory();
                break;
            case "help":
                showHelp();
                break;
            case "status":
                showStatus();
                break;
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
            player.useItem(item);
        } else {
            view.display("Item not found in inventory.");
        }
    }

    private void pickUpItem(String itemName) {
        if (itemName.isEmpty()) {
            view.display("Please specify an item to pick up.");
            return;
        }

        Room currentRoom = map.getCurrentRoom();
        Item item = currentRoom.findItem(itemName);
        if (item != null) {
            if (!player.addItem(item)) {
                view.display("Inventory full. Use or drop an item before picking up another.");
                return;
            }

            currentRoom.removeItem(item);
            view.display("Picked up " + itemName + ": " + item.getItem_Description());
        } else {
            view.display("No " + itemName + " in the room.");
        }
    }

    private void storeItem(String itemName) {
        pickUpItem(itemName);
    }

    private void combineItems(String args) {
        String[] parts = args.split(" ", 2);
        if (parts.length < 2) {
            view.display("Please specify two items to combine.");
            return;
        }
        String item1 = parts[0];
        String item2 = parts[1];

        // Special case for Cure Vital
        if ((item1.equalsIgnoreCase("cure") && item2.equalsIgnoreCase("vital")) ||
            (item1.equalsIgnoreCase("vital") && item2.equalsIgnoreCase("cure"))) {
            int count = 0;
            for (Item item : player.getInventory()) {
                if (item.getItem_Name().equalsIgnoreCase("Cure Vital")) {
                    count++;
                }
            }
            if (count >= 5) {
                // Remove 5 vials
                int removed = 0;
                for (int i = player.getInventory().size() - 1; i >= 0 && removed < 5; i--) {
                    Item item = player.getInventory().get(i);
                    if (item.getItem_Name().equalsIgnoreCase("Cure Vital")) {
                        player.removeItem(item);
                        removed++;
                    }
                }
                // Add cure
                QuestItem cure = new QuestItem("A13", "Cure", "A powerful remedy that can cure the plague", "quest", 0);
                player.addItem(cure);
                view.display("Combined 5 Cure Vital vials into the Cure!");
            } else {
                view.display("You need at least 5 Cure Vital vials to create the Cure. You have " + count + ".");
            }
            return;
        }

        Tool combinedItem = player.combineItems(item1, item2);
        if (combinedItem == null) {
            view.display("These items cannot be combined.");
            return;
        }

        view.display("Combined into " + combinedItem.getItem_Name() + ".");
    }

    private void buyFood() {
        Room room = map.getCurrentRoom();
        if (!room.hasVendingMachine()) {
            view.display("No vending machine in this room.");
            return;
        }

        player.buyFood(room.getVendingMachine(), "food");
    }

    private void consumeItem(String itemName) {
        if (itemName.isEmpty()) {
            view.display("Please specify an item to consume.");
            return;
        }

        Item item = player.getItem(itemName);
        if (item instanceof Consumable) {
            player.useItem(item);
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
        if (item instanceof Weapon) {
            view.display(player.equipWeapon((Weapon) item));
        } else if (item != null) {
            view.display(weaponName + " is not a weapon.");
        } else {
            view.display("No weapon available.");
        }
    }

    private void showRoom() {
        Room room = map.getCurrentRoom();
        view.display("Current room: " + room.getName());
        if (room.hasVendingMachine()) {
            view.display("A vending machine is here. Use `buy food` to purchase food.");
        }

        if (room.getInventory().isEmpty()) {
            view.display("No items are in the room.");
            return;
        }

        view.display("Items in the room:");
        for (Item item : room.getInventory()) {
            view.display("- " + item.getItem_Name() + " [" + item.getItem_type() + "]");
        }
    }

    private void showInventory() {
        if (player.getInventory().isEmpty()) {
            view.display("Your inventory is empty.");
            return;
        }

        view.display("Inventory (" + player.getInventory().size() + "/" + player.getInventoryCapacity() + "):");
        for (Item item : player.getInventory()) {
            view.display("- " + item.getItem_Name() + " [" + item.getItem_type() + "]");
        }
    }

    private void showStatus() {
        view.displayStatus(player.getHP(), player.getATK(), player.getDEF(), 0, player.getCoins());
        if (player.getEquippedWeapon() != null) {
            view.display("Equipped weapon: " + player.getEquippedWeapon().getItem_Name());
        }
    }

    private void showHelp() {
        view.display("Commands: look, inventory, status, pick <item>, drop <item>, use <item>, consume <item>, equip <weapon>, combine <item1> <item2>, buy food, help, quit");
    }

    public void battle() {
        view.display("Battle started");
    }

    public void puzzle() {
        view.display("Puzzle started");
    }

    public void restartGame() {
        view.display("Game restarted");
    }

    public void escapeGame() {
        isRunning = false;
        view.display("Game escaped");
    }
}
