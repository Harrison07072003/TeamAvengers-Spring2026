import java.io.*;
import java.util.*;

public class Item {
    private String item_Id;
    private String item_Name;
    private String item_Description;
    private String item_type;
    private int value; // HP for consumables, attack for weapons, etc.

    public Item(String item_Id, String item_Name, String item_Description, String item_type, int value) {
        this.item_Id = item_Id;
        this.item_Name = item_Name;
        this.item_Description = item_Description;
        this.item_type = item_type;
        this.value = value;
    }

    public String getItem_Id() {
        return item_Id;
    }

    public String getItem_Name() {
        return item_Name;
    }

    public String getItem_Description() {
        return item_Description;
    }

    public String getItem_type() {
        return item_type;
    }

    public int getValue() {
        return value;
    }


    @Override
    public String toString() {
        return "Item{" +
                "item_Id='" + item_Id + '\'' +
                ", item_Name='" + item_Name + '\'' +
                ", item_Description='" + item_Description + '\'' +
                ", item_type='" + item_type + '\'' +
                ", value=" + value +
                '}';
    }

    public static List<Item> loadItemsFromFile(String filePath) throws IOException {
        List<Item> items = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(",", -1);
                for (int i = 0; i < parts.length; i++) {
                    parts[i] = parts[i].trim();
                }
                String id = parts[0];
                String name = parts[1];
                String desc;
                String type;
                int val = 0;
                if (parts.length >= 4 && isNumeric(parts[3])) {
                    desc = parts[1];
                    type = parts[2];
                    val = Integer.parseInt(parts[3]);
                } else {
                    desc = parts[2];
                    type = parts[3];
                    val = 0;
                }
                Item item;
                switch (type.toLowerCase()) {
                    case "consumable":
                        item = new Consumable(id, name, desc, type, val);
                        break;
                    case "weapon":
                        item = new Weapon(id, name, desc, type, val);
                        break;
                    case "tool":
                    case "tools":
                        // Default utilityType; could be parsed from desc if needed
                        String utilityType = "utility";
                        item = new Tool(id, name, desc, type, val, utilityType);
                        break;
                    case "quest":
                        item = new QuestItem(id, name, desc, type, val);
                        break;
                    default:
                        item = new Item(id, name, desc, type, val);
                        break;
                }
                items.add(item);
            }
        }
        return items;
    }

    private static boolean isNumeric(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
