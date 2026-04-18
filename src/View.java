public class View {
    //fields
    //constructor
    public View(){

    }
    //methods
    public void display(String str){
        System.out.println(str);
    }

    public void displayStatus(int health, int attack, int defense, int vials, int coins) {
        System.out.println("Health: " + health);
        System.out.println("Attack: " + attack);
        System.out.println("Defense: " + defense);
        System.out.println("Vials: " + vials);
        System.out.println("Coins: " + coins);
    }

    public static String noPlayerForAction(String action, String itemName) {
        return "No player available to " + action + " " + itemName + ".";
    }

    public static String itemNotInInventory(String itemName) {
        return itemName + " is not in your inventory.";
    }

    public static String consumedItem(String itemName, int hpRestore) {
        return "Consumed " + itemName + ", HP restored by " + hpRestore + ".";
    }

    public static String officeDoorAlreadyUnlocked() {
        return "The office door is already unlocked.";
    }

    public static String officeDoorUnlocked() {
        return "You used the Office Key and unlocked the office door.";
    }

    public static String cureVitalHint() {
        return "Cure Vital is a quest ingredient. Keep it until you are ready to make the cure.";
    }

    public static String plagueAlreadyCured() {
        return "The plague has already been cured.";
    }

    public static String plagueCured() {
        return "You used the Cure and cured the plague.";
    }

    public static String usedQuestItem(String itemName) {
        return "Used quest item: " + itemName + ".";
    }

    public static String inventoryCapacityAlreadyIncreased(String itemName) {
        return itemName + " is already increasing your inventory capacity.";
    }

    public static String inventoryCapacityIncreased(String itemName, int capacity) {
        return "Used " + itemName + ". Inventory capacity increased to " + capacity + ".";
    }

    public static String flashlightRequired(String itemName) {
        return "You need a Flashlight before using " + itemName + ".";
    }

    public static String flashlightPowered(String itemName) {
        return "You used " + itemName + " to power the Flashlight.";
    }

    public static String flashlightNeedsBatteries() {
        return "The Flashlight has no batteries. Combine it with Batteries first.";
    }

    public static String litArea(String itemName) {
        return "You turned on " + itemName + " and lit the area.";
    }

    public static String unlockToolNotConfigured(String itemName) {
        return itemName + " can unlock something, but it is not configured as a tool in this build.";
    }

    public static String usedItem(String itemName) {
        return "Used " + itemName + ".";
    }

    public static String weaponNotFound() {
        return "Weapon not found";
    }

    public static String weaponNotInInventory() {
        return "Weapon not in inventory";
    }

    public static String weaponAlreadyEquipped(String itemName) {
        return itemName + " is already equipped";
    }

    public static String equippedWeapon(String itemName) {
        return "Equipped " + itemName;
    }

    public static String exploringRoom() {
        return "Exploring room";
    }
}
