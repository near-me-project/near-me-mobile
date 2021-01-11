package near.me.mobile.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationDto {

    private String clientId;
    private String latitude;
    private String longitude;
    private String city;
    private String country;
    private String locationType;
    private String clientDefinedLocationType;
    private String description;

}
