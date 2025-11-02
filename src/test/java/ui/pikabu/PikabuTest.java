package ui.pikabu;

import com.codeborne.selenide.Selenide;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class PikabuTest extends PikabuBaseTest {


    @BeforeEach
    void init() {
        Selenide.open(uiConfig.pikabuBaseUrl());
    }

    @Test
    void shouldPopUpErrorMessageOnInvalidLoginAttemptFromModalWindow() throws InterruptedException {
        mainPage.check()
                .checkTitle()
                .page()
                .openLoginModalWindow()
                .performLoginModalWindow(invalidUser)
                .check()
                .checkInvalidLoginModal();
    }
}
