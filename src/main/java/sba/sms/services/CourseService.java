package sba.sms.services;


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

public class CourseService implements CourseI {

    @Override
    public void createCourse(Course course) {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().openSession()){
            tx = s.beginTransaction();
            s.persist(course);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public Course getCourseById(int courseId) {
        try(Session s = HibernateUtil.getSessionFactory().openSession()) {
            Course course = s.get(Course.class, courseId);
            if (course == null)
                throw new HibernateException("Did not find course");
            else return course;
        } catch (HibernateException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Course> getAllCourses() {
        ArrayList<Course> allCourses = null;
        try(Session s = HibernateUtil.getSessionFactory().openSession()){
            allCourses = (ArrayList<Course>) s.createNativeQuery("Select * from course", Course.class).getResultList();
        } catch (HibernateException e){
            e.printStackTrace();
        }
        return allCourses;
    }
}
