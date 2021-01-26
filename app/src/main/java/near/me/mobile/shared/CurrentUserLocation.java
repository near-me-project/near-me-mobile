package near.me.mobile.shared;

public class CurrentUserLocation {
    private Double latitude = new Double(0.0);
    private Double longitude = new Double(0.0);
    private Boolean updated = false;

    private static final CurrentUserLocation instance = new CurrentUserLocation();

    public static synchronized CurrentUserLocation instance() {
        return instance;
    }

    private CurrentUserLocation() {
    }

    public Double getLatitude() {
        return instance().latitude;
    }

    public void setLatitude(Double latitude) {
        instance().latitude = latitude;
    }

    public Double getLongitude() {
        return instance().longitude;
    }

    public void setLongitude(Double longitude) {
        instance().longitude = longitude;
    }

    public void setUpdated(boolean updated) {
        instance().updated = updated;
    }

    public Boolean getUpdated() {
        return instance().updated;
    }
}


