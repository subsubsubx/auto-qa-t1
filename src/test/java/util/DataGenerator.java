package util;

import qa.auto.innotech.Student;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DataGenerator {
    public static List<Integer> VALID_GRADES = List.of(2, 3, 4, 5);

    public static Student generateStudent() {
        Student student = new Student(generateString().collect(Collectors.joining()));
        student.setClient(new StudentClientMock());
        return student;
    }

    public static IntStream generateGrades() {
        return IntStream.rangeClosed(-5, 10);
    }

    public static Stream<String> generateString() {
        String characters = "abcdefghijklmnopqrstuvwxyz";
        return Stream.generate(() -> {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 10; i++) {
                int randomIndex = new Random().nextInt(characters.length());
                sb.append(characters.charAt(randomIndex));
            }
            return sb.toString();
        }).limit(3);
    }
}
