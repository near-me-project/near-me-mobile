package near.me.mobile.task;

import android.location.Location;
import android.os.AsyncTask;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import near.me.mobile.shared.Constants;

public class SendLocationUpdatesToServerTask extends AsyncTask<Location, Void, Void> {

    @Override
    protected Void doInBackground(Location... locations) {
        RestTemplate template = new RestTemplate();
        template.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(Constants.URL.GET_UPDATES)
                .queryParam("clientId", "1")
                .queryParam("latitude", locations[0].getLatitude())
                .queryParam("longitude", locations[0].getLongitude());

        template.getForObject(builder.build().toUriString(), String.class);

        return null;
    }
}
