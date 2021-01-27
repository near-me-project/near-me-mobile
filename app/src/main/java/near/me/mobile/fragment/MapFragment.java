package near.me.mobile.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import near.me.mobile.R;
import near.me.mobile.dto.LocationResponseDto;
import near.me.mobile.listeners.map.OnMarkerClickListener;
import near.me.mobile.shared.CurrentUserLocation;
import near.me.mobile.shared.MarkerTag;
import near.me.mobile.task.GetLocationsTask;

public class MapFragment extends AbstractTabFragment implements OnMapReadyCallback {

    private static final int LAYOUT = R.layout.fragment_map;
    private GoogleMap map;
    private static MapFragment mapFragment;
    private FloatingActionButton floatingActionButton;

    public static MapFragment getInstance(Context context) {
        if (mapFragment != null) return mapFragment;

        mapFragment = new MapFragment();
        mapFragment.setArguments(new Bundle());
        mapFragment.setTitle(context.getString(R.string.tab_item_map));
        return mapFragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        new GetLocationsTask(this).execute();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab_map);
        mapFragment.getMapAsync(this);
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        if (CurrentUserLocation.instance().getUpdated()) {
            LatLng place = new LatLng(CurrentUserLocation.instance().getLatitude(), CurrentUserLocation.instance().getLongitude());
            map.addMarker(new MarkerOptions().position(place).title("You").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(place, 17.0f));
        } else {
            LatLng berlin = new LatLng(52.51643607501274, 13.378888830535033);
            map.addMarker(new MarkerOptions().position(berlin).title("Default"));
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(berlin, 15.0f));
        }

        map.setOnMarkerClickListener(new OnMarkerClickListener(getContext(), floatingActionButton));
    }

    public void refreshMap(List<LocationResponseDto> locationResponseDtos) {
        map.clear();

        for (LocationResponseDto location : locationResponseDtos) {
            LatLng place = new LatLng(Double.parseDouble(location.getLatitude()), Double.parseDouble(location.getLongitude()));
            Marker marker = map.addMarker(new MarkerOptions().position(place).title("Near you").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            marker.setTag(new MarkerTag("id", location.getLocationId()));
        }

        LatLng place = new LatLng(CurrentUserLocation.instance().getLatitude(), CurrentUserLocation.instance().getLongitude());
        map.addMarker(new MarkerOptions().position(place).title("You").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(place, 17.0f));
    }

    @Override
    public void onPause() {
        super.onPause();
        new GetLocationsTask(this).execute();
    }
}