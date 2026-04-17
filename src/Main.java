import java.util.List;

public class Main {
   /* String a = "Gethsemane";
    String b = "Jose Munoz-suastegui";
    String c = "Harrison";
    String d = "Asliee";
    String e = "Jocelin";
    */
    public static void main(String[] args) {
        View view = new View();
        try {
            List<Item> items = Item.loadItemsFromFile("Item.txt");
            for (Item item : items) {
                view.display(item.toString());
            }
        } catch (Exception e) {
            view.display("Error: " + e.getMessage());
        }
    }
}