package employee;

import qa.auto.innotech.db.dao.dao.DepartmentDao;
import qa.auto.innotech.db.dao.dao.EmployeeDao;
import qa.auto.innotech.db.dao.entity.DepartmentEntity;
import qa.auto.innotech.db.dao.entity.EmployeeEntity;

import java.util.List;

import static util.TestData.*;

public class EmployeeBaseDbTest {
    protected EmployeeDao employeeDao = new EmployeeDao();
    protected DepartmentDao departmentDao = new DepartmentDao();

    protected void init() {
        List<DepartmentEntity> departments = readListFromJson("TestDepartments.json", DepartmentEntity.class);
        List<EmployeeEntity> employees = readListFromJson("TestEmployees.json", EmployeeEntity.class);

        departments.forEach(department -> {
            departmentDao.getJdbi().useHandle(handle -> {
                handle.createUpdate("""
                                INSERT INTO Department (ID, NAME) VALUES(:id, :name)
                                """)
                        .bind("id", department.getId())
                        .bind("name", department.getName())
                        .execute();

            });
        });

        employees.forEach(employee -> {
            employeeDao.getJdbi().useHandle(handle -> {
                handle.createUpdate("INSERT INTO Employee (ID, NAME, DepartmentID) VALUES (:id, :name, :departmentId)")
                        .bind("id", employee.getId())
                        .bind("name", employee.getName())
                        .bind("departmentId", employee.getDepartmentId())
                        .execute();
            });
        });
    }
}
