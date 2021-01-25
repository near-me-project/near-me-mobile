package near.me.mobile.listeners;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import near.me.mobile.service.LocationService;
import near.me.mobile.websocket.EchoWebSocketListener;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;

public class PulseListener implements CompoundButton.OnCheckedChangeListener {

    private final Context context;
    protected LocationManager locationManagerForSendingUpdates;
    private WebSocket ws;
    private final OkHttpClient client;

    public PulseListener(Context context) {
        this.context = context;
        locationManagerForSendingUpdates = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        client = new OkHttpClient();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if (isChecked) {
            executeUpdateServerWithCurrentLocation();
            ws = openChanelCommunication();
        } else {
            stopExecuteUpdateServerWithCurrentLocation();
            stopChanelCommunication();
        }
    }

    private void executeUpdateServerWithCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            return;
        }
        if (locationManagerForSendingUpdates.isProviderEnabled(LocationManager.GPS_PROVIDER) && locationManagerForSendingUpdates.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationManagerForSendingUpdates.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1 * 1000, 0, locationListenerForUpdates);
            locationManagerForSendingUpdates.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1 * 1000, 0, locationListenerForUpdates);
        }
    }

    private void stopChanelCommunication() {
        ws.close(1000, "Goodbye !");
    }

    private WebSocket openChanelCommunication() {
//        Request request = new Request.Builder().url("ws://10.0.2.2:8585/affirm?client=1").build();
        Request request = new Request.Builder().url("ws://192.168.0.219:9191/v1/subscribe/1").build();
        return client.newWebSocket(request, new EchoWebSocketListener(context));
    }

    private void stopExecuteUpdateServerWithCurrentLocation() {
        locationManagerForSendingUpdates.removeUpdates(locationListenerForUpdates);
    }

    private final LocationListener locationListenerForUpdates = new LocationListener() {
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
            Intent intent = new Intent(context, LocationService.class);
            intent.putExtra("latitude", String.valueOf(location.getLatitude()));
            intent.putExtra("longitude", String.valueOf(location.getLongitude()));
            context.startService(intent);
        }
    };
}
