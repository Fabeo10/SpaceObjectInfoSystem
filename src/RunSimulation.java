import java.util.Scanner;

/**
 * Entry point for the Space Object Info System simulation.
 * Main menu allows selecting user type and redirects to appropriate console.
 * Only the Administrator console may create users; other users must already exist.
 */
public class RunSimulation {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Administrator admin = null;
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
                                runScientistConsole(s, scanner);
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
                        System.out.println("Welcome back, " + admin.getRole() + " " + admin.getName() + "!");
                        System.out.println("Your ID is: " + admin.getId());
                    }
                    runAdminConsole(admin, scanner);
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

    /**
     * Runs the scientist console loop: menu actions for a verified scientist.
     */
    private static void runScientistConsole(Scientist s, Scanner scanner) {
        boolean exit = false;
        while (!exit) {
            System.out.println("\nScientist Console:"
                    + "\n1. Track Objects in Space"
                    + "\n2. Assess Object Orbital Status"
                    + "\n3. Go Back");
            System.out.print("Select an option (1-3): ");
            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 3.");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.println("Select the type of object to track:");
                    System.out.println("ROCKET BODY | PAYLOAD | DEBRIS | UNKNOWN");
                    String objectType = scanner.nextLine();
                    s.trackObjectsInSpace(objectType);
                    break;

                case 2:
                    boolean subExit = false;
                    while (!subExit) {
                        System.out.println("\nWould you like to:"
                                + "\n1. Assess Objects Still in Orbit"
                                + "\n2. Assess Risk Level of Objects"
                                + "\n3. Go Back");
                        System.out.print("Select an option (1-3): ");
                        int subChoice;
                        try {
                            subChoice = Integer.parseInt(scanner.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter a number between 1 and 3.");
                            continue;
                        }

                        switch (subChoice) {
                            case 1:
                                System.out.println("Assessing objects still in orbit...");
                                s.assessStillInOrbit();
                                System.out.println("Records updated");
                                break;
                            case 2:
                                System.out.println("Assessing risk level of objects...");
                                s.assessRiskLevel();
                                System.out.println("Records updated");
                                break;
                            case 3:
                                subExit = true;
                                break;
                            default:
                                System.out.println("Invalid option. Please try again.");
                        }
                    }
                    break;

                case 3:
                    exit = true;
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }

        System.out.println("Generating Updated Metrics...");
        s.updateData("Updated_RSO_Metrics_test.csv");
    }

    /**
     * Runs the administrator console loop for creating, managing,
     * and deleting users.
     */
    private static void runAdminConsole(Administrator admin, Scanner scanner) {
        boolean exit = false;
        while (!exit) {
            System.out.println("\n=== Administrator Console ==="
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
                    System.out.println("\n***Exited Administrative Console Gracefully.***");
                    break;

                default:
                    System.out.println("Invalid option. Choose a number between 1 and 4.");
            }
        }
    }
}
