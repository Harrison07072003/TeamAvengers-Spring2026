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
    public boolean canUse() {
        return true;
    }
}