package com.example.ptoject1.service;


import com.example.ptoject1.Pojo.Classes;
import com.example.ptoject1.Pojo.Course;
import com.example.ptoject1.Pojo.Student;
import com.example.ptoject1.mapper.courseMapper;
import com.example.ptoject1.mapper.teacherMapper;
import com.example.ptoject1.utlis.Mybatis;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.List;

public class CourseService {


    public Course findById(Integer cid) {
        Course course = new Course();
        SqlSession sqlSession = null;
        try {
            sqlSession = Mybatis.getInstance().openSession();   // Mybatis 封装
            courseMapper Mapper = sqlSession.getMapper(courseMapper.class);
            course = Mapper.findById(cid);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
        System.out.println(course);
        return course;
    }


    public List<Course> findByName(String cname) {
        List<Course> courses = new ArrayList<>();
        SqlSession sqlSession = null;
        try {
            sqlSession = Mybatis.getInstance().openSession();   // Mybatis 封装
            courseMapper Mapper = sqlSession.getMapper(courseMapper.class);
            courses = Mapper.findByName(cname);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
        return courses;
    }

    public List<Course> findByCreditList(float ccredit) {
        List<Course> courses = new ArrayList<>();
        SqlSession sqlSession = null;
        try {
            sqlSession = Mybatis.getInstance().openSession();   // Mybatis 封装
            courseMapper Mapper = sqlSession.getMapper(courseMapper.class);
            courses = Mapper.findByCreditList(ccredit);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
        return courses;
    }

    public List<Course> findPrecise(String cname, float ccredit) {
        List<Course> courses = new ArrayList<>();
        System.out.println(cname + ccredit);
        SqlSession sqlSession = null;
        try {
            sqlSession = Mybatis.getInstance().openSession();   // Mybatis 封装
            courseMapper Mapper = sqlSession.getMapper(courseMapper.class);
            courses = Mapper.findPrecise(cname, ccredit);
//            System.out.println(courses);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
        return courses;
    }

    public boolean updateById(Course course) {
        boolean check = false;
        SqlSession sqlSession = null;
        try {
            sqlSession = Mybatis.getInstance().openSession();
            courseMapper Mapper = sqlSession.getMapper(courseMapper.class);
            System.out.println(course.getTid());
            if(course.getTid() != 0) {
                teacherMapper teacherMapper = sqlSession.getMapper(teacherMapper.class);
                course.setTname(teacherMapper.findById(course.getTid()).getTname());
            }
            check = Mapper.updateById(course);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
        return check;
    }

    public boolean insertCourse(Course course) {
        boolean check = false;
        SqlSession sqlSession = null;
        try {
            sqlSession = Mybatis.getInstance().openSession();
            courseMapper Mapper = sqlSession.getMapper(courseMapper.class);
            check = Mapper.insertCourse(course);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
        return check;
    }

    public boolean deleteById(Integer cid) {
        boolean flag = false;
        Student student = new Student();
        SqlSession sqlSession = null;
        try {
            sqlSession = Mybatis.getInstance().openSession();   // Mybatis 封装
            courseMapper Mapper = sqlSession.getMapper(courseMapper.class);
            flag = Mapper.deleteById(cid);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
        return flag;
    }

    public List<Course> findAll() {
        SqlSession sqlSession = null;
        try {
            sqlSession = Mybatis.getInstance().openSession();   // Mybatis 封装
            courseMapper Mapper = sqlSession.getMapper(courseMapper.class);
            return Mapper.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
        return null;
    }

    public List<Course> findMyCourse(Integer tid) {
        List<Course> course = new ArrayList<>();
        SqlSession sqlSession = null;
        try {
            sqlSession =  Mybatis.getInstance().openSession();   // Mybatis 封装
            courseMapper Mapper = sqlSession.getMapper(courseMapper.class);
            course = Mapper.findMyCourse(tid);
            sqlSession.commit();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
        return course;
    }

    public List<Classes> findClassId(Integer cid) {
        List<Classes> classes = new ArrayList<>();
        SqlSession sqlSession = null;
        try {
            sqlSession =  Mybatis.getInstance().openSession();   // Mybatis 封装
            courseMapper Mapper = sqlSession.getMapper(courseMapper.class);
            classes= Mapper.findClassId(cid);
            sqlSession.commit();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
        return classes;
    }

    public List<Course> findByClassId(Integer classid) {
        List<Course> course = new ArrayList<>();
        SqlSession sqlSession = null;
        try {
            sqlSession =  Mybatis.getInstance().openSession();   // Mybatis 封装
            courseMapper Mapper = sqlSession.getMapper(courseMapper.class);
            course = Mapper.findByClassId(classid);
            sqlSession.commit();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
        return course;
    }
}

