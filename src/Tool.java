public class Tool extends Item {
    private String utilityType;

    public Tool(String item_Id, String item_Name, String item_Description, String item_type, int value, String utilityType) {
        super(item_Id, item_Name, item_Description, item_type, value);
        this.utilityType = utilityType;
    }

    public String getUtilityType() {
        return utilityType;
    }

}