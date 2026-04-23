import java.util.Scanner;

public class GameController {

    // private final RoomMap map;
    private final Player player;
    private final View view;
    private final Scanner input;
    private boolean isRunning;

    public GameController() {
        view = new View();
        player = new Player("Player", 100, 10, 5);
        input = new Scanner(System.in);
        isRunning = true;
    }

    //  MAIN LOOP
    public void run() {
        while (isRunning) {
            String command = input.nextLine();
            if(command.equals("drop"))
                view.display(player.dropItem(Item.getName()));
            else if(command.equals("use"))
                view.display(player.useItem(Item.getName()));
            else if(command.equals("pick up")){
                view.display(player.pickUpItem(Item.getName()));
            else if(command.equals("store")){
                view.display(player.storeItem(player.getItem()));
            else if(command.equals("remove")){
                view.display(player.removeItem(Item.getName()));
            else if(command.equals("combine")){
                view.display(player.combineItems(Item.getName(), Item.getName()));
            else  if(command.equals("buy food")){
                view.display(player.buyFood(,Item.getName()));
            else if(command.equals("consume")){
                view.display(player.consumeFood(Item.getName));
            else if(command.equals("equip")){
                view.display(player.equipWeapon(Item.getName()));
            }
        }
    }


}