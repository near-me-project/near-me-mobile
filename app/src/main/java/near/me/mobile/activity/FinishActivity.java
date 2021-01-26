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
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;

import near.me.mobile.R;
import near.me.mobile.model.Location;
import near.me.mobile.model.LocationsModel;
import near.me.mobile.shared.CurrentUserLocation;

public class FinishActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finish_layout);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        String data = getIntent().getStringExtra("data");
        LocationsModel locationsModel = getLocationsModel(data);

        for (Location location : locationsModel.getNearestPlaces()) {
            LatLng place = new LatLng(Double.parseDouble(location.getLatitude()), Double.parseDouble(location.getLongitude()));
            mMap.addMarker(new MarkerOptions().position(place).title("Near you").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        }

        LatLng place = new LatLng(CurrentUserLocation.instance().getLatitude(), CurrentUserLocation.instance().getLongitude());
        mMap.addMarker(new MarkerOptions().position(place).title("You").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place, 17.0f));
    }

    private LocationsModel getLocationsModel(String data) {
        try {
            return new ObjectMapper().readValue(data.getBytes(), LocationsModel.class);
        } catch (IOException e) {
            return new LocationsModel();
        }
    }
}
