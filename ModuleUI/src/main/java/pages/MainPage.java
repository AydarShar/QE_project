package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;
import static io.qameta.allure.Allure.step;

/**
 * Главная страница mail.ru
 */
public class MainPage {
    private final SelenideElement allProjectsButton = $x("//div[contains(@class, 'ph-project__all-projects-text')]");
    private final SelenideElement loginButton = $x("//button[contains(@class, 'ph-login')]");
    private final SelenideElement weatherSection = $x("//div[contains(@class, 'weather')]");
    private final SelenideElement gearWheelButton = $x("//div[contains(@class,'ph-settings')]");
    private final SelenideElement darkThemeRadioButton = $x("//label[@for=\"choice-dark\"]");
    private final SelenideElement header = $x("//div[contains(@class, 'headline headline_white')]");
    private final SelenideElement watchAllButton = $x("//a[contains(@class, 'ph-all-projects')]");
    private final SelenideElement tasks = $$x("//li[contains(@class, 'ph-projects-bar-item')]").get(3);
    private final SelenideElement games = $$x("//a[contains(@class, 'ph-project__link')]").get(7);
    private final SelenideElement inputUserName = $x("//input[@name='username']");
    private final SelenideElement inputPassword = $x("//input[@name='password']");
    private final SelenideElement errorMessage = $x("//div[@data-test-id=\"error-footer-text\"]");


    public void showAllProjects() {
        step("Кликнуть на значок кубик Рубика в главном меню", () ->
                allProjectsButton.shouldBe(visible).click());
    }

    public void login() {
        step("кликнуть на \"Войти\" в правом верхнем углу окна", () ->
                loginButton.shouldBe(visible).click());
    }

    public WeatherPage clickOnWeather(){
        step("Кликнуть на блок с погодой", () ->
                weatherSection.shouldBe(visible).click());
                return new WeatherPage();
    }

    public void pushGearWheel() {
        step("Нажать на шестеренку в правом верхнем углу окна", () ->
                gearWheelButton.shouldBe(visible).click());
    }

    public void chooseDarkTheme() {
        step("В открывшемся окне настроек выбрать тёмную тему", () ->
            darkThemeRadioButton.shouldBe(visible).click());
    }

    public String getHeaderColor() {
        return step("Взять фоновый цвет header", () ->
                header.shouldBe(visible).getCssValue("background-color"));
    }

    public AllProjectsPage goToWatchAll() {
        step("В меню \"Все проекты\" кликнуть на \"Смотреть все\"", () ->
                watchAllButton.shouldBe(visible).click());
        return new AllProjectsPage();
    }

    public void goToTasks() {
        step("В меню \"Все проекты\" кликнуть на \"Задачи\"", () ->
                tasks.shouldBe(visible).click());
    }

    public void goToGames() {
        step("Кликнуть на \"Игры\" в главном меню", () ->
                games.shouldBe(visible).click());
    }

    public void setUserName(String userName) {
        step("В окне авторизации в поле \"Имя аккаунта\" вводим указанное значение и нажимаем Enter", () ->
                inputUserName.shouldBe(exist).setValue(userName).pressEnter());
    }

    public boolean existErrorMessage() {
        step("Проверяем наличие сообщения об ошибке", () ->
                errorMessage.shouldBe(exist));
        return true;
    }

    public void setPassword(String password) {
        step("В окне авторизации в поле \"Пароль\" вводим указанное значение и нажимаем Enter", () ->
                inputPassword.shouldBe(exist).setValue(password).pressEnter());
    }
}
