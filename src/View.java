public class View {
    //fields
    //constructor
    public View(){

    }
    //methods
    public void display(String str){
        System.out.println(str);
    }

    public void displayStatus(String health, int attack, int defense, int vials, int coins) {
        System.out.println("Health: " + health);
        System.out.println("Attack: " + attack);
        System.out.println("Defense: " + defense);
        System.out.println("Vials: " + vials);
        System.out.println("Coins: " + coins);
    }

    public void navUI() {
        System.out.println("Navigation UI");
    }

    public void MonsterUI() {
        System.out.println("Monster UI");
    }

    public void puzzleUI() {
        System.out.println("Puzzle UI");
    }

    public void navHelp() {
        System.out.println("Navigation Help");
    }

    public void showMap() {
        System.out.println("Showing map");
    }
}
