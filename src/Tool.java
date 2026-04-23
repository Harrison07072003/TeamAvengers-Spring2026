// Tools are non-consumable utility items such as flashlights or batteries.
public class Tool extends Item {
    private String utilityType;

    public Tool(String itemId, String itemName, String description,
                String location, String roomLocation, int value, int price,
                String utilityType) {
        super(itemId, itemName, "Tool", description, location, roomLocation, value, price);
        this.utilityType = utilityType;
    }

    // Utility type gives game rules a hook for special-purpose tools later.
    public String getUtilityType() {
        return utilityType;
    }
}
