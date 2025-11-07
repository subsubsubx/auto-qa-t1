package util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import qa.auto.innotech.model.BookingData;
import qa.auto.innotech.model.TicketData;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TestData {
    public static List<String> TEST_STRINGS = List.of("John Doe", "Test", "333");
    public static List<Integer> VALID_GRADES = List.of(2, 3, 4, 5);
    private static ObjectMapper objectMapper;

    private static ObjectMapper getObjectMapper() {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }

        return objectMapper;
    }

    public static TicketData getTestTicketData() {
        return new TicketData("Москва", "Санкт-Петербург", null, null);
    }

    @SneakyThrows
    public static <T> T readFromJson(String path, Class<T> cls) {
        return getObjectMapper().readValue(new File(path), cls);
    }

    @SneakyThrows
    public static <T> List<T> readListFromJson(String path, Class<T> cls) {
        List<T> result = new ArrayList<>();
        List<?> list = getObjectMapper().readValue(new File(path), List.class);

        list.forEach(o -> {
            T t = getObjectMapper().convertValue(o, cls);
            result.add(t);

        });

        return result;
    }

    public static BookingData getTestBookingData() {
        return new BookingData("Qwerty", "XXXXXX");
    }
}


