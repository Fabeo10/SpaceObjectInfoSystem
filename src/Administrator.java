import java.util.Map;
import java.util.Scanner;

/**
 * Represents an Administrator who can create, manage and delete users within the system.
 * Internally stores users in a map keyed by their unique ID.
 * <p>
 * Example:
 * <pre>
 *     Administrator admin = new Administrator("Fabian");
 *     // create two users
 *     admin.createUser("Scientist", "Dr. Jane");
 *     admin.createUser("Policy Maker", "Senator Alex");
 *     // list everyone
 *     admin.displayAllUsers();
 *     // manage a specific user
 *     admin.manageUser(someId);
 *     // delete
 *     admin.deleteUser(someOtherId);
 * </pre>
 * </p>
 *
 * @author Fabian Ornelas
 * @author David Jones
 * @version 1.0
 */
public class Administrator extends User{
    private DataManager manager;
    private Map<String, User> users;

    /**
     * Constructs an Administrator with the given name.
     *
     * @param name the name of the administrator
     */
    public Administrator(String name) {
        super(name, "Administrator");
    }

    /**
     * Creates a new user of userType with userName, thens adds the new User to the current list, by Name
     * 
     * @param userType - The type of user to be created i.e. Scientist, Administrator, Space Agency Representative
     * @param userName - The name to be assigned to the newly created User
     */
    public void createUser(String userType, String userName, Scanner scanner) {
        if (userType == null || userName == null) {
            throw new IllegalArgumentException("userType and userName must not be null");
        }
        User u;
        switch (userType.toLowerCase()) {

            case "scientist":
                u = new Scientist(userName);
                u.promptNewPassword(scanner);
                users.put(u.getName(), u);
                break;

            case "space agency representative":
            case "spaceagencyrepresentative":
                u = new SpaceAgencyRepresentative(userName);
                u.promptNewPassword(scanner);
                users.put(u.getName(), u);
                break;

            case "administrator":
                u = new Administrator(userName);
                u.promptNewPassword(scanner);
                users.put(u.getName(), u);
                break;
                
            default:
                throw new IllegalArgumentException("Invalid User Type: " + userType);
        }
        manager.setUsers(users);
    }

    /**
     * Retrieves a user by Name and invokes its displayRole().
     *
     * @param userName the unique Name of the user to manage
     */
    public void manageUser(String userName, Scanner scanner) {
        User u = users.get(userName);
        if (u != null) {
            boolean exit = false;
            while(!exit){
                System.out.print("Managing user: ");
                u.displayRole();
                System.out.println("\n===Update User Info===" +
                                    "\n1. Change User Name" +
                                    "\n2. Change User Role" +
                                    "\n3. Change User Password" +
                                    "\n4. Go Back");
                int manageUserChoice;
                try{
                    manageUserChoice = Integer.parseInt(scanner.nextLine());
                }catch(NumberFormatException e){
                    System.out.println("Invalid input. Please enter a number 1-4");
                    continue;
                }
                switch(manageUserChoice){
                    case 1:
                        System.out.println("Please enter new Name:");
                        String newName = scanner.nextLine();
                        u.setName(newName);
                        break;
                    case 2:
                        System.out.println("Please enter new Role:");
                        String newRole = scanner.nextLine();
                        u.setRole(newRole);
                        break;
                    case 3:
                        System.out.println("Please enter new Password:");
                        String newPassword = scanner.nextLine();
                        u.setPassword(newPassword);
                        break;
                    case 4:
                        exit = true;
                        break;
                }
            }
        } else {
            System.out.println("No user found with Name: " + userName);
        }
    }

    /**
     * Deletes the user with the given ID from the system.
     *
     * @param id the unique ID of the user to delete
     */
    public void deleteUser(String userName) {
        if (users.remove(userName) != null) {
            System.out.println("Deleted user with Name: " + userName);
            manager.setUsers(users);
        } else {
            System.out.println("No user found with Name: " + userName);
        }
    }

    /**
     * Displays the role of the administrator.
     */
    @Override
    public void displayRole() {
        System.out.println("Administrator: " + name);
    }

    /**
     * Retrieves a user by their unique Name.
     *
     * @param name the Name of the user
     * @return the User object, or null if not found
     */
    public User getUserByName(String name) {
        return users.get(name);
    }

    /**
     * Used to assign a DataManager to an Administrator
     * 
     * @param manager - The assigned User DataManager
     */
    public void setManager(DataManager manager) {
        this.manager = manager;
    }

    /**
     * Used to provide an administrator with a list of authorized users
     * 
     * @param users - The list of authorized users
     */
    public void setUsers(Map<String, User> users) {
        this.users = users;
    }
}
