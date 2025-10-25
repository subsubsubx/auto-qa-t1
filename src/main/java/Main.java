import org.aeonbits.owner.ConfigFactory;
import qa.auto.innotech.db.dao.entity.EmployeeEntity;
import qa.auto.innotech.db.dao.repository.DaoRepository;
import qa.auto.innotech.env.config.DBConfig;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<EmployeeEntity> list = DaoRepository.employeeDao.findAllEmployees();
        System.out.println(list);
    }
}
