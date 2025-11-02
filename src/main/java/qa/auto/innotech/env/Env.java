package qa.auto.innotech.env;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.ConfigFactory;
import qa.auto.innotech.env.config.APIConfig;
import qa.auto.innotech.env.config.DBConfig;
import qa.auto.innotech.env.config.UiConfig;

public enum Env {
    DB {
        @Override
        @SuppressWarnings("unchecked")
        public DBConfig getConfig() {
            return ConfigFactory.create(DBConfig.class);
        }
    },
    API {
        @Override
        @SuppressWarnings("unchecked")
        public APIConfig getConfig() {
            return ConfigFactory.create(APIConfig.class);
        }
    },
    UI {
        @Override
        @SuppressWarnings("unchecked")
        public UiConfig getConfig() {
            return ConfigFactory.create(UiConfig.class);
        }
    };

    public abstract <T extends Config> T getConfig();

    public static String getCurrentEnv() {
        return System.getenv("env");
    }
}
