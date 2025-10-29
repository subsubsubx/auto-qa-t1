package util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TestData {
    public static List<String> TEST_STRINGS = List.of("John Doe", "Test", "333");
    public static List<Integer> VALID_GRADES = List.of(2, 3, 4, 5);
    private static ObjectMapper objectMapper;
    private static final String TEST_DATA_PATH = "src/test/resources/testdata/db/%s".replace("/", File.separator);

    private static ObjectMapper getObjectMapper() {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }
        return objectMapper;
    }

    @SneakyThrows
    public static <T> T readFromJson(String fileName, Class<T> cls) {
        File file = new File(String.format(TEST_DATA_PATH, fileName));
        return getObjectMapper().readValue(file, cls);
    }

    @SneakyThrows
    public static <T> List<T> readListFromJson(String fileName, Class<T> cls) {
        List<T> result = new ArrayList<>();
        File file = new File(String.format(TEST_DATA_PATH, fileName));
        List<?> list = getObjectMapper().readValue(file, List.class);

        list.forEach(o -> {
            T t = getObjectMapper().convertValue(o, cls);
            result.add(t);

        });

        return result;
    }
}


