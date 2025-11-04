package qa.auto.innotech.env.config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:env",
        "system:properties",
        "classpath:config/${env}/uiConfig.properties",
        "classpath:config/uiDefaults.properties"})
public interface UiConfig extends Config {

    @Key("ui.resolution")
    String resolution();

    @Key("ui.remote.url")
    String remoteUrl();

    @Key("ui.reports.folder")
    String reportsFolder();

    @Key("ui.browser")
    String browser();

    @Key("ui.chrome.mac.binary.loc")
    String getChromeMacBinaryLoc();

    @Key("ui.chrome.win64.binary.loc")
    String getChrome64BinaryLoc();

    @Key("ui.timeout")
    long timeout();

    @Key("ui.google.base.url")
    String googleBaseUrl();

    @Key("ui.pikabu.base.url")
    String pikabuBaseUrl();

    @Key("ui.pobeda.base.url")
    String pobedaBaseUrl();
}
