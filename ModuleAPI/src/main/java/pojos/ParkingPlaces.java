package pojos;

import lombok.Data;

@Data
public class ParkingPlaces{
	private Integer id;
	private boolean isWarm;
	private boolean isCovered;
	private Integer placesCount;
}
