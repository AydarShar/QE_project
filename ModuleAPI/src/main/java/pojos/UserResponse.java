package pojos;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserResponse {
    private Integer id;
    private String firstName;
    private String secondName;
    private Integer age;
    private String sex;
    private BigDecimal money;
}
