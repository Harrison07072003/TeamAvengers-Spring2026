import java.util.Scanner;

public class GameController {
    private final View view;
    private final Scanner input;
    private boolean isRunning;
    private final GameEngine engine;

    public GameController() {
        this.view = new View();
        this.input = new Scanner(System.in);
        this.isRunning = true;
        this.engine = new GameEngine();
    }

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

            if (cmd.equals("quit")) {
                isRunning = false;
            } else if (cmd.equals("inventory")) {
                view.display(engine.getPlayer().showInventory());
            } else if (cmd.equals("drop")) {
                view.display(engine.getPlayer().dropItem(arg, engine.getCurrentRoom()));
            } else if (cmd.equals("pick") || cmd.equals("take")) {
                view.display(engine.getPlayer().pickUpItem(arg, engine.getCurrentRoom()));
            } else if (cmd.equals("use")) {
                view.display(engine.getPlayer().useItem(arg));
            } else if (cmd.equals("consume")) {
                view.display(engine.getPlayer().consumeItem(arg));
            } else if (cmd.equals("equip")) {
                view.display(engine.getPlayer().equipWeapon(arg));
            } else if (cmd.equals("combine")) {
                view.display(engine.getPlayer().combineItems(arg));
            } else if (cmd.equals("buy")) {
                view.display(engine.getPlayer().buyItem(arg, engine.getCurrentRoom()));
            } else {
                view.display(engine.navCommand(command));
            }
        }

        view.display("You have left the game.");
    }
}
