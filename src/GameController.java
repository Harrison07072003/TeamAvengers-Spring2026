import java.util.Scanner;

public class GameController {

    private final RoomMap map;
    private final Player player;
    private final View view;
    private final Scanner input;
    private boolean isRunning;

    public GameController() {
        view = new View();
        player = new Player("Player", 100, 10, 5);
        input = new Scanner(System.in);
        isRunning = true;

        map = new RoomMap(); // assuming you initialize rooms elsewhere
    }

    //  MAIN LOOP
    public void start() {
        while (isRunning) {
            view.display("\nEnter command:");
            String command = input.nextLine();
            processCommand(command);
        }
    }

    //  COMMAND PROCESSOR

    private void processCommand(String command) {
        if (command == null || command.isEmpty()) {
            view.display("Enter a command.");
            return;
        }

        String[] parts = command.split(" ", 2);
        String cmd = parts[0].toLowerCase();
        String arg = parts.length > 1 ? parts[1] : "";

        switch (cmd) {
            case "inventory": showInventory();
            break;
            case "drop": dropItem(arg);
            break;
            case "use": useItem(arg);
            break;
            case "pick":
            case "store": pickUpItem(arg);
            break;
            case "combine": combineItems(arg);
            break;
            case "buy": buyFood();
            break;
            case "consume": consumeItem(arg);
            break;
            case "equip": equipWeapon(arg);
            break;
            default: view.display("Unknown command.");
        }
    }
    private Room getCurrentRoom() {
        return map.getRoom(Player.getroomLocation());
    }

    // ACTIONS

    private void dropItem(String Name) {

        if (Name.isEmpty()) {
            view.display("Specify item to drop.");
            return;
        }

        Item item = player.dropItem(Name);

        if (item == null) {
            view.display("You don't have that item.");
            return;
        }

        getCurrentRoom().addItem(item);
        view.display("Dropped " + item.getName());
    }

    private void useItem(String itemName) {
        Item item = player.getItem(itemName);

        if (itemName.isEmpty()) {
            view.display("Specify item to use.");
            return;
        }

        if (item == null) {
            view.display("Item not found.");
            return;
        }

        if (!player.useItem(item)) {
            view.display("You can't use that item.");
        }
    }

    private void pickUpItem(String itemName) {

        Room room = getCurrentRoom();

        Item item = room.findItem(itemName);

        if (item == null) {
            view.display("Item not in room.");
            return;
        }

        if (!player.addItem(item)) {
            view.display("Inventory full.");
            return;
        }

        room.removeItem(item);
        view.display("Picked up " + item.getName());
    }

    // COMBINE

    private void combineItems(String args) {

        String[] parts = args.split(" ", 2);

        if (parts.length < 2) {
            view.display("Specify two items.");
            return;
        }

        Tool result = player.combineItems(parts[0], parts[1]);

        if (result == null) {
            view.display("Cannot combine these items.");
        } else {
            view.display("Created " + result.getName());
        }
    }

    // BUY

    private void buyFood() {

        Room room = getCurrentRoom();

        if (!room.hasVendingMachine()) {
            view.display("No vending machine here.");
            return;
        }
        if (player.buyFood(room.getVendingMachine(), "food")) {
            view.display("Food purchased.");
        } else {
            view.display("Purchase failed.");
        }
    }

    //  CONSUME

    private void consumeItem(String itemName) {

        Item item = player.getItem(itemName);

        if (item == null) {
            view.display("Item not found.");
            return;
        }

        if (!(item instanceof Consumable)) {
            view.display("Not consumable.");
            return;
        }

        player.useItem(item);
        player.removeItem(item);
        view.display("Consumed " + item.getName());
    }

    //  EQUIP

    private void equipWeapon(String weaponName) {

        Item item = player.getItem(weaponName);

        if (!(item instanceof Weapon)) {
            view.display("Not a weapon.");
            return;
        }

        boolean result = player.equipWeapon((Weapon) item);

        if (result) {
            view.display("Equipped " + item.getName());
        } else {
            view.display("Cannot equip weapon.");
        }
    }

    // INVENTORY

    private void showInventory() {
        player.showInventory(view);
    }
}