package near.me.mobile.task;

import android.os.AsyncTask;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import near.me.mobile.dto.LocationResponseDto;
import near.me.mobile.dto.LocationResponseList;
import near.me.mobile.fragment.MapFragment;
import near.me.mobile.shared.Constants;

public class GetLocationsTask extends AsyncTask<Void, Void, List<LocationResponseDto>> {

    private MapFragment mapFragment;

    public GetLocationsTask(MapFragment mapFragment) {
        this.mapFragment = mapFragment;
    }

    @Override
    protected List<LocationResponseDto> doInBackground(Void... voids) {
        String userid = "1";
        RestTemplate template = new RestTemplate();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        converter.setObjectMapper(objectMapper);
        template.getMessageConverters().add(converter);
        ResponseEntity<LocationResponseDto[]> responseEntity = template.getForEntity(Constants.URL.GET_LOCATIONS + userid, LocationResponseDto[].class);
        List<LocationResponseDto> collect = Stream.of(responseEntity.getBody()).collect(Collectors.toList());
        return collect;
    }

    @Override
    protected void onPostExecute(List<LocationResponseDto> locations) {
        mapFragment.refreshMap(locations);
    }
}
