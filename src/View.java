public class View {
    //fields
    //constructor
    public View(){}
    //methods
    public void display(String str){
        System.out.println(str);
    }
    public void navUI(String RoomName){
        System.out.println(RoomName +"\nCommands Available: Status, Inventory, Map, Inspect, Equip, Quit, Help" +"\nEnter a command:");
    }

    public void monsterUI(String health,String monster,String monsterHealth){
        System.out.println("Your HP: " + health + "\n" +
                           monster+"'s HP: " + monsterHealth + "\n" +
                "Commands: Attack, Heavy Attack, Defend, Dodge, Check Weapon, Retreat \n" +
                "--------------------------------\n" + "Action:");
    }

    public void puzzleUI(){}
    public void navHelp(int state){
        if(state == 1)
            System.out.println("Commands: \n" + "1.Status - Display current HP,stats,vial count, and money count\n" + "2.Inventory - View collected items" +
                "\n" + "3.Help - Show a list of available commands\n" + "4.Checkpoint - Saves checkpoint\n" + "5.Show map - Displays the map and the player's location\n"
        + "6.Escape - Escape the school(only usable with all vials)\n" + "7.Save Game - Saves gameplay\n" + "8.Load Game - Loads saved game or checkpoint\n" +
                "9.Quit - Exit the game\n" + "10.Restart - Restart gameplay\n----------------------------");
        else if(state == 2)
            System.out.println("Commands: \n" + "1.Attack - Attack the monster with a weapon\n" + "2.Heavy Attack - Attack a monster with more damage, but with a chance to miss\n"
            +"3.Defend - Defend against an attack from a monster\n" + "4.Dodge - Chance to dodge monster attack\n" +"5.Check Weapon - View the stats of the currently held weapon\n" + "6.Retreat - Flee from the monster\n--------------------");
    }
    public void showMap(String building) {
        if (building.equals("E Building")) {
            System.out.println(
                            "                         |Building E|\n" +
                            " [Game Room] -------- [Kitchen] -------- [Dining Hall]\n" +
                            "      |                    |                   |\n" +
                            " [Meditation Room] --- [Student Lounge] --- [Campus Cafe] -----> Library"
            );
        } else if (building.equals("Library")) {
            System.out.println(
                            "Building E <------        |Library|\n" +
                            "  [Study Room]                              -----[Server Room]-----> Parking Lot\n" +
                            "         |                                  |            |\n" +
                            "         -------[Library Lobby]----[Computer Classroom]  |\n" +
                            "                          |                 |            |\n" +
                            "[Electric Control Room]----                 ----[Malfunctioning Elevators]"
            );
        } else if (building.equals("H Building")) {
            System.out.println(
                            "Library<-----             |H Building|\n" +
                            " [Chemistry Lab] ---- [Professor Office] ---- [Chemistry Lab 2]\n" +
                            "        |                    |                      |\n" +
                            "        |                    |            [Digital Media Lab]\n" +
                            "        |                    |                      |\n" +
                            " [Health Lab] -------- [Bathroom] -------- [Janitor's Closet]\n" +
                            "        |\n" +
                            "        |\n" +
                            "Parking Lot"
            );
        } else if (building.equals("Parking Lot")) {
            System.out.println(
                            "                        H Building\n" +
                            "                             ^\n" +
                            "                             |\n" +
                            "                              \n" +
                                    "Library<------     |Parking Lot|"
            );
        } else {
            System.out.println("Map unavailable.");
        }
    }
}
    public void showCredits(){
        System.out.println("Teams Involved\n----------------\nCreative Team: Team Cobra\nDevelopment Team: Team Avengers\n" +
                "\nDevelopers\n----------------\nTeam Manager:Jocelin Mendoza\nTechnical Lead:Harrison Allen\nTesting Lead:José Muñoz-Suastegui" +
                "\nData Lead:Asliee Pena Cabrera\nRequirements & Documentation Lead:Gethsemane Gonzalez Cirilo");
    }

}