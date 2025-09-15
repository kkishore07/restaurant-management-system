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

