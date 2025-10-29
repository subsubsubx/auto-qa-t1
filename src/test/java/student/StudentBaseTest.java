package student;

import qa.auto.innotech.util.StudentClient;
import qa.auto.innotech.step.StudentStep;
import util.StudentClientMock;

public class StudentBaseTest {

    protected final StudentClient client = new StudentClientMock();
    protected final StudentStep studentStep = new StudentStep();
}
