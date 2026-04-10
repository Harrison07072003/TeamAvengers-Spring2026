public class Main {
    String a = "Gethsemane";
    String b = "Jose Munoz-suastegui";
    String c = "Harrison";
    String d = "Asliee";
    String e = "Jocelin";

    public static void main(String[] args) {
        View view = new View();
        GameController gc = new GameController(view);
        gc.startGame();

    }
}