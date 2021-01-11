package near.me.mobile.shared;

import android.view.View;

public class ViewAnimation {

    public static void scaleView(final View v) {
        v.animate().scaleX(0.8f).scaleY(0.8f).setDuration(300)
                .withEndAction(() -> v.animate().scaleX(1).scaleY(1).setDuration(300));
    }
}
