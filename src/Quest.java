// Quest items carry a quest type token that Player.useItem(...) interprets.
public class Quest extends Item {
    private String questType;

    public Quest(String itemId, String itemName, String description,
                     String location, String roomLocation, int value, int price,
                     String questType) {
        super(itemId, itemName, "Quest", description, location, roomLocation, value, price);
        this.questType = questType.toLowerCase();
    }

    public String getQuestType() {
        return questType;
    }

    @Override
    // Quest items are the only current items that opt into the generic use flow.
    public boolean canUse() {
        return true;
    }
}
