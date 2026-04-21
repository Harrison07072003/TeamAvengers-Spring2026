import java.util.ArrayList;
public class RoomMap  {
    //fields
    ArrayList<Room> rooms;
    //constructor
    public RoomMap(){
        this.rooms = new ArrayList<>();
    }


    //Takes in the current room number and is able to get the actual room data
    public Room getRoom(String num) {
        int roomNumber = Integer.parseInt(num.substring(1));
        return rooms.get(roomNumber-1);
    }
    public ArrayList<Room> getRooms(){
        return this.rooms;
    }
}