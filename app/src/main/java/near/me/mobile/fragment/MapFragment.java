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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import near.me.mobile.R;
import near.me.mobile.dto.LocationResponseDto;
import near.me.mobile.task.GetLocationsTask;

public class MapFragment extends AbstractTabFragment implements OnMapReadyCallback {

    private static final int LAYOUT = R.layout.fragment_map;
    private Context context;
    private GoogleMap mMap;
    private static MapFragment mapFragment;

    public static MapFragment getInstance(Context context) {
        if(mapFragment != null) return mapFragment;

        Bundle args = new Bundle();
        mapFragment = new MapFragment();
        mapFragment.setArguments(args);
        mapFragment.setContext(context);
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
        mapFragment.getMapAsync(this);
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng berlin = new LatLng(52.51643607501274, 13.378888830535033);
        mMap.addMarker(new MarkerOptions().position(berlin).title("Marker in Berlin"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(berlin,11));
    }

    public void refreshMap(List<LocationResponseDto> data) {
        data.forEach(res -> {
            LatLng berlin = new LatLng(Double.parseDouble(res.getLatitude()), Double.parseDouble(res.getLongitude()));
            mMap.addMarker(new MarkerOptions().position(berlin).title("Marker in Berlin"));
        });
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void onResume() {
        super.onResume();
        new GetLocationsTask(this).execute();
    }
}