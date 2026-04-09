import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;

public class RoomMap {
    //fields
    private ArrayList<Room> rooms;

    private String roomsFile;
    private String puzzlesFile;
    private String monstersFile;
    private String itemsFile;

    //one save file
    private String saveFile;


    public RoomMap(){
        rooms = new ArrayList<Room>();
        roomsFile = "data/rooms.txt";
        puzzlesFile = "data/puzzles.txt";
        monstersFile = "data/monsters.txt";
        itemsFile = "data/items.txt";
        saveFile = "data/saveGame.txt";
    }


        //empty methods
    public void generateRooms() {
        // this will read from rooms.txt
        // should create actual Room Objects

        try {
            Scanner input = new Scanner(new File(roomsFile));
            while (input.hasNextLine()) {
                String line = input.nextLine().trim();
                if (line.length() == 0) {
                    continue; // skip empty lines
                }
                rooms.add(line); //place holder, should create Room object and add to rooms list
            }
            input.close();
            System.out.println("Rooms loaded successfully.");
        } catch (FileNotFoundException e) {
            System.out.println("Error: rooms.txt file not found.");
        }
    }

    public void loadPuzzles(){
        //read from puzzles.txt
        //find room and assign puzzle data to that room

        try {
            Scanner input = new Scanner(new File(puzzlesFile));
            while (input.hasNextLine()) {
                String line = input.nextLine().trim();
                if (line.length() == 0) {
                    continue; // skip empty lines
                }
                //to do still:
                //parse puzzle line
                //find matching room
                //assign puzzle to that room

                System.out.println("Puzzle loaded: " + line); //placeholder, should assign to room
            }

            input.close();
            System.out.println("Puzzle loaded successfully.");
        } catch (FileNotFoundException e) {
            System.out.println("puzzles.txt file not found.");
        }
    }

    public void spawnMonsters(){
        //read from monsters.txt
        //find room and assign monster data to that room
    }

    public void loadItems(){
        //read from items.txt
        //find room and assign item data to that room
    }

    public String getRoom(String roomId){
        // should return Room object based on roomId
        return null;
    }

    public void saveGame(){
        //write current game state into saveGame.txt
    }

    public void loadGame(){
        //read from most recent saved game form ONE file
        //restore room/world/player related state from that file
    }


}
