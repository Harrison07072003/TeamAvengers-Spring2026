public class GameResult {
    private final String message;
    private final boolean finished;

    public GameResult(String message, boolean finished) {
        this.message = message;
        this.finished = finished;
    }

    public String getMessage() {
        return message;
    }

    public boolean isFinished() {
        return finished;
    }
}