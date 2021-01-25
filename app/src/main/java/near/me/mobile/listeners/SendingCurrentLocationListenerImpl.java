package near.me.mobile.listeners;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;

import java.time.LocalDateTime;

import near.me.mobile.task.SendCurrentLocationToServerAsyncTask;

public class SendingCurrentLocationListenerImpl implements LocationListener {

    private LocalDateTime lastTimeButtonTyped;
    private Location lastSavedLocation;
    protected LocationManager locationManager;
    private SendCurrentLocationToServerAsyncTask task;

    public SendingCurrentLocationListenerImpl(Context context, SendCurrentLocationToServerAsyncTask task) {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        this.task = task;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        if (valid(LocalDateTime.now()) && validLocation(location)) {
            task.execute(location);
            lastTimeButtonTyped = LocalDateTime.now();
            lastSavedLocation = location;
            stopLocationUpdates();
        }
    }

    private boolean validLocation(Location location) {
        return lastSavedLocation == null || lastSavedLocation.distanceTo(location) > 50.0;
    }

    private boolean valid(LocalDateTime now) {
        return lastTimeButtonTyped == null || now.minusSeconds(10).isAfter(lastTimeButtonTyped);
    }

    private void stopLocationUpdates() {
        locationManager.removeUpdates(this);
    }
}
