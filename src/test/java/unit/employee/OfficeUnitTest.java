package unit.employee;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import qa.auto.innotech.office.Department;
import qa.auto.innotech.office.Employee;
import qa.auto.innotech.office.Service;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;
import static qa.auto.innotech.office.Service.*;

public class OfficeUnitTest {

    @BeforeEach
    void init() {
        createDB();
    }

    @Test
    void shouldDropTablesAndCreateDB() {
        try (Connection con = DriverManager.getConnection("jdbc:h2:.\\Office")) {
            Statement statement = con.createStatement();
            ResultSet resultSet2 = statement.executeQuery("SELECT COUNT(ID) from Department");
            int depCountExpected = resultSet2.getInt(1);
            ResultSet resultSet3 = statement.executeQuery("SELECT COUNT(ID) FROM Employee");
            int employeeCountExpected = resultSet3.getInt(1);

            createDB();

            ResultSet resultSet = statement.executeQuery("SELECT COUNT(ID) from Department");
            int depCountActual = resultSet.getInt(1);
            ResultSet resultSet1 = statement.executeQuery("SELECT COUNT(ID) FROM Employee");
            int employeeCountActual = resultSet1.getInt(1);

            assertAll(() -> {
                assertEquals(depCountExpected, depCountActual);
                assertEquals(employeeCountExpected, employeeCountActual);
            });
        } catch (Throwable t) {
            fail(t);
        }
    }

    @Test
    void shouldAddDepartment() {
        Department testDep = new Department(4, "Test Dep");
        addDepartment(testDep);
        try (Connection con = DriverManager.getConnection("jdbc:h2:.\\Office")) {
            PreparedStatement preparedStatement = con.prepareStatement("SELECT NAME FROM Department WHERE ID =?");
            preparedStatement.setInt(1, testDep.getDepartmentID());
            ResultSet resultSet = preparedStatement.executeQuery();
            String name = resultSet.getString("NAME");
            assertEquals(testDep.getName(), name);
        } catch (Throwable t) {
            fail(t);
        }
    }

    @Test
    void shouldRemoveDepartment() {
        Department departmentToRemove = new Department(1, "");
        removeDepartment(departmentToRemove);
        try (Connection con = DriverManager.getConnection("jdbc:h2:.\\Office")) {
            PreparedStatement preparedStatement = con.prepareStatement("SELECT COUNT(ID) FROM Department WHERE ID=?");
            preparedStatement.setInt(1, departmentToRemove.getDepartmentID());
            int actualDep = preparedStatement.executeQuery().getInt(1);

            PreparedStatement preparedStatement1 = con.prepareStatement("SELECT COUNT(ID) FROM Employee where DepartmentID=?");
            preparedStatement1.setInt(1, departmentToRemove.getDepartmentID());
            int actualEmployees = preparedStatement.executeQuery().getInt(1);

            assertAll(() -> {
                assertEquals(0, actualDep);
                assertEquals(0, actualEmployees);
            });
        } catch (Throwable t) {
            fail(t);
        }
    }

    @Test
    void shouldAddEmployee() {
        Employee expected = new Employee(10, "John Doe", 1);
        Service.addEmployee(expected);
        try (Connection con = DriverManager.getConnection("jdbc:h2:.\\Office")) {
            PreparedStatement preparedStatement = con.prepareStatement("""
                    SELECT ID AS ID, NAME AS NAME, DepartmentID AS DEPARTMENTID 
                    FROM Employee WHERE ID=?
                    """);
            preparedStatement.setInt(1, expected.getEmployeeId());
            ResultSet resultSet = preparedStatement.executeQuery();
            Employee actual = new Employee(resultSet.getInt("ID"), resultSet.getString("NAME"),
                    resultSet.getInt("DEPARTMENTID"));
            assertEquals(expected, actual);
        } catch (Throwable t) {
            fail(t);
        }
    }

    @Test
    void shouldRemoveEmployee() {
        Employee employee = new Employee(1, "", 1);
        Service.removeEmployee(employee);
        try (Connection con = DriverManager.getConnection("jdbc:h2:.\\Office")) {
            PreparedStatement preparedStatement = con.prepareStatement("SELECT COUNT(ID) FROM Employee WHERE ID=?");
            preparedStatement.setInt(1, employee.getEmployeeId());
            int actual = preparedStatement.executeQuery().getInt(1);
            assertEquals(0, actual);
        } catch (Throwable t) {
            fail(t);
        }
    }
}

