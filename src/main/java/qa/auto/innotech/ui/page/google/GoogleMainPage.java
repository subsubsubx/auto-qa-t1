package qa.auto.innotech.ui.page.google;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class GoogleMainPage {

    SelenideElement searchBar = $x("");

    public GoogleSearchPage search(String val) {
        searchBar.setValue(val);
        return new GoogleSearchPage();
    }
}
