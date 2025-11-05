package ui.pikabu;

import qa.auto.innotech.env.Env;
import qa.auto.innotech.model.User;
import qa.auto.innotech.ui.page.pikabu.PikabuMainPage;
import ui.UiBaseTest;
import util.TestData;

import java.io.File;

public class PikabuBaseTest extends UiBaseTest {

    private final String UI_USER_PATH = String.format("src/main/resources/users/%s/", Env.getCurrentEnv())
            .replace("/", File.separator);

    protected PikabuMainPage mainPage = new PikabuMainPage();

    protected User invalidUser = TestData.readFromJson(UI_USER_PATH.concat("InvalidUser.json"), User.class);
}
