package qa.auto.innotech.ui.page.pobeda;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import qa.auto.innotech.model.TicketData;
import qa.auto.innotech.ui.assertions.Assertable;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class PobedaMainPage implements Assertable<PobedaMainPageAssert> {

    String title = "Авиакомпания «Победа» - купить авиабилеты онлайн, дешёвые билеты на самолёт, прямые и трансферные рейсы с пересадками";
    SelenideElement mainPageKaliningradPic = $x("//img[contains(@srcset, 'Kaliningrad')]");
    SelenideElement localeIcon = $x("//button[@aria-label='Поиск']//preceding-sibling::button");
    ElementsCollection localeList = $$x("//div[@role='menuitem']");
    ElementsCollection ticketOptionsItem = $$x("//div[@class='dp-1amht5s-root']//div[@class='dp-zohsg-root']//input");
    SelenideElement companyLogo = $x("//a[@aria-label='«Авиакомпания «Победа», Группа «Аэрофлот»'][1]//img");
    SelenideElement informationSection = $x("//a[@href='/information']");
    SelenideElement searchButton = $x("//button[@class='dp-k64vy3-root-root-root']");
    String cityXpath = "//div[@class='dp-1loxy1b-root']//div[contains(text(), '%s')]";
    Locale locale = Locale.RUSSIAN;


    public PobedaMainPage hoverInformationSection() {
        informationSection.hover()
                .shouldBe(visible);
        return this;
    }

    public PobedaMainPage scrollToSearchItem() {
        Predicate<SelenideElement> predicate = element ->
                locale.getCheckStrings().get(0)
                        .equals(element.shouldBe(visible)
                                .getAttribute("placeholder"));

        ticketOptionsItem.stream()
                .filter(predicate)
                .findFirst()
                .orElseThrow()
                .scrollTo();

        return this;
    }

    @SneakyThrows
    public PobedaMainPage enterData(TicketData data) {
        SelenideElement fromWhere = ticketOptionsItem.find(attribute("placeholder", locale.getCheckStrings().get(0)))
                .shouldBe(visible);
        if (Objects.nonNull(data.getFromWhere())) {
            fromWhere.setValue(data.getFromWhere()).pressEnter();
            $x(String.format(cityXpath, data.getFromWhere())).click();
        }

        SelenideElement toWhere = ticketOptionsItem.find(attribute("placeholder", locale.getCheckStrings().get(1)))
                .shouldBe(visible);
        if (Objects.nonNull(data.getToWhere())) {
            toWhere.setValue(data.getToWhere()).pressEnter();
            $x(String.format(cityXpath, data.getToWhere())).click();
        }

        SelenideElement departing = ticketOptionsItem.find(attribute("placeholder", locale.getCheckStrings().get(2)))
                .shouldBe(visible);
        if (Objects.nonNull(data.getDeparting())) {
            executeJavaScript("arguments[0].setAttribute('value', arguments[1]);", departing, data.getDeparting());
        }

        SelenideElement returning = ticketOptionsItem.find(attribute("placeholder", locale.getCheckStrings().get(3)))
                .shouldBe(visible);
        if (Objects.nonNull(data.getReturning())) {
            executeJavaScript("arguments[0].setAttribute('value', arguments[1]);", returning, data.getReturning());
        }

        searchButton.click();
        return this;
    }

    public PobedaMainPage changeLocale(Locale locale) {
        localeIcon.shouldBe(visible).click();
        localeList.find(text(locale.value)).click();
        this.locale = locale;
        return this;
    }

    @Getter
    @RequiredArgsConstructor
    public enum Locale {
        RUSSIAN("Русский") {
            @Override
            public List<String> getCheckStrings() {
                return List.of("Откуда", "Куда", "Туда", "Обратно", "Клиенты");
            }
        },
        ENGLISH("English") {
            @Override
            public List<String> getCheckStrings() {
                return List.of("From where", "To where", "Departing", "Returning", "Clients");
            }
        };

        private final String value;

        public abstract List<String> getCheckStrings();
    }
}

