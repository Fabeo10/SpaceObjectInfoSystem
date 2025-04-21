import java.util.HashMap;
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
 * @author Fabian
 * @author David
 * @version 1.0
 */
public class Administrator extends User {

    private final Map<String, User> users = new HashMap<>();

    /**
     * Constructs an Administrator with the given name.
     *
     * @param name the name of the administrator
     */
    public Administrator(String name) {
        super(name, "Administrator");
    }

    /**
     * Creates a new user of the specified type and name, stores it by its ID,
     * and prints confirmation.
     *
     * @param userType the type of user to create (e.g. "Scientist", "Policy Maker")
     * @param userName the name of the new user
     */
    public void createUser(String userType, String userName) {
        User u = UserFactory.createUser(userType, userName);
        users.put(u.getId(), u);
        System.out.println("Created a user " + u.getRole() + ", with Name: " + u.getName() + " and ID: " + u.getId());
    }

    /**
     * Retrieves a user by ID and invokes its displayRole().
     *
     * @param id the unique ID of the user to manage
     */
    public void manageUser(String id) {
        User u = users.get(id);
        if (u != null) {
            System.out.print("Managing user: ");
            u.displayRole();
            // (additional management logic could go here)
        } else {
            System.out.println("No user found with ID: " + id);
        }
    }

    /**
     * Deletes the user with the given ID from the system.
     *
     * @param id the unique ID of the user to delete
     */
    public void deleteUser(String id) {
        if (users.remove(id) != null) {
            System.out.println("Deleted user with ID: " + id);
        } else {
            System.out.println("No user found with ID: " + id);
        }
    }

    /**
     * Prints a summary of all users currently registered.
     */
    public void displayAllUsers() {
        System.out.println("=== Registered Users ===");
        users.values()
             .forEach(u -> System.out.println("- " + u.getName() + " (" + u.getRole() + ") ID: " + u.getId()));
    }

    /**
     * Displays the role of the administrator.
     */
    @Override
    public void displayRole() {
        System.out.println("Administrator: " + name);
    }

    /**
     * Retrieves a user by their unique ID.
     *
     * @param id the ID of the user
     * @return the User object, or null if not found
     */
    public User getUserById(String id) {
        return users.get(id);
    }
}
