package qa.auto.innotech.db.dao._base;

import lombok.extern.slf4j.Slf4j;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.ColumnMappers;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import qa.auto.innotech.db.dao.DataSourceProvider;
import qa.auto.innotech.env.Env;

@Slf4j
public class _BaseMainDao {
    protected Jdbi jdbi;


    public _BaseMainDao() {
        log.info("Base main DAO init");

        jdbi = Jdbi.create(DataSourceProvider.getH2DataSource(Env.DB.getConfig()));
        jdbi.installPlugin(new SqlObjectPlugin());
        jdbi.getConfig(ColumnMappers.class).setCoalesceNullPrimitivesToDefaults(false);

        log.info("Base main DAO done");
    }
}
