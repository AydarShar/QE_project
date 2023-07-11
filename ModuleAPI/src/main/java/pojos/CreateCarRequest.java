package pojos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateCarRequest {
	private String engineType;
	private String mark;
	private String model;
	private BigDecimal price;
}
