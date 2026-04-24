public class View {
    //fields
    //constructor
    public View(){}
    //methods
    public void display(String message) {
        System.out.println(message);
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
    public void navHelp(){
        System.out.println("Commands: \n" + "1.Enter Room - type 'enter <room#>'(ex: enter r19) allows you to travel between differnt rooms\n"
                + "2.Explore Room - type 'explore room' allows you to examine the room for puzzles,items,monsters, and vending machines\n"
                + "3.Inspect - type 'inspect' if monster you will be prompted to ignore or engage monster\n"
                + "4.Engage - type 'engage' if you want to do battle with the monster and enter combat after inspecting it\n"
                + "5.Ignore - type 'ignore' if you want to ignore a monster encounter after inspecting it\n"
                + "6.Explore Puzzle - type 'explore puzzle' if you want to see a puzzle and be prompted to solve or ignore\n"
                + "7.Solve Puzzle - type 'solve puzzle' if you want to be given a chance to solve the puzzle and put in answer\n"
                + "8.Ignore Puzzle - type 'ignore puzzle if you want to ignore the puzzle and come back to it later\n"
                + "9.Pickup Item - \n"
                + "10.Drop Item - \n"
                + "11.Use Item - \n"
                + "12.Store Item - \n"
                + "13.Combine Items - \n"
                + "14.Buy Food - \n"
                + "15.Consume Item - \n"
                + "16.Examine Item - \n"
                + "17.Equip Weapon - \n----------------------------");
    }
    public void help(int state){
        if(state == 1)
            System.out.println("Commands: \n" + "1.Status - Display current HP,stats,vial count, and money count\n" + "2.Inventory - View collected items" +
                "\n" + "3.navHelp - Show a list of available navigation/word interaction commands\n" + "4.Checkpoint - Saves checkpoint\n" + "5.Show map - Displays the map and the player's location\n"
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
                            " [R5|Game Room]    -------     [R18|Kitchen]     ----- [R8|Dining Hall]\n" +
                            "      |                             |                             |\n" +
                            " [R13|Meditation Room] ----- [R4|Student Lounge] ----- [R16|Campus Cafe] -----> Library(R11-Study Room)"
            );
        } else if (building.equals("Library")) {
            System.out.println(
                            "                                    |Library|\n" +
                            "Building E(R6)<----[R11|Study Room]                  [R12|Server Room]-----> Parking Lot(R20)\n" +
                            "                        |                            |            \n" +
                            "                   [R3|Library Lobby]----[R9|Computer Classroom]  \n" +
                            "                              |             |            \n" +
                            "[R14|Electric Control Room]----             ----[R7|Malfunctioning Elevators]"
            );
        } else if (building.equals("H Building")) {
            System.out.println(
                            "                         |H Building|\n" +
                            " [R1|Chemistry Lab] ---- [R17|Professor Office] ---- [R16|Chemistry Lab 2]\n" +
                            "        |                    |                                  |\n" +
                            "        |                    |                       [R15|Digital Media Lab]\n" +
                            "        |                    |                                  |\n" +
                            " [R19|Health Lab] -------- [R|10Bathroom] --------   [R2|Janitor's Closet]\n" +
                            "        |\n" +
                            "        |\n" +
                            "Parking Lot(R20)"
            );
        } else if (building.equals("Parking Lot")) {
            System.out.println(
                            "                        H Building(R19-Health Lab)\n" +
                            "                                      ^\n" +
                            "                                      |\n" +
                            "                                       \n" +
                            "Library(R12-Server Room)<------   |R20|Parking Lot|"
            );
        } else {
            System.out.println("Map unavailable.");
        }
        System.out.println("------------------------------");
    }

    public void showCredits(){
        System.out.println("Teams Involved\n----------------\nCreative Team: Team Cobra\nDevelopment Team: Team Avengers\n" +
                "\nDevelopers\n----------------\nTeam Manager:Jocelin Mendoza\nTechnical Lead:Harrison Allen\nData Lead:José Muñoz-Suastegui" +
                "\nTesting Lead:Asliee Pena Cabrera\nRequirements & Documentation Lead:Gethsemane Gonzalez Cirilo");
    }

}