package near.me.mobile.model;

import java.util.List;

public class LocationsModel {
    private String clientId;
    private List<Location> nearestPlaces;

    public LocationsModel() {
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public List<Location> getNearestPlaces() {
        return nearestPlaces;
    }

    public void setNearestPlaces(List<Location> nearestPlaces) {
        this.nearestPlaces = nearestPlaces;
    }
}
