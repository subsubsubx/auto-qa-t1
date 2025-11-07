package qa.auto.innotech.ui;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebElementCondition;

import static com.codeborne.selenide.Condition.visible;

public abstract class BasePage {

    public SelenideElement hoverTo(SelenideElement element) {
        return element.shouldBe(visible).hover();
    }

    public SelenideElement getElementFromCollection(ElementsCollection collection, WebElementCondition condition) {
        return collection.find(condition).shouldBe(visible);
    }

    public SelenideElement getElementFromCollection(ElementsCollection collection, int number) {
        return collection.get(number - 1).shouldBe(visible);
    }
}
