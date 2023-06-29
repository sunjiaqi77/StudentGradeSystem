package com.example.ptoject1.service;


import com.example.ptoject1.Pojo.Teacher;
import com.example.ptoject1.mapper.teacherMapper;
import com.example.ptoject1.utlis.Mybatis;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TeacherService {

    public Teacher findById(Integer tid) {
        Teacher teacher = new Teacher();
        SqlSession sqlSession = null;
        try {
            sqlSession =  Mybatis.getInstance().openSession();   // Mybatis 封装
            teacherMapper Mapper = sqlSession.getMapper(teacherMapper.class);
            teacher = Mapper.findById(tid);
            sqlSession.commit();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
        System.out.println(teacher);
        return teacher;
    }

    public List<Teacher> findByIdList(Integer tid){
        List<Teacher> teachers = new ArrayList<>();
        SqlSession sqlSession = null;
        try {
            sqlSession =  Mybatis.getInstance().openSession();   // Mybatis 封装
            teacherMapper Mapper = sqlSession.getMapper(teacherMapper.class);
            teachers= Mapper.findByIdList(tid);
            sqlSession.commit();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
        return teachers;
    }

    public List<Teacher> findByName(String tname){
        List<Teacher> teachers= new ArrayList<>();
        SqlSession sqlSession = null;
        try {
            sqlSession =  Mybatis.getInstance().openSession();   // Mybatis 封装
            teacherMapper Mapper = sqlSession.getMapper(teacherMapper.class);
            teachers = Mapper.findByName(tname);
            sqlSession.commit();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
        return teachers;
    }
    public boolean updateById(Teacher teacher) {
        boolean check = false;
        SqlSession sqlSession = null;
        try {
            sqlSession =  Mybatis.getInstance().openSession();
            teacherMapper Mapper = sqlSession.getMapper(teacherMapper.class);
            check = Mapper.updateById(teacher);
            sqlSession.commit();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
        return check;
    }

    public boolean save(Teacher teacher) {
        boolean check = false;
        SqlSession sqlSession = null;
        try {
            sqlSession =  Mybatis.getInstance().openSession();
            teacherMapper Mapper = sqlSession.getMapper(teacherMapper.class);
            check = Mapper.save(teacher);
            sqlSession.commit();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
        return check;
    }

    public boolean addTeacher(String tname,String password) {
        boolean check = false;
        SqlSession sqlSession = null;
        Teacher teacher = new Teacher();
        teacher.setTname(tname);
        teacher.setPassword(password);

        try {
            sqlSession =  Mybatis.getInstance().openSession();
            teacherMapper Mapper = sqlSession.getMapper(teacherMapper.class);
            check = Mapper.save(teacher);
            sqlSession.commit();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
        return check;
    }

    public boolean deleteById(Integer tid) {
        boolean flag = false;
        Teacher teacher = new Teacher();
        SqlSession sqlSession = null;
        try {
            sqlSession =  Mybatis.getInstance().openSession();   // Mybatis 封装
            teacherMapper Mapper = sqlSession.getMapper(teacherMapper.class);
            flag = Mapper.deleteById(tid);
            sqlSession.commit();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
        return flag;
    }

    public List<Teacher> findAll() {
        SqlSession sqlSession = null;
        try {
            sqlSession = Mybatis.getInstance().openSession();   // Mybatis 封装
            teacherMapper Mapper = sqlSession.getMapper(teacherMapper.class);
            return Mapper.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
        return null;
    }
}
