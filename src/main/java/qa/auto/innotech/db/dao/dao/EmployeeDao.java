package qa.auto.innotech.db.dao.dao;

import qa.auto.innotech.db.dao._base._BaseMainDao;
import qa.auto.innotech.db.dao.entity.EmployeeEntity;

import java.util.List;

public class EmployeeDao extends _BaseMainDao {

    public List<EmployeeEntity> findAllEmployees() {
        final String query = "SELECT * FROM Employee";
        return getJdbi().withHandle(
                handle -> handle.createQuery(query)
                        .mapToBean(EmployeeEntity.class)
                        .list()
        );
    }

    public int updateEmployeeById(Integer id, String column, Object value) {
        final String query = """
                UPDATE Employee SET %s = :value
                WHERE ID = :id
                """;
        return getJdbi().withHandle(handle -> handle.createUpdate(String.format(query, column))
                .bind("value", value)
                .bind("id", id)
                .execute());
    }


    public EmployeeEntity selectEmployeeById(Integer id) {
        final String query = "SELECT * FROM Employee WHERE ID = :id";
        return getJdbi().withHandle(handle -> handle.createQuery(query)
                .bind("id", id)
                .mapToBean(EmployeeEntity.class)
                .findFirst()
                .orElseThrow());
    }

    @Override
    protected String getTableName() {
        return "Employee";
    }
}
