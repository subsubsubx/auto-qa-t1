package qa.auto.innotech.db.dao.dao;

import qa.auto.innotech.db.dao._base._BaseMainDao;
import qa.auto.innotech.db.dao.entity.DepartmentEntity;

import java.util.List;

public class DepartmentDao extends _BaseMainDao {

    public List<DepartmentEntity> findAllDepartments() {
        final String query = "SELECT * FROM Department";
        return getJdbi().withHandle(
                handle -> handle.createQuery(query)
                        .mapToBean(DepartmentEntity.class)
                        .list()
        );
    }

    @Override
    public String getTableName() {
        return "Department";
    }
}
