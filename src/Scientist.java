import java.util.ArrayList;
import java.util.List;

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
 * @author Fabian Ornelas
 * @author David Jones
 * @version 1.0
 */
public class Scientist extends User implements FilterInterface{
    private DataManager manager;
    private List<SpaceObject> entries;
    /**
     * Constructs a Scientist with the specified name and loads space object data.
     *
     * @param name the name of the scientist
     */
    public Scientist(String name) {
        super(name, "Scientist");
    }

    /**
     * Used to assign the scientist user with a RSO_Metrics DataManager
     */
    public void setManager(DataManager manager) {
        this.manager = manager;
    }
    
    /**
     * Used to assign the scientist user with a list of space objects
     */
    public void setEntries(List<SpaceObject> entries) {
        this.entries = entries;
    }

    /**
     * Displays the role and name of the scientist.
     */
    @Override
    public void displayRole() {
        System.out.println("Scientist " + name + "!");
    }

    /**
     * Allows the scientist user to filter the list of space objects by either
     * those in Low Earth Orbit, or those of a certain Object_type
     * 
     * @param filteredEntries - The empty list of space objects to be filled
     * @param criteria - The field that the scientist would like to filter by, eith LEO or Object_type
     */
    @Override
    public void filterByField(List<SpaceObject> filteredEntries, String criteria){
        for(SpaceObject object : entries){
            if(criteria.equalsIgnoreCase("leo")){
                if(object.getOrbitType().toLowerCase().contains("leo")){
                    filteredEntries.add(object);
                }
            }
            if(object.getObject_type().toLowerCase().contains(criteria.toLowerCase())){
                filteredEntries.add(object);
            }
        }
    }

    /**
     * Filters and displays all space objects that match a specified object type.
     * Also prints the time taken to complete the filtering and display.
     *
     * @param object_type the type of object to track (e.g., "debris", "satellite")
     */
    public void trackObjectsInSpace(String object_type){
        List<SpaceObject> filteredEntries = new ArrayList<>();
        filterByField(filteredEntries, object_type);
        for(SpaceObject object : filteredEntries){
            System.out.println(object);
        }
    }

    /**
     * Filters and displays all space objects currently in Low Earth Orbit (LEO).
     * Also prints the time taken to complete the filtering and display.
     */
    public void trackObjectsInLEO(){
        List<SpaceObject> filteredEntries = new ArrayList<>();
        filterByField(filteredEntries, "leo");
        for(SpaceObject object : filteredEntries){
            System.out.println(object);
        }
    }

    /**
     * Assesses the risk level of each space object based on its orbital drift.
     * Risk levels are categorized as Low, Moderate, or High.
     * Also prints the time taken to complete the assessment.
     */
    public void assessRiskLevel(){
        for(SpaceObject object : entries){
            double orbitalDrift = Math.abs((object.getLongitude() - object.getAverageLongitude()));
            if(orbitalDrift >= 50){
                object.setRiskLevel("High");
            }
            else if(orbitalDrift < 50 && orbitalDrift >= 10){
                object.setRiskLevel("Moderate");
            }
            else{
                object.setRiskLevel("Low");
            }
        }
        manager.setRso_metrics(entries);
    }

    /**
     * Evaluates whether each space object is still in orbit based on various conditions,
     * such as orbit type, longitude, age, and conjunction count.
     * Also prints the time taken to complete the evaluation.
     */
    public void assessStillInOrbit(){
        for(SpaceObject object : entries){
            if(((object.getOrbitType() == null) || ((object.getLongitude() > 180) ||
                (object.getLongitude() < -180)) || (object.getDaysOld() >= 15000)) &&
                (object.getConjunctionCount() == 0)){
                    object.setStillInOrbit(false);
            } else {
                    object.setStillInOrbit(true);   
            }
        }
        manager.setRso_metrics(entries);
    }
}
