public class Player {
    private String currentRoomId;
    private int coins;

    public Player() {
        currentRoomId = null;
        coins = 15;
    }

    public boolean enterRoom(String roomId, RoomMap map) {
        if (roomId == null || map == null) {
            return false;
        }

        Room room = map.getRoom(roomId.trim().toUpperCase());
        if (room == null) {
            return false;
        }

        currentRoomId = room.getRoomId();
        return true;
    }

    public Room getCurrentRoom(RoomMap map) {
        if (map == null || currentRoomId == null) {
            return null;
        }
        return map.getRoom(currentRoomId);
    }

    public void addCoins(int amount) {
        if (amount > 0) {
            coins += amount;
        }
    }

    public int getCoins() {
        return coins;
    }
}