package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static io.qameta.allure.Allure.step;

/**
 * Страница "Погода"
 * используется в тесте №2
 */
public class WeatherPage {
    private final SelenideElement inputWeatherField = $x("//input[@type='text']");

    public void setCityName(String cityName) {
        step("В поле ввода вставить название города и нажать Enter", () ->
                inputWeatherField.shouldBe(visible).setValue(cityName).pressEnter());
    }
}
