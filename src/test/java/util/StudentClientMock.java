package util;

import qa.auto.innotech.util.StudentClient;

public class StudentClientMock implements StudentClient {

    @Override
    public boolean checkGrade(int grade) {
        return (grade >= 2 && grade <= 5);
    }

}
