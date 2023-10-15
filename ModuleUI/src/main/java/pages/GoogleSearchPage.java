package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static io.qameta.allure.Allure.step;

/**
 * Страница с результатами поиска в Google
 * используется в тесте №1
 */
public class GoogleSearchPage {
    private final SelenideElement mailLink = $x("//a[@href='https://mail.ru/']");

    public boolean findLink() {
        step("Найти ссылку на сайт mail.ru", () ->
                mailLink.shouldBe(visible));
                return true;
    }

//    //a[@href='https://mail.ru/']
}
