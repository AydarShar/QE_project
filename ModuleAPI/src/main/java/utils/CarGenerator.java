package utils;

import models.cars.CarRequest;
import java.math.BigDecimal;

public class CarGenerator {
    public static CarRequest getFirstCar() {
        return CarRequest.builder()
                .engineType("Electric")
                .mark("Mercedes-Benz")
                .model("EQA")
                .price(BigDecimal.valueOf(80))
                .build();
    }

    public static CarRequest getSecondCar() {
        return CarRequest.builder()
                .engineType("Gasoline")
                .mark("BMW")
                .model("X7")
                .price(BigDecimal.valueOf(120))
                .build();
    }
}
