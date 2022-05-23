package sba.sms.services;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.*;
import sba.sms.models.Course;
import sba.sms.utils.CommandLine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
class CourseServiceTest {

    static CourseService courseService;

    @BeforeAll
    static void beforeAll() {
        courseService = new CourseService();
        CommandLine.addData();
    }

    @Test
    void getAllCourses() {

        List<Course> expected = new ArrayList<>(Arrays.asList(
                new Course("Java", "Phillip Witkin"),
                new Course("Frontend", "Kasper Kain"),
                new Course("JPA", "Jafer Alhaboubi"),
                new Course("Spring Framework", "Phillip Witkin"),
                new Course("SQL", "Phillip Witkin")
        ));
        List<Course> actual = courseService.getAllCourses();
        expected.sort(Comparator.comparing(Course::getId));
        actual.sort(Comparator.comparing(Course::getId));
        Assertions.assertEquals(expected, actual);
        //assertThat(studentService.getAllStudents()).hasSameElementsAs(expected);
    }

    @Test
    void createStudent(){
        Course expected = new Course("SQL on the Fly", "Ella");
        Course actual = new Course("SQL on the Fly", "Ella");
        Assertions.assertEquals(expected, actual);
    }
}