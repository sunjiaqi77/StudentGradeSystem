package com.example.ptoject1.service;

import com.example.ptoject1.Pojo.Student;
import com.example.ptoject1.mapper.classMapper;
import com.example.ptoject1.mapper.studentMapper;
import com.example.ptoject1.utlis.Mybatis;
import org.apache.ibatis.session.SqlSession;
import com.example.ptoject1.Pojo.Classes;

import java.util.ArrayList;
import java.util.List;


public class StudentService {
    public List<Student> findByPage(Integer num, Integer size) {
        // num：第几页，size：一页多大
        // num：从零开始
        List<Student> studentList = new ArrayList<>();
        SqlSession sqlSession = null;
        try {
            sqlSession =  Mybatis.getInstance().openSession();   // Mybatis 封装
            studentMapper Mapper = sqlSession.getMapper(studentMapper.class);
            studentList = Mapper.findAll();
            sqlSession.commit();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }

        ArrayList<Student> list = new ArrayList<Student>();

        int start = size * num;
        int end = size * (num + 1);
        int sz = studentList.size();

        for (int i = start; i < end && i < sz; i++) {
            list.add(studentList.get(i));
        }

        return list;
    }

    public List<Student> findBySearch(Integer sid, String sname) {
        Student student = new Student();
        List<Student> list = new ArrayList<>();
        student.setSid(sid);
        student.setSname(sname);
        SqlSession sqlSession = null;
        try {
            sqlSession =  Mybatis.getInstance().openSession();   // Mybatis 封装
            studentMapper Mapper = sqlSession.getMapper(studentMapper.class);
            list = Mapper.findBySearch(student);
            sqlSession.commit();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
        return list;
    }

    public Integer getLength() {
        List<Student> studentList = new ArrayList<>();
        SqlSession sqlSession = null;
        try {
            sqlSession =  Mybatis.getInstance().openSession();   // Mybatis 封装
            studentMapper Mapper = sqlSession.getMapper(studentMapper.class);
            studentList = Mapper.findAll();
            sqlSession.commit();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
        return studentList.size();
    }

    public Student findById(Integer sid) {
        Student student = new Student();
        SqlSession sqlSession = null;
        try {
            sqlSession =  Mybatis.getInstance().openSession();   // Mybatis 封装
            studentMapper Mapper = sqlSession.getMapper(studentMapper.class);
            student = Mapper.findById(sid);
            sqlSession.commit();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
        System.out.println(student);
        return student;
    }

    public List<Student> findByName(String sname){
        List<Student> students = new ArrayList<>();
        SqlSession sqlSession = null;
        try {
            sqlSession =  Mybatis.getInstance().openSession();   // Mybatis 封装
            studentMapper Mapper = sqlSession.getMapper(studentMapper.class);
            students = Mapper.findByName(sname);
            sqlSession.commit();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
        return students;
    }

    public List<Student> findByIdList(Integer sid){
        List<Student> students = new ArrayList<>();
        SqlSession sqlSession = null;
        try {
            sqlSession =  Mybatis.getInstance().openSession();   // Mybatis 封装
            studentMapper Mapper = sqlSession.getMapper(studentMapper.class);
            students = Mapper.findByIdList(sid);
            sqlSession.commit();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
        return students;
    }

    public boolean updateById(Student student) {
        boolean check = false;
        SqlSession sqlSession = null;
        try {
            sqlSession =  Mybatis.getInstance().openSession();
            studentMapper Mapper = sqlSession.getMapper(studentMapper.class);
            check = Mapper.updateById(student);
            sqlSession.commit();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
        return check;
    }

    public boolean save(Student student) {
        boolean check = false;
        SqlSession sqlSession = null;
        try {
            sqlSession =  Mybatis.getInstance().openSession();
            studentMapper Mapper = sqlSession.getMapper(studentMapper.class);
            check = Mapper.save(student);
            sqlSession.commit();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
        return check;
    }

    public boolean deleteById(Integer sid) {
        boolean flag = false;
        Student student = new Student();
        SqlSession sqlSession = null;
        try {
            sqlSession =  Mybatis.getInstance().openSession();   // Mybatis 封装
            studentMapper Mapper = sqlSession.getMapper(studentMapper.class);
            flag = Mapper.deleteById(sid);
            sqlSession.commit();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
        return flag;
    }

    public List<Student> findAll() {
        SqlSession sqlSession = null;
        try {
            sqlSession = Mybatis.getInstance().openSession();   // Mybatis 封装
            studentMapper Mapper = sqlSession.getMapper(studentMapper.class);
            return Mapper.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
        return null;
    }

    public List<Classes> findByCourseName(Integer cid) {
        try (SqlSession sqlSession = Mybatis.getInstance().openSession()){
            classMapper Mapper = sqlSession.getMapper(classMapper.class);
            return Mapper.findByCourseName(cid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Student> findByClassName(Integer classid) {
        try (SqlSession sqlSession = Mybatis.getInstance().openSession()){
            studentMapper Mapper = sqlSession.getMapper(studentMapper.class);
            return Mapper.findByClassName(classid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Classes findByClassId(Integer classid) {
        try (SqlSession sqlSession = Mybatis.getInstance().openSession()){
            classMapper Mapper = sqlSession.getMapper(classMapper.class);
            return Mapper.findById(classid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Classes findBySid(Integer sid) {
        try (SqlSession sqlSession = Mybatis.getInstance().openSession()){
            studentMapper Mapper = sqlSession.getMapper(studentMapper.class);
            return Mapper.findBySid(sid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void export(Integer classid){
        try (SqlSession sqlSession = Mybatis.getInstance().openSession()){
            studentMapper Mapper = sqlSession.getMapper(studentMapper.class);
//            return Mapper.findBySid(sid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public  List<Classes> findCAll() {
        try (SqlSession sqlSession = Mybatis.getInstance().openSession()){
            classMapper Mapper = sqlSession.getMapper(classMapper.class);
            return Mapper.findCAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}