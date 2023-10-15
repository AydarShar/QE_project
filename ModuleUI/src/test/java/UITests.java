import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.*;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.*;

    public class UITests extends BaseTest {
    private final static String MAIL_URL = "https://mail.ru";
    private final static String MAIL_SEARCH = "mail.ru";
    private final static String GOOGLE_URL = "https://www.google.ru/";
    private final static String KAZAN = "Казань";
    private final static String KAZAN_WEATHER_URL = "https://pogoda.mail.ru/prognoz/kazan/";
    private final static String EXPECTED_BG_COLOR = "rgba(25, 25, 26, 1)";
    private final static String TITLE_TEXT = "Our projects";
    private final static String TAG_NAME = "footer";
    private final static String LOGIN_IFRAME = "//iframe[@class=\"ag-popup__frame__layout__iframe\"]";
    private final static String NOT_VALID_USER_NAME = "SgfdisxxUZ&3";
    private final static String USER_NAME = "Sharafetdin89";
    private final static String NOT_VALID_PASSWORD = "SHJBbws1nsdn";

    @Test
    @DisplayName("1. Проверка поиска mail.ru через Google")
    void findMailRu() {
        UISteps.openWebsite(GOOGLE_URL);
        Assertions.assertTrue(new GooglePage().search(MAIL_SEARCH).findLink());
    }

    @Test
    @DisplayName("2. Проверка страницы погоды в Казани")
    void weatherInKazan() {
        UISteps.openWebsite(MAIL_URL);
        new MainPage().clickOnWeather();
        UISteps.switchToTab(1);
        new WeatherPage().setCityName(KAZAN);
        assertTrue(UISteps.checkUrl(KAZAN_WEATHER_URL));
    }

    @Test
    @DisplayName("3. Проверка переключения на темную тему")
    void blackTheme() {
        UISteps.openWebsite(MAIL_URL);
        new MainPage().pushGearWheel();
        new MainPage().chooseDarkTheme();
        step("Проверяем, что фоновый цвет header тёмный", () ->
                assertEquals(EXPECTED_BG_COLOR, new MainPage().getHeaderColor()));
    }

    @Test
    @DisplayName("4. Проверка смены языка на английский")
    void changeLanguage() {
        UISteps.openWebsite(MAIL_URL);
        new MainPage().showAllProjects();
        new MainPage().goToWatchAll();
        UISteps.switchToTab(1);
        new AllProjectsPage().changeLanguage();
        step("Проверяем, что текст заголовка стал на английском языке", () ->
                assertEquals(TITLE_TEXT, new AllProjectsPage().getTitleText()));
    }

    @Test
    @DisplayName("5. Проверка появления подсказки с голосовым помощником")
    void helpMessage() {
        UISteps.openWebsite(MAIL_URL);
        new MainPage().showAllProjects();
        new MainPage().goToTasks();
        UISteps.switchToTab(1);
        new TasksPage().goToHelp();
        UISteps.switchToTab(2);
        UISteps.scrollToTag(TAG_NAME);
        step("Проверить что сообщение с подсказкой голосового помощника видно", () ->
                $x("//span[contains(@class, 'marusia__balloon__text')]")
                .shouldBe(visible));
    }

    @Test
    @DisplayName("6. Проверка наличия французского языка в меню \"Игры\"")
    void frenchLanguageInGames() {
        UISteps.openWebsite(MAIL_URL);
        new MainPage().goToGames();
        UISteps.switchToTab(1);
        new GamesPage().clickOnLanguages();
        assertTrue(new GamesPage().frenchVisible());
    }

    @Test
    @DisplayName("7. Проверка входа с неверным именем пользователя")
    void wrongUserName() {
        UISteps.openWebsite(MAIL_URL);
        new MainPage().login();
        UISteps.switchToIframeByXpath(LOGIN_IFRAME);
        new MainPage().setUserName(NOT_VALID_USER_NAME);
        assertTrue(new MainPage().existErrorMessage());
    }

    @Test
    @DisplayName("8. Проверка входа с неверным паролем")
    void wrongPassword() {
        UISteps.openWebsite(MAIL_URL);
        new MainPage().login();
        UISteps.switchToIframeByXpath(LOGIN_IFRAME);
        new MainPage().setUserName(USER_NAME);
        new MainPage().setPassword(NOT_VALID_PASSWORD);
        assertTrue(new LoginPage().checkErrorMessage());
    }
}
