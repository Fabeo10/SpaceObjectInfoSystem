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
 * @author Fabian
 * @author David
 * @version 1.0
 */
public abstract class User {
    private static int idCounter = 0;

    protected final String id;
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
        this.id = String.valueOf(++idCounter);
        this.name = name;
        this.role = role;
    }

    /**
     * Returns the unique ID of the user.
     *
     * @return the user's ID
     */
    public String getId() {
        return id;
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
     * Displays the role associated with the user.
     * <p>
     * Each subclass must implement this method to define how the role is displayed.
     * </p>
     */
    public abstract void displayRole();
}
