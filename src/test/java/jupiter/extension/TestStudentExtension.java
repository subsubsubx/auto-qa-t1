package jupiter.extension;

import jupiter.annotation.TestStudent;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import qa.auto.innotech.model.Student;
import unit.student.StudentBaseTest;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import static util.TestData.TEST_STRINGS;

public class TestStudentExtension extends StudentBaseTest implements BeforeEachCallback, AfterEachCallback {

    public static final List<Student> students = new ArrayList<>();

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        if (context.getRequiredTestMethod().isAnnotationPresent(TestStudent.class)) {
            Annotation[] declaredAnnotations = context.getRequiredTestMethod().getDeclaredAnnotations();
            for (Annotation annotation : declaredAnnotations) {
                if (annotation.annotationType().equals(TestStudent.class)) {
                    TestStudent studentAnnotation = (TestStudent) annotation;
                    IntStream.range(0, studentAnnotation.quantity()).forEach(it -> {
                        Student student = new Student(new Random().nextInt(Integer.MAX_VALUE),
                                TEST_STRINGS.get(new Random().nextInt(TEST_STRINGS.size())), client);
                        studentStep.doPost(student);
                        students.add(student);
                    });
                }
            }
        }
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        if (!students.isEmpty()) {
            students.forEach(student -> studentStep.doDelete(student.getId()));
            students.clear();
        }
    }
}
