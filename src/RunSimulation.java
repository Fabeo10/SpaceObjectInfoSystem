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
        DataManager userManager = new DataManager("users");
        DataManager metricsManager = new DataManager("metrics");
        userManager.updateLog("Sytem initialized");

        Scanner scanner = new Scanner(System.in);
        Administrator loginAdmin = new Administrator("loginAdmin");
        loginAdmin.setManager(userManager);
        boolean exit = false;
        while(!exit){
            loginAdmin.setUsers(userManager.getUsers());
            System.out.println("Please enter your Name (or exit): ");
            String loginAttemptName = scanner.nextLine();
            if(loginAttemptName.equalsIgnoreCase("exit")){
                exit = true;
                break;
            }
            System.out.println("Please enter your password: ");
            String loginAttemptPassword = scanner.nextLine();
            boolean loginSuccess = userManager.validateLogin(loginAttemptName, loginAttemptPassword);

            if(loginSuccess){
                Console console = new Console();
                User loggedInUser = loginAdmin.getUserByName(loginAttemptName);
                if(loggedInUser.getRole().equalsIgnoreCase("scientist")){
                    Scientist user = (Scientist) loggedInUser;
                    user.setManager(metricsManager);
                    user.setEntries(metricsManager.getRso_metrics());
                    console.runScientistConsole(user, scanner, metricsManager);
                }
                else if(loggedInUser.getRole().equalsIgnoreCase("administrator")){
                    Administrator user = (Administrator) loggedInUser;
                    user.setManager(userManager);
                    user.setUsers(userManager.getUsers());
                    console.runAdminConsole(user, scanner, userManager);
                }
                else if(loggedInUser.getRole().equalsIgnoreCase("Space Agency Representative")){
                    SpaceAgencyRepresentative user = (SpaceAgencyRepresentative) loggedInUser;
                    user.setManager(metricsManager);
                    user.setEntries(metricsManager.getRso_metrics());
                    console.runSpaceAgencyRepConsole(user, scanner, metricsManager);
                }
                else{
                    System.out.println("Error reaching console");
                }
            }
        }
        scanner.close();
    }
}
