package unit.student;

import qa.auto.innotech.api.util.StudentClient;
import qa.auto.innotech.api.step.StudentStep;
import util.StudentClientMock;

public class StudentBaseTest {

    protected final StudentClient client = new StudentClientMock();
    protected final StudentStep studentStep = new StudentStep();
}
