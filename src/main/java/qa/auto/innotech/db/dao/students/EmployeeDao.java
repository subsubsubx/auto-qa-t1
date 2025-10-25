package qa.auto.innotech.db.dao.students;

import qa.auto.innotech.db.dao._base._BaseMainDao;
import qa.auto.innotech.db.dao.entity.EmployeeEntity;

import java.util.List;

public class EmployeeDao extends _BaseMainDao {

    public List<EmployeeEntity> findAllEmployees() {
        final String query = "SELECT * FROM PUBLIC.Employee";
        return jdbi.withHandle(
                handle -> handle.createQuery(query)
                        .mapToBean(EmployeeEntity.class)
                        .list()
        );
    }
}
