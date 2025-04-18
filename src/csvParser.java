import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * Used for reading a list of entries from a file which are stored in Comma Separated Value format
 * while allowing for entries to contain escaped commas so long as they are enclosed in double quotes.
 * 
 * @author David Jones, Fabian Ornelas
 */
public class csvParser {
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
        
        //We iterate over the string one character at a time
        for (int i = 0; i < line.length(); i++) {
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
    public static spaceObject spaceObjectFromEntry(List<String> fields){
        String recordID = fields.get(0);
        String sattelliteName = fields.get(2);
        String country = fields.get(3);
        String orbitType = fields.get(4);
        int launchYear = Integer.parseInt(fields.get(6));
        String launchSite = fields.get(7);
        double longitude = Double.parseDouble(fields.get(8));
        double averageLongitude = Double.parseDouble(fields.get(9));
        String geohash = fields.get(10);
        int daysOld = Integer.parseInt(fields.get(18));
        long conjunctionCount = Long.parseLong(fields.get(19));
        return new spaceObject(recordID, sattelliteName, country, orbitType,
                            launchYear, launchSite, longitude, averageLongitude,
                            geohash, daysOld, conjunctionCount);
    }

    /**
     * Reads a CSV file line by line
     * 
     * @param filename - The given file where the data is stored
     * @return A list of all the entries in the file
     */
    public static List<spaceObject> readCsvFile(String filename) {
        List<spaceObject> entries = new ArrayList<>();                                 //Fields are stored as a list of Strings

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
    
    public static void main(String[] args){
        String fileName = "rso_metrics.csv";
        List<spaceObject> testList = readCsvFile(fileName);
        for(spaceObject x : testList){
            System.out.println(x);
        }
    }
}
