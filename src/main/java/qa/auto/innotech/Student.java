package qa.auto.innotech;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Student {

    private StudentClient client;
    private String name;
    private List<Integer> grades = new ArrayList<>();

    public Student(String name) {
        this.name = name;
    }

    public Student(String name, StudentClient client) {
        this.name = name;
        this.client = client;
    }

    public List<Integer> getGrades() {
        return new ArrayList<>(grades);
    }

    public void addGrade(int grade) {
        if (!client.checkGrade(grade)) {
            throw new IllegalArgumentException(grade + " is wrong grade");
        }
        grades.add(grade);
    }
}