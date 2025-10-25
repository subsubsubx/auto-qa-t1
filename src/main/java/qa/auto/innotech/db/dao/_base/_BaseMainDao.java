package qa.auto.innotech.db.dao._base;

import lombok.extern.slf4j.Slf4j;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.ColumnMappers;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import qa.auto.innotech.db.dao.DataSourceProvider;
import qa.auto.innotech.env.Env;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@Slf4j
public class _BaseMainDao {
    protected Jdbi jdbi;


    public _BaseMainDao() {
        log.info("Base main DAO init");

        jdbi = Jdbi.create(DataSourceProvider.getH2DataSource(Env.DB.getConfig()));
        jdbi.installPlugin(new SqlObjectPlugin());
        jdbi.getConfig(ColumnMappers.class).setCoalesceNullPrimitivesToDefaults(false);
        log.info("init database");
        if ("qa".equals(System.getenv("env"))) {
            createDB();
            log.info("h2 DB created successfully");
        }
        log.info("Base main DAO done");
    }

    private void createDB() {
        try (Connection con = DriverManager.getConnection("jdbc:h2:.\\Office")) {
            Statement stm = con.createStatement();
            stm.executeUpdate("DROP TABLE Department IF EXISTS");
            stm.executeUpdate("CREATE TABLE Department(ID INT PRIMARY KEY, NAME VARCHAR(255))");
            stm.executeUpdate("INSERT INTO Department VALUES(1,'Accounting')");
            stm.executeUpdate("INSERT INTO Department VALUES(2,'IT')");
            stm.executeUpdate("INSERT INTO Department VALUES(3,'HR')");

            stm.executeUpdate("DROP TABLE Employee IF EXISTS");
            stm.executeUpdate("CREATE TABLE Employee(ID INT PRIMARY KEY, NAME VARCHAR(255), DepartmentID INT)");
            stm.executeUpdate("INSERT INTO Employee VALUES(1,'Pete',1)");
            stm.executeUpdate("INSERT INTO Employee VALUES(2,'Ann',1)");

            stm.executeUpdate("INSERT INTO Employee VALUES(3,'Liz',2)");
            stm.executeUpdate("INSERT INTO Employee VALUES(4,'Tom',2)");

            stm.executeUpdate("INSERT INTO Employee VALUES(5,'Todd',3)");

        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
