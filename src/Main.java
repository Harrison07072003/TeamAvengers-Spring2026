public class Main {
    public static void main(String[] args) {
        View view = new View();
        try {
            GameController controller = new GameController();
            controller.run();
        } catch (Exception e) {
            view.display("Error: " + e.getMessage());
        }
    }
}
