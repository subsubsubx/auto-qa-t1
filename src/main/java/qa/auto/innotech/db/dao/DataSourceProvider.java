package qa.auto.innotech.db.dao;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.jdbi.v3.core.Jdbi;
import qa.auto.innotech.env.config.DBConfig;

import javax.sql.DataSource;

@UtilityClass
@Slf4j
public class DataSourceProvider {

    public static DataSource getH2DataSource(DBConfig config) {
        log.info("h2 datasource");

        HikariConfig hikariConfig = new HikariConfig();

        hikariConfig.setJdbcUrl(config.url());
       // hikariConfig.setUsername(config.user());
       // hikariConfig.setPassword(config.password());

        hikariConfig.setConnectionTimeout(30000);
        hikariConfig.setPoolName("H2");
        hikariConfig.setAutoCommit(true);

        log.info("h2 datasource config done");
        return new HikariDataSource(hikariConfig);

    }
}
