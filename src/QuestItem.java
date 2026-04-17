public class QuestItem extends Item {
    private boolean isQuestItem;

    public QuestItem(String item_Id, String item_Name, String item_Description, String item_type, int value) {
        super(item_Id, item_Name, item_Description, item_type, value);
        this.isQuestItem = true; // Assuming all instances are quest items
    }

    public void use() {
        // Logic to use the quest item would go here
        // For now, just a placeholder
    }
}
