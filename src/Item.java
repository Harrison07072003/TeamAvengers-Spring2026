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
    private String item_Location;
    private int value; // HP for consumables, attack for weapons, etc.

    public Item(String item_Id, String item_Name, String item_Description, String item_type, int value) {
        this(item_Id, item_Name, item_Description, item_type, "", value);
    }

    public Item(String item_Id, String item_Name, String item_Description, String item_type, String item_Location,int value) {
        this.item_Id = item_Id;
        this.item_Name = item_Name;
        this.item_Description = item_Description;
        this.item_type = item_type;
        this.item_Location = item_Location;
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
    public String getItem_Location() {
        return item_Location;
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
                ", item_Location='" + item_Location + '\'' +
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
                Item item = parseItem(line);
                if (item != null) {
                    items.add(item);
                }
            }
        }
        return items;
    }

    private static Item parseItem(String line) throws IOException {
        String trimmedLine = line.trim();
        if (trimmedLine.isEmpty() || trimmedLine.startsWith("#")) {
            return null;
        }

        String[] parts = line.split("\\|", -1);
        for (int i = 0; i < parts.length; i++) {
            parts[i] = parts[i].trim();
        }

        if (parts.length >= 1 && "ID".equalsIgnoreCase(parts[0])) {
            return null;
        }

        if (parts.length < 4 || parts.length > 6) {
            throw new IOException("Invalid item row: " + line);
        }

        String id = parts[0];
        String name = parts[1];
        String type = parts[2];
        String desc = name;
        String location = "";
        int val = 0;

        if (parts.length == 4) {
            if (isNumeric(parts[3])) {
                val = Integer.parseInt(parts[3]);
            } else {
                desc = parts[3];
            }
        } else if (parts.length == 5) {
            if (isNumeric(parts[4])) {
                location = parts[3];
                val = Integer.parseInt(parts[4]);
            } else {
                desc = parts[3];
                location = parts[4];
            }
        } else if (parts.length == 6) {
            desc = parts[3];
            location = parts[4];
            if (!parts[5].isEmpty()) {
                val = Integer.parseInt(parts[5]);
            }
        }

        return createItem(id, name, desc, type, location,val);
    }

    private static Item createItem(String id, String name, String desc, String type, String location,int value) {
        switch (type.toLowerCase()) {
            case "consumable":
                return new Consumable(id, name, desc, type, location,value);
            case "weapon":
                return new Weapon(id, name, desc, type, location,value);
            case "tool":
            case "tools":
                return new Tool(id, name, desc, type, location,value, inferUtilityType(name, desc));
            case "quest":
                return new QuestItem(id, name, desc, type, location,value);
            default:
                return new Item(id, name, desc, type, location,value);
        }
    }

    private static String inferUtilityType(String name, String description) {
        String combinedText = (name + " " + description).toLowerCase();
        if (combinedText.contains("batter")) {
            return "power";
        }
        if (combinedText.contains("flashlight")) {
            return "light";
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
