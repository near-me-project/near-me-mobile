package near.me.mobile.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDateTime;

import near.me.mobile.R;
import near.me.mobile.shared.ViewAnimation;
import near.me.mobile.task.AddLocationTask;
import near.me.mobile.service.LocationService;
import near.me.mobile.websocket.EchoWebSocketListener;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;

public class MainFragment extends AbstractTabFragment {
    private LocalDateTime lastTimeButtonTyped;
    private Location lastSavedLocation;

    private Context context;
    protected LocationManager locationManager;
    protected LocationManager locationManagerForSendingUpdates;
    private static final int LAYOUT = R.layout.fragment_main;
    private FloatingActionButton button;
    private SwitchCompat switchPulse;

    private OkHttpClient client;
    private WebSocket ws;

    public static MainFragment getInstance(Context context) {
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setTitle(context.getString(R.string.tab_item_main));
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        locationManagerForSendingUpdates = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        client = new OkHttpClient();
        view = inflater.inflate(LAYOUT, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        button = (FloatingActionButton) getView().findViewById(R.id.fab);
        button.setOnClickListener(view -> {
            Snackbar.make(button, "sending location ...", Snackbar.LENGTH_LONG).show();
            executeTask(locationListener);
            ViewAnimation.scaleView(view);
        });
        switchPulse = (SwitchCompat) getView().findViewById(R.id.switchPulse);
        switchPulse.setOnCheckedChangeListener(onSwitchPulseListener);
    }

    private final CompoundButton.OnCheckedChangeListener onSwitchPulseListener = new CompoundButton.OnCheckedChangeListener() {
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
    };

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

    private void executeUpdateServerWithCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            return;
        }
        if (locationManagerForSendingUpdates.isProviderEnabled(LocationManager.GPS_PROVIDER) && locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationManagerForSendingUpdates.requestLocationUpdates(LocationManager.GPS_PROVIDER, 15 * 1000, 0, locationListenerForUpdates);
            locationManagerForSendingUpdates.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 15 * 1000, 0, locationListenerForUpdates);
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
                new AddLocationTask(button).execute(location);
                lastTimeButtonTyped = LocalDateTime.now();
                lastSavedLocation = location;
                stopLocationUpdates();
            }
        }
    };

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

    private boolean validLocation(Location location) {
        return lastSavedLocation == null || lastSavedLocation.distanceTo(location) > 50.0;
    }

    private boolean valid(LocalDateTime now) {
        return lastTimeButtonTyped == null || now.minusSeconds(10).isAfter(lastTimeButtonTyped);
    }

    private void executeTask(LocationListener locationListener) {
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

    private void stopLocationUpdates() {
        locationManager.removeUpdates(locationListener);
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
