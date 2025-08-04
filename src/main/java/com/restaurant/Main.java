package com.restaurant;

import com.restaurant.model.Order;
import com.restaurant.model.OrderItem;
import com.restaurant.model.Table;
import com.restaurant.model.Bill;
import com.restaurant.service.OrderService;
import com.restaurant.service.TableService;
import com.restaurant.service.BillService;

import java.util.*;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final TableService tableService = new TableService();
    private static final OrderService orderService = new OrderService();
    private static final BillService billService = new BillService();

    public static void main(String[] args) {
        while (true) {
            printMenu();
            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1 -> viewAvailableTables();
                case 2 -> bookTable();
                case 3 -> placeOrder();
                case 4 -> markOrderPrepared();
                case 5 -> generateBill();
                case 6 -> recordPayment();
                case 7 -> {
                    System.out.println("Exiting system.");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n--- Restaurant Management System ---");
        System.out.println("1. View Available Tables");
        System.out.println("2. Book a Table");
        System.out.println("3. Place an Order");
        System.out.println("4. Mark Order as Prepared");
        System.out.println("5. Generate Bill");
        System.out.println("6. Record Payment");
        System.out.println("7. Exit");
    }

    private static void viewAvailableTables() {
        List<Table> tables = tableService.getAvailableTables();
        if (tables.isEmpty()) {
            System.out.println("No available tables.");
        } else {
            System.out.println("Available Tables:");
            for (Table t : tables) {
                System.out.println("Table ID: " + t.getTableId() + ", Capacity: " + t.getCapacity());
            }
        }
    }

    private static void bookTable() {
        int tableId = readInt("Enter table ID to book: ");
        boolean success = tableService.bookTable(tableId);
        System.out.println(success ? "Table booked successfully." : "Failed to book table.");
    }

    private static void placeOrder() {
        int tableId = readInt("Enter table ID: ");
        List<OrderItem> items = new ArrayList<>();
        while (true) {
            int itemId = readInt("Enter menu item ID: ");
            int qty = readInt("Enter quantity: ");
            items.add(new OrderItem(itemId, qty));

            System.out.print("Add more items? (y/n): ");
            String more = scanner.nextLine().trim().toLowerCase();
            if (!more.equals("y")) break;
        }
        Order order = new Order(tableId, items);
        int orderId = orderService.placeOrder(order);
        if (orderId > 0) {
            System.out.println("Order placed. Order ID: " + orderId);
        } else {
            System.out.println("Failed to place order.");
        }
    }

    private static void markOrderPrepared() {
        int orderId = readInt("Enter Order ID to mark as prepared: ");
        boolean updated = orderService.prepareOrder(orderId);
        System.out.println(updated ? "Order marked as prepared." : "Failed to update order.");
    }

    private static void generateBill() {
        int orderId = readInt("Enter Order ID for billing: ");
        double total = readDouble("Enter total amount: ");
        Bill bill = new Bill(orderId, total);
        int billId = billService.generateBill(bill);
        if (billId > 0) {
            System.out.println("Bill generated. Bill ID: " + billId);
        } else {
            System.out.println("Failed to generate bill.");
        }
    }

    private static void recordPayment() {
        int billId = readInt("Enter Bill ID to record payment: ");
        boolean paid = billService.recordPayment(billId);
        System.out.println(paid ? "Payment recorded successfully." : "Payment update failed.");
    }

    private static int readInt(String message) {
        System.out.print(message);
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. " + message);
            scanner.next();
        }
        int val = scanner.nextInt();
        scanner.nextLine(); // consume newline
        return val;
    }

    private static double readDouble(String message) {
        System.out.print(message);
        while (!scanner.hasNextDouble()) {
            System.out.print("Invalid input. " + message);
            scanner.next();
        }
        double val = scanner.nextDouble();
        scanner.nextLine(); // consume newline
        return val;
    }
}
