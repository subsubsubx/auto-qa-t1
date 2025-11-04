package jupiter.extension;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import qa.auto.innotech.env.Env;
import qa.auto.innotech.env.config.UiConfig;

import java.io.File;

@Slf4j
public class UiTestExtension implements AfterEachCallback, BeforeAllCallback {

    private final UiConfig uiConfig = Env.UI.getConfig();
    private final String driverPath = "driver/chrome/chromedriver".replace("/", File.separator);

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        log.info("Selenide Configuration");
        Configuration.screenshots = true;
        Configuration.savePageSource = true;
        Configuration.timeout = uiConfig.timeout();
        Configuration.reportsFolder = uiConfig.reportsFolder();
        WebDriverRunner.setWebDriver(getWebDriver());
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        Selenide.closeWebDriver();
    }

    private WebDriver getWebDriver() {
        if ("firefox".equals(System.getenv("ui.browser"))) {
            Configuration.browser = "firefox";
            return getFirefoxWebDriver();
        }
        return getChromeWebDriver();
    }

    private ChromeDriver getChromeWebDriver() {
        log.info("Loading ChromeDriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        if (System.getProperty("os.name").startsWith("Mac")) {
            System.setProperty("webdriver.chrome.driver", driverPath);
            //     options.setBinary(uiConfig.getChromeMacBinaryLoc());
        } else {
            System.setProperty("webdriver.chrome.driver", driverPath.concat(".exe"));
            //      options.setBinary(uiConfig.getChrome64BinaryLoc());
        }

        return new ChromeDriver(options);
    }

    private FirefoxDriver getFirefoxWebDriver() {
        FirefoxOptions options = new FirefoxOptions();
        //TODO: firefox options
        log.info("Loading FirefoxDriver");
        return new FirefoxDriver(options);
    }

    private RemoteWebDriver getRemoteWebDriver() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        //TODO: remote
        return new RemoteWebDriver(capabilities);
    }
}
