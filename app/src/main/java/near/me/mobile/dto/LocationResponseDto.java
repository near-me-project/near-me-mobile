package near.me.mobile.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LocationResponseDto {
    private String locationId;
    private String clientId;
    private String latitude;
    private String longitude;
}
