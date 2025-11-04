package qa.auto.innotech.ui.page.google;

import org.assertj.core.api.AbstractAssert;

public class GoogleSearchPageAssert extends AbstractAssert<GoogleSearchPageAssert, GoogleSearchPage> {

    public GoogleSearchPageAssert(GoogleSearchPage googleSearchPage) {
        super(googleSearchPage, GoogleSearchPageAssert.class);
    }

    public GoogleSearchPage page() {
        return actual;
    }

}
