package qa.auto.innotech.ui.page.pikabu;

import com.codeborne.selenide.SelenideElement;
import qa.auto.innotech.model.User;
import qa.auto.innotech.ui.assertions.Assertable;

import java.util.List;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class MainPage implements Assertable<MainPage, MainPageAssert> {

     final String title = "Горячее – самые интересные и обсуждаемые посты | Пикабу";
     final String inputField = ".//input[@name='%s']";
     final String errorMessage = "Ошибка. Вы ввели неверные данные авторизации";
     SelenideElement loginModalButton = $x("//button[@class='pkb-normal-btn header-right-menu__login-button']");
     SelenideElement loginModalWindow = $x("//div[@class='popup__container']//div[@class='tabs__tab tabs__tab_visible auth']");
     SelenideElement loginInputModal = loginModalWindow.$x(String.format(inputField, "username"));
     SelenideElement passwordInputModal = loginModalWindow.$x(String.format(inputField, "password"));
     SelenideElement submitLoginButtonModal = loginModalWindow.$x(".//button[@type='submit']");
     SelenideElement errorPopUpMessageModal = loginModalWindow.$x(".//ancestor::div[@class='auth-modal']//preceding-sibling::span[@class='auth__error auth__error_top']");

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


}
