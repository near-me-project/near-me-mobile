package near.me.mobile.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import near.me.mobile.dto.LocationResponseDto;
import near.me.mobile.fragment.AbstractTabFragment;
import near.me.mobile.fragment.MainFragment;
import near.me.mobile.fragment.MapFragment;

public class TabsFragmentAdapter extends FragmentPagerAdapter {

    private List<LocationResponseDto> data;
    private Map<Integer, AbstractTabFragment> tabs;
    private Context context;
    private MapFragment mapFragment;

    public TabsFragmentAdapter(@NonNull FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        initMap(context);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return tabs.get(position);
    }

    @Override
    public int getCount() {
        return tabs.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabs.get(position).getTitle();
    }

    private void initMap(Context context) {
        tabs = new HashMap<>();
        mapFragment = MapFragment.getInstance(context, data);

        tabs.put(0, MainFragment.getInstance(context));
        tabs.put(1, mapFragment);
    }

}

