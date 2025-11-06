package ui.google;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import qa.auto.innotech.ui.page.pobeda.PobedaMainPage.Locale;


public class GoogleTest extends GoogleBaseTest {

    @BeforeEach
    void init() {
      //  Selenide.open(uiConfig.googleBaseUrl());
        Selenide.open(uiConfig.pobedaBaseUrl());
    }

    @Test
    void shouldChangeLocaleOnPobedaMainPage() {
//        googleMainPage
//                .search("Сайт компании Победа")
//                .selectSearchResultByNumber(0, pobedaMainPage)
                 pobedaMainPage.check()
                .checkLoadPage()
                .page()
                .changeLocale(Locale.ENGLISH)
                .check()
                .checkEngLocale(Locale.ENGLISH);
    }
}
