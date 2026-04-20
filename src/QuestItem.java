public class QuestItem extends Item {
    private boolean isQuestItem;

    public QuestItem(String item_Id, String item_Name, String item_Description, String item_type, int value) {
        super(item_Id, item_Name, item_Description, item_type, value);
        this.isQuestItem = true; // Assuming all instances are quest items
    }

    public boolean isQuestItem() {
        return isQuestItem;
    }

    public String use(Player player) {
        if (player == null) {
            return View.noPlayerForAction("use", getItem_Name());
        }

        if (!player.getInventory().contains(this)) {
            return View.itemNotInInventory(getItem_Name());
        }

        String itemName = getItem_Name().toLowerCase();

        if (itemName.contains("office key")) {
            if (player.isOfficeUnlocked()) {
                return View.officeDoorAlreadyUnlocked();
            }

            player.setOfficeUnlocked(true);
            player.removeItem(this);
            return View.officeDoorUnlocked();
        }

        if (itemName.contains("cure vital")) {
            return View.cureVitalHint();
        }

        if (itemName.equals("cure")) {
            if (player.isPlagueCured()) {
                return View.plagueAlreadyCured();
            }

            player.setPlagueCured(true);
            player.removeItem(this);
            return View.plagueCured();
        }

        return View.usedQuestItem(getItem_Name());
    }
}