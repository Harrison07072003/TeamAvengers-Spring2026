import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

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

    public static ArrayList<Item> loadItemsFromDefaultFile() throws IOException {
        return loadItemsFromFile("Item.txt");
    }

    public static ArrayList<Item> loadItemsFromFile(String filePath) throws IOException {
        ArrayList<Item> items = new ArrayList<>();
        Path resolvedPath = resolveItemPath(filePath);
        try (BufferedReader br = Files.newBufferedReader(resolvedPath)) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                items.add(parseItem(line));
            }
        }
        return items;
    }

    private static Item parseItem(String line) throws IOException {
        String[] parts = line.split(",", -1);
        for (int i = 0; i < parts.length; i++) {
            parts[i] = parts[i].trim();
        }

        if (parts.length < 4 || parts.length > 5) {
            throw new IOException("Invalid item row: " + line);
        }

        String id = parts[0];
        String name = parts[1];
        String desc;
        String type;
        int val = 0;

        if (parts.length == 4) {
            if (isNumeric(parts[3])) {
                // Format: ID, Name, Type, Value
                desc = name;
                type = parts[2];
                val = Integer.parseInt(parts[3]);
            } else {
                // Format: ID, Name, Type, Description
                desc = parts[3];
                type = parts[2];
                val = 0;
            }
        } else {
            // Format: ID, Name, Type, Description, Value
            desc = parts[3];
            type = parts[2];
            if (!parts[4].isEmpty()) {
                val = Integer.parseInt(parts[4]);
            }
        }

        return createItem(id, name, desc, type, val);
    }

    private static Item createItem(String id, String name, String desc, String type, int value) {
        switch (type.toLowerCase()) {
            case "consumable":
                return new Consumable(id, name, desc, type, value);
            case "weapon":
                return new Weapon(id, name, desc, type, value);
            case "tool":
            case "tools":
                return new Tool(id, name, desc, type, value, inferUtilityType(name, desc));
            case "quest":
                return new QuestItem(id, name, desc, type, value);
            default:
                return new Item(id, name, desc, type, value);
        }
    }

    private static String inferUtilityType(String name, String description) {
        String combinedText = (name + " " + description).toLowerCase();
        if (combinedText.contains("flashlight")) {
            return "light";
        }
        if (combinedText.contains("batter")) {
            return "power";
        }
        if (combinedText.contains("backpack")) {
            return "carry";
        }
        if (combinedText.contains("key")) {
            return "unlock";
        }
        return "utility";
    }

    private static Path resolveItemPath(String filePath) throws IOException {
        Path directPath = Paths.get(filePath);
        if (Files.exists(directPath)) {
            return directPath;
        }

        Path currentDirectory = Paths.get(System.getProperty("user.dir")).toAbsolutePath();
        for (int i = 0; i < 5 && currentDirectory != null; i++) {
            Path candidate = currentDirectory.resolve(filePath);
            if (Files.exists(candidate)) {
                return candidate;
            }
            currentDirectory = currentDirectory.getParent();
        }

        throw new FileNotFoundException("Could not locate item file: " + filePath);
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
