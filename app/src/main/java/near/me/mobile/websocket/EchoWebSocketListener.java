package near.me.mobile.websocket;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;

import near.me.mobile.R;
import near.me.mobile.activity.FinishActivity;
import near.me.mobile.fragment.MapFragment;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class EchoWebSocketListener extends WebSocketListener {
    private static final int NORMAL_CLOSURE_STATUS = 1000;
    private NotificationManager notificationManager;
    private Context context;
    private final int NOTIFICATION_ID = 127;

    public EchoWebSocketListener(Context context) {
        notificationManager = (NotificationManager) context.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        this.context = context;
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        System.out.println("Opening websocket...");
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        System.out.println("Receiving : " + text);
        showNotification(text);
    }

    private void showNotification(String text) {

        NotificationChannel mChannel = new NotificationChannel("near.me.chanel", "near.me.chanel.notify", NotificationManager.IMPORTANCE_DEFAULT);
        mChannel.enableLights(true);
        notificationManager.createNotificationChannel(mChannel);


        Notification.Builder builder = new Notification.Builder(context.getApplicationContext());

        Intent intent = new Intent(context.getApplicationContext(), FinishActivity.class);
        intent.putExtra("data", text);
        PendingIntent pendingIntent = PendingIntent.getActivity(context.getApplicationContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        builder .setChannelId("near.me.chanel")
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.diving_helmet)
                .setLargeIcon(BitmapFactory.decodeResource(context.getApplicationContext().getResources(), R.drawable.diving_helmet))
                .setTicker("near me")
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setContentTitle("some locations were found near you")
                .setContentText("click to see details");

        Notification notification = builder.build();

        notificationManager.notify(NOTIFICATION_ID, notification);
    }


    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {
        System.out.println("Receiving bytes : " + bytes.hex());
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        webSocket.close(NORMAL_CLOSURE_STATUS, null);
        System.out.println("Closing : " + code + " / " + reason);
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        System.out.println("Error : " + t.getMessage());
    }
}
