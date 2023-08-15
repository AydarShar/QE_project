package models.houses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParkingPlacesRequest {
    private Boolean isWarm;
    private Boolean isCovered;
    private Integer placesCount;
}
