package unit.student;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import qa.auto.innotech.model.Student;
import util.StudentClientMock;

import static org.junit.jupiter.api.Assertions.*;
import static util.TestData.TEST_STRINGS;
import static util.TestData.VALID_GRADES;


public class StudentTest extends StudentBaseTest {

    @Test
    void getGradesReturnsDefensiveCopy() {
        Student student = new Student("", client);

        int gradesSize = student.getGrades().size();
        int add = 2;

        student.getGrades().add(add);
        assertEquals(student.getGrades().size(), gradesSize);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Jane Doe"})
    void constructorStringParamSetsName(String str) {
        Student student = new Student(str, client);

        assertEquals(str, student.getName());
    }

    @ParameterizedTest
    @ValueSource(strings = {"John Doe"})
    void setNameUpdatesNameProperty(String str) {
        Student student = new Student("", client);

        student.setName(str);
        assertEquals(str, student.getName());
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 4, 5})
    void addValidGradeDoesNotThrow(int grade) {
        String name = TEST_STRINGS.get(0);

        Student student = new Student(name, client);

        assertDoesNotThrow(() -> {
            student.addGrade(grade);
            assertEquals(1, student.getGrades().size());
            assertEquals(grade, student.getGrades().get(0));
        });
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 6})
    void addInvalidGradeThrows(int grade) {
        Student student = new Student("", client);

        assertThrows(IllegalArgumentException.class, () -> student.addGrade(grade));
    }

    @Test
    void hashCodesEqualIfAllPropertiesEqual() {
        String name = TEST_STRINGS.get(0);

        Student student1 = new Student(name, client);
        Student student2 = new Student(name, client);

        VALID_GRADES.forEach(student1::addGrade);
        VALID_GRADES.forEach(student2::addGrade);

        assertEquals(student1.hashCode(), student2.hashCode());
    }

    @Test
    void hashCodesEqualIfClientsNotEqual() {
        String name = TEST_STRINGS.get(0);

        Student student1 = new Student(name, client);
        Student student2 = new Student(name, new StudentClientMock());

        assertEquals(student1.hashCode(), student2.hashCode());
    }

    @Test
    void hashCodesNotEqualIfGradesNotEqual() {
        String name = TEST_STRINGS.get(0);

        Student student1 = new Student(name, client);
        Student student2 = new Student(name, client);

        VALID_GRADES.forEach(student1::addGrade);
        VALID_GRADES.forEach(student2::addGrade);

        student1.addGrade(2);
        assertNotEquals(student1.hashCode(), student2.hashCode());
    }

    @Test
    void equalsReturnsTrueIfNamesEqual() {
        String name = TEST_STRINGS.get(0);

        Student student1 = new Student(name, client);
        Student student2 = new Student(name, client);

        assertEquals(student1, student2);
    }

    @Test
    void equalsReturnsTrueIfGradesEqual() {
        String name = TEST_STRINGS.get(0);

        Student student1 = new Student(name, client);
        Student student2 = new Student(name, client);

        VALID_GRADES.forEach(student1::addGrade);
        VALID_GRADES.forEach(student2::addGrade);

        assertEquals(student1, student2);
    }

    @Test
    void equalsReturnsTrueIfClientsNotEqual() {
        String name = TEST_STRINGS.get(0);

        Student student1 = new Student(name, client);
        Student student2 = new Student(name, new StudentClientMock());

        assertEquals(student1, student2);
    }

    @Test
    void equalsReturnsFalseIfGradesNotEqual() {
        String name = TEST_STRINGS.get(0);

        Student student1 = new Student(name, client);
        Student student2 = new Student(name, client);

        student1.addGrade(2);
        assertNotEquals(student1, student2);
    }
}

