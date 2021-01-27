package near.me.mobile.listeners.map;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import near.me.mobile.shared.MarkerTag;
import near.me.mobile.task.DeleteLocationAsyncTask;

public class OnMarkerClickListener implements GoogleMap.OnMarkerClickListener {
    private Context context;
    private FloatingActionButton floatingActionButton;

    public OnMarkerClickListener(Context context, FloatingActionButton floatingActionButton) {
        this.context = context;
        this.floatingActionButton = floatingActionButton;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (floatingActionButton.getVisibility() == View.VISIBLE) {
            floatingActionButton.setVisibility(View.INVISIBLE);
            return true;
        }

        if (!marker.getTitle().equals("You")) {

            MarkerTag markerTag = (MarkerTag) marker.getTag();

            floatingActionButton.setVisibility(View.VISIBLE);
            floatingActionButton.setOnClickListener((view) -> {
                new DeleteLocationAsyncTask().execute(markerTag.getValue());
                Toast.makeText(context, "Deleted: " + marker.getTitle(), Toast.LENGTH_SHORT).show();
                floatingActionButton.setVisibility(View.INVISIBLE);
            });
        }
        return true;
    }
}
