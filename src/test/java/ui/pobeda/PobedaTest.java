package ui.pobeda;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.TestData;

import static util.TestData.getTestBookingData;
import static util.TestData.getTestTicketData;

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
                .fillTicketData(getTestTicketData())
                .check()
                .checkFailedValidationInput();
    }

    @Test
    void shouldReturnErrorOnInvalidInput() {
        mainPage.check()
                .checkLoadPage()
                .page()
                .selectTopBarItem(3)
                .check()
                .checkBookingItemOpen()
                .page()
                .fillBookingData(getTestBookingData(), true, bookingPage)
                .searchBooking()
                .check()
                .checkErrorBookingMessage();

    }
}
