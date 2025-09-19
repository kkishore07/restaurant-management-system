import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class restaurant_management_system {
    public static void main(String[] args) {
       
        Scanner sc = new Scanner(System.in);
        RestaurantManagementSystem rms = new RestaurantManagementSystem();
        rms.loadMenu("menu.txt");

        
        List<User> users = new ArrayList<>();
       
        users.add(new Admin("admin", "admin123"));
        users.add(new Customer("customer", ""));
        users.add(new Staff("staff", "staff123"));

        System.out.println("Welcome to Restaurant Management System");
        System.out.println("Select role:");
        System.out.println("1. Admin");
        System.out.println("2. Customer");
        System.out.println("3. Staff");
        System.out.print("Enter choice: ");
        
        int roleChoice = sc.nextInt();
        User loggedInUser = null;
       
        switch (roleChoice) {
            case 1 -> {
                System.out.print("Enter admin username: ");
                String username = sc.next();
                System.out.print("Enter admin password: ");
                String password = sc.next();
                
                if (username.equals("admin") && password.equals("admin123")) {
                    loggedInUser = new Admin(username, password);
                }
            }
            case 2 -> {
                System.out.print("Enter your name: ");
                String name = sc.next();
                loggedInUser = new Customer(name,""); // No password needed
            }
            case 3 -> {
                System.out.print("Enter staff username: ");
                String username = sc.next();
                System.out.print("Enter staff password: ");
                String password = sc.next();
                
                if (username.equals("staff") && password.equals("staff123")) {
                    loggedInUser = new Staff(username, password);
                }
            }
            default -> System.out.println("Invalid role selection.");
        }

        if (loggedInUser == null) {
            System.out.println("Invalid credentials or role. Exiting...");
            sc.close();
            return;
        }

        System.out.println("Login successful! Role: " + loggedInUser.getRole());

        
        boolean running = true;
        while (running) {
            System.out.println("\n--- " + loggedInUser.getRole() + " Menu ---");
            switch (loggedInUser.getRole()) {

                case "Admin" -> {
                    System.out.println("1. Display Menu");
                    System.out.println("2. Add Menu Item");
                    System.out.println("3. Update Menu Item");
                    System.out.println("4. Delete Menu Item");
                    System.out.println("5. Exit");
                    System.out.print("Choose an option : ");
                    
                    int choice = sc.nextInt();

                    if (choice == 1) rms.displayMenu();
                    else if (choice == 2) rms.addMenuItem(sc);
                    else if (choice == 3) rms.updateMenuItem(sc);
                    else if (choice == 4) rms.deleteMenuItem(sc);
                    else if (choice == 5) running = false;
                    else System.out.println("Invalid choice.");
                }

                case "Customer" -> {
                    System.out.println("1. Display Menu");
                    System.out.println("2. Place Order");
                    System.out.println("3. View Orders");
                    System.out.println("4. Exit");
                    System.out.print("Choose an option: ");
                    
                    int choice = sc.nextInt();

                    if (choice == 1) rms.displayMenu();
                    else if (choice == 2) rms.placeOrder(sc);
                    else if (choice == 3) rms.viewOrders();
                    else if (choice == 4) running = false;
                    else System.out.println("Invalid choice.");
                }

                case "Staff" -> {
                    System.out.println("1. View Orders");
                    System.out.println("2. Exit");
                    System.out.print("Choose an option: ");
                    
                    int choice = sc.nextInt();
                    
                    if (choice == 1) rms.viewOrders();
                    else if (choice == 2) running = false;
                    else System.out.println("Invalid choice.");
                }
            }
        }
        System.out.println("Exiting...");
        sc.close();
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
    
    void addMenuItem(Scanner sc) {
        System.out.print("Enter new item name: ");
        String name = sc.next();
        sc.nextLine();
        
        System.out.print("Enter price: ");
        double price = sc.nextDouble();
        
        menu.add(new MenuItem(name, price));
        System.out.println("Menu item added.");
    }

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
    
    void updateMenuItem(Scanner sc) {
        displayMenu();
        System.out.print("Enter item number to update: ");
        int idx = sc.nextInt();
        sc.nextLine();
        if (idx < 1 || idx > menu.size()) {
            System.out.println("Invalid item number.");
            return;
        }
        MenuItem item = menu.get(idx - 1);
        System.out.print("Enter new name (current: " + item.name + "): ");
        String name = sc.next();
        System.out.print("Enter new price (current: " + item.price + "): ");
        double price = sc.nextDouble();
        sc.nextLine();
        item.name = name;
        item.price = price;
        System.out.println("Menu item updated.");
    }
    
    void deleteMenuItem(Scanner sc) {
        displayMenu();
        System.out.print("Enter item number to delete: ");
        int idx = sc.nextInt();
        if (idx < 1 || idx > menu.size()) {
            System.out.println("Invalid item number.");
            return;
        }
        menu.remove(idx - 1);
        System.out.println("Menu item deleted.");
    }
    
    void placeOrder(Scanner sc) {
        Order order = new Order();
        while (true) {
            displayMenu();
            System.out.print("Enter item number to add to order (0 to finish): ");
                int itemNum = sc.nextInt();
            
                if (itemNum == 0) break;
            if (itemNum < 1 || itemNum > menu.size()) {
                System.out.println("Invalid item number.");
                continue;
            }
            order.addItem(menu.get(itemNum - 1));
            System.out.println("Item added.");
        }
        if (!order.items.isEmpty()) {
                
                System.out.println("\nOrder Summary:");
                System.out.println(order);

                System.out.print("Confirm order? (y/n): ");
                sc.nextLine(); 
                String confirm = sc.nextLine();
                if (confirm.equalsIgnoreCase("y")) {
                    orders.add(order);
                    System.out.println("Order placed successfully!");
                } else {
                    System.out.println("Order cancelled.");
                }
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






