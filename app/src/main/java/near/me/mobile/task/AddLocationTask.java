package near.me.mobile.task;

import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import near.me.mobile.dto.CreatedLocationResponseDto;
import near.me.mobile.dto.LocationDto;
import near.me.mobile.shared.Constants;

public class AddLocationTask extends AsyncTask<Location, Void, CreatedLocationResponseDto> {

    private FloatingActionButton button;

    public AddLocationTask(FloatingActionButton button) {
        this.button = button;
    }

    @Override
    protected CreatedLocationResponseDto doInBackground(Location... location) {

        LocationDto build = LocationDto.builder()
                .uuid(UUID.randomUUID().toString())
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
        if (createdLocationResponseDto != null) {
            Snackbar.make(button, "Location sent to service", Snackbar.LENGTH_LONG).show();
        } else {
            Snackbar.make(button, "Location wasn't sent to service", Snackbar.LENGTH_LONG).setBackgroundTint(Color.parseColor("#B00020")).show();
        }
    }
}
