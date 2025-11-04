package qa.auto.innotech.ui.page.pobeda;


import org.assertj.core.api.AbstractAssert;

import static com.codeborne.selenide.CollectionCondition.allMatch;
import static com.codeborne.selenide.Condition.visible;

public class PobedaMainPageAssert extends AbstractAssert<PobedaMainPageAssert, PobedaMainPage> {

    public PobedaMainPageAssert(PobedaMainPage pobedaMainPage) {
        super(pobedaMainPage, PobedaMainPageAssert.class);
    }

    public PobedaMainPage page() {
        return actual;
    }

    public PobedaMainPageAssert checkLoadPage() {
        boolean match = actual
                .mainPagePic
                .shouldBe(visible)
                .$$x(".//following-sibling::div//div").stream()
                .anyMatch(element -> "Полетели в Калининград!".equals(element.getText()));
        if (!match) {
            throw new AssertionError("Text not found");
        }
        return this;
    }

    public PobedaMainPageAssert checkEngLocale(PobedaMainPage.Locale locale) {
        actual.ticketOptionsList.should(allMatch("Текстовки на странице на языке ".concat(locale.getValue()),
                e -> locale.getCheckStrings().contains(e.getText())));
        return this;
    }
}
