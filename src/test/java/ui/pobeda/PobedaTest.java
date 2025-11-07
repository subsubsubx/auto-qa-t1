package ui.pobeda;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.TestData;

public class PobedaTest extends PobedaBaseTest {

    @BeforeEach
    void init() {
        Selenide.open(uiConfig.pobedaBaseUrl());
    }

    @Test
    void shouldCheckPopUpWindow() {
        mainPage.check()
                .checkLoadPage()
                .page()
                .hoverInformationSection();
    }

    @Test
    void shouldTriggerValidationOnInput() {
        mainPage.check()
                .checkLoadPage()
                .checkLocale()
                .page()
                .scrollToSearchItem()
                .enterData(TestData.getTestTicketData())
                .check()
                .checkFailedValidationInput();
    }
}
