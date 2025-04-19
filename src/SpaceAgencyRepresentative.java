/**
 * Represents a Space Agency Representative in the Space Object Info System.
 * These users represent organizations such as NASA or ESA and collaborate on missions and data.
 *
 * <p>
 * Example:
 * <pre>
 *     SpaceAgencyRepresentative rep = new SpaceAgencyRepresentative("Commander Liu Yang");
 *     rep.displayRole(); // Outputs: "Space Agency Representative: Commander Liu Yang"
 * </pre>
 * </p>
 *
 * @author Fabian
 * @author David
 * @version 1.0
 */
public class SpaceAgencyRepresentative extends User {

    /**
     * Constructs a SpaceAgencyRepresentative with the specified name.
     *
     * @param name the name of the representative
     */
    public SpaceAgencyRepresentative(String name) {
        super(name, "Space Agency Representative");
    }

    /**
     * Displays the role and name of the representative.
     */
    @Override
    public void displayRole() {
        System.out.println("Space Agency Representative: " + name);
    }
}
