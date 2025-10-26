package employee;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import qa.auto.innotech.db.dao.entity.DepartmentEntity;
import qa.auto.innotech.db.dao.entity.EmployeeEntity;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmployeeDbTest extends EmployeeBaseDbTest {

    @BeforeEach
    void before() {
        init();
    }

    @AfterEach
    void clear() {
        employeeDao.truncate();
        departmentDao.truncate();
    }

    @Test
    @DisplayName("""
            Найдите ID сотрудника с именем Ann. 
            Если такой сотрудник только один, то установите его департамент в HR.
            """)
    void shouldSetHRDepartmentIfEmployeeExists() {
        List<EmployeeEntity> employees = employeeDao.findAllEmployees();
        String testName = "Ann";
        EmployeeEntity testEmployee = employees.stream()
                .filter(e -> testName.equals(e.getName()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("Работник с именем %s не найден", testName)));

        List<DepartmentEntity> departments = departmentDao.findAllDepartments();

        Integer expectedId = departments.stream()
                .filter(it -> "HR".equals(it.getName()))
                .map(DepartmentEntity::getId)
                .findFirst()
                .orElseThrow();

        employeeDao.updateEmployeeById(testEmployee.getId(), "DepartmentID", expectedId);

        Integer actualId = employeeDao.selectEmployeeById(testEmployee.getId()).getDepartmentId();
        assertEquals(expectedId, actualId);
    }

    @Test
    @DisplayName("""
            Проверьте имена всех сотрудников. 
            Если чьё-то имя написано с маленькой буквы, исправьте её на большую. 
            Выведите на экран количество исправленных имён.
            """)
    void shouldUpdateFirstLetterIfNameStartsWithLowerCase() {
        List<EmployeeEntity> employees = employeeDao.findAllEmployees();
        AtomicInteger affectedRows = new AtomicInteger();
        employees.forEach(employee -> {
            char[] charArray = employee.getName().toCharArray();
            if (Character.isLowerCase(charArray[0])) {
                affectedRows.getAndIncrement();
                charArray[0] = Character.toUpperCase(charArray[0]);
                employeeDao.updateEmployeeById(employee.getId(), "name", new String(charArray));
            }
        });
        assertEquals(2, affectedRows.get());
    }

    @Test
    @DisplayName("Выведите на экран количество сотрудников в IT-отделе")
    void shouldReturnEmployeesCountForItDep() {
        List<EmployeeEntity> employees = employeeDao.findAllEmployees();
        List<DepartmentEntity> departments = departmentDao.findAllDepartments();
        Integer itId = departments.stream()
                .filter(department -> "IT".equals(department.getName()))
                .map(DepartmentEntity::getId)
                .findFirst()
                .orElseThrow();

        List<EmployeeEntity> expectedList = employees.stream()
                .filter(employee -> Objects.equals(employee.getDepartmentId(), itId))
                .toList();

        assertEquals(2, expectedList.size());
    }

}
