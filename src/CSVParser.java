import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * Used for reading a list of entries from a file which are stored in Comma Separated Value format
 * while allowing for entries to contain escaped commas so long as they are enclosed in double quotes.
 * 
 * @author David Jones, Fabian Ornelas
 */
public class CSVParser {
    /**
     * Parses a String of data from a CSV file into a list of fields, allowing for
     * escaped commas which are enclosed by double quotes
     * 
     * @param line - The String of data to be parsed for entries
     * @return The parsed list of fields
     */
    public static List<String> parseLine(String line) {
        List<String> fields = new ArrayList<>();
        StringBuilder field = new StringBuilder(32);
        boolean inQuotes = false;
        
        for (int i = 0; i < line.length(); i++) {   //We iterate over the string one character at a time
            char c = line.charAt(i);

            if (c == '\"') {                        //Enclosing quote detected
                inQuotes = !inQuotes;               //Switches mode to include next comma in entry
            } else if (c == ',' && !inQuotes) {     //Comma outside of enclosing quotes means end of entry
                fields.add(field.toString());       //Adds current field to list
                field.setLength(0);       //Resets field builder
            } else {
                field.append(c);                    //Adds current character to field string
            }
        }

        fields.add(field.toString());               // Add final field
        return fields;
    }

    /**
     * Creates a new Space Object from the data in the fields, indexed to our CSV file
     * 
     * @param fields - The list of fields derived from the file
     * @return A new Space Object with the given attributes
     */
    public static SpaceObject spaceObjectFromEntry(List<String> fields){
        String recordID = fields.get(0);
        String sattelliteName = fields.get(2);
        String country = fields.get(3);
        String orbitType = fields.get(4);
        String object_type = fields.get(5);
        int launchYear = Integer.parseInt(fields.get(6));
        String launchSite = fields.get(7);
        double longitude = Double.parseDouble(fields.get(8));
        double averageLongitude = Double.parseDouble(fields.get(9));
        String geohash = fields.get(10);
        int daysOld = Integer.parseInt(fields.get(18));
        long conjunctionCount = Long.parseLong(fields.get(19));
        return new SpaceObject(recordID, sattelliteName, country, orbitType, object_type,
                            launchYear, launchSite, longitude, averageLongitude,
                            geohash, daysOld, conjunctionCount);
    }

    /**
     * Reads a CSV file line by line and creates a new object from each entry
     * 
     * @param filename - The given file where the data is stored
     * @return A list of all the created objects
     */
    public static List<SpaceObject> readCsvFile(String filename) {
        List<SpaceObject> entries = new ArrayList<>();                                 //Fields are stored as a list of Strings

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            reader.readLine();                                                         //Discards the header row   
            String line;
            while ((line = reader.readLine()) != null) {
                List<String> entry = parseLine(line);
                entries.add(spaceObjectFromEntry(entry));                              //Adds current entry to the list of Space Objects
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        return entries;
    }

    public static void writeRecordsToCsv(List<SpaceObject> records, String filename){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            // Write header
            writer.write("record_id,norad_cat_id,satellite_name,country,approximate_orbit_type,object_type,launch_year,launch_site,longitude,avg_longitude,geohash,HRR_Category,is_nominated,nominated_at,has_dossier,last_updated_at,justification,focused_analysis,days_old,conjunction_count,is_unk_object,all_maneuvers,days_since_ob,recent_maneuvers,deltaV_90day,has_sister_debris");
            writer.newLine();

            // Write each record
            for (SpaceObject object : records) {
                writer.write(object.toCsv());
                writer.newLine();
            }
        }catch(IOException e){
            System.out.println(e);
        }
    }
    
    public static void main(String[] args){
        String fileName = "rso_metrics_test1.csv";
        long startTime = System.nanoTime();
        List<SpaceObject> testList = readCsvFile(fileName);
        for(SpaceObject x : testList){
            if(x.getOrbitType().equals("LEO")){
                System.out.println(x);
            }
        }
        writeRecordsToCsv(testList, "rso_metrics_test2.csv");
        long endTime = System.nanoTime();
        long totalTime = (endTime - startTime) / 1000000;
        System.out.println("Elapsed time: " + totalTime);
    }
}
