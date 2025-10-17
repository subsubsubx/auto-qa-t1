package student;

import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import qa.auto.innotech.model.Student;
import qa.auto.innotech.step.BaseStep;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static util.TestData.TEST_STRINGS;
import static util.TestData.VALID_GRADES;

public class StudentApiTest extends StudentBaseTest {

    private final List<Student> students = new ArrayList<>();

    @BeforeEach
    void addStudents() {
        IntStream.range(0, 5).forEach(it -> {
            Student student = new Student(getRandomId(), TEST_STRINGS.get(new Random().nextInt(TEST_STRINGS.size())), client);
            studentStep.doPost(student);
            students.add(student);
        });
    }

    @AfterEach
    void deleteStudents() {
        students.forEach(student -> studentStep.doDelete(student.getId()));
        students.clear();
    }

    @Test
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
    @DisplayName("get по id возвращает код 404, если студента с данным ID в базе нет")
    void shouldReturn400IfStudentNotExists() {
        studentStep.doGet(getRandomId())
                .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("post добавляет студента в базу, если студента с таким ID ранее не было, при этом имя заполнено, код 201.")
    void shouldAddUserAndReturn201IfNoIdExists() {
        Student student = new Student(getRandomId(), TEST_STRINGS.get(0), client);
        VALID_GRADES.forEach(student::addGrade);
        studentStep.doPost(student)
                .then()
                .statusCode(201);
        students.add(student);
    }

    @Test
    @DisplayName("post обновляет студента в базе, если студент с таким ID ранее был, при этом имя заполнено, код 201.")
    void shouldUpdateUserAndReturn201IfIdExists() {
        Integer id = students.get(0).getId();
        Student expectedStudent = new Student(id, "Test name", client);
        expectedStudent.addGrade(VALID_GRADES.get(0));
        studentStep.doPost(expectedStudent)
                .then()
                .statusCode(201);

        Student actualStudent = studentStep.doGet(id).as(Student.class);

        assertEquals(expectedStudent, actualStudent);
    }

    @Test
    @DisplayName("post добавляет студента в базе, если ID null, то возвращается назначенный ID, код 201")
    void shouldAddUserAndReturn201IfIdNull() {
        Student student = new Student("Test null name", client);
        Response response = studentStep.doPost(student);
        response
                .then()
                .statusCode(201)
                .body("", notNullValue())
                .body("", instanceOf(Integer.class));
        student.setId(Integer.parseInt(response.body().print()));
        students.add(student);
    }

    @Test
    @DisplayName("post возвращает код 400, если имя не заполнено")
    void shouldReturn400IfNameNull() {
        Student student = new Student(getRandomId(), null, client);
        studentStep.doPost(student)
                .then()
                .statusCode(400);
    }

    @Test
    @DisplayName("delete удаляет студента с указанным ID из базы, код 200")
    void shouldReturn200AndDeleteStudent() {
        Integer randomStudentId = students.get(new Random().nextInt(students.size())).getId();
        studentStep.doDelete(randomStudentId)
                .then()
                .statusCode(200);

        assertEquals(404, studentStep.doGet(randomStudentId).statusCode());
    }

    @Test
    @DisplayName("delete возвращает код 404, если студента с таким ID в базе не")
    void shouldReturn404IfNoStudentFoundById() {
        studentStep.doDelete(getRandomId())
                .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("get /topStudent код 200 и пустое тело, если студентов в базе нет")
    void shouldReturn200AndEmptyResponseIfNoStudentsFound() {
        BaseStep step = new BaseStep("/topStudent");
        deleteStudents();
        step.doGet()
                .then()
                .statusCode(200)
                .body(emptyString());
    }

    @Test
    @DisplayName("get /topStudent код 200 и пустое тело, если ни у кого из студентов в базе нет оценок")
    void shouldReturn200AndEmptyResponseIfNoStudentGradesFound() {
        BaseStep step = new BaseStep("/topStudent");
        step.doGet()
                .then()
                .statusCode(200)
                .body(emptyString());
    }

    @Test
    @DisplayName
            ("get /topStudent код 200 и один студент, если у него максимальная средняя оценка, либо же среди всех " +
                    "студентов с максимальной средней у него их больше всего")
    void shouldReturn200AndStudentWithHighestAvgGrade() {
        BaseStep step = new BaseStep("/topStudent");

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
    @DisplayName
            ("get /topStudent код 200 и несколько студентов, если у них всех эта оценка максимальная и при этом они " +
                    "равны по количеству оценок")
    void shouldReturn200AndStudentsWithHighestGradeHavingEqualGradeCount() {
        BaseStep step = new BaseStep("/topStudent");

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
