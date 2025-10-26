package util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import qa.auto.innotech.db.dao.entity.DepartmentEntity;
import qa.auto.innotech.db.dao.entity.EmployeeEntity;

import java.io.File;
import java.util.List;

public class TestData {
    private static final String TEST_DATA_PATH = "src/test/resources/testdata/db/%s".replace("/", File.separator);

    public static List<String> TEST_STRINGS = List.of("John Doe", "Test", "333");
    public static List<Integer> VALID_GRADES = List.of(2, 3, 4, 5);
    private static ObjectMapper objectMapper;

    private static ObjectMapper getObjectMapper() {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }
        return objectMapper;
    }

    @SneakyThrows
    public static List<DepartmentEntity> fillDepartments() {
        ObjectMapper objectMapper = getObjectMapper();
        File file = new File(String.format(TEST_DATA_PATH, "TestDepartments.json"));
        return objectMapper.readValue(file, new TypeReference<List<DepartmentEntity>>() {
        });
    }

    @SneakyThrows
    public static List<EmployeeEntity> fillEmployees() {
        ObjectMapper objectMapper = getObjectMapper();
        File file = new File(String.format(TEST_DATA_PATH, "TestEmployees.json"));
        return objectMapper.readValue(file, new TypeReference<List<EmployeeEntity>>() {
        });
    }
}


