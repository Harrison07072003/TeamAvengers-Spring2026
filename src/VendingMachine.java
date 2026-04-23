import java.util.HashMap;

public class VendingMachine {
    private final String location;
    private final HashMap<Item, Integer> itemsForSale;

    public VendingMachine(String location) {
        this.location = location;
        this.itemsForSale = new HashMap<>();
    }

    public VendingMachine(String location, HashMap<Item, Integer> itemsForSale) {
        this.location = location;
        this.itemsForSale = new HashMap<>();
        addItems(itemsForSale);
    }

    public void addItem(Item item, int price) {
        if (item == null) {
            return;
        }

        item.setPrice(price);
        item.setLocation(location);
        itemsForSale.put(item, price);
    }

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

    public Item dispenseItem(String itemName) {
        Item item = findItem(itemName);
        if (item != null) {
            itemsForSale.remove(item);
        }
        return item;
    }
}
