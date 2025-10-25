package qa.auto.innotech.env.config;

import org.aeonbits.owner.Config;


@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:env",
        "system:properties",
        "classpath:config/${env}/dbConfig.properties"
})

public interface DBConfig extends Config {

    @Key("db.h2.jdbcUrl")
    String url();

    @Key("db.h2.user")
    String user();

    @Key("db.h2.password")
    String password();
}
