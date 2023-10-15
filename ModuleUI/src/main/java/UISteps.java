import com.codeborne.selenide.Selenide;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.url;
import static io.qameta.allure.Allure.step;

public class UISteps {
    public static void openWebsite(String url) {
        step("Открыть заданный сайт", () -> Selenide.open(url));
    }

    public static void switchToTab(int tabIndex) {
        step("Открыть заданную вкладку", () -> Selenide.switchTo().window(tabIndex));
    }

    public static boolean checkUrl(String url) {
        step("Проверяем, что находимся на нужной странице", () ->
                webdriver().shouldHave(url(url)));
        return true;
    }

    public static void scrollToTag(String tagName) {
        step("Проскролить к заданному тегу", () ->
                $(By.tagName(tagName)).scrollTo());
    }
    public static void switchToIframeByXpath(String xpath) {
        step("Переключиться на iframe по xpath", () ->
                switchTo().frame($(By.xpath(xpath))));
    }
}
