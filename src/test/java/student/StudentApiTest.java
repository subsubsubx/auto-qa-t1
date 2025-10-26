package student;

import io.restassured.response.Response;
import jupiter.TestStudent;
import jupiter.extension.TestStudentExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import qa.auto.innotech.model.Student;
import qa.auto.innotech.step._BaseStep;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static jupiter.extension.TestStudentExtension.students;
import static util.TestData.TEST_STRINGS;
import static util.TestData.VALID_GRADES;


@ExtendWith(TestStudentExtension.class)
public class StudentApiTest extends StudentBaseTest {

    @Test
    @TestStudent
    @DisplayName("get по id возвращает JSON студента с указанным ID и заполненным именем, если такой есть в базе, код 200")
    void shouldReturn200IfStudentExists() {
        int studentId = students.get(0).getId();
        studentStep.doGet(studentId)
                .then()
                .statusCode(200)
                .body("id", equalTo(studentId))
                .body("name", notNullValue());
    }

    @Test
    @TestStudent
    @DisplayName("get по id возвращает код 404, если студента с данным ID в базе нет - в бд есть записи")
    void shouldReturn400IfStudentNotExists() {
        studentStep.doGet(getRandomId())
                .then()
                .statusCode(404)
                .body(emptyString());
    }

    @Test
    @DisplayName("get по id возвращает код 404, если студента с данным ID в базе нет - пустая бд")
    void shouldReturn400IfStudentNotExistsEmptyDB() {
        studentStep.doGet(getRandomId())
                .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("post добавляет студента в базу, если студента с таким ID ранее не было, при этом имя заполнено, код 201.")
    void shouldAddUserAndReturn201IfNoIdExists() {
        Student student = new Student(getRandomId(), TEST_STRINGS.get(0), client);
        studentStep.doPost(student)
                .then()
                .statusCode(201);
        students.add(student);
    }

    @Test
    @DisplayName("post возвращает код 400, если имя не заполнено")
    void shouldReturn400IfNameNull() {
        Student student = new Student(getRandomId(), null, client);
        students.add(student);
        studentStep.doPost(student)
                .then()
                .statusCode(400);
    }

    @Test
    @DisplayName("post добавляет студента в базе, если ID null, то возвращается назначенный ID, код 201")
    void shouldAddUserAndReturn201IfIdNull() {
        Student student = new Student("Test null id", client);
        Response response = studentStep.doPost(student);
        response.then()
                .statusCode(201)
                .body("", notNullValue())
                .body("", instanceOf(Integer.class));

        int actualId = studentStep.doGet(Integer.parseInt(response.body().print()))
                .jsonPath()
                .getInt("id");

        student.setId(actualId);
        students.add(student);
        assertEquals(Integer.parseInt(response.body().print()), actualId, "вернулся другой ид");
    }

    @Test
    @TestStudent
    @DisplayName("post обновляет студента в базе, если студент с таким ID ранее был, при этом имя заполнено, код 201.")
    void shouldUpdateUserAndReturn201IfIdExists() {
        Integer id = students.get(0).getId();
        Student expectedStudent = new Student(id, "Test name", client);
        studentStep.doPost(expectedStudent)
                .then()
                .statusCode(201);

        Student actualStudent = studentStep.doGet(id).as(Student.class);

        assertEquals(expectedStudent, actualStudent);
    }

    @Test
    @TestStudent
    @DisplayName("delete удаляет студента с указанным ID из базы, код 200")
    void shouldReturn200AndDeleteStudent() {
        Integer randomStudentId = students.get(0).getId();
        studentStep.doDelete(randomStudentId)
                .then()
                .statusCode(200);

        assertNotEquals(201, studentStep.doGet(randomStudentId).statusCode());
    }

    @Test
    @DisplayName("delete возвращает код 404, если студента с таким ID в базе нет")
    void shouldReturn404IfNoStudentFoundById() {
        studentStep.doDelete(getRandomId())
                .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("get /topStudent код 200 и пустое тело, если студентов в базе нет")
    void shouldReturn200AndEmptyResponseIfNoStudentsFound() {
        _BaseStep step = new _BaseStep("/topStudent");
        step.doGet()
                .then()
                .statusCode(200)
                .body(emptyString());
    }

    @Test
    @TestStudent(quantity = 3)
    @DisplayName("get /topStudent код 200 и пустое тело, если ни у кого из студентов в базе нет оценок")
    void shouldReturn200AndEmptyResponseIfNoStudentGradesFound() {
        _BaseStep step = new _BaseStep("/topStudent");
        step.doGet()
                .then()
                .statusCode(200)
                .body(emptyString());
    }

    @Test
    @TestStudent(quantity = 5)
    @DisplayName
            ("get /topStudent код 200 и один студент, если у него максимальная средняя оценка, либо же среди всех " +
                    "студентов с максимальной средней у него их больше всего")
    void shouldReturn200AndStudentWithHighestAvgGrade() {
        _BaseStep step = new _BaseStep("/topStudent");

        Student student1 = students.get(0);
        VALID_GRADES.forEach(student1::addGrade);

        Student student2 = students.get(students.size() - 1);
        VALID_GRADES.forEach(student2::addGrade);
        student2.addGrade(3);
        student2.addGrade(4);

        Student student3 = students.get(students.size() / 2);
        student3.addGrade(4);
        student3.addGrade(2);

        Stream.of(student1, student2, student3).forEach(studentStep::doPost);

        Response response = step.doGet();
        response.then().statusCode(200);
        assertEquals(student2.getId(), response.as(Student[].class)[0].getId());
    }

    @Test
    @TestStudent(quantity = 5)
    @DisplayName
            ("get /topStudent код 200 и несколько студентов, если у них всех эта оценка максимальная и при этом они " +
                    "равны по количеству оценок")
    void shouldReturn200AndStudentsWithHighestGradeHavingEqualGradeCount() {
        _BaseStep step = new _BaseStep("/topStudent");

        Student student1 = students.get(0);
        Student student2 = students.get(students.size() - 1);
        Stream.of(student1, student2).forEach(student -> {
            IntStream.range(0, 3).forEach(it -> student.addGrade(5));
        });

        Student student3 = students.get(students.size() / 2);
        IntStream.range(0, 3).forEach(it -> student3.addGrade(4));

        Stream.of(student1, student2, student3).forEach(studentStep::doPost);

        Response response = step.doGet();
        response.then().statusCode(200);

        List<Student> responseList = response.jsonPath()
                .getList("", Student.class);

        assertEquals(2, responseList.size());
        assertTrue(responseList.stream()
                .mapToInt(Student::getId)
                .noneMatch(id -> student3.getId() == id)
        );
    }

    private int getRandomId() {
        if (students.isEmpty()) {
            return new Random().nextInt(Integer.MAX_VALUE);
        }
        List<Integer> ids = students.stream()
                .map(Student::getId)
                .toList();
        int randomId = new Random().nextInt(Integer.MAX_VALUE);
        while (ids.contains(randomId)) {
            randomId++;
        }
        return randomId;
    }
}