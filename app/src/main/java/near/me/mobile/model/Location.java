package near.me.mobile.model;

public class Location {
    private String locationId;
    private String latitude;
    private String longitude;

    public Location() {
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLocationId() {
        return locationId;
    }
}
