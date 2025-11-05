package qa.auto.innotech.ui.page.pobeda;


import qa.auto.innotech.ui.assertions.AbstractAssertions;

import static com.codeborne.selenide.CollectionCondition.allMatch;
import static com.codeborne.selenide.Condition.visible;

public class PobedaMainPageAssert extends AbstractAssertions<PobedaMainPage> {

    public PobedaMainPageAssert(PobedaMainPage pobedaMainPage) {
        super(pobedaMainPage);
    }

    public PobedaMainPageAssert checkLoadPage() {
        boolean match = page()
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
        page().ticketOptionsList.should(allMatch("Текстовки на странице на языке ".concat(locale.getValue()),
                e -> locale.getCheckStrings().contains(e.getText())));
        return this;
    }
}
