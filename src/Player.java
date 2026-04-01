public class Player extends Character {
    //fields
    private Room currentRoom;
    private int currentRoomNumber;
    //constructor
    public Player(String id, int maxHP, int attack, int defense) {
        super(id, maxHP, attack, defense);
        this.currentRoom = startingRoom;
    }


    public int getCurrentRoomNumber() {
        return this.currentRoomNumber;
    }

    public void setCurrentRoomNumber(int roomNumber) {
        this.currentRoomNumber = roomNumber;
    }

    //methods
    //load game, save game, view status, movement
    public void loadGame() {
        //load game logic
    }


    public void saveGame() {
        //save game logic
    }


    public void viewStatus() {
        //view status logic
    }


    public void move(String direction) {
        //movement logic
    }


}
