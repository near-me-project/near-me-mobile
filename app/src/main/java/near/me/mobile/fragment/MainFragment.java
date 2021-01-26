package near.me.mobile.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import near.me.mobile.R;
import near.me.mobile.listeners.PulseListener;
import near.me.mobile.shared.ViewAnimation;
import near.me.mobile.task.SendCurrentLocationToServerAsyncTask;
import near.me.mobile.task.SendingCurrentLocation;

public class MainFragment extends AbstractTabFragment {
    private Context context;
    private FloatingActionButton floatingActionButton;
    private SwitchCompat switchPulse;
    private PulseListener pulseListener;

    public static MainFragment getInstance(Context context) {

        MainFragment fragment = new MainFragment();
        fragment.setArguments(new Bundle());
        fragment.setContext(context);
        fragment.setTitle(context.getString(R.string.tab_item_main));
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("[MainFragment] Created");
        pulseListener = new PulseListener(context);
    }

    @Override
    public void onStart() {
        super.onStart();
        System.out.println("[MainFragment] Started");
        floatingActionButton = (FloatingActionButton) getView().findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(view -> {
            Snackbar.make(floatingActionButton, "sending location ...", Snackbar.LENGTH_LONG).show();
            new SendingCurrentLocation(context, new SendCurrentLocationToServerAsyncTask(floatingActionButton)).executeTask();
            ViewAnimation.scaleView(view);
        });
        switchPulse = (SwitchCompat) getView().findViewById(R.id.switchPulse);
        switchPulse.setOnCheckedChangeListener(pulseListener);

    }

    public void setContext(Context context) {
        this.context = context;
    }


    @Override
    public void onStop() {
        super.onStop();
        System.out.println("[MainFragment]: Stopped");
//        pulseListener.stopChanelCommunication();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("[MainFragment]: Destroyed");
        pulseListener.stopChanelCommunication();
    }
}
