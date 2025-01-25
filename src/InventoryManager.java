// import java.io.*;
// import java.util.*;

// public class InventoryManager {

//     public static void main(String[] args) {
//         // Entry point for the program
//         // TODO: Implement menu-driven program for inventory management
//     }

//     public static void readInventory(String fileName) {
//         // TODO: Read and display inventory from file
//     }

//     public static void addItem(String fileName, String itemName, int itemCount) {
//         // TODO: Add a new item to the inventory
//     }

//     public static void updateItem(String fileName, String itemName, int itemCount) {
//         // TODO: Update the count of an existing item
//     }
// }


import java.io.*;
import java.util.*;

public class InventoryManager {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String fileName = "src/inventory.txt";

        System.out.println("Welcome to the Inventory Management System");
        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. View Inventory");
            System.out.println("2. Add New Item");
            System.out.println("3. Update Existing Item");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    readInventory(fileName);
                    break;
                case 2:
                    System.out.print("Enter item name: ");
                    String newItemName = scanner.nextLine();
                    System.out.print("Enter item count: ");
                    int newItemCount = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    addItem(fileName, newItemName, newItemCount);
                    break;
                case 3:
                    System.out.print("Enter item name: ");
                    String updateItemName = scanner.nextLine();
                    System.out.print("Enter new item count: ");
                    int updateItemCount = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    updateItem(fileName, updateItemName, updateItemCount);
                    break;
                case 4:
                    System.out.println("Exiting the program. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static void readInventory(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("Inventory file does not exist. No items to display.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            System.out.println("\nCurrent Inventory:");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading inventory file: " + e.getMessage());
        }
    }

    public static void addItem(String fileName, String itemName, int itemCount) {
        Map<String, Integer> inventory = loadInventory(fileName);

        if (inventory.containsKey(itemName)) {
            System.out.println("Item already exists. Use the update option to modify its count.");
            return;
        }

        inventory.put(itemName, itemCount);
        saveInventory(fileName, inventory);
        System.out.println("Item added successfully.");
    }

    public static void updateItem(String fileName, String itemName, int itemCount) {
        Map<String, Integer> inventory = loadInventory(fileName);

        if (!inventory.containsKey(itemName)) {
            System.out.println("Item does not exist. Use the add option to add it.");
            return;
        }

        inventory.put(itemName, itemCount);
        saveInventory(fileName, inventory);
        System.out.println("Item updated successfully.");
    }

    private static Map<String, Integer> loadInventory(String fileName) {
        Map<String, Integer> inventory = new HashMap<>();
        File file = new File(fileName);

        if (!file.exists()) {
            return inventory;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String itemName = parts[0];
                    int itemCount = Integer.parseInt(parts[1]);
                    inventory.put(itemName, itemCount);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading inventory file: " + e.getMessage());
        }

        return inventory;
    }

    private static void saveInventory(String fileName, Map<String, Integer> inventory) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to inventory file: " + e.getMessage());
        }
    }
}
