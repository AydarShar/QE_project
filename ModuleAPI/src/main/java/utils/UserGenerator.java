package utils;

import models.users.UserRequest;
import java.math.BigDecimal;

public class UserGenerator {
    public static UserRequest getFirstUser() {
        return UserRequest.builder()
                .firstName("Michael")
                .secondName("Jordan")
                .age(60)
                .sex("MALE")
                .money(BigDecimal.valueOf(800000.42))
                .build();
    }

    public static UserRequest getSecondUser() {
        return UserRequest.builder()
                .firstName("Jeff")
                .secondName("Bezos")
                .age(59)
                .sex("MALE")
                .money(BigDecimal.valueOf(1200000))
                .build();
    }
}
