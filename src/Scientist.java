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
 * @author Fabian
 * @author David
 * @version 1.0
 */
public class Scientist extends User {
    private List<SpaceObject> entries;

    /**
     * Constructs a Scientist with the specified name.
     *
     * @param name the name of the scientist
     */
    public Scientist(String name) {
        super(name, "Scientist");
        loadData();
    }

    /**
     * Displays the role and name of the scientist.
     */
    @Override
    public void displayRole() {
        System.out.println("Scientist " + name + "!");
    }

    public void loadData(){
        this.entries = CSVParser.readCsvFile("rso_metrics.csv");
    }

    public void updateData(String filename){
        long startTime = System.nanoTime();
        CSVParser.writeRecordsToCsv(entries, filename);
        long endTime = System.nanoTime();
        long updateTime = (endTime - startTime) / 1000000;
        System.out.println("Time to load " + entries.size() + " entries: " + updateTime + "ms");
    }

    public void trackObjectsInSpace(String object_type){
        long startTime = System.nanoTime();
        List<SpaceObject> filteredEntries = new ArrayList<>();
        for(SpaceObject object : entries){
            if(object.getObject_type().toLowerCase().contains(object_type.toLowerCase())){
                filteredEntries.add(object);
            }
        }
        for(SpaceObject object : filteredEntries){
            System.out.println(object);
        }
        long endTime = System.nanoTime();
        long trackingTime = (endTime - startTime) / 1000000;
        System.out.println("Time to track " + filteredEntries.size() + " objects of this type in space: " + trackingTime + "ms");
    }

    public void trackObjectsInLEO(){
        long startTime = System.nanoTime();
        List<SpaceObject> filteredEntries = new ArrayList<>();
        for(SpaceObject object : entries){
            if(object.getOrbitType().toLowerCase().contains("leo")){
                filteredEntries.add(object);
            }
        }
        for(SpaceObject object : filteredEntries){
            System.out.println(object);
        }
        long endTime = System.nanoTime();
        long trackLEOtime = (endTime - startTime) /1000000;
        System.out.println("Time to track " + filteredEntries.size() + " in LEO: " + trackLEOtime + "ms");
    }

    public void assessRiskLevel(){
        long startTime = System.nanoTime();
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
        long endTime = System.nanoTime();
        long assesssRiskTime = (endTime - startTime) / 1000000;
        System.out.println("Time to assess the risk level of " + entries.size() + " entries: " + assesssRiskTime + "ms");
    }

    public void assessStillInOrbit(){
        long startTime = System.nanoTime();
        for(SpaceObject object : entries){
            if(((object.getOrbitType() == null) || ((object.getLongitude() > 180) ||
                (object.getLongitude() < -180)) || (object.getDaysOld() >= 15000)) &&
                (object.getConjunctionCount() == 0)){
                    object.setStillInOrbit(false);
            }else{
                    object.setStillInOrbit(true);   
            }
        }
        long endTime = System.nanoTime();
        long assessOrbitalStatusTime = (endTime - startTime) / 1000000;
        System.out.println("Time to assess the orbital status of " + entries.size() + " entries:" + assessOrbitalStatusTime + "ms");
    }
}
