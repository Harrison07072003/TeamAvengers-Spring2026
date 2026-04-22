public class Tool extends Item {
    private String utilityType;
    public Tool(String item_Id, String item_Name,String item_type,String item_Description,String location,String roomLocation,int value, String utilityType) {
        super(item_Id, item_Name,"Tool", item_Description,location, roomLocation,value);
        this.utilityType = utilityType;

    }

    public String getUtilityType() {
        return utilityType;
    }

}