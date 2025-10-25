package qa.auto.innotech.env.config;


import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:env",
        "system:properties",
        "classpath:config/${env}/apiConfig.properties"
})
public interface APIConfig extends Config {
    @Key("api.base.url")
    String url();

}
