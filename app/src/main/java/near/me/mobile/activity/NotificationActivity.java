package near.me.mobile.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;

import near.me.mobile.R;
import near.me.mobile.listeners.map.OnMarkerClickListener;
import near.me.mobile.model.Location;
import near.me.mobile.model.LocationsModel;
import near.me.mobile.shared.CurrentUserLocation;
import near.me.mobile.shared.MarkerTag;

public class NotificationActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap map;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_layout);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab_map);
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        String data = getIntent().getStringExtra("data");
        LocationsModel locationsModel = getLocationsModel(data);

        for (Location location : locationsModel.getNearestPlaces()) {
            LatLng place = new LatLng(Double.parseDouble(location.getLatitude()), Double.parseDouble(location.getLongitude()));
            Marker marker = map.addMarker(new MarkerOptions().position(place).title("Near you").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            marker.setTag(new MarkerTag("id", location.getLocationId()));
        }

        LatLng place = new LatLng(CurrentUserLocation.instance().getLatitude(), CurrentUserLocation.instance().getLongitude());
        map.addMarker(new MarkerOptions().position(place).title("You").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(place, 17.0f));

        map.setOnMarkerClickListener(new OnMarkerClickListener(getApplicationContext(), floatingActionButton));
    }

    private LocationsModel getLocationsModel(String data) {
        try {
            return new ObjectMapper().readValue(data.getBytes(), LocationsModel.class);
        } catch (IOException e) {
            return new LocationsModel();
        }
    }
}


