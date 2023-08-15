package models.houses;

import lombok.Data;

@Data
public class ParkingPlacesResponse {
    private Integer id;
    private Boolean isWarm;
    private Boolean isCovered;
    private Integer placesCount;
}
