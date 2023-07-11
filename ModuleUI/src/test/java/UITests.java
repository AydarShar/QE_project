import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import patterns.MailPage;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.*;

    public class UITests {
    private static final String ALLURE_SELENIDE_LISTENER_NAME = "AllureSelenide";

    {
        SelenideLogger.addListener(ALLURE_SELENIDE_LISTENER_NAME, new AllureSelenide()
                .screenshots(true).savePageSource(false));
    }

    @AfterEach
    public void closeWindow() {
        Selenide.closeWindow(); // закрытие окна после каждого теста
    }

    @Test
    @DisplayName("1. Проверка поиска mail.ru через Google")
    void findMailRu() {

        step("Открыть google.ru", () -> open("https://www.google.ru/"));

        step("В поле ввода вставить \"mail\", нажать Enter", () -> {
            $x("//textarea[contains(@title,'Поиск')]")
                    .shouldBe(visible)
                    .setValue("mail.ru")
                    .pressEnter();});

        step("Найти сайт mail.ru, кликнуть по нему", () ->
                $x("//a[contains(@href,'https://mail.ru')]")
                        .shouldBe(visible)
                        .click());

        Selenide.sleep(2000);
    }

    @Test
    @DisplayName("2. Проверка отображения погоды в Казани")
    void weatherInKazan() {

        step("Открыть страницу mail.ru", () -> {
            open("https://mail.ru");});

        step("Закрыть всплывающее окно", () -> {
            new MailPage().closePopUp()
                    .shouldBe(visible)
                    .click();});

        step("Кликнуть на блок с погодой", () -> {
            $x("//div[@data-testid=\"weather\"]")
                    .shouldBe(visible)
                    .click();});

        step("Переключиться на 2-ю вкладку браузера", () -> {
            switchTo().window(1);});

        step("В поле ввода вставить \"Казань\", нажать Enter", () -> {
            $x("//form[@action=\"/search/\"]//input[@type=\"text\"]")
                    .shouldBe(visible)
                    .setValue("Казань")
                    .pressEnter();});

        step("Убедиться, что отображается погода в Казани", () -> {
            $x("//h1[text()='Прогноз погоды в Казани']")
                    .shouldBe(visible);});

        Selenide.sleep(2000);
    }

    @Test
    @DisplayName("3. Проверка переключения на темную тему")
    void blackTheme() {

        step("Открыть страницу mail.ru", () -> {
            open("https://mail.ru");});

        step("Закрыть всплывающее окно", () -> {
            new MailPage().closePopUp()
                    .shouldBe(visible)
                    .click();});

        step("Нажать на шестеренку в правом верхнем углу окна", () -> {
            $x("//div[contains(@class, 'ph-settings svelte-1ke9xx5')]")
                    .shouldBe(visible)
                    .click();});

        step("В открывшемся окне настроек выбрать тёмную тему", () -> {
            $x("//label[@for=\"choice-dark\"]")
                    .shouldBe(visible)
                    .click();});

        String colorValue = step("Взять фоновый цвет header", () -> {
            return $x("//div[contains(@class, 'headline headline_white')]")
                    .shouldBe(visible)
                    .getCssValue("background-color");});

        step("Проверить, что фоновый цвет header тёмный", () -> {
            assertEquals("rgba(25, 25, 26, 1)", colorValue);});

        Selenide.sleep(2000);
    }

    @Test
    @DisplayName("4. Проверка открытия окна для пожертвования")
    void donationWindow() {

        step("Открыть страницу mail.ru", () -> {
            open("https://mail.ru");});

        step("Закрыть всплывающее окно", () -> {
            new MailPage().closePopUp()
                    .shouldBe(visible)
                    .click();});

        step("Кликнуть на значок кубик Рубика в главном меню", () -> {
            new MailPage().cubeMenu()
                    .shouldBe(visible)
                    .click();});

        step("В открывшемся окне меню кликнуть на \"Добро\"", () -> {
            new MailPage.CubeMenuElements()
                    .kindness()
                    .shouldBe(visible)
                    .click();});

        Selenide.sleep(8000);

        step("Переключиться на 2-ю вкладку браузера", () -> {
            switchTo().window("VK Добро - благотворительность в России - сервис добрых дел");});

        step("Кликнуть на кнопку \"Помочь сейчас\"", () -> {
            $x("//span[text()='Помочь сейчас']")
                    .shouldHave(exist)
                    .click();});

        step("Переключиться на iframe", () -> {
            switchTo()
                    .frame($(By.xpath("//iframe[@src='/projects/donate/recipients/?eventCategory=Header']")));});

        step("Проверить что элементы появившегося окна видны", () -> {
            $x("//div[child::h2[text()='Добавить платёж'] and child::span[text()='Кому вы хотите помочь']]")
                    .shouldBe(visible);});

        Selenide.sleep(2000);
    }

    @Test
    @DisplayName("5. Проверка смены языка")
    void changeLanguage() {

        step("Открыть страницу mail.ru", () -> {
            open("https://mail.ru");
        });

        step("Закрыть всплывающее окно", () -> {
            new MailPage().closePopUp()
                    .shouldBe(visible)
                    .click();
        });

        step("Кликнуть на значок кубик Рубика в главном меню", () -> {
            new MailPage().cubeMenu()
                    .shouldBe(visible)
                    .click();
        });

        step("В открывшемся окне меню кликнуть на \"Смотреть все\"", () -> {
            new MailPage.CubeMenuElements()
                    .watchAll()
                    .shouldBe(visible)
                    .click();});
        Selenide.sleep(2000);

        step("Переключиться на 2-ю вкладку браузера", () -> {
            switchTo().window(1);});

        step("Кликнуть на кнопку смены языка в правом верхнем углу", () -> {
            $x("//button[contains(@class, 'header__change-language')]")
                    .shouldBe(visible)
                    .click();});

        String title = step("Проверить что текст заголовка стал на английском языке", () -> {
            return $x("//h1[@class='title-h2']//span")
                    .shouldBe(visible)
                    .getText();});

        assertEquals("Our projects", title);

        Selenide.sleep(2000);
    }

    @Test
    @DisplayName("6. Проверка появления подсказки с голосовым помощником")
    void helpMessage() {
        step("Открыть страницу mail.ru", () -> {
            open("https://mail.ru");
        });

        step("Закрыть всплывающее окно", () -> {
            new MailPage().closePopUp()
                    .shouldBe(visible)
                    .click();
        });

        step("Кликнуть на значок кубик Рубика в главном меню", () -> {
            new MailPage().cubeMenu()
                    .shouldBe(visible)
                    .click();
        });

        step("В открывшемся окне меню кликнуть на \"Задачи\"", () -> {
            new MailPage.CubeMenuElements()
                    .tasks()
                    .shouldBe(visible)
                    .click();});

        step("Переключиться на 2-ю вкладку браузера", () -> {
            switchTo().window(1);});

        step("кликнуть на \"Помощь\" в footer", () -> {
            $x("//a[contains(text(), 'Помощь')]")
                    .shouldBe(visible)
                    .click();});

        step("Переключиться на 3-ю вкладку браузера", () -> {
            switchTo().window(2);});

        step("скрол к footer", () -> {
            $(By.tagName("footer"))
                    .scrollTo();});

        step("Проверить что сообщение с подсказкой голосового помощника видно", () -> {
            $x("//span[contains(@class, 'marusia__balloon__text')]")
                    .shouldBe(visible);});

        Selenide.sleep(2000);

    }
}
