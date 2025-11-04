package qa.auto.innotech.ui.page.google;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class GoogleMainPage {

    SelenideElement searchBar = $("textarea[name=q]");

    public GoogleSearchPage search(String val) {
        searchBar.setValue(val);
        searchBar.pressEnter();
        return new GoogleSearchPage();
    }
}
