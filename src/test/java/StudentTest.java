import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import qa.auto.innotech.Student;
import qa.auto.innotech.StudentClient;
import util.DataGenerator;
import util.StudentClientMock;

import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static util.DataGenerator.VALID_GRADES;
import static util.DataGenerator.generateStudent;


public class StudentTest {

    private final StudentClient client = new StudentClientMock();

    @Test
    void encapsulationCollectionTest() {
        Student student = generateStudent();

        int gradesSize = student.getGrades().size();
        int add = 2;
        student.getGrades().add(add);

        assertEquals(student.getGrades().size(), gradesSize);
    }

    @ParameterizedTest
    @MethodSource("util.DataGenerator#generateString")
    void constructorTest(String str) {
        Student student = new Student(str, client);

        assertEquals(str, student.getName());
    }

    @ParameterizedTest
    @MethodSource("util.DataGenerator#generateString")
    void setterTest(String str) {
        Student student = generateStudent();
        student.setName(str);

        assertEquals(str, student.getName());
    }

    @ParameterizedTest
    @MethodSource("util.DataGenerator#generateGrades")
    void addGradeTest(int grade) {
        Student student = DataGenerator.generateStudent();
        if (VALID_GRADES.contains(grade)) {
            student.addGrade(grade);
            assertEquals(1, student.getGrades().size());
            assertTrue(student.getGrades().contains(grade));
        } else {
            assertThrows(IllegalArgumentException.class, () -> student.addGrade(grade));
        }
    }

    @Test
    void hashCodeTest() {
        String randomString = DataGenerator.generateString().collect(Collectors.joining());

        Student student1 = new Student(randomString, client);
        Student student2 = new Student(randomString, client);


        VALID_GRADES.forEach(student1::addGrade);
        VALID_GRADES.forEach(student2::addGrade);


        assertEquals(student1.hashCode(), student2.hashCode());
        student1.addGrade(2);
        assertNotEquals(student1.hashCode(), student2.hashCode());
    }

    @Test
    void equalsTest() {
        String randomString = DataGenerator.generateString().collect(Collectors.joining());

        Student student1 = new Student(randomString, client);
        Student student2 = new Student(randomString, client);

        assertEquals(student1, student2);
        student1.addGrade(2);
        assertNotEquals(student1, student2);
    }

}

