/**
 * The {@code User} interface defines the core behavior that any user type
 * in the Space Object Info System must implement.
 * <p>
 * Implementing classes are expected to provide a concrete implementation
 * for displaying the role of the user.
 * </p>
 *
 * <p>
 * Example usage:
 * <pre>
 *     User user = new Scientist();
 *     user.displayRole(); // Outputs the role of the user
 * </pre>
 * </p>
 *
 * @authors Fabian, David
 */
public interface User {
    /**
     * Displays the role associated with the user.
     * <p>
     * This method should be implemented by each user type (e.g., Scientist,
     * SpaceAgencyRepresentative, PolicyMaker) to print or return the role
     * information respective to the user input.
     * </p>
     */
    void displayRole();
}
