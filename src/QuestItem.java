// Quest items carry a quest type token that Player.useItem(...) interprets.
public class QuestItem extends Item {
    private String questType;

    public QuestItem(String itemId, String itemName, String category,
                 String description, int value, String roomLocation,String location,int price) {
        super(itemId, itemName, category, description,value, roomLocation, location, price);
    }

    public String getQuestType() {
        return questType;
    }
    public void setQuestType(String questType) {
        this.questType = questType;
    }
    @Override
    // Quest items are the only current items that opt into the generic use flow.
    public boolean canUse() {
        return true;
    }
}
