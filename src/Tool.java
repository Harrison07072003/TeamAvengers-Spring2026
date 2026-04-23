// Tools are non-consumable utility items such as flashlights or batteries.
public class Tool extends Item {
    private String utilityType;

    public Tool(String itemId, String itemName, String category,
                String description, int value, String roomLocation,String location,int price) {
        super(itemId, itemName, category, description,value, roomLocation, location, price);
    }

    // Utility type gives game rules a hook for special-purpose tools later.
    public String getUtilityType() {
        return utilityType;
    }
    public void setUtilityType(String utilityType) {
        this.utilityType = utilityType;
    }
}
