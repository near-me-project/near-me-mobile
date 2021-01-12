package near.me.mobile.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LocationResponseList {
    private List<LocationResponseDto> locations = new ArrayList<>();

}
