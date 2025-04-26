import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**
 * Used for reading a list of entries from a file which are stored in Comma Separated Value format
 * while allowing for entries to contain escaped commas so long as they are enclosed in double quotes.
 * 
 * @author David Jones
 * @author Fabian Ornelas
 * @version 1.0
 */
public class CSVParser {
    private static HashMap<String, Integer> headers;
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
        // Basic attributes for identification and location
        String recordID = getField(fields, "record_id");
        String sattelliteName = getField(fields, "satellite_name");
        String country = getField(fields, "country");
        String orbitType = getField(fields, "approximate_orbit_type");
        String object_type = getField(fields, "object_type");
        int launchYear = Integer.parseInt(getField(fields, "launch_year"));
        String launchSite = getField(fields, "launch_site");
        double longitude = Double.parseDouble(getField(fields, "longitude"));
        double averageLongitude = Double.parseDouble(getField(fields, "avg_longitude"));
        String geohash = getField(fields, "geohash");

        // Analytical metrics
        int daysOld = Integer.parseInt(getField(fields, "days_old"));
        long conjunctionCount = Long.parseLong(getField(fields, "conjunction_count"));

        return new SpaceObject(recordID, sattelliteName, country, orbitType, object_type,
                            launchYear, launchSite, longitude, averageLongitude,
                            geohash, daysOld, conjunctionCount);
    }

    private static String getField(List<String> fields, String columnName) {
        Integer index = headers.get(columnName.toLowerCase());
        if (index == null || index >= fields.size()) {
            return ""; // or throw an error, depending on how strict you want to be
        }
        return fields.get(index);
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
            String headerLine = reader.readLine();                                     
            List<String> headerFields = parseLine(headerLine); 

            headers = new HashMap<>();                                                 
            for (int i = 0; i < headerFields.size(); i++) {                            
                headers.put(headerFields.get(i).toLowerCase(), i);                     //Parses the header line into a list of fields, mapped to integers
            }
        
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

    /**
     * Writes a formatted header followed by a CSV formatted list of entries into a new CSV file
     * 
     * @param records - The formatted list of records to be written into the csv
     * @param filename - The name of the newly created CSV file
     */
    public static void writeRecordsToCsv(List<SpaceObject> records, String filename){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            // Write header
            writer.write("record_id,norad_cat_id,satellite_name,country,approximate_orbit_type,object_type,launch_year,launch_site,longitude,avg_longitude,geohash,HRR_Category,is_nominated,nominated_at,has_dossier,last_updated_at,justification,focused_analysis,days_old,conjunction_count,is_unk_object,all_maneuvers,days_since_ob,recent_maneuvers,deltaV_90day,has_sister_debris,still_in_orbit,risk_level");
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
}
