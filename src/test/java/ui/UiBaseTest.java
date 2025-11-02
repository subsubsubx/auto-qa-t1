package ui;

import jupiter.annotation.UiTest;
import qa.auto.innotech.env.Env;
import qa.auto.innotech.env.config.UiConfig;

@UiTest
public class UiBaseTest {
    protected UiConfig uiConfig = Env.UI.getConfig();
}
