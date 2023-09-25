import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

public class MailSteps {
    public static void openPage() {
        step("Открыть страницу mail.ru", () -> open("https://mail.ru"));
    }

    public static void closePopUp() {
        step("Закрыть всплывающее окно", () ->
                $x("//div[contains(@class, 'balloon__close')]")
                        .shouldBe(visible)
                        .click());
    }

    public static void rubikCube() {
        step("Кликнуть на значок кубик Рубика в главном меню", () ->
                $x("//*[@id=\"ph-whiteline\"]/div/nav/button")
                        .shouldBe(visible)
                        .click());
    }

    public static void switchToSecondTab() {
        step("Переключиться на 2-ю вкладку браузера", () ->
                switchTo().window(1));
    }

    public static void loginButton() {
        step("кликнуть на \"Войти\" в правом верхнем углу окна", () ->
                $x("//button[contains(text(), 'Войти')]")
                        .shouldBe(visible)
                        .click());
    }
}
