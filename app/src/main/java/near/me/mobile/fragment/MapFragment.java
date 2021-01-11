package near.me.mobile.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import near.me.mobile.R;
import near.me.mobile.dto.LocationResponseDto;

public class MapFragment extends AbstractTabFragment {

    private static final int LAYOUT = R.layout.fragment_map;
    private List<LocationResponseDto> data;

    public static MapFragment getInstance(Context context, List<LocationResponseDto> data) {
        Bundle args = new Bundle();
        MapFragment fragment = new MapFragment();
        fragment.setData(data);
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setTitle(context.getString(R.string.tab_item_map));
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);
        return view;
    }

    public void setData(List<LocationResponseDto> data) {
        this.data = data;
    }
}