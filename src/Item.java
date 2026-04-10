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
        this.value = 0; // Default value, can be set based on type
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
                String[] parts = line.split(", ", -1);
                if (parts.length >= 4) {
                    Item item = new Item(parts[0], parts[1], parts[2], parts[3]);
                    items.add(item);
                }
            }
        }
        return items;
    }
}
