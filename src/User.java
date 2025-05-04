import java.util.Scanner;

/**
 * The {@code User} abstract class defines the core behavior and common functionality
 * for all user types in the Space Object Info System.
 * <p>
 * Subclasses (e.g., Scientist, Administrator, SpaceAgencyRepresentative, PolicyMaker)
 * must provide a concrete implementation of {@link #displayRole()}.
 * </p>
 *
 * <p>
 * Example usage:
 * <pre>
 *     User user = new Scientist("Dr. Jane Doe");
 *     user.displayRole(); // Outputs the role of the user
 * </pre>
 * </p>
 *
 * <p>
 * IDs are generated automatically using a basic static counter
 * to ensure uniqueness within a single runtime session.
 * </p>
 *
 * @author Fabian Ornelas
 * @author David Jones
 * @version 1.0
 */
public abstract class User {
    protected String password;
    protected String name;
    protected String role;

    /**
     * Constructs a User with a specified name and role.
     * A unique ID is assigned using a simple static counter.
     *
     * @param name the name of the user
     * @param role the role of the user
     */
    public User(String name, String role) {
        this.name = name;
        this.role = role;
    }

    /**
     * Gets the name of the user.
     *
     * @return the user's name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the role of the user.
     *
     * @return the user's role
     */
    public String getRole() {
        return role;
    }

    /**
     * Gets the password of the user
     * @return the user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the users password
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Sets the users name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the users role
     * @param role
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Displays the role associated with the user.
     * <p>
     * Each subclass must implement this method to define how the role is displayed.
     * </p>
     */
    public abstract void displayRole();

    /**
     * Propmts a newly created user to enter their password
     * 
     * @param scanner
     */
    public void promptNewPassword(Scanner scanner){
        System.out.println("Please enter this user's password:");
        String password = scanner.nextLine();
        this.password = password;
    }

    /**
     * Formats the user data in Comma Separated Value format
     * @return the formatted String
     */
    public String toCsv(){
        return String.format("%s,%s,%s",name, role, password);
    }
}
