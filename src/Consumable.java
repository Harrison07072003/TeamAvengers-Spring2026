public class Consumable extends Item {
    private int hpRestore;

    public Consumable(String item_Id, String item_Name, String item_Description, String item_type, int value) {
        this(item_Id, item_Name, item_Description, item_type, "", value);
    }

    public Consumable(String item_Id, String item_Name, String item_Description, String item_type, String item_Location, int value) {
        super(item_Id, item_Name, item_Description, item_type, item_Location, value);
        this.hpRestore = value;
    }

    public int getHpRestore() {
        return hpRestore;
    }

    public String use(Player player) {
        if (player == null) {
            return View.noPlayerForAction("consume", getItem_Name());
        }

        if (!player.getInventory().contains(this)) {
            return View.itemNotInInventory(getItem_Name());
        }

        player.setHP(player.getHP() + hpRestore);
        player.removeItem(this);
        return View.consumedItem(getItem_Name(), hpRestore);
    }
}
