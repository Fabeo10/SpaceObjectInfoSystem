import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataManager {
    private List<SpaceObject> rso_metrics;
    private Map<String, User> users;

    public DataManager(String datatype){
        if("users".equalsIgnoreCase(datatype)){
            loadUserData();
        }
        if("metrics".equalsIgnoreCase(datatype)){
            loadMetricData();
        }
    }

    /**
     * Loads an existing user of the specified type and name then stores it by its Name.
     *
     * @param userType the type of user to create (e.g. "Scientist", "Policy Maker")
     * @param userName the name of the new user
     */
    public User loadUser(String userType, String userName) {
        if (userType == null || userName == null) {
            throw new IllegalArgumentException("userType and userName must not be null");
        }
        switch (userType.toLowerCase()) {

            case "scientist":
                return new Scientist(userName);

            case "space agency representative":
            case "spaceagencyrepresentative":
                return new SpaceAgencyRepresentative(userName);

            case "policy maker":
            case "policymaker":
                return new PolicyMaker(userName);

            case "administrator":
                return new Administrator(userName);
                
            default:
                throw new IllegalArgumentException("Invalid User Type: " + userType);
        }
    }

    /**
     * Loads a list of already created users into the system
     */
    public void loadUserData() {
        Map<String, User> users = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("USERS.csv"))) {
            reader.readLine();                                                                      // Skip header line

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    String userName = parts[0].trim();
                    String userType = parts[1].trim();
                    String userPassword = parts[2].trim();

                    User user = loadUser(userType, userName);
                    user.setPassword(userPassword);
                    if (user != null) {
                        users.put(userName, user);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading users: " + e.getMessage());
        }
        this.users = users;
    }

    /**
     * Rewrites the list of created users adding any new Users
     */
    public void updateUserData(String filename){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("name,role,password");                                                 // First, write the header
            writer.newLine();

            for (User user : users.values()) {                                                      // Next, add each user
                String line = user.getName() + "," + user.getRole() + "," + user.getPassword();
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing users file: " + e.getMessage());
        }
    }

    /**
     * Loads the space object data from a CSV file into the entries list.
     */
    public void loadMetricData(){
        this.rso_metrics = CSVParser.readCsvFile("rso_metrics.csv");
    }

    /**
     * Updates and writes the current space object data to the specified file.
     * Also prints the time taken to perform the update.
     *
     * @param filename the file to which data will be written
     */
    public void updateMetricData(String filename){
        long startTime = System.nanoTime();
        CSVParser.writeRecordsToCsv(rso_metrics, filename);
        long endTime = System.nanoTime();
        long updateTime = (endTime - startTime) / 1000000;
        System.out.println("Time to load " + rso_metrics.size() + " entries: " + updateTime + "ms");
    }

    /**
     * Creates a timestamped log of user activities
     * 
     * @param description - A brief description of the users action
     */
    public static void updateLog(String description) {
        DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timestamp = LocalDateTime.now().format(FORMATTER);
        String entry = "[" + timestamp + "] " + description;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("LOGS.txt", true))) {
            writer.write(entry);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }

    public List<SpaceObject> getRso_metrics() {
        return rso_metrics;
    }

    public Map<String, User> getUsers() {
        return users;
    }

    public void setRso_metrics(List<SpaceObject> rso_metrics) {
        this.rso_metrics = rso_metrics;
    }

    public void setUsers(Map<String, User> users) {
        this.users = users;
    }

    public boolean validateLogin(String name, String password){
        User loginAttempt = users.get(name);
        if(loginAttempt != null){
            return loginAttempt.getPassword().equals(password);
        }
        else{
            return false;
        }
    }
}
