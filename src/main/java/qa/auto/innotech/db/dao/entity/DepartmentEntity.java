package qa.auto.innotech.db.dao.entity;

import lombok.Data;
import org.jdbi.v3.core.mapper.reflect.ColumnName;

@Data
public class DepartmentEntity {
    @ColumnName("ID")
    private Integer id;
    @ColumnName("NAME")
    private String name;
}
