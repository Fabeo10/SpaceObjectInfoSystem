import java.util.Scanner;

/**
 * Entry point for the Space Object Info System simulation.
 * Main menu allows selecting user type and redirects to appropriate console.
 * Only the Administrator console may create users; other users must already exist.
 * Assumes an Administrator "admin" with ID "1" has created a Scientist "S" with ID "2"
 * 
 * @author Fabian Ornelas
 * @author David Jones
 * @version 1.2
 */
public class RunSimulation {
    public static void main(String[] args) {
        Log.updateLog("Sytem initialized");
        Scanner scanner = new Scanner(System.in);
        Administrator admin = new Administrator("admin");
        Log.updateLog("New Administrator created");
        Log.updateLog("New Scientist created");
        admin.createUser("Scientist", "S");
        Console console = new Console();
        boolean exitProgram = false;

        while (!exitProgram) {
            System.out.println("\n========== Main Menu ==========\n"
                    + "1. Scientist\n"
                    + "2. Space Agency Representative\n"
                    + "3. Policy Maker\n"
                    + "4. Administrator\n"
                    + "5. Exit");
            System.out.print("Select an option (1-5): ");

            int mainChoice;
            try {
                mainChoice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 5.");
                continue;
            }

            switch (mainChoice) {
                case 1: // Scientist
                    if (admin == null) {
                        System.out.println("No users exist yet. Please have an Administrator create users first.");
                    } else {
                        // ID validation before launching scientist console
                        while (true) {
                            System.out.print("Enter Scientist ID or 'B' to go back: ");
                            String id = scanner.nextLine();
                            if (id.equalsIgnoreCase("B")) break;
                            User u = admin.getUserById(id);
                            if (u instanceof Scientist) {
                                Scientist s = (Scientist) u;
                                System.out.print("\nWelcome, ");
                                s.displayRole();
                                console.runScientistConsole(s, scanner);
                                break;
                            } else {
                                System.out.println("Invalid Scientist ID. Contact Administrator, try again or 'B' to return to the main menu.");
                            }
                        }
                    }
                    break;

                case 2: // Space Agency Representative
                    if (admin == null) {
                        System.out.println("No users exist yet. Please have an Administrator create users first.");
                        break;
                    }
                    while (true) {
                        System.out.print("Enter Representative ID or 'B' to go back: ");
                        String id = scanner.nextLine();
                        if (id.equalsIgnoreCase("B")) break;
                        User u = admin.getUserById(id);
                        if (u instanceof SpaceAgencyRepresentative) {
                            System.out.print("Welcome, ");
                            u.displayRole();
                            break;
                        } else {
                            System.out.println("Invalid Space Agency Representative ID. Contact Administrator, try again, or 'B' to return to the main menu.");
                        }
                    }
                    break;

                case 3: // Policy Maker
                    if (admin == null) {
                        System.out.println("No users exist yet. Please have an Administrator create users first.");
                        break;
                    }
                    while (true) {
                        System.out.print("Enter Policy Maker ID or 'B' to go back: ");
                        String id = scanner.nextLine();
                        if (id.equalsIgnoreCase("B")) break;
                        User u = admin.getUserById(id);
                        if (u instanceof PolicyMaker) {
                            System.out.print("Welcome, ");
                            u.displayRole();
                            break;
                        } else {
                            System.out.println("Invalid Policy Maker ID. Contact Administrator, try again, or 'B' to return to the main menu.");
                        }
                    }
                    break;

                case 4: // Administrator
                    if (admin == null) {
                        System.out.print("Enter Administrator name: ");
                        String adminName = scanner.nextLine();
                        admin = new Administrator(adminName);
                        System.out.println("Welcome, " + admin.getRole() + " " + admin.getName() + "!");
                        System.out.println("Your ID is: " + admin.getId());
                    }
                    console.runAdminConsole(admin, scanner);
                    break;

                case 5: // Exit
                    exitProgram = true;
                    System.out.println("***Exiting the system gracefully*** . . . DONE, goodbye!");
                    break;

                default:
                    System.out.println("Invalid option. Choose a number between 1 and 5.");
            }
        }

        scanner.close();
    }
}
