import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public abstract class BaseTest {
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
    public void closeWindow() { Selenide.closeWindow(); }

}
