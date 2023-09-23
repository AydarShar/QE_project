package models.cars;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CarResponse extends CarRequest {
    private Integer id;
}
