package qa.auto.innotech.db.dao._base;

import lombok.extern.slf4j.Slf4j;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.ColumnMappers;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import qa.auto.innotech.db.dao.DataSourceProvider;
import qa.auto.innotech.env.Env;

@Slf4j
public abstract class _BaseMainDao {
    private final Jdbi jdbi;

    public _BaseMainDao() {
        log.info("Base main DAO");
        jdbi = Jdbi.create(DataSourceProvider.getH2DataSource(Env.DB.getConfig()));
        jdbi.installPlugin(new SqlObjectPlugin());
        jdbi.getConfig(ColumnMappers.class).setCoalesceNullPrimitivesToDefaults(false);
        log.info("init database");
        if ("qa".equals(System.getenv("env"))) {
            createDB();
            log.info("Local DB created successfully");
        }
        log.info("Base main DAO done");
    }

    public abstract String getTableName();

    public Jdbi getJdbi() {
        return jdbi;
    }

    public void truncate() {
        jdbi.useHandle(handle -> {
            handle.createUpdate(String.format("TRUNCATE TABLE %s", getTableName()))
                    .execute();
        });
    }

    private void createDB() {
        jdbi.useHandle(handle -> {
            handle.execute("DROP TABLE Department IF EXISTS");
            handle.execute("CREATE TABLE Department (ID INT PRIMARY KEY, NAME VARCHAR(255))");

            handle.execute("DROP TABLE Employee IF EXISTS");
            handle.execute("CREATE TABLE EMPLOYEE (ID INT PRIMARY KEY, NAME VARCHAR(255), DepartmentID INT)");
        });
    }
}
