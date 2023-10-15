package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static io.qameta.allure.Allure.step;

/**
 * Страница "Все проекты"
 * используется в тесте №4
 */
public class AllProjectsPage {
    private final SelenideElement languageButton = $x("//button[contains(@class, 'Header_changeLanguageButton')]");
    private final SelenideElement title = $x("//h1[@class='title-h2']//span");

    public void changeLanguage() {
        step("Кликнуть на кнопку смены языка в правом верхнем углу", () ->
                languageButton.shouldBe(visible).click());
    }

    public String getTitleText() {
        return step("Берем текст заголовка", () ->
                title.shouldBe(visible).getText());
    }
}
