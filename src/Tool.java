public class Tool extends Item {
    private String utilityType;

    public Tool(String item_Id, String item_Name, String item_Description, String item_type, int value, String utilityType) {
        this(item_Id, item_Name, item_Description, item_type, "", value, utilityType);
    }

    public Tool(String item_Id, String item_Name, String item_Description, String item_type, String item_Location, int value, String utilityType) {
        super(item_Id, item_Name, item_Description, item_type, item_Location, value);
        this.utilityType = utilityType;
    }

    public String use(Player player) {
        if (player == null) {
            return View.noPlayerForAction("use", getItem_Name());
        }

        if (!player.getInventory().contains(this)) {
            return View.itemNotInInventory(getItem_Name());
        }

        if ("carry".equalsIgnoreCase(utilityType)) {
            if (player.getInventoryCapacity() >= 10) {
                return View.inventoryCapacityAlreadyIncreased(getItem_Name());
            }
            player.expandInventory(10);
            return View.inventoryCapacityIncreased(getItem_Name(), player.getInventoryCapacity());
        }

        if ("power".equalsIgnoreCase(utilityType)) {
            Item flashlight = player.getItem("Flashlight");
            if (flashlight == null) {
                return View.flashlightRequired(getItem_Name());
            }

            player.removeItem(this);
            player.removeItem(flashlight);
            player.addItem(new Tool(
                    "combined_id",
                    "Powered Flashlight",
                    "A flashlight with batteries, ready to use.",
                    "tool",
                    0,
                    "light"
            ));
            return View.flashlightPowered(getItem_Name());
        }

        if ("light".equalsIgnoreCase(utilityType)) {
            if ("Flashlight".equalsIgnoreCase(getItem_Name())) {
                return View.flashlightNeedsBatteries();
            }
            return View.litArea(getItem_Name());
        }

        if ("unlock".equalsIgnoreCase(utilityType)) {
            return View.unlockToolNotConfigured(getItem_Name());
        }

        return View.usedItem(getItem_Name());
    }
}
