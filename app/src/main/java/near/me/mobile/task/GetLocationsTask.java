package near.me.mobile.task;

import android.os.AsyncTask;

import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import near.me.mobile.dto.LocationResponseDto;
import near.me.mobile.fragment.MapFragment;
import near.me.mobile.shared.Constants;
import near.me.mobile.shared.RestClient;

import static near.me.mobile.shared.Constants.TEST_CLIENT_ID;

public class GetLocationsTask extends AsyncTask<Void, Void, List<LocationResponseDto>> {

    private MapFragment mapFragment;
    private RestClient restClient;

    public GetLocationsTask(MapFragment mapFragment) {
        this.mapFragment = mapFragment;
        restClient = new RestClient();
    }

    @Override
    protected List<LocationResponseDto> doInBackground(Void... voids) {
        ResponseEntity<LocationResponseDto[]> responseEntity = restClient.getForEntity(Constants.URL.GET_LOCATIONS + TEST_CLIENT_ID, LocationResponseDto[].class);
        return Stream.of(responseEntity.getBody()).collect(Collectors.toList());
    }

    @Override
    protected void onPostExecute(List<LocationResponseDto> locationResponseDtos) {
        mapFragment.refreshMap(locationResponseDtos);
    }
}
