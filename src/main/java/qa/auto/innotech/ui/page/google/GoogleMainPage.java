package qa.auto.innotech.ui.page.google;

import com.codeborne.selenide.SelenideElement;
import qa.auto.innotech.ui.BasePage;

import static com.codeborne.selenide.Selenide.$;

public class GoogleMainPage extends BasePage {

    SelenideElement searchBar = $("textarea[name=q]");

    public GoogleSearchPage search(String val) {
        searchBar.setValue(val).pressEnter();

        return new GoogleSearchPage();
    }
}
