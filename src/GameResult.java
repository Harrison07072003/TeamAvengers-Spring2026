import java.util.ArrayList;

public class GameResult {
    //fields
    private String message;
    private boolean status;
    private ArrayList<String> values;
    //constructor
    GameResult(){}
    //methods
    //getters and setters
    public String getMessage(){
        return this.message;
    }
    public void setMessage(String text){
        this.message = text;
    }
    public void resetMessage() {
        this.message = "";
    }
    public boolean getStatus(){
        return this.status;
    }
    public void setStatus(boolean update){
        this.status = update;
    }
    public void resetStatus(){
        this.status = false;
    }
}
