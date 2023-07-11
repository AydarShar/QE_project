package pojos;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
@Data
public class HouseResponse {
	private Integer id;
	private Integer floorCount;
	private BigDecimal price;
	private List<ParkingPlaces> parkingPlaces;
	private List<UserResponse> lodgers;
}