package near.me.mobile.service;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

import org.springframework.web.util.UriComponentsBuilder;

import near.me.mobile.shared.Constants;
import near.me.mobile.shared.RestClient;

import static near.me.mobile.shared.Constants.TEST_CLIENT_ID;

public class LocationService extends IntentService {
    private RestClient restClient;

    public LocationService() {
        super("LocationService");
        restClient = new RestClient();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        System.out.println("[LocationService]:  service start working");

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(Constants.URL.CONSUMER_SEND_UPDATES)
                .queryParam("clientId", TEST_CLIENT_ID)
                .queryParam("latitude", intent.getStringExtra("latitude"))
                .queryParam("longitude", intent.getStringExtra("longitude"));

        restClient.getForObject(builder.build().toUriString(), String.class);
    }

    @Override
    public void onDestroy() {
        System.out.println("[LocationService]: service stopped working");
    }
}
