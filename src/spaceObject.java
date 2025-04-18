public class spaceObject{
    private String recordID;
    private String sattelliteName;
    private String country;
    private String orbitType;
    private int launchYear;
    private String launchSite;
    private double longitude;
    private double averageLongitude;
    private String geohash;
    private int daysOld;
    private long conjunctionCount;

    spaceObject(String recordID, String satelliteName, String country, String orbitType,
                int launchYear, String launchSite, double longitude, double averageLongitude,
                String geohash, int daysOld, long conjunctionCount){
                    this.recordID = recordID;
                    this.sattelliteName = satelliteName;
                    this.country = country;
                    this.orbitType = orbitType;
                    this.launchYear = launchYear;
                    this.launchSite = launchSite;
                    this.longitude = longitude;
                    this.averageLongitude = averageLongitude;
                    this.geohash = geohash;
                    this.daysOld = daysOld;
                    this.conjunctionCount = conjunctionCount;
                }

    public String getRecordID() {
        return recordID;
    }
    public String getSattelliteName() {
        return sattelliteName;
    }
    public String getCountry() {
        return country;
    }
    public String getOrbitType() {
        return orbitType;
    }
    public int getLaunchYear() {
        return launchYear;
    }
    public String getLaunchSite() {
        return launchSite;
    }
    public double getLongitude() {
        return longitude;
    }
    public double getAverageLongitude() {
        return averageLongitude;
    }
    public String getGeohash() {
        return geohash;
    }
    public int getDaysOld() {
        return daysOld;
    }
    public long getConjunctionCount() {
        return conjunctionCount;
    }

    @Override
    public String toString() {
        return "RecordID: " + recordID + " Launch Year: " + launchYear + " Launch Site: " + launchSite;
    }
}