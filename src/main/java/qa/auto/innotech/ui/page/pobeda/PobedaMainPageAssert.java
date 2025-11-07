package qa.auto.innotech.ui.page.pobeda;


import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Assertions;
import qa.auto.innotech.ui.assertions.AbstractAssertions;

import static com.codeborne.selenide.CollectionCondition.allMatch;
import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.visible;

public class PobedaMainPageAssert extends AbstractAssertions<PobedaMainPage> {

    public PobedaMainPageAssert(PobedaMainPage pobedaMainPage) {
        super(pobedaMainPage);
    }

    public PobedaMainPageAssert checkLoadPage() {
        Assertions.assertEquals(page().title, Selenide.title());
        Assertions.assertTrue(page().companyLogo.shouldBe(visible).isImage());
        return this;
    }


    public PobedaMainPageAssert checkKaliningradPicAndText() {
        boolean match = page()
                .mainPageKaliningradPic
                .shouldBe(visible)
                .$$x(".//following-sibling::div//div").stream()
                .anyMatch(element -> "Полетели в Калининград!".equals(element.getText()));
        if (!match) {
            throw new AssertionError("Text not found");
        }
        return this;
    }

    public PobedaMainPageAssert checkLocale() {
        page().ticketOptionsItem.should(allMatch("Текстовки на странице на языке ".concat(page().locale.getValue()),
                e -> page().locale.getCheckStrings().contains(e.getAttribute("placeholder"))));
        return this;
    }

    public PobedaMainPageAssert checkFailedValidationInput() {
        page().ticketOptionsItem.find(attribute("placeholder", page().locale.getCheckStrings().get(2)))
                .$x("./../div").shouldHave(attribute("data-failed", "true"));

        return this;
    }
}
