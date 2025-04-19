import java.util.Scanner;

/**
 * Entry point for the Space Object Info System simulation.
 * Provides a console-based administrator interface for creating,
 * managing, and deleting users.
 *
 * Usage:
 * - On startup, prompts for the administrator's name.
 * - Presents a menu of options until the admin chooses to exit.
 *
 * Roles supported: Scientist, Space Agency Representative, Policy Maker
 *
 * Example:
 * <pre>
 *   Enter Administrator name: Fabian
 *   ========== Administrator Console ==========
 *   1. Create User
 *   2. Manage User
 *   3. Delete User
 *   4. EXIT
 *   Select an option (e.g., 1):
 * </pre>
 */
public class RunSimulation {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Administrator name: ");
        String adminName = scanner.nextLine();
        Administrator admin = new Administrator(adminName);
        boolean exit = false;

        System.out.println("Welcome back, Administrator " + admin.getName() + "!");
        System.out.println("Your user ID is: " + admin.getId());

        while (!exit) {
            System.out.println("\n========== Administrator Console ==========");
            System.out.println("1. Create User");
            System.out.println("2. Manage User");
            System.out.println("3. Delete User");
            System.out.println("4. EXIT");
            System.out.print("Select an option (e.g., 1): ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, choose a number between 1 and 4.");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.print("Enter User Name: ");
                    String nameIn = scanner.nextLine();
                    while (true) {
                        System.out.print("Enter User Type [Scientist, Space Agency Representative, Policy Maker] or 'B' to go back: ");
                        String typeIn = scanner.nextLine();
                        if (typeIn.equalsIgnoreCase("B")) {
                            break;
                        }
                        try {
                            admin.createUser(typeIn, nameIn);
                            break;
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                            System.out.println("Please try again or enter 'B' to discard operation and return to admin console.");
                        }
                    }
                    break;

                case 2:
                    while (true) {
                        System.out.print("Enter the User ID to manage or 'B' to go back: ");
                        String manageId = scanner.nextLine();
                        if (manageId.equalsIgnoreCase("B")) {
                            break;
                        }
                        if (admin.getUserById(manageId) != null) {
                            admin.manageUser(manageId);
                            break;
                        } else {
                            System.out.println("No user found with ID: " + manageId + ". Try again or 'B' to discard operation and return to admin console.");
                        }
                    }
                    break;

                case 3:
                    while (true) {
                        System.out.print("Enter the User ID to delete or 'B' to go back: ");
                        String deleteId = scanner.nextLine();
                        if (deleteId.equalsIgnoreCase("B")) {
                            break;
                        }
                        if (admin.getUserById(deleteId) != null) {
                            admin.deleteUser(deleteId);
                            break;
                        } else {
                            System.out.println("No user found with ID: " + deleteId + ". Try again or 'B' to discard operation and return to admin console.");
                        }
                    }
                    break;

                case 4:
                    exit = true;
                    System.out.println("You are now exiting the simulation. Goodbye.");
                    break;

                default:
                    System.out.println("Invalid option, choose a number between 1 and 4.");
            }
        }

        scanner.close();
    }
}
