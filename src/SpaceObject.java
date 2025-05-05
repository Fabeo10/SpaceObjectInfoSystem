import java.security.Timestamp;

/**
 * Represents a space object being tracked, such as a satellite or debris,
 * with various attributes like position, launch info, and risk assessments.
 *
 * <p>This class is used for analysis, tracking, and exporting data related
 * to space objects in Earth orbit.</p>
 *
 * <p>Example usage:
 * <pre>
 *     SpaceObject obj = new SpaceObject("123", "Sat-1", "USA", "LEO", "satellite", 2020, "KSC",
 *                                       45.0, 44.5, "dr5rs", 1000, 2);
 *     System.out.println(obj.getOrbitType()); // LEO
 * </pre>
 * </p>
 * 
 * @author David Jones
 * @author Fabian Ornelas
 * @version 1.0
 */
public class SpaceObject {
    // Basic attributes for identification and location
    private String recordID;
    private String norad_cat_id;
    private String sattelliteName;
    private String country;
    private String orbitType;
    private String object_type;
    private int launchYear;
    private String launchSite;
    private double longitude;
    private double averageLongitude;
    private String geohash;

    // Advanced/optional metadata
    private String hrr_category = "";
    private boolean is_nominated = false;
    private Timestamp nominated_at = null;
    private boolean has_dossier = false;
    private Timestamp last_updated = null;
    private String justification = "";
    private String focusedAnalysis = "";

    // Analytical metrics
    private int daysOld;
    private long conjunctionCount;
    private boolean is_unk_obj;
    private String allManeuvers;
    private int days_since_ob;
    private String recentManeuvers;
    private double deltaV90day;
    private boolean has_sister_debris;

    // Risk and status assessments
    private boolean stillInOrbit;
    private String riskLevel;

    /**
     * Constructs a space object with essential tracking data.
     *
     * @param recordID the record ID (also used as NORAD catalog ID)
     * @param satelliteName the name of the satellite
     * @param country the country of origin
     * @param orbitType the orbit classification (e.g., LEO, GEO)
     * @param object_type the object type (e.g., "satellite", "debris")
     * @param launchYear the year the object was launched
     * @param launchSite the launch site
     * @param longitude the current longitude of the object
     * @param averageLongitude the long-term average longitude
     * @param geohash the encoded geohash location
     * @param daysOld age of the object in days
     * @param conjunctionCount number of conjunction events with other objects
     */
    SpaceObject(String recordID, String satelliteName, String country, String orbitType, String object_type,
                int launchYear, String launchSite, double longitude, double averageLongitude,
                String geohash, int daysOld, long conjunctionCount) 
    {
        this.recordID = recordID;
        this.norad_cat_id = recordID;
        this.sattelliteName = satelliteName;
        this.country = country;
        this.orbitType = orbitType;
        this.object_type = object_type;
        this.launchYear = launchYear;
        this.launchSite = launchSite;
        this.longitude = longitude;
        this.averageLongitude = averageLongitude;
        this.geohash = geohash;
        this.daysOld = daysOld;
        this.conjunctionCount = conjunctionCount;
    }

    /**
     * Returns the type of orbit for this space object.
     * 
     * @return the orbit type
     */
    public String getOrbitType() {
        return orbitType;
    }

    /**
     * Returns the current longitude of the space object.
     * 
     * @return the longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Returns the average longitude over time for this object.
     * 
     * @return the average longitude
     */
    public double getAverageLongitude() {
        return averageLongitude;
    }

    /**
     * Returns the object type, such as "satellite" or "debris".
     * 
     * @return the object type
     */
    public String getObject_type() {
        return object_type;
    }

    /**
     * Returns the number of days since the object was launched.
     * 
     * @return the object's age in days
     */
    public int getDaysOld() {
        return daysOld;
    }

    /**
     * Returns how many conjunction events (near collisions) this object has had.
     * 
     * @return the number of conjunctions
     */
    public long getConjunctionCount() {
        return conjunctionCount;
    }

    /**
     * Sets whether the object is still in orbit.
     * 
     * @param stillInOrbit true if still orbiting, false otherwise
     */
    public void setStillInOrbit(boolean stillInOrbit) {
        this.stillInOrbit = stillInOrbit;
    }

    /**
     * Sets the assessed risk level for this object.
     * 
     * @param riskLevel a risk category like "Low", "Moderate", or "High"
     */
    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    /**
     * Returns a detailed string representation of the space object,
     * including vital fields.
     *
     * @return a formatted string of vital object data
     */
    @Override
    public String toString() {
        return String.format("%s, %s, %s, %s, %d, %s, %.8f, %.8f, %s, %d",
                             recordID,sattelliteName,country,orbitType,launchYear,
                             launchSite,longitude,averageLongitude,geohash,daysOld);
    }

    /**
     * Formats this space object as a CSV row in Updated_RSO_Metrics
     * 
     * @return a comma-separated string of values
     */
    public String toCsvMetrics() {
        return String.format("%s,%s,%s,%s,%s,%s,%s,%s,%.8f,%.8f,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s",
                            recordID, norad_cat_id, sattelliteName, country, orbitType, object_type,
                            launchYear, launchSite, longitude, averageLongitude, escaped(geohash), hrr_category,
                            is_nominated, nominated_at, has_dossier, last_updated, justification,
                            focusedAnalysis, daysOld, conjunctionCount, is_unk_obj, allManeuvers, days_since_ob,
                            recentManeuvers, deltaV90day, has_sister_debris, stillInOrbit, riskLevel);
    }

    /**
     * Formats this space objects as a row in a Density_Report
     */
    public String toCsvReports(){
        return String.format("%s, %s, %s, %s, %d, %s",
                            recordID,sattelliteName,country,orbitType,
                            launchYear,object_type);
    }

    /**
     * Formats this space object for display in the terminal
     */
    public String toImpactDisplay(){
        return String.format("%s, %s, %s, %s, %s, %d, %d",
                            recordID,sattelliteName,country,orbitType,
                            object_type,daysOld,conjunctionCount);
    }

    /**
     * Escapes a string for safe CSV output.
     * 
     * @param geohash the geohash string to escape
     * @return the escaped geohash wrapped in quotes
     */
    public String escaped(String geohash) {
        return "\"" + geohash + "\"";
    }
}
