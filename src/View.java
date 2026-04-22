public class View {

    public void showTitle() {
        System.out.println("====================================");
        System.out.println("         GGC PLAGUE PUZZLE");
        System.out.println("====================================");
    }

    public void showHelp() {
        System.out.println("Commands:");
        System.out.println("explore puzzle");
        System.out.println("solve puzzle");
        System.out.println("ignore puzzle");
        System.out.println("status");
        System.out.println("quit");
        System.out.println();
    }

    public void showPrompt() {
        System.out.print("Enter command: ");
    }

    public void display(String message) {
        System.out.println(message);
        System.out.println();
    }
}