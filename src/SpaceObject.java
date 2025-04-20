import java.security.Timestamp;

public class SpaceObject{
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
    private String hrr_category = "";
    private boolean is_nominated = false;
    private Timestamp nominated_at = null;
    private boolean has_dossier = false;
    private Timestamp last_updated = null;
    private String justification = "";
    private String focusedAnalysis = "";
    private int daysOld;
    private long conjunctionCount = -1;
    private boolean is_unk_obj;
    private String allManeuvers = "";
    private int days_since_ob = -1;
    private String recentManeuvers = "";
    private double deltaV90day = -1.0;
    private boolean has_sister_debris = false;
    private boolean stillInOrbit;
    private String riskLevel;
    

    SpaceObject(String recordID, String satelliteName, String country, String orbitType, String object_type,
                int launchYear, String launchSite, double longitude, double averageLongitude,
                String geohash, int daysOld, long conjunctionCount){
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

    public String getOrbitType() {
        return orbitType;
    }
    public double getLongitude() {
        return longitude;
    }
    public double getAverageLongitude() {
        return averageLongitude;
    }
    public String getObject_type() {
        return object_type;
    }
    public int getDaysOld() {
        return daysOld;
    }
    public long getConjunctionCount() {
        return conjunctionCount;
    }

    public void setStillInOrbit(boolean stillInOrbit) {
        this.stillInOrbit = stillInOrbit;
    }
    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }


    @Override
    public String toString() {
        return "  " + recordID + "  " + norad_cat_id +  "  " + sattelliteName + "  " +
                country + "  " + orbitType + "  " + object_type + "  " + launchYear + "  " + 
                launchSite + "  " + longitude + "  " + averageLongitude + "  " +
                geohash + "  " + hrr_category + "  " + is_nominated + "  " + nominated_at + "  " +
                has_dossier + "  " + last_updated + "  " + justification + "  " + focusedAnalysis + "  " +
                daysOld + "  " + conjunctionCount + "  " + is_unk_obj + "  " + allManeuvers + "  " +
                days_since_ob + "  " + recentManeuvers + "  " + deltaV90day + "  " + has_sister_debris + "  " +
                stillInOrbit + "  " + riskLevel;
    }

    public String toCsv(){
        return String.format("%s,%s,%s,%s,%s,%s,%s,%s,%.8f,%.8f,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s",
                            recordID, norad_cat_id, sattelliteName, country, orbitType, object_type,
                            launchYear, launchSite, longitude, averageLongitude, escaped(geohash), hrr_category,
                            is_nominated, nominated_at, has_dossier, last_updated, justification,
                            focusedAnalysis, daysOld, conjunctionCount, is_unk_obj, allManeuvers, days_since_ob,
                            recentManeuvers, deltaV90day, has_sister_debris, stillInOrbit, riskLevel);
    }

    public String escaped(String geohash){
        return "\"" + geohash + "\"";
    }
}