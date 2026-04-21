import java.util.HashMap;

public class RoomMap {
    //fields
    private HashMap<String, Room> rooms;

    //constructor
    public RoomMap(){
        this.rooms = new HashMap<>();
    }

    //getters
    public Room getRoom(String roomId){
        return rooms.get(roomId);
    }

    //load rooms in this class??

}
