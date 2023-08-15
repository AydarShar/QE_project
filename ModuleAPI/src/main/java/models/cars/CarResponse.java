package models.cars;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CarResponse {
    private Integer id;
    private String engineType;
    private String mark;
    private String model;
    private BigDecimal price;
}
