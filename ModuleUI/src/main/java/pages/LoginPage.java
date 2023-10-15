package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static io.qameta.allure.Allure.step;

/**
 * Страница авторизации в mail.ru
 * используется в тесте №8
 */

public class LoginPage {
    private final SelenideElement passwordErrorMessage = $x("//div[@data-test-id=\"password-input-error\"]");

    public boolean checkErrorMessage() {
        step("Проверяем, что появилось сообщение о неверном пароле", () ->
                passwordErrorMessage.shouldBe(visible));
        return true;
    }
}
