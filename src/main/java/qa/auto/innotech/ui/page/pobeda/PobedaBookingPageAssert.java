package qa.auto.innotech.ui.page.pobeda;

import qa.auto.innotech.ui.assertions.AbstractAssertions;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;

public class PobedaBookingPageAssert extends AbstractAssertions<PobedaBookingPage> {
    public PobedaBookingPageAssert(PobedaBookingPage pobedaBookingPage) {
        super(pobedaBookingPage);
    }

    public PobedaBookingPageAssert checkErrorBookingMessage() {
        page()
                .errorMessage
                .shouldBe(visible)
                .shouldHave(text("Заказ с указанными параметрами не найден"));

        return this;
    }
}
