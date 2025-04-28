import java.util.Map;

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
    public void createUser(String userType, String userName) {
        if (userType == null || userName == null) {
            throw new IllegalArgumentException("userType and userName must not be null");
        }
        User u;
        switch (userType.toLowerCase()) {

            case "scientist":
                u = new Scientist(userName);
                u.promptNewPassword();
                users.put(u.getName(), u);
                break;

            case "space agency representative":
            case "spaceagencyrepresentative":
                u = new SpaceAgencyRepresentative(userName);
                u.promptNewPassword();
                users.put(u.getName(), u);
                break;

            case "policy maker":
            case "policymaker":
                u = new PolicyMaker(userName);
                u.promptNewPassword();
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
    public void manageUser(String userName) {
        User u = users.get(userName);
        if (u != null) {
            System.out.print("Managing user: ");
            u.displayRole();
            // (additional management logic could go here)
        } else {
            System.out.println("No user found with ID: " + userName);
        }
    }

    /**
     * Deletes the user with the given ID from the system.
     *
     * @param id the unique ID of the user to delete
     */
    public void deleteUser(String userName) {
        if (users.remove(userName) != null) {
            System.out.println("Deleted user with ID: " + userName);
            manager.setUsers(users);
        } else {
            System.out.println("No user found with ID: " + userName);
        }
    }

    /**
     * Prints a summary of all users currently registered.
     */
    public void displayAllUsers() {
        System.out.println("=== Registered Users ===");
        users.values()
             .forEach(u -> System.out.println("- " + u.getName() + " (" + u.getRole() + ") Password: " + u.getPassword()));
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

    public void setManager(DataManager manager) {
        this.manager = manager;
    }

    public void setUsers(Map<String, User> users) {
        this.users = users;
    }
}
