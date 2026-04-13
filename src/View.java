public class View {
    //fields
    //constructor
    public View(){}
    //methods
    public void display(String str){
        System.out.println(str);
    }
    public void displayStatus(String health,int attack,int weapon,int defense, int vials, int coins){
        System.out.println("HP: " + health + "  \n" +
        "Attack: " + attack + " | ATK Bonus: (+" + weapon +") | Defense: " + defense + "\n" +
        "Vials: " + vials + "/5 | Coins: " + coins);
    }
    public void navUI(){}
    public void monsterUI(String health,String monster,String monsterHealth){
        System.out.println("Your HP: " + health + "\n" +
                           monster+"'s HP: " + monsterHealth + "\n" +
                "Commands: Attack, Heavy Attack, Defend,Dodge, Retreat \n" +
                "--------------------------------\n" + "Action:");
    }
    public void puzzleUI(){}
    public void navHelp(int state){
        if(state == 1)
            System.out.println("Commands: \n" + "1.Status - Display current HP,stats,vial count, and money count\n" + "2.Inventory - View collected items" +
                "\n" + "3.Help - Show a list of available commands\n" + "4.Checkpoint - Saves checkpoint\n" + "5.Show map - Displays the map and the player's location\n"
        + "6.Escape - Escape the school(only usable with all vials)\n" + "7.Save Game - Saves gameplay\n" + "8.Load Game - Loads saved game or checkpoint\n" +
                "9.Quit - Exit the game\n" + "10.Restart - Restart gameplay");
        else
            System.out.println("Commands: \n" + "1.Attack - Use equipped weapon to deal damage to the enemy\n" + "2.Heavy Attack - Deal increased damage with a chance to miss");
    }
    public void showMap(String building) {
        if (building.equals("Building E")) {
            System.out.println(
                    "                            |Building E|                                       \n" +
                            " [   Game Room   ] -------- [   Kitchen    ] ------  [The Dining Hall] \n" +
                            "       |                          |                        |\n" +
                            " [Meditation Room] -------- [Student Lounge] ------  [  Campus Cafe  ] -----> Library");
        }
        else{
            System.out.println("You good");
        }
    }

}
