package util;

import qa.auto.innotech.Student;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DataGenerator {

    public static Student generateStudent() {
        return new Student(generateString().collect(Collectors.joining()));
    }

    public static void generateGrades(Student student, boolean valid) {
        if (valid) {
            IntStream.rangeClosed(3, 5).forEach(student::addGrade);
        } else {
            IntStream.rangeClosed(0, 1).forEach(student::addGrade);
            IntStream.rangeClosed(6, 10).forEach(student::addGrade);
        }
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
