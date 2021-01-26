package near.me.mobile.task;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import java.time.LocalDateTime;

public class SendingCurrentLocation {

    private Context context;
    protected LocationManager locationManager;
    private LocalDateTime lastTimeButtonTyped;
    private Location lastSavedLocation;
    private SendCurrentLocationToServerAsyncTask asyncTask;

    public SendingCurrentLocation(Context context, SendCurrentLocationToServerAsyncTask asyncTask) {
        this.context = context;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        this.asyncTask = asyncTask;
    }

    public void executeTask() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            return;
        }
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) && locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 100, locationListener);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 100, locationListener);
        }
    }

    private final LocationListener locationListener = new LocationListener() {
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
                asyncTask.execute(location);
                lastTimeButtonTyped = LocalDateTime.now();
                lastSavedLocation = location;
                stopLocationUpdates();
            }
        }
    };

    private boolean validLocation(Location location) {
        return lastSavedLocation == null || lastSavedLocation.distanceTo(location) > 50.0;
    }

    private boolean valid(LocalDateTime now) {
        return lastTimeButtonTyped == null || now.minusSeconds(10).isAfter(lastTimeButtonTyped);
    }

    private void stopLocationUpdates() {
        locationManager.removeUpdates(locationListener);
    }
}
