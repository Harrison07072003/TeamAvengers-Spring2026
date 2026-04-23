// Application entry point for the console prototype.
public class Main {
    public static void main(String[] args) {
        View view = new View();
        try {
            // The controller owns the game loop once startup succeeds.
            GameController controller = new GameController();
            controller.run();
        } catch (Exception e) {
            // Console output is enough here because the program is entirely terminal-based.
            view.display("Error: " + e.getMessage());
        }

    }
}
