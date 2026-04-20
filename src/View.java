public class View {
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