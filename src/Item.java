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
}
