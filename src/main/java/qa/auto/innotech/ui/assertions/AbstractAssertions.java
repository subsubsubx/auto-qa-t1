package qa.auto.innotech.ui.assertions;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class AbstractAssertions<PAGE> {

    private PAGE page;

    public PAGE page() {
        return page;
    }
}
