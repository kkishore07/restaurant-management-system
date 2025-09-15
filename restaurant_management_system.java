import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class restaurant_management_system {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        RestaurantManagementSystem rms = new RestaurantManagementSystem();
        rms.loadMenu("menu.txt");

        while (true) {
            System.out.println("\nRestaurant Management System");
            System.out.println("1. Display Menu");
            System.out.println("2. Place Order");
            System.out.println("3. View Orders");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> rms.displayMenu();
                case 2 -> rms.placeOrder(scanner);
                case 3 -> rms.viewOrders();
                case 4 -> {
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}

class MenuItem {
    String name;
    double price;

    MenuItem(String name, double price) {
        this.name = name;
        this.price = price;
    }
}

class Order {
    List<MenuItem> items = new ArrayList<>();
    double total = 0.0;

    void addItem(MenuItem item) {
        items.add(item);
        total += item.price;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (MenuItem item : items) {
            sb.append(item.name).append(" - ").append(String.format("%.2f", item.price)).append("\n");
        }
        sb.append("Total: ").append(String.format("%.2f", total));
        return sb.toString();
    }
}


class RestaurantManagementSystem {
    List<MenuItem> menu = new ArrayList<>();
    List<Order> orders = new ArrayList<>();

    void loadMenu(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String name = parts[0].trim();
                    double price = Double.parseDouble(parts[1].trim());
                    menu.add(new MenuItem(name, price));
                }
            }
        } catch (IOException e) {
                System.out.println("Menu file not found. Using default menu.");
                menu.add(new MenuItem("Idly", 30.0));
                menu.add(new MenuItem("Dosa", 40.0));
                menu.add(new MenuItem("Chapathi", 35.0));
                menu.add(new MenuItem("Parotta", 45.0));
        }
    }

    void displayMenu() {
        System.out.println("\nMenu:");
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.get(i);
            System.out.printf("%d. %s - %.2f\n", i + 1, item.name, item.price);
        }
    }

}