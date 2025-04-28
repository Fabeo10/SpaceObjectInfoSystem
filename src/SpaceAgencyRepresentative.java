import java.util.List;

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
 * @author Fabian Ornelas
 * @author David Jones
 * @version 1.0
 */
public class SpaceAgencyRepresentative extends User{

    private List<SpaceObject> entries;

    /**
     * Constructs a SpaceAgencyRepresentative with the specified name.
     *
     * @param name the name of the representative
     */
    public SpaceAgencyRepresentative(String name) {
        super(name, "Space Agency Representative");
    }

    /** 
     * Analyze the long-term impact for all objects in LEO by assessing data from the CSV.
     * <p>
     *     The data analyzed includes: 
     *     <ul>
     *         <li>days_old -> determines if the age of the object is greater than 200 days.</li>
     *         <li>conjunction_count -> determines if the conjunction count is greater than 0.</li>
     *     </ul>
     * </p>
     * 
     * this method is a placeholder and does not perform any actual analysis.
     */
    void analyzeLongTermImpact() {}

    /**
     * Generates a report on the density of space objects.
     * The user will be prompted to enter two values for longitudes.
     * <p>
     *     Based on the values entered, the report will generate:
     *     <ul>
     *         <li>Count of objects in the longitude range.</li>
     *         <li>Record ID</li>
     *         <li>Satellite Name</li>
     *         <li>Country</li>
     *         <li>Orbit Type</li>
     *         <li>Launch Year</li>
     *         <li>Object Type</li>
     *     </ul>
     * </p>
     * 
     * this method is a placeholder and does not perform any actual report generation.
     */
    void generateDensityReport() {}

    /**
     * Displays the role and name of the representative.
     */
    @Override
    public void displayRole() {
        System.out.println("Space Agency Representative: " + name);
    }


}
