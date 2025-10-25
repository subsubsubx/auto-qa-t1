package qa.auto.innotech.db.dao.entity;


import lombok.*;
import org.jdbi.v3.core.mapper.reflect.ColumnName;

@Data
public class EmployeeEntity {
    @ColumnName("ID")
    Integer ID;
    @ColumnName("NAME")
    String name;
    @ColumnName("DepartmentID")
    Integer departmentId;

}
