package qa.auto.innotech.ui.page.google;

import com.codeborne.selenide.ElementsCollection;
import qa.auto.innotech.ui.assertions.Assertable;

import static com.codeborne.selenide.Selenide.$$x;

public class GoogleSearchPage implements Assertable<GoogleSearchPage, GoogleSearchPageAssert> {

    ElementsCollection searchResults = $$x("//div[@id='rso']//div[starts-with(@class, 'notranslate')]");

    public <T> T selectSearchResultByNumber(int num, T page) {
        searchResults
                .get(num)
                .click();

        return page;
    }

}
