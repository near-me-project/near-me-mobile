package near.me.mobile.fragment;

import android.view.View;

import androidx.fragment.app.Fragment;

public abstract class AbstractTabFragment extends Fragment {

    protected View view;
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
