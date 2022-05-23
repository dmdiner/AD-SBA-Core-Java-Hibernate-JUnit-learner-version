package sba.sms.services;

import org.hibernate.query.NativeQuery;
import sba.sms.dao.CourseI;
import sba.sms.dao.StudentI;
import sba.sms.models.Course;
import sba.sms.models.Student;
import sba.sms.utils.HibernateUtil;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.extern.java.Log;
import org.hibernate.Session;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.hibernate.query.Query;


import java.util.ArrayList;
import java.util.List;

public class StudentService implements StudentI {


    @Override
    public List<Student> getAllStudents() {
        List<Student> allStudents = new ArrayList<>();
        try(Session s = HibernateUtil.getSessionFactory().openSession()) {
            allStudents = s.createQuery("from Student", Student.class).getResultList();
        } catch (HibernateException e){
            e.printStackTrace();
        }
        return allStudents;
    }

    @Override
    public void createStudent(Student student) {
        Transaction tx = null;
        try(Session s = HibernateUtil.getSessionFactory().openSession()){
            tx = s.beginTransaction();
            s.persist(student);
            tx.commit();
        } catch(HibernateException e){
            if(tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public Student getStudentByEmail(String email) {
        try(Session s = HibernateUtil.getSessionFactory().openSession()){
            Student student = s.get(Student.class, email);
            if(student == null)
                throw new HibernateException("Did not find student");
            else return student;
        } catch (HibernateException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean validateStudent(String email, String password) {
        try(Session s = HibernateUtil.getSessionFactory().openSession()){
            Student student = s.get(Student.class, email);
            if(student == null)
                throw new HibernateException("Student does not exist");
            else return true;
        } catch (HibernateException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void registerStudentToCourse(String email, int courseId) {
        Transaction tx = null;
        try(Session s = HibernateUtil.getSessionFactory().openSession()) {
            tx = s.beginTransaction();
            Student student = s.get(Student.class, email);
            Course course = s.get(Course.class, courseId);
            course.addStudent(student);
            s.merge(course);
            tx.commit();
        } catch(HibernateException e) {
            if(tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<Course> getStudentCourses(String email) {
        Transaction tx = null;
        List<Course> courseList = null;
        try (Session s = HibernateUtil.getSessionFactory().openSession()){
            tx = s.beginTransaction();
            NativeQuery q = s.createNativeQuery("select c.id, c.name, c.instructor from Course as c join student_courses as sc on c.id = sc.course_id join student as s on sc.student_email = s.email where email = :email", Course.class);
            q.setParameter("email", email);
            courseList = q.getResultList();
            tx.commit();
        } catch(HibernateException e){
            if(tx != null) tx.rollback();
            e.printStackTrace();
        }
        return courseList;
    }
}
