package models.houses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HouseRequest {
    private Integer floorCount;
    private BigDecimal price;
    @JsonProperty( "parkingPlaces" )
    private List<ParkingPlacesRequest> parkingPlaces;
}
