package models.houses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import models.users.UserResponse;

import java.math.BigDecimal;
import java.util.List;

@Data
public class HouseResponse {
    private Integer id;
    private Integer floorCount;
    private BigDecimal price;
    @JsonProperty( "parkingPlaces" )
    private List<ParkingPlacesResponse> parkingPlaces;
    @JsonProperty( "lodgers" )
    private List <UserResponse> lodgers;
}
