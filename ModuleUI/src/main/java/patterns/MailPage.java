package patterns;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;

public class MailPage {

    // Page object - закрывает всплывающее окно
    public SelenideElement closePopUp() {
        return $x("//div[contains(@class, 'balloon__close')]");
    }

    // Page object - в главном меню в header взят значок кубик Рубика в котором все проекты
    public SelenideElement cubeMenu() {
        return $x("//div[contains(@class, 'ph-project svelte-a9o3e5')]");
    }

    // Page elements - взяты элементы меню в кубике Рубика
    public static class CubeMenuElements {

        // Добро
        public SelenideElement kindness() {
            return $x("//a[contains(@href, 'https://trk.mail.ru/c/szuzz5?mt_sub1=mail.ru')]");
        }

        // Задачи
        public SelenideElement tasks() {
            return $x("//span[contains(text(), 'Задачи')]");
        }

        // Смотреть все
        public SelenideElement watchAll() {
            return $x("//a[contains(@href, 'https://trk.mail.ru/c/lb9kv9?mt_sub1=mail.ru')]");
        }
    }
}
