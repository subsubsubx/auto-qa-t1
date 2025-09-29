import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import qa.auto.innotech.Student;
import util.DataGenerator;

import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static util.DataGenerator.generateGrades;
import static util.DataGenerator.generateStudent;


public class StudentTest {

    @Test
    void encapsulationCollectionTest() {
        Student student = generateStudent();
        generateGrades(student, true);
        int gradesSize = student.getGrades().size();
        int add = 2;

        student.getGrades().add(add);

        assertEquals(student.getGrades().size(), gradesSize);
    }

    @ParameterizedTest
    @MethodSource("util.DataGenerator#generateString")
    void constructorTest(String str) {
        Student student = new Student(str);

        assertEquals(str, student.getName());
    }

    @ParameterizedTest
    @MethodSource("util.DataGenerator#generateString")
    void setterTest(String str) {
        Student student = generateStudent();
        student.setName(str);

        assertEquals(str, student.getName());
    }

    @Test
    void validGradeTest() {
        Student student = DataGenerator.generateStudent();

        Assertions.assertDoesNotThrow(
                () -> DataGenerator.generateGrades(student, true));

    }

    @Test
    void invalidGradeTest() {
        Student student = DataGenerator.generateStudent();

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> DataGenerator.generateGrades(student, false));

    }

    @Test
    void hashCodeTest() {
        String randomString = DataGenerator.generateString().collect(Collectors.joining());

        Student student1 = new Student(randomString);
        Student student2 = new Student(randomString);

        generateGrades(student1, true);
        generateGrades(student2, true);

        assertEquals(student1.hashCode(), student2.hashCode());
        student1.addGrade(2);
        assertNotEquals(student1.hashCode(), student2.hashCode());
    }

    @Test
    void equalsTest() {
        String randomString = DataGenerator.generateString().collect(Collectors.joining());

        Student student1 = new Student(randomString);
        Student student2 = new Student(randomString);

        generateGrades(student1, true);
        generateGrades(student2, true);

        assertTrue(student1.equals(student2));

        student1.addGrade(2);

        assertNotEquals(student1, student2);
    }

}

