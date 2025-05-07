import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
/**
 * Creates a timestamped LOGS.txt with a description of system processes and user actions
 *  * <p>
 * Example:
 * <pre>
 * //Creating a log of Scientist s requesting a filtered list
 * Log.updateLog("Scientist " + s.name + " requested a " + objectType + " list");
 * </pre>
 * </p>
 * 
 * @author David Jones
 * @author Fabian Ornelas
 * @version 1.0
 */
public class Log {
    private static final String LOG_FILE = "LOGS.txt";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public void updateLog(String description) {
        String timestamp = LocalDateTime.now().format(FORMATTER);
        String entry = "[" + timestamp + "] " + description;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
            writer.write(entry);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }
}
