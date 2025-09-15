import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class restaurant_management_system {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        RestaurantManagementSystem rms = new RestaurantManagementSystem();
        rms.loadMenu("menu.txt");

        
        List<User> users = new ArrayList<>();
        users.add(new Admin("admin", "admin123"));
        users.add(new Customer("customer", "custo123"));
        users.add(new Staff("staff", "staff123"));

        System.out.println("Welcome to Restaurant Management System");
        System.out.print("Enter username: ");
        String username = scanner.next();
        System.out.print("Enter password: ");
        String password = scanner.next();

        User loggedInUser = null;
        for (User user : users) {
            if (user.username.equals(username) && user.password.equals(password)) {
                loggedInUser = user;
                break;
            }
        }

        if (loggedInUser == null) {
            System.out.println("Invalid credentials. Exiting...");
            scanner.close();
            return;
        }

        System.out.println("Login successful! Role: " + loggedInUser.getRole());

        
        boolean running = true;
        while (running) {
            System.out.println("\n--- " + loggedInUser.getRole() + " Menu ---");
            switch (loggedInUser.getRole()) {

                case "Admin" -> {
                    System.out.println("1. Display Menu");
                    System.out.println("2. Exit");
                    System.out.print("Choose an option : ");
                    int choice = scanner.nextInt();
                    if (choice == 1) rms.displayMenu();
                    else if (choice == 2) running = false;
                    else System.out.println("Invalid choice.");
                }

                case "Customer" -> {
                    System.out.println("1. Display Menu");
                    System.out.println("2. Place Order");
                    System.out.println("3. View Orders");
                    System.out.println("4. Exit");
                    System.out.print("Choose an option: ");
                    int choice = scanner.nextInt();
                    if (choice == 1) rms.displayMenu();
                    else if (choice == 2) rms.placeOrder(scanner);
                    else if (choice == 3) rms.viewOrders();
                    else if (choice == 4) running = false;
                    else System.out.println("Invalid choice.");
                }

                case "Staff" -> {
                    System.out.println("1. View Orders");
                    System.out.println("2. Exit");
                    System.out.print("Choose an option: ");
                    int choice = scanner.nextInt();
                    if (choice == 1) rms.viewOrders();
                    else if (choice == 2) running = false;
                    else System.out.println("Invalid choice.");
                }
            }
        }
        System.out.println("Exiting...");
        scanner.close();
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
    String status = "Pending"; // Order status

    void addItem(MenuItem item) {
        items.add(item);
        total += item.price;
    }

    void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (MenuItem item : items) {
            sb.append(item.name).append(" - ").append(String.format("%.2f", item.price)).append("\n");
        }
        sb.append("Total: ").append(String.format("%.2f", total)).append("\n");
        sb.append("Status: ").append(status);
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
void placeOrder(Scanner scanner) {
        Order order = new Order();
        while (true) {
            displayMenu();
            System.out.print("Enter item number to add to order (0 to finish): ");
            int itemNum = scanner.nextInt();
            if (itemNum == 0) break;
            if (itemNum < 1 || itemNum > menu.size()) {
                System.out.println("Invalid item number.");
                continue;
            }
            order.addItem(menu.get(itemNum - 1));
            System.out.println("Item added.");
        }
        if (!order.items.isEmpty()) {
            orders.add(order);
            System.out.println("Order placed:\n" + order);
        } else {
            System.out.println("No items ordered.");
        }
    }

    void viewOrders() {
        if (orders.isEmpty()) {
            System.out.println("No orders placed yet.");
            return;
        }
        System.out.println("\nOrders:");
        for (int i = 0; i < orders.size(); i++) {
            System.out.println("Order #" + (i + 1));
            System.out.println(orders.get(i));
            System.out.println();
        }
    }
}

abstract class User {
    String username;
    String password;

    User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    abstract String getRole();
}

// Admin subclass
class Admin extends User {
    Admin(String username, String password) {
        super(username, password);
    }

    @Override
    String getRole() {
        return "Admin";
    }
}

// Customer subclass
class Customer extends User {
    Customer(String username, String password) {
        super(username, password);
    }

    @Override
    String getRole() {
        return "Customer";
    }
}

// Staff subclass
class Staff extends User {
    Staff(String username, String password) {
        super(username, password);
    }

    @Override
    String getRole() {
        return "Staff";
    }
}






