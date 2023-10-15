package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static io.qameta.allure.Allure.step;

/**
 * Страница "Игры"
 * используется в тесте №6
 */

public class GamesPage {
    private final SelenideElement languages = $x("//span[contains(@class, 'ph-menu__icon_lang')]");
    private final SelenideElement french = $x("//a[contains(text(), 'Français')]");

    public void clickOnLanguages() {
        step("Кликнуть иконку с разными языками", () ->
                languages.shouldBe(visible).click());
    }

    public boolean frenchVisible() {
        step("Убедиться, что присутствует французский язык", () ->
                french.shouldBe(visible));
        return true;
    }
}
