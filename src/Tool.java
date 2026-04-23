public class Tool extends Item {
    private String utilityType;

    public Tool(String itemId, String itemName, String description,
                String location, String roomLocation, int value, int price,
                String utilityType) {
        super(itemId, itemName, "Tool", description, location, roomLocation, value, price);
        this.utilityType = utilityType;
    }

    public String getUtilityType() {
        return utilityType;
    }
}