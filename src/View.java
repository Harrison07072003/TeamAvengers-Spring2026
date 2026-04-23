public class View {
    public View() {
    }

    public void display(String str) {
        System.out.println(str);
    }

    public void showCommandPrompt(String roomName) {
        System.out.println(roomName + "\nEnter a command:");
    }
}
