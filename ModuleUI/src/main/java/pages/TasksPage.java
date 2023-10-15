package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$x;
import static io.qameta.allure.Allure.step;

/**
 * Страница "Задачи"
 * используется в тесте №5
 */

public class TasksPage {
    private final SelenideElement help = $$x("//a[contains(@class, 'footer-links-item')]").get(2);

    public void goToHelp() {
        step("Кликнуть на \"Помощь\" в footer", () ->
                help.shouldBe(visible).click());
    }
}
