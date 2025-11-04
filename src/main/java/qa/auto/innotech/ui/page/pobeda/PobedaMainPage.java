package qa.auto.innotech.ui.page.pobeda;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import qa.auto.innotech.ui.assertions.Assertable;

import java.util.List;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class PobedaMainPage implements Assertable<PobedaMainPage, PobedaMainPageAssert> {

    SelenideElement mainPagePic = $x("");
    SelenideElement localeIcon = $x("");
    ElementsCollection localeList = localeIcon.$$x("");

    ElementsCollection ticketOptionsList = $$x("");

    public PobedaMainPage changeLocale(Locale locale) {
        localeIcon
                .shouldBe(visible)
                .click();

        localeList.stream()
                .filter(e -> Locale.ENGLISH.getValue().equals(e.getText()))
                .findFirst()
                .orElseThrow()
                .click();

        return this;
    }

    @Getter
    @RequiredArgsConstructor
    public enum Locale {
        RUSSIAN("Русский") {
            @Override
            public List<String> getCheckStrings() {
                return List.of("Поиск билета", "Онлайн-регистрация", "Управление бронированием");

            }
        },
        ENGLISH("English") {
            @Override
            public List<String> getCheckStrings() {
                return List.of("Ticket search", "Online check-in", "Manage my booking");

            }
        };

        private final String value;

        public abstract List<String> getCheckStrings();
    }
}

