public class QuestItem extends Item {

    private String questType; // office_key, cure_vital, cure

    public QuestItem(String id, String name, String description,
                     String location, String questType) {
        super(id, name, "quest", description, location, 0);
        this.questType = questType.toLowerCase();
    }

    public String getQuestType() {
        return questType;
    }

    public boolean use(Player player) {

        if (player == null) return false;

        switch (questType) {

            case "office_key":
                if (player.isOfficeUnlocked()) return false;
                player.setOfficeUnlocked(true);
                player.removeItem(this);
                return true;

            case "cure":
                if (player.isPlagueCured()) return false;
                player.setPlagueCured(true);
                player.removeItem(this);
                return true;

            case "cure_vital":
                return true; // hint item only

            default:
                return false;
        }
    }
}