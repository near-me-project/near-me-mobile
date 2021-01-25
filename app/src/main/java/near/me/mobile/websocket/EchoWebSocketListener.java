package near.me.mobile.websocket;

import android.content.Context;

import near.me.mobile.notifications.LocationFoundPushNotification;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class EchoWebSocketListener extends WebSocketListener {
    private static final int NORMAL_CLOSURE_STATUS = 1000;

    private final Context context;

    public EchoWebSocketListener(Context context) {
        this.context = context;
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        System.out.println("Opening websocket...");
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        System.out.println("Receiving : " + text);
        new LocationFoundPushNotification(context).show(text);
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
