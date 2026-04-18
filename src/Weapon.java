public class Weapon extends Item {
    private int atkIncrease;

    public Weapon(String item_Id, String item_Name, String item_Description, String item_type, int value) {
        super(item_Id, item_Name, item_Description, item_type, value);
        this.atkIncrease = value;
    }

    public int getAtkIncrease() {
        return atkIncrease;
    }

    public String use(Player player) {
        if (player == null) {
            return View.noPlayerForAction("equip", getItem_Name());
        }

        return player.equipWeapon(this);
    }
}
