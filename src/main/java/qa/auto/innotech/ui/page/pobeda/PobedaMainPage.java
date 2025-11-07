package qa.auto.innotech.ui.page.pobeda;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import qa.auto.innotech.model.BookingData;
import qa.auto.innotech.model.TicketData;
import qa.auto.innotech.ui.BasePage;
import qa.auto.innotech.ui.assertions.Assertable;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class PobedaMainPage extends BasePage
        implements Assertable<PobedaMainPageAssert> {

    Locale locale = Locale.RUSSIAN;
    String title = "Авиакомпания «Победа» - купить авиабилеты онлайн, дешёвые билеты на самолёт, прямые и трансферные рейсы с пересадками";
    String cityXpath = "//div[@class='dp-1loxy1b-root']//div[contains(text(), '%s')]";
    SelenideElement mainPageKaliningradPic = $x("//img[contains(@srcset, 'Kaliningrad')]");
    SelenideElement localeIcon = $x("//button[@aria-label='Поиск']//preceding-sibling::button");
    SelenideElement companyLogo = $x("//a[@aria-label='«Авиакомпания «Победа», Группа «Аэрофлот»'][1]//img");
    SelenideElement informationSection = $x("//a[@href='/information']");
    SelenideElement searchButton = $x("//button[@class='dp-k64vy3-root-root-root']");
    SelenideElement bookingItem = $x("//div[@style]//div[@class='dp-1dbdmhf-root']");
    ElementsCollection localeList = $$x("//div[@role='menuitem']");
    ElementsCollection ticketOptionsItem = $$x("//div[@class='dp-1amht5s-root']//div[@class='dp-zohsg-root']//input");
    ElementsCollection topBar = $$x("//div[@class='dp-1ctlc8i-root-tabsControlList']//span[not(@aria-hidden)]");

    public PobedaMainPage selectTopBarItem(int num) {
        getElementFromCollection(topBar,
                text(locale.getTopBarElements().get(num - 1)))
                .click();

        return this;
    }


    public PobedaMainPage hoverInformationSection() {
        hoverTo(informationSection);
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
    public PobedaMainPage fillTicketData(TicketData data) {
        SelenideElement fromWhere = getElementFromCollection(ticketOptionsItem, attribute("placeholder",
                locale.getCheckStrings().get(0)));

        if (Objects.nonNull(data.getFromWhere())) {
            fromWhere.setValue(data.getFromWhere());
            $x(String.format(cityXpath, data.getFromWhere())).click();
        }

        SelenideElement toWhere = getElementFromCollection(ticketOptionsItem, attribute("placeholder",
                locale.getCheckStrings().get(1)));

        if (Objects.nonNull(data.getToWhere())) {
            toWhere.setValue(data.getToWhere());
            $x(String.format(cityXpath, data.getToWhere())).click();
        }

        SelenideElement departing = getElementFromCollection(ticketOptionsItem, attribute("placeholder",
                locale.getCheckStrings().get(2)));

        if (Objects.nonNull(data.getDeparting())) {
            executeJavaScript("arguments[0].setAttribute('value', arguments[1]);", departing, data.getDeparting());
        }

        SelenideElement returning = getElementFromCollection(ticketOptionsItem, attribute("placeholder",
                locale.getCheckStrings().get(3)));

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

    public <T extends BasePage> T fillBookingData(BookingData bookingData, boolean clickSearch, T pageToReturn) {
        if (Objects.nonNull(bookingData.getLastName())) {
            bookingItem.$x(String.format(".//input[@placeholder='%s']",
                            locale.getBookingItemList().get(0)))
                    .setValue(bookingData.getLastName());
        }

        if (Objects.nonNull(bookingData.getBookingNumber())) {

            bookingItem.$x(String.format(".//input[@placeholder='%s']",
                            locale.getBookingItemList().get(1)))
                    .setValue(bookingData.getBookingNumber());
        }

        if (clickSearch) {
            bookingItem.$x(".//button").click();
            switchTo().window(1);
        }

        return t;
    }

    @Getter
    @RequiredArgsConstructor
    public enum Locale {
        RUSSIAN("Русский") {
            @Override
            public List<String> getTopBarElements() {
                return List.of("Поиск билета", "Онлайн-регистрация", "Управление бронированием");
            }

            public List<String> getBookingItemList() {
                return List.of("Фамилия клиента", "Номер бронирования или билета", "Поиск");
            }

            @Override
            public List<String> getCheckStrings() {
                return List.of("Откуда", "Куда", "Туда", "Обратно", "Клиенты");
            }
        },
        ENGLISH("English") {
            @Override
            public List<String> getTopBarElements() {
                return List.of("Ticket search", "Online check-in", "Manage my booking");
            }

            public List<String> getBookingItemList() {
                return List.of("Client's last name", "Order or ticket number", "Search");
            }

            @Override
            public List<String> getCheckStrings() {
                return List.of("From where", "To where", "Departing", "Returning", "Customers");
            }
        };

        private final String value;

        public abstract List<String> getBookingItemList();

        public abstract List<String> getTopBarElements();

        public abstract List<String> getCheckStrings();
    }
}

