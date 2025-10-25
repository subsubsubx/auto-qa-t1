package qa.auto.innotech.db.dao.repository;


import lombok.experimental.UtilityClass;
import qa.auto.innotech.db.dao.students.EmployeeDao;

@UtilityClass
public class DaoRepository {
    public static final EmployeeDao employeeDao = new EmployeeDao();
}
