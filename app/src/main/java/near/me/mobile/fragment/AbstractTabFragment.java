package near.me.mobile.fragment;

import android.content.Context;
import android.view.View;

import androidx.fragment.app.Fragment;

public abstract class AbstractTabFragment extends Fragment {

    protected View view;
    private String title;
    protected Context context;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
