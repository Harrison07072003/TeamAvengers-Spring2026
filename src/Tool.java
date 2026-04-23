public class Tool extends Item {
    private String utilityType;
    public Tool(String Id, String Name,String type,String Description,String location,String roomLocation,int value, String utilityType) {
        super(Id, Name,"Tool", Description,location, roomLocation,value);
        this.utilityType = utilityType;

    }

    public String getUtilityType() {
        return utilityType;
    }

}