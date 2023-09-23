package utils;

import models.cars.CarRequest;
import models.houses.HouseRequest;
import models.houses.ParkingPlacesRequest;
import models.houses.ParkingPlacesResponse;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class HouseGenerator {
    public static HouseRequest getFirstHouse() {
        List<ParkingPlacesRequest> parkingPlacesList = new ArrayList<>();
        parkingPlacesList.add(ParkingPlacesRequest.builder().isWarm(true).isCovered(true).placesCount(2).build());
        parkingPlacesList.add(ParkingPlacesRequest.builder().isWarm(false).isCovered(false).placesCount(1).build());

        return HouseRequest.builder()
                .floorCount(4)
                .price(BigDecimal.valueOf(300))
                .parkingPlaces(parkingPlacesList)
                .build();
    }

    public static HouseRequest getSecondHouse() {
        List<ParkingPlacesRequest> parkingPlacesList = new ArrayList<>();
        parkingPlacesList.add(ParkingPlacesRequest.builder().isWarm(true).isCovered(true).placesCount(3).build());

        return HouseRequest.builder()
                .floorCount(3)
                .price(BigDecimal.valueOf(250))
                .parkingPlaces(parkingPlacesList)
                .build();
    }
}
