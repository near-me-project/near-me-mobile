package near.me.mobile.notifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;

import near.me.mobile.R;
import near.me.mobile.activity.NotificationActivity;

public class LocationFoundPushNotification {
    private final NotificationManager notificationManager;
    private final Context context;

    public LocationFoundPushNotification(Context context) {
        this.context = context;
        notificationManager = (NotificationManager) context.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public void show(String text) {
        NotificationChannel mChannel = new NotificationChannel("near.me.chanel", "near.me.chanel.notify", NotificationManager.IMPORTANCE_DEFAULT);
        mChannel.enableLights(true);
        notificationManager.createNotificationChannel(mChannel);

        Notification.Builder builder = new Notification.Builder(context.getApplicationContext());

        Intent intent = new Intent(context.getApplicationContext(), NotificationActivity.class);
        intent.putExtra("data", text);
        PendingIntent pendingIntent = PendingIntent.getActivity(context.getApplicationContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        builder.setChannelId("near.me.chanel")
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.diving_helmet)
                .setLargeIcon(BitmapFactory.decodeResource(context.getApplicationContext().getResources(), R.drawable.diving_helmet))
                .setTicker("near me")
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setContentTitle("some locations were found near you")
                .setContentText("click to see details");

        Notification notification = builder.build();

        int NOTIFICATION_ID = 127;
        notificationManager.notify(NOTIFICATION_ID, notification);
    }
}
