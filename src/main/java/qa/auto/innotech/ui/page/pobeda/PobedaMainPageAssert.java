package qa.auto.innotech.ui.page.pobeda;


import org.assertj.core.api.AbstractAssert;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PobedaMainPageAssert extends AbstractAssert<PobedaMainPageAssert, PobedaMainPage> {

    public PobedaMainPageAssert(PobedaMainPage pobedaMainPage) {
        super(pobedaMainPage, PobedaMainPageAssert.class);
    }

    public PobedaMainPage page() {
        return actual;
    }

    public PobedaMainPageAssert checkLoadPage() {
        actual
                .mainPagePic
                .shouldBe(visible)
                .shouldHave(text("Полетели в Калининград!"));

        return this;
    }

    public PobedaMainPageAssert checkEngLocale(PobedaMainPage.Locale locale) {
        assertTrue(actual.ticketOptionsList.stream()
                .allMatch(e -> locale.getCheckStrings().contains(e.getText())));

        return this;
    }
}
