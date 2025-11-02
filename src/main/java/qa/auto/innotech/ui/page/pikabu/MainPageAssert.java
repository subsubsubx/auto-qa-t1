package qa.auto.innotech.ui.page.pikabu;

import org.assertj.core.api.AbstractAssert;
import org.junit.jupiter.api.Assertions;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.title;

public class MainPageAssert extends AbstractAssert<MainPageAssert, MainPage> {

    public MainPageAssert(MainPage mainPage) {
        super(mainPage, MainPageAssert.class);
    }

    public MainPage page() {
        return actual;
    }

    public MainPageAssert checkTitle() {
        Assertions.assertEquals(page().title, title());

        return this;
    }

    public MainPageAssert checkInvalidLoginModal() {
        page().errorPopUpMessageModal
                .shouldBe(visible)
                .shouldHave(text(page().errorMessage));

        return this;
    }
}
