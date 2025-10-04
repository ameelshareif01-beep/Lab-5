import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Inventory {
    private List<Item> items;

    public Inventory() {
        this.items = new ArrayList<>();
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void display() {
        if (items.isEmpty()) {
            System.out.println("Inventory is empty.");
        } else {
            System.out.println("Current Inventory:");
            for (Item item : items) {
                System.out.println("- " + item);
            }
        }
    }

    public void combineItems(String name1, String name2) {
        boolean found1 = false;
        boolean found2 = false;

        Iterator<Item> iter = items.iterator();

        // Safely search and remove
        while (iter.hasNext()) {
            Item current = iter.next();
            if (current.getName().equalsIgnoreCase(name1)) {
                found1 = true;
                iter.remove();
            } else if (current.getName().equalsIgnoreCase(name2)) {
                found2 = true;
                iter.remove();
            }
        }

        if (found1 && found2) {
            String combinedName = name1 + " " + name2 + " (Combined)";
            items.add(new Item(combinedName));
            System.out.println("Successfully combined " + name1 + " and " + name2 + " into " + combinedName + "!");
        } else {
            System.out.println("Combination failed — missing required items.");
        }
    }
}
