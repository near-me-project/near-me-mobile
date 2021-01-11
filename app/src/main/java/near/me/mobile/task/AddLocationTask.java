package near.me.mobile.task;

import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import near.me.mobile.shared.Constants;
import near.me.mobile.dto.CreatedLocationResponseDto;
import near.me.mobile.dto.LocationDto;

public class AddLocationTask extends AsyncTask<Location, Void, CreatedLocationResponseDto> {

    private FloatingActionButton button;

    public AddLocationTask(FloatingActionButton button) {
        this.button = button;
    }

    @Override
    protected CreatedLocationResponseDto doInBackground(Location... location) {

        LocationDto build = LocationDto.builder()
                .clientId("1")
                .latitude(Double.toString(location[0].getLatitude()))
                .longitude(Double.toString(location[0].getLongitude()))
                .build();

        RestTemplate template = new RestTemplate();
        template.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        return template.postForObject(Constants.URL.POST_ADD_LOCATION, build, CreatedLocationResponseDto.class);
    }

    @Override
    protected void onPostExecute(CreatedLocationResponseDto createdLocationResponseDto) {
        if (createdLocationResponseDto.getCreatedLocationId() != null) {
            Snackbar.make(button, "Location sent to service", Snackbar.LENGTH_LONG).show();
        } else {
            Snackbar.make(button, "Location wasn't sent to service", Snackbar.LENGTH_LONG).setBackgroundTint(Color.parseColor("#B00020")).show();
        }
    }
}
