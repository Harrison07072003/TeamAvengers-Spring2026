import java.util.HashMap;

// Vending machine inventory maps each stocked item to the price it sells for.
public class VendingMachine {
    private final String location;
    private final HashMap<Item, Integer> itemsForSale;

    // A vending machine only needs its container/location ID to be usable.
    public VendingMachine(String location) {
        this.location = location;
        this.itemsForSale = new HashMap<>();
    }

    public VendingMachine(String location, HashMap<Item, Integer> itemsForSale) {
        this.location = location;
        this.itemsForSale = new HashMap<>();
        addItems(itemsForSale);
    }

    // Stocking an item also stamps the item with vending-machine location metadata.
    public void addItem(Item item, int price) {
        if (item == null) {
            return;
        }

        item.setPrice(price);
        item.setLocation(location);
        itemsForSale.put(item, price);
    }

    // Bulk add exists to support loading stock from an already prepared map.
    public void addItems(HashMap<Item, Integer> itemsWithPrices) {
        if (itemsWithPrices == null) {
            return;
        }

        for (Item item : itemsWithPrices.keySet()) {
            Integer price = itemsWithPrices.get(item);
            if (item != null && price != null) {
                addItem(item, price);
            }
        }
    }

    public void addItem(HashMap<Item, Integer> itemsWithPrices) {
        addItems(itemsWithPrices);
    }

    public String getLocation() {
        return location;
    }

    public HashMap<Item, Integer> getItemsForSale() {
        return itemsForSale;
    }

    // Produces a simple menu string that can be shown directly in the console.
    public String getItemList() {
        StringBuilder sb = new StringBuilder();

        for (Item item : itemsForSale.keySet()) {
            sb.append(item.getItemName())
                    .append(" - ")
                    .append(itemsForSale.get(item))
                    .append(" coins\n");
        }

        return sb.toString();
    }

    // Price lookup is based on item name because controller commands are text-driven.
    public int getPrice(String itemName) {
        if (itemName == null) {
            return -1;
        }

        for (Item item : itemsForSale.keySet()) {
            if (item.getItemName().equalsIgnoreCase(itemName)) {
                return itemsForSale.get(item);
            }
        }

        return -1;
    }

    public Item findItem(String itemName) {
        if (itemName == null) {
            return null;
        }

        for (Item item : itemsForSale.keySet()) {
            if (item.getItemName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }

        return null;
    }

    // Dispensing removes the sold item from stock and returns it to the caller.
    public Item dispenseItem(String itemName) {
        Item item = findItem(itemName);
        if (item != null) {
            itemsForSale.remove(item);
        }
        return item;
    }
}
