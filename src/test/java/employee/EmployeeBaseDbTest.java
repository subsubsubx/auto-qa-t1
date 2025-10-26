package employee;

import qa.auto.innotech.db.dao.dao.DepartmentDao;
import qa.auto.innotech.db.dao.dao.EmployeeDao;
import qa.auto.innotech.db.dao.entity.DepartmentEntity;
import qa.auto.innotech.db.dao.entity.EmployeeEntity;

import java.util.List;

import static util.TestData.fillDepartments;
import static util.TestData.fillEmployees;

public class EmployeeBaseDbTest {
    protected EmployeeDao employeeDao = new EmployeeDao();
    protected DepartmentDao departmentDao = new DepartmentDao();

    protected void init() {
        List<DepartmentEntity> departments = fillDepartments();
        List<EmployeeEntity> employees = fillEmployees();

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
