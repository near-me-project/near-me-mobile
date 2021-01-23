package near.me.mobile.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import near.me.mobile.R;
import near.me.mobile.activity.FinishActivity;
import near.me.mobile.adapter.TabsFragmentAdapter;

public class MainActivity extends AppCompatActivity {

    private TabsFragmentAdapter adapter;
    private ViewPager viewPager;
//    private NotificationManager notificationManager;
    private final int NOTIFICATION_ID = 127;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppDefault);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        initTabs();
    }

    private void initTabs() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        adapter = new TabsFragmentAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }

    public void showNotification(View view) {
        Notification.Builder builder = new Notification.Builder(getApplicationContext());

        Intent intent = new Intent(getApplicationContext(), FinishActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        builder
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.diving_helmet)
                .setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.diving_helmet))
                .setTicker("near me app found near by locations")
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setContentTitle("beer bar location found")
                .setContentText("click to see details");

        Notification notification = builder.build();

//        notificationManager.notify(NOTIFICATION_ID, notification);
    }
}
