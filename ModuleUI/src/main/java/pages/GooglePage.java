package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static io.qameta.allure.Allure.step;

/**
 * Главная страница Google
 * используется в тесте №1
 */
public class GooglePage {
    private final SelenideElement inputField = $x("//textarea[contains(@name,'q')]");

    public GoogleSearchPage search(String inputValue) {
        step("В поле ввода вставляем нужный текст и нажимаем Enter", () ->
        inputField.shouldBe(visible).setValue(inputValue).pressEnter());
        return new GoogleSearchPage();
    }
}
