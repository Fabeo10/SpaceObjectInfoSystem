import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
public class SpaceAgencyRepresentative extends User implements FilterInterface{
    private DataManager manager;
    private List<SpaceObject> entries;

    /**
     * Constructs a SpaceAgencyRepresentative with the specified name.
     *
     * @param name the name of the representative
     */
    public SpaceAgencyRepresentative(String name) {
        super(name, "Space Agency Representative");
    }

    public void setManager(DataManager manager) {
        this.manager = manager;
    }

    public void setEntries(List<SpaceObject> entries) {
        this.entries = entries;
    }

    /** 
     * Analyze the long-term impact for all objects in LEO by assessing data from the CSV.
     * Objects are considered to have a long-term impact if they are greater than 200 days old
     * and their conjunction count is greater than 0.
     */
    public void analyzeLongTermImpact() {
        List<SpaceObject> filteredEntries = new ArrayList<>();
        filterByField(filteredEntries, "leo");
        for(SpaceObject object : filteredEntries){
            if(object.getDaysOld() > 200 && object.getConjunctionCount() > 0){
                System.out.println(object.toImpactDisplay());
            }
        }
    }

    /**
     * Generates a report on the density of space objects.
     * The user will be prompted to enter two values for longitudes.
     */
    public void generateDensityReport(Scanner scnr) {
        List<SpaceObject> filteredEntries = new ArrayList<>();
        filterByLongitude(filteredEntries, scnr);
        System.out.println("Please enter the identifier for your report: ");
        String identifier = scnr.nextLine();
        manager.generateDensityReport(filteredEntries, identifier);
    }

    /**
     * Displays the role and name of the representative.
     */
    @Override
    public void displayRole() {
        System.out.println("Space Agency Representative: " + name);
    }

    /**
     * Filters the list of all Space Objects into the provided filtered list by field
     * This implementation is only for filtering objects in Low Earth Orbit
     */
    @Override
    public void filterByField(List<SpaceObject> filteredEntries, String criteria){
        for(SpaceObject object : entries){
            if(object.getOrbitType().toLowerCase().contains("leo")){
                filteredEntries.add(object);
            }
        }
    }

    /**
     * Filters the list of all Objects to include only those with a Longitude within the upper and lower limit
     * 
     * @param filteredEntries - An empty list to store the filtered entries
     */
    private void filterByLongitude(List<SpaceObject> filteredEntries, Scanner scnr){
        System.out.println("Please enter the lower longitude limit: ");
        double lowerLimit = Double.parseDouble(scnr.nextLine());
        System.out.println("Please enter the upper longitude limit: ");
        double upperLimit = Double.parseDouble(scnr.nextLine());

        for(SpaceObject object : entries){
            if(object.getLongitude() < upperLimit && object.getLongitude() > lowerLimit){
                filteredEntries.add(object);
            }
        }
    }
}
