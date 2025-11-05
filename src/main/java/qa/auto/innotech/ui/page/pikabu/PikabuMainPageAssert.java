package qa.auto.innotech.ui.page.pikabu;

import org.junit.jupiter.api.Assertions;
import qa.auto.innotech.ui.assertions.AbstractAssertions;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.title;

public class PikabuMainPageAssert extends AbstractAssertions<PikabuMainPage> {

    public PikabuMainPageAssert(PikabuMainPage pikabuMainPage) {
        super(pikabuMainPage);
    }

    public PikabuMainPageAssert checkTitle() {
        Assertions.assertEquals(page().title, title());

        return this;
    }

    public PikabuMainPageAssert checkInvalidLoginModal() {
        page()
                .errorPopUpMessageModal
                .shouldBe(visible)
                .shouldHave(text(page().errorMessage));

        return this;
    }
}
