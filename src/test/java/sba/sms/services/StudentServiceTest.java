package sba.sms.services;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import sba.sms.models.Student;
import sba.sms.utils.CommandLine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
class StudentServiceTest {

    static StudentService studentService;

    @BeforeAll
    static void beforeAll() {
        studentService = new StudentService();
        CommandLine.addData();
    }

    @Test
    void getAllStudents() {

        List<Student> expected = new ArrayList<>(Arrays.asList(
                new Student("reema@gmail.com", "reema brown", "password"),
                new Student("annette@gmail.com", "annette allen", "password"),
                new Student("anthony@gmail.com", "anthony gallegos", "password"),
                new Student("ariadna@gmail.com", "ariadna ramirez", "password"),
                new Student("bolaji@gmail.com", "bolaji saibu", "password")
        ));
        List<Student> actual = studentService.getAllStudents();
        expected.sort(Comparator.comparing(Student::getEmail));
        actual.sort(Comparator.comparing(Student::getEmail));
        Assertions.assertEquals(expected, actual);
        //assertThat(studentService.getAllStudents()).hasSameElementsAs(expected);
    }

    @Test
    void createStudent(){
        Student expected = new Student("jDoe@gmail.com", "Jane Doe", "password");
        Student actual = new Student("jDoe@gmail.com", "Jane Doe", "password");
        Assertions.assertEquals(expected, actual);
    }
}