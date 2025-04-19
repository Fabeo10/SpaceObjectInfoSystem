/**
 * Represents a Scientist user in the Space Object Info System.
 * Scientists are typically involved in research and analysis of space data.
 *
 * <p>
 * Example:
 * <pre>
 *     Scientist scientist = new Scientist("Dr. Jane Doe");
 *     scientist.displayRole(); // Outputs: "Scientist: Dr. Jane Doe"
 * </pre>
 * </p>
 *
 * @author Fabian
 * @author David
 * @version 1.0
 */
public class Scientist extends User {

    /**
     * Constructs a Scientist with the specified name.
     *
     * @param name the name of the scientist
     */
    public Scientist(String name) {
        super(name, "Scientist");
    }

    /**
     * Displays the role and name of the scientist.
     */
    @Override
    public void displayRole() {
        System.out.println("Scientist: " + name);
    }
}
