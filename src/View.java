// Minimal console view for printing text to the terminal.
public class View {
    public View() {
    }

    // General-purpose output helper used by the controller.
    public void display(String str) {
        System.out.println(str);
    }

    // Keeps the current room name attached to the command prompt.
    public void showCommandPrompt(String roomName) {
        System.out.println(roomName + "\nEnter a command:");
    }
}
