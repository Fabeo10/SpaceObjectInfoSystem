import java.util.Scanner;
/**
 * Handles UI for menu inputs and data manipulation
 * 
 * 
 * @author David Jones
 * @author Fabian Ornelas
 * @version 1.0
 */
public class Console {
     /**
     * Runs the administrator console loop for creating, managing,
     * and deleting users.
     */
    public void runAdminConsole(Administrator admin, Scanner scanner, DataManager manager) {
        boolean exit = false;
        while (!exit) {
            System.out.println("\n===== Administrator Console ====="
                    + "\n1. Create User"
                    + "\n2. Manage User"
                    + "\n3. Delete User"
                    + "\n4. Back to Main Menu");
            System.out.print("Select an option (1-4): ");

            int adminChoice;
            try {
                adminChoice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Choose a number between 1 and 4.");
                continue;
            }

            switch (adminChoice) {
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
                        String name = scanner.nextLine();
                        if (name.equalsIgnoreCase("B")) {
                            break;
                        }
                        if (admin.getUserByName(name) != null) {
                            admin.manageUser(name);
                            break;
                        } else {
                            System.out.println("No user found with Name: " + name + ". Try again or 'B' to discard operation and return to admin console.");
                        }
                    }
                    break;

                case 3:
                    while (true) {
                        System.out.print("Enter the User ID to delete or 'B' to go back: ");
                        String name = scanner.nextLine();
                        if (name.equalsIgnoreCase("B")) {
                            break;
                        }
                        if (admin.getUserByName(name) != null) {
                            admin.deleteUser(name);
                            break;
                        } else {
                            System.out.println("No user found with Name: " + name + ". Try again or 'B' to discard operation and return to admin console.");
                        }
                    }
                    break;

                case 4:
                    exit = true;
                    manager.updateUserData("USERS.csv");
                    System.out.println("\n***Exited Administrative Console Gracefully.***");
                    break;

                default:
                    System.out.println("Invalid option. Choose a number between 1 and 4.");
            }
        }
    }

     /**
     * Runs the scientist console loop: menu actions for a verified scientist.
     */
    public void runScientistConsole(Scientist s, Scanner scanner, DataManager manager) {
        boolean exit = false;
        while (!exit) {
            System.out.println("\n===== Scientist Console ====="
                    + "\n1. Track Objects in Space"
                    + "\n2. Assess Object Orbital Status"
                    + "\n3. Go Back");
            System.out.print("Select an option (1-3): ");

            int scientistChoice;
            try {
                scientistChoice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 3.");
                continue;
            }

            switch (scientistChoice) {
                case 1:
                    Log.updateLog("Scientist " + s.name + " began tracking objects in space");
                    System.out.println("Select the type of object to track:");
                    System.out.println("ROCKET BODY | PAYLOAD | DEBRIS | UNKNOWN");
                    String objectType = scanner.nextLine();
                    s.trackObjectsInSpace(objectType);
                    Log.updateLog("Scientist " + s.name + " requested a " + objectType + " list");
                    break;

                case 2:
                    boolean subExit = false;
                    Log.updateLog("Scientist " + s.name + " began updating orbital metrics");
                    while (!subExit) {
                        System.out.println("\nWould you like to:"
                                + "\n1. Track Objects In Low Earth Orbit"
                                + "\n2. Assess Objects Still in Orbit"
                                + "\n3. Assess Risk Level of Objects"
                                + "\n4. Go Back");
                        System.out.print("Select an option (1-4): ");
                        int subChoice;
                        try {
                            subChoice = Integer.parseInt(scanner.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter a number 1 - 4.");
                            continue;
                        }

                        switch (subChoice) {
                            case 1:
                                Log.updateLog("Scientist " + s.name + " requested a list of objects in LEO");
                                s.trackObjectsInLEO();
                                break;
                            case 2:
                                System.out.println("Assessing objects still in orbit...");
                                s.assessStillInOrbit();
                                System.out.println("Records updated");
                                Log.updateLog("Scientist " + s.name + " added still_in_orbit column to Updated_RSO_Metrics.csv");
                                Log.updateLog("Scientist " + s.name + " added results to still_in_orbit column");
                                break;
                            case 3:
                                System.out.println("Assessing risk level of objects...");
                                s.assessRiskLevel();
                                System.out.println("Records updated");
                                Log.updateLog("Scientist " + s.name + " added risk_level column to Updated_RSO_Metrics.csv");
                                Log.updateLog("Scientist " + s.name + " added results to risk_level column");
                                break;
                            case 4:
                                subExit = true;
                                Log.updateLog("Scientist " + s.name + " ceased adding to Updated_RSO_Metrics.csv");
                                break;
                            default:
                                System.out.println("Invalid option. Please try again.");
                        }
                    }
                    Log.updateLog("Scientist " + s.name + " ceased updating orbital metrics");
                    break;

                case 3:
                    exit = true;
                    Log.updateLog("Scientist " + s.name + " logged out");
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }

        System.out.println("Generating Updated Metrics...");
        Log.updateLog("Writing Updated_RSO_Metrics.csv...");
        manager.updateMetricData("Updated_RSO_Metrics_test.csv");
        Log.updateLog("Update complete");
    }
}
