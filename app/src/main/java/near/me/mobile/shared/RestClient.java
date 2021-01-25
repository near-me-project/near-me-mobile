package near.me.mobile.shared;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import near.me.mobile.dto.LocationDto;

public class RestClient {

    private final RestTemplate template;

    public RestClient() {
        template = new RestTemplate();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        converter.setObjectMapper(objectMapper);
        template.getMessageConverters().add(converter);
    }

    public <T> T getForObject(String uri, Class<T> responseType) {
        return template.getForObject(uri, responseType);
    }

    public <T> T postForObject(String uri, LocationDto model, Class<T> response) {
        return template.postForObject(uri, model, response);
    }

    public <T> ResponseEntity<T> getForEntity(String uri, Class<T> response) {
        return template.getForEntity(uri, response);
    }
}
