import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

public class GameMap {
    public HashMap<String, Room> rooms; //public for testing purposes

    public GameMap() {
        rooms = new HashMap<>();
    }

    public Room getRoom(String roomId) {
        return rooms.get(roomId);
    }


    public void loadRooms(String filename) {
        try {
            Scanner scan = new Scanner(new File(filename));
            while (scan.hasNextLine()) {
                String line = scan.nextLine();

                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                String[] parts = line.split("\\|");

                //# id | name | description | building | exits NESW | requires flashlight
                String roomId = parts[0];
                Room room = new Room(roomId, parts[1], parts[2], parts[3], Boolean.parseBoolean(parts[5]));
                String compass = "NESW";
                String[] exits = parts[4].split(",");
                for (int i = 0; i < 4; i++) {
                    if (!exits[i].equals("0")) {
                        room.addExit(compass.charAt(i), exits[i]);
                    }
                }
                rooms.put(roomId, room);
            }
        }catch(Exception e){
            System.out.println("Error loading rooms: " + e.getMessage());
        }
    }
}