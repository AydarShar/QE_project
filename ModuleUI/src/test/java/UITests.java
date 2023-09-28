import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.url;
import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.*;

    public class UITests {
    private static final String ALLURE_SELENIDE_LISTENER_NAME = "AllureSelenide";

    {
        SelenideLogger.addListener(ALLURE_SELENIDE_LISTENER_NAME, new AllureSelenide()
                .screenshots(true).savePageSource(false));
    }

    @BeforeEach
    public void chromedriver() {
        System.setProperty("webdriver.chrome.driver","/usr/local/bin/chromedriver");
    }

    @AfterEach
    public void closeWindow() {
        Selenide.closeWindow(); // новая сессия для каждого теста
    }

    @Test
    @DisplayName("1. Проверка поиска mail.ru через Google")
    void findMailRu() {

        step("Открыть google.ru", () -> open("https://www.google.ru/"));

        step("В поле ввода вставить \"mail\", нажать Enter",
                $x("//textarea[contains(@title,'Поиск')]")
                .shouldBe(visible)
                .setValue("mail.ru")::pressEnter);

        step("Найти сайт mail.ru, кликнуть по нему", () ->
                $x("//a[contains(@href,'https://mail.ru')]")
                .shouldBe(visible)
                .click());
    }

    @Test
    @DisplayName("2. Проверка страницы погоды в Казани")
    void weatherInKazan() {
        MailSteps.openPage();
        MailSteps.closePopUp();

        step("Кликнуть на блок с погодой", () ->
                $x("//div[contains(@class, 'weather')]")
                .shouldBe(visible)
                .click());

        MailSteps.switchToSecondTab();

        step("В поле ввода вставить \"Казань\", нажать Enter", () ->
                $x("//form[@action=\"/search/\"]//input[@type=\"text\"]")
                .shouldBe(visible)
                .setValue("Казань")
                .pressEnter());

        step("Проверяем, что перешли на страницу погоды в Казани", () ->
                webdriver().shouldHave(url("https://pogoda.mail.ru/prognoz/kazan/")));
    }

    @Test
    @DisplayName("3. Проверка переключения на темную тему")
    void blackTheme() {
        MailSteps.openPage();
        MailSteps.closePopUp();

        step("Нажать на шестеренку в правом верхнем углу окна", () ->
                $x("//div[contains(@class,'ph-settings')]")
                .shouldBe(visible)
                .click());

        step("В открывшемся окне настроек выбрать тёмную тему", () ->
                $x("//label[@for=\"choice-dark\"]")
                .shouldBe(visible)
                .click());

        String colorValue = step("Взять фоновый цвет header", () ->
                $x("//div[contains(@class, 'headline headline_white')]")
                .shouldBe(visible)
                .getCssValue("background-color"));

        step("Проверить, что фоновый цвет header тёмный", () ->
                assertEquals("rgba(25, 25, 26, 1)", colorValue));
    }

    @Test
    @DisplayName("4. Проверка смены языка на английский")
    void changeLanguage() {
        MailSteps.openPage();
        MailSteps.closePopUp();
        MailSteps.rubikCube();

        step("В открывшемся окне меню кликнуть на \"Смотреть все\"", () ->
                $x("//span[contains(text(), 'Смотреть все')]")
                .shouldBe(visible)
                .click());

        MailSteps.switchToSecondTab();

        step("Кликнуть на кнопку смены языка в правом верхнем углу", () ->
                $x("//button[@title='Сменить язык']")
                .shouldBe(visible)
                .click());

        String title = step("Проверяем, что текст заголовка стал на английском языке", () ->
                $x("//h1[@class='title-h2']//span")
                .shouldBe(visible)
                .getText());
        assertEquals("Our projects", title);
    }

    @Test
    @DisplayName("5. Проверка появления подсказки с голосовым помощником")
    void helpMessage() {
        MailSteps.openPage();
        MailSteps.closePopUp();
        MailSteps.rubikCube();

        step("В открывшемся окне меню кликнуть на \"Задачи\"", () ->
                $x("//span[contains(text(), 'Задачи')]")
                .shouldBe(visible)
                .click());

        step("Переключиться на вкладку \"Задачи Mail.ru\"", () ->
                switchTo().window("Задачи Mail.ru"));

        step("кликнуть на \"Помощь\" в footer", () ->
                $x("//a[contains(text(), 'Помощь')]")
                .shouldBe(visible)
                .click());

        step("Переключиться на вкладку \"Задачи — Помощь Mail.ru\"", () ->
                switchTo().window("Задачи — Помощь Mail.ru"));

        step("Проскролить к footer", () ->
                $(By.tagName("footer"))
                .scrollTo());

        step("Проверить что сообщение с подсказкой голосового помощника видно", () ->
                $x("//span[contains(@class, 'marusia__balloon__text')]")
                .shouldBe(visible));
    }

    @Test
    @DisplayName("6. Проверка наличия французского языка в меню \"Игры\"")
    void frenchLanguageInGames() {
        MailSteps.openPage();
        MailSteps.closePopUp();

        step("Кликнуть на \"Игры\" в главном меню", () ->
                $$x("//a[text()='Игры']").get(0)
                .shouldBe(visible)
                .click());

        MailSteps.switchToSecondTab();

        step("Кликнуть иконку с разными языками", () ->
                $x("//div[contains(@data-original-title, 'Язык')]")
                .shouldBe(visible)
                .click());

        step("Убедиться, что присутствует французский язык", () ->
                $x("//a[contains(text(), 'Français')]")
                .shouldBe(visible));
    }

    @Test
    @DisplayName("7. Проверка входа с неверным именем пользователя")
    void wrongUserName() {
        MailSteps.openPage();
        MailSteps.closePopUp();
        MailSteps.loginButton();

        step("Переключиться на iframe", () ->
                switchTo()
                .frame($(By.xpath("//iframe[@class=\"ag-popup__frame__layout__iframe\"]"))));

        step("В поле Имя аккаунта вводим \"SHJBbws1nsdn\"", () ->
                $x("//input[@name='username']")
                .shouldBe(exist)
                .setValue("SHJBbws1nsdn")
                .pressEnter());

        step("Проверяем, что появилось сообщение об ошибке", () ->
                $x("//div[@data-test-id=\"error-footer-text\"]")
                .shouldBe(exist));

        step("Проверяем, что появилась надпись \"Такой аккаунт не зарегистрирован\"", () ->
                $x("//small[text()='Такой аккаунт не зарегистрирован']")
                .shouldBe(visible));
    }

    @Test
    @DisplayName("8. Проверка входа с неверным паролем")
    void wrongPassword() {
        MailSteps.openPage();
        MailSteps.closePopUp();
        MailSteps.loginButton();

        step("Переключиться на iframe", () ->
                switchTo()
                .frame($(By.xpath("//iframe[@class=\"ag-popup__frame__layout__iframe\"]"))));

        step("в поле Имя аккаунта вводим \"Sharafetdin89\"", () ->
                $x("//input[@name='username']")
                .shouldBe(exist)
                .setValue("Sharafetdin89")
                .pressEnter());

        step("в поле для пароля вводим \"DFUjguk\"", () ->
                $x("//input[@name='password']")
                .shouldBe(exist)
                .setValue("DFUjguk")
                .pressEnter());

        step("проверяем, что появилось сообщение о неверном пароле", () ->
                $x("//div[@data-text=\"Неверный пароль, попробуйте ещё раз\"]")
                .shouldBe(visible));
    }
}
