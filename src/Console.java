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
     * 
     * @param admin - The authorized Administrator user
     * @param scanner - A scanner for prompting user input
     * @param manager - The administrator user's assigned DataManager
     */
    public void runAdminConsole(Administrator admin, Scanner scanner, DataManager manager, Log logger) {
        logger.updateLog("Administrator " + admin.getName() + " logged in");
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
                    logger.updateLog(admin.getName() + " began adding new users");
                    while (true) {
                        System.out.print("Enter User Type [Scientist, Space Agency Representative, Administrator] or 'B' to go back: ");
                        String typeIn = scanner.nextLine();
                        if (typeIn.equalsIgnoreCase("B")) {
                            logger.updateLog(admin.getName() + " ceased adding new users");
                            break;
                        }
                        System.out.print("Enter User Name: ");
                        String nameIn = scanner.nextLine();
                        try {
                            admin.createUser(typeIn, nameIn, scanner);
                            logger.updateLog(admin.getName() + " added " + typeIn + " " + nameIn + " to the USERS list");
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                            System.out.println("Please try again or enter 'B' to discard operation and return to admin console.");
                        }
                    }
                    break;

                case 2:
                    logger.updateLog(admin.getName() + " began updating user info");
                    while (true) {
                        System.out.print("Enter the User Name to manage or 'B' to go back: ");
                        String name = scanner.nextLine();
                        if (name.equalsIgnoreCase("B")) {
                            logger.updateLog(admin.getName() + " ceased updating user info");
                            break;
                        }
                        if (admin.getUserByName(name) != null) {
                            admin.manageUser(name, scanner);
                            logger.updateLog(admin.getName() + " updated user " + name + "'s info");
                        } else {
                            System.out.println("No user found with Name: " + name + ". Try again or 'B' to discard operation and return to admin console.");
                        }
                    }
                    break;

                case 3:
                    logger.updateLog(admin.getName() + " began deleting users");
                    while (true) {
                        System.out.print("Enter the User Name to delete or 'B' to go back: ");
                        String name = scanner.nextLine();
                        if (name.equalsIgnoreCase("B")) {
                            logger.updateLog(admin.getName() + " ceased deleting users");
                            break;
                        }
                        if (admin.getUserByName(name) != null) {
                            admin.deleteUser(name);
                            logger.updateLog(admin.getName() + " deleted user " + name);
                        } else {
                            System.out.println("No user found with Name: " + name + ". Try again or 'B' to discard operation and return to admin console.");
                        }
                    }
                    break;

                case 4:
                    exit = true;
                    manager.updateUserData("USERS.csv");
                    logger.updateLog(admin.getName() + " logged out");
                    System.out.println("\n***Exited Administrative Console Gracefully.***");
                    break;

                default:
                    System.out.println("Invalid option. Choose a number between 1 and 4.");
            }
        }
    }

     /**
     * Runs the scientist console loop for tracking objects in space,
     * assessing objects orbital status, and updating the RSO_Metrics.
     * 
     * @param s - The authorized Scientist user
     * @param scanner - A scanner for prompting user input
     * @param manager - The scientist user's assigned DataManager
     */
    public void runScientistConsole(Scientist s, Scanner scanner, DataManager manager, Log logger) {
        logger.updateLog("Scientist " + s.getName() + " logged in");
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
                    logger.updateLog(s.getName() + " began tracking objects in space");
                    System.out.println("Select the type of object to track:");
                    System.out.println("ROCKET BODY | PAYLOAD | DEBRIS | UNKNOWN");
                    String objectType = scanner.nextLine();
                    s.trackObjectsInSpace(objectType);
                    logger.updateLog(s.getName() + " requested a " + objectType + " list");
                    break;

                case 2:
                    boolean subExit = false;
                    logger.updateLog(s.getName() + " began updating orbital metrics");
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
                                logger.updateLog(s.getName() + " requested a list of objects in LEO");
                                s.trackObjectsInLEO();
                                break;
                            case 2:
                                System.out.println("Assessing objects still in orbit...");
                                s.assessStillInOrbit();
                                System.out.println("Records updated");
                                logger.updateLog(s.getName() + " added results to still_in_orbit column");
                                break;
                            case 3:
                                System.out.println("Assessing risk level of objects...");
                                s.assessRiskLevel();
                                System.out.println("Records updated");
                                logger.updateLog(s.getName() + " added results to risk_level column");
                                break;
                            case 4:
                                subExit = true;
                                logger.updateLog(s.getName() + " ceased adding to Updated_RSO_Metrics.csv");
                                break;
                            default:
                                System.out.println("Invalid option. Please try again.");
                        }
                    }
                    logger.updateLog(s.getName() + " ceased updating orbital metrics");
                    break;

                case 3:
                    exit = true;
                    logger.updateLog(s.getName() + " logged out");
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }

        System.out.println("Generating Updated Metrics...");
        logger.updateLog("Writing Updated_RSO_Metrics.csv...");
        manager.updateMetricData("Updated_RSO_Metrics_test.csv");
        logger.updateLog("Update complete");
    }

    /**
     * Runs the Space Agency Representative console loop for analyzing long-term impacts
     * and generating density reports
     * 
     * @param s - The authorized space agency representative
     * @param scanner - A scanner for prompting users
     * @param manager - The users assigned DataManager
     */
    public void runSpaceAgencyRepConsole(SpaceAgencyRepresentative s, Scanner scanner, DataManager manager, Log logger){
        logger.updateLog("Space Agency Representative " + s.getName() + " logged in");
        boolean exit = false;
        while(!exit){
            System.out.println("\n====Space Agency Rep Console===="+
                                "\n1. Analyze Long-Term Impact" +
                                "\n2. Generate Density Report" +
                                "\n3. Go Back");
            System.out.println("Select an option 1 - 3:");
            int spAgRepChoice;
            try{
                spAgRepChoice = Integer.parseInt(scanner.nextLine());
            }catch(NumberFormatException e){
                System.out.println("Invalid input. Please enter a number between 1 - 3");
                continue;
            }

            switch(spAgRepChoice){
                case 1:
                    logger.updateLog(s.getName() + " began analyzing long term impacts");
                    s.analyzeLongTermImpact();
                    break;

                case 2:
                    logger.updateLog(s.getName() + " began generating a density report");
                    s.generateDensityReport(scanner);
                    break;

                case 3:
                    exit = true;
                    logger.updateLog(s.getName() + " logged out");
                    break;
            }
        }
    }
}
