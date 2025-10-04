import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Main {

    // ----------- Item class -----------
    static class Item {
        private String name;

        public Item(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    // ----------- Inventory class -----------
    static class Inventory {
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
            while (iter.hasNext()) {
                Item current = iter.next();
                if (current.getName().equalsIgnoreCase(name1)) {
                    found1 = true;
                    iter.remove(); // Safe removal
                } else if (current.getName().equalsIgnoreCase(name2)) {
                    found2 = true;
                    iter.remove(); // Safe removal
                }
            }

            if (found1 && found2) {
                String combinedName = name1 + " + " + name2 + " (Combined)";
                items.add(new Item(combinedName));
                System.out.println("Successfully combined " + name1 + " and " + name2 + " into " + combinedName + "!");
            } else {
                System.out.println("Combination failed — one or both items not found.");
            }
        }
    }

    // ----------- Main method -----------
    public static void main(String[] args) {
        Inventory inventory = new Inventory();

        // Add starting items
        inventory.addItem(new Item("Stick"));
        inventory.addItem(new Item("Gem"));
        inventory.addItem(new Item("String"));

        System.out.println("Before combining:");
        inventory.display();

        // Combine two items
        inventory.combineItems("Stick", "Gem");

        System.out.println("\nAfter combining:");
        inventory.display();
    }
}
