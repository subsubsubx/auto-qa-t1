package qa.auto.innotech.ui.page.pobeda;

import com.codeborne.selenide.SelenideElement;
import qa.auto.innotech.ui.BasePage;
import qa.auto.innotech.ui.assertions.Assertable;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class PobedaBookingPage extends BasePage
        implements Assertable<PobedaBookingPageAssert> {
    SelenideElement searchButton = $x("//div[@class='item item_order_search_params']//following-sibling::button");
    SelenideElement confirmationCheckbox = $x("//div[@class='customCheckbox']");
    SelenideElement errorMessage = $x("//div[@class='message_error']");

    public PobedaBookingPage searchBooking() {
        confirmationCheckbox
                .shouldBe(enabled)
                .click();

        searchButton
                .shouldBe(visible)
                .click();

        return this;
    }


}
