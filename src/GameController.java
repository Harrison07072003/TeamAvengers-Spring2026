import java.util.Scanner;

// Thin command loop that delegates game rules to Player and GameEngine.
public class GameController {
    private final View view;
    private final Scanner input;
    private boolean isRunning;
    private final GameEngine engine;

    // Wires together console IO and the current engine instance.
    public GameController() {
        this.view = new View();
        this.input = new Scanner(System.in);
        this.isRunning = true;
        this.engine = new GameEngine();
    }

    // Reads commands from the console and routes each one to the owning class.
    public void run() {
        while (isRunning) {
            view.showCommandPrompt(engine.getRoomName());
            String command = input.nextLine().trim();
            if (command.isEmpty()) {
                view.display("Enter a command.");
                continue;
            }

            String[] parts = command.split(" ", 2);
            String cmd = parts[0].toLowerCase();
            String arg = parts.length > 1 ? parts[1] : "";

            // Inventory-related commands are kept here as routing only; Player owns the behavior.
            if (cmd.equals("quit")) {
                isRunning = false;
            } else if (cmd.equals("inventory")) {
                view.display(engine.getPlayer().showInventory());
            } else if (cmd.equals("drop")) {
                engine.getPlayer().dropItem(arg);
                view.display(engine.getPlayer().getLastActionMessage());
            } else if (cmd.equals("pick") || cmd.equals("take")) {
                engine.getPlayer().pickUpItem(arg);
                view.display(engine.getPlayer().getLastActionMessage());
            } else if (cmd.equals("store")) {
                engine.getPlayer().storeItem(engine.getPlayer().getItem(arg));
                view.display(engine.getPlayer().getLastActionMessage());
            } else if (cmd.equals("use")) {
                engine.getPlayer().useItem(engine.getPlayer().getItem(arg));
                view.display(engine.getPlayer().getLastActionMessage());
            } else if (cmd.equals("consume")) {
                engine.getPlayer().consumeFood();
                view.display(engine.getPlayer().getLastActionMessage());
            } else if (cmd.equals("equip")) {
                Item item = engine.getPlayer().getItem(arg);
                if (item instanceof Weapon) {
                    engine.getPlayer().equipWeapon((Weapon) item);
                } else {
                    // Passing null keeps the controller simple while still letting Player own the message.
                    engine.getPlayer().equipWeapon(null);
                }
                view.display(engine.getPlayer().getLastActionMessage());
            } else if (cmd.equals("combine")) {
                engine.getPlayer().combineItems();
                view.display(engine.getPlayer().getLastActionMessage());
            } else if (cmd.equals("buy")) {
                engine.getPlayer().buyFood();
                view.display(engine.getPlayer().getLastActionMessage());
            } else {
                view.display(engine.navCommand(command));
            }
        }

        view.display("You have left the game.");
    }
}
