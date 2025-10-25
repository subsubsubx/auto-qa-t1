package qa.auto.innotech.env;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.ConfigFactory;
import qa.auto.innotech.env.config.APIConfig;
import qa.auto.innotech.env.config.DBConfig;

public enum Env {
    DB {
        @Override
        public DBConfig getConfig() {
            return ConfigFactory.create(DBConfig.class);
        }
    },
    API{
        @Override
        public APIConfig getConfig() {
            return ConfigFactory.create(APIConfig.class);
        }
    };

    public abstract <T extends Config> T getConfig();
}
