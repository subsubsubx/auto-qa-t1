package qa.auto.innotech.ui.page.pikabu;

import com.codeborne.selenide.SelenideElement;
import com.sun.tools.javac.Main;
import org.junit.jupiter.api.Assertions;
import qa.auto.innotech.model.User;

import java.util.List;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.title;

public class MainPage {

    private final String title = "Горячее – самые интересные и обсуждаемые посты | Пикабу";
    private final String inputField = ".//input[@name='%s']";
    private final String errorMessage = "Ошибка. Вы ввели неверные данные авторизации";
    private SelenideElement loginModalButton = $x("//button[@class='pkb-normal-btn header-right-menu__login-button']");
    private SelenideElement loginModalWindow = $x("//div[@class='popup__container']//div[@class='tabs__tab tabs__tab_visible auth']");
    private SelenideElement loginInputModal = loginModalWindow.$x(String.format(inputField, "username"));
    private SelenideElement passwordInputModal = loginModalWindow.$x(String.format(inputField, "password"));
    private SelenideElement submitLoginButtonModal = loginModalWindow.$x(".//button[@type='submit']");
    private SelenideElement errorPopUpMessageModal = loginModalWindow.$x(".//ancestor::div[@class='auth-modal']//preceding-sibling::span[@class='auth__error auth__error_top']");

    public MainPage checkTitle() {
        Assertions.assertEquals(title, title());

        return this;
    }

    public MainPage openLoginModalWindow() {
        loginModalButton
                .shouldBe(visible)
                .click();

        return this;
    }

    public MainPage performLoginModalWindow(User user) {
        List.of(loginModalButton, loginInputModal, passwordInputModal, submitLoginButtonModal)
                .forEach(e -> e.shouldBe(visible));
        loginInputModal.setValue(user.getLogin());
        passwordInputModal.setValue(user.getPassword());
        submitLoginButtonModal.click();

        return this;
    }

    public MainPage checkInvalidLoginModal() {
        errorPopUpMessageModal
                .shouldBe(visible)
                .shouldHave(text(errorMessage));

        return this;
    }

}
