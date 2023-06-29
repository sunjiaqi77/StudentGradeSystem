package com.example.ptoject1.controller;


import com.alibaba.fastjson2.JSON;
import com.example.ptoject1.Pojo.Classes;
import com.example.ptoject1.Pojo.Course;
import com.example.ptoject1.Pojo.Teacher;
import com.example.ptoject1.service.CourseService;
import com.example.ptoject1.service.TeacherService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/course/*")
public class CourseController extends HttpServlet {

    private CourseService courseService;

    @Override
    public void init() throws ServletException {
        super.init();
        courseService = new CourseService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        switch (pathInfo) {
            case "/findBySearch":
                findBySearch(request, response);
                break;
            case "/save":
                save(request, response);
                break;
            case "/updateCourse":
                updateCourse(request, response);
                break;
            default:
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        switch (pathInfo) {
            case "/findById":
                findById(request, response);
                break;
            case "/deleteById":
                deleteById(request, response);
                break;
            case "/findMyCourse":
                findMyCourse(request, response);
                break;
            case "/findClassId":
                findClassId(request, response);
                break;
            case "/findByClassId":
                findByClassId(request, response);
                break;
            default:
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }

    private void findMyCourse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int tid = Integer.parseInt(request.getParameter("tid"));

        System.out.println("查询教师课程：" + tid);
        List<Course> courses = courseService.findMyCourse(tid);
        System.out.println(courses);
        sendResponse(response, courses);
    }

    private void findClassId(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int tid = Integer.parseInt(request.getParameter("tid"));

        List<Classes> classes = courseService.findClassId(tid);
        System.out.println(classes);
        sendResponse(response, classes);
    }

    private void findBySearch(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Course course = parseRequestBody(request, Course.class);
        List<Course> courses;
        System.out.println(course);

        if(course == null || (course.getCcredit() == 0.0  && course.getCname() == null)){
            courses = courseService.findAll();
        }else if( course.getCcredit() == 0.0 && course.getCname() != null){
            courses = courseService.findByName(course.getCname());
        }else if(  course.getCcredit() != 0.0&& course.getCname() == null) {
            courses = courseService.findByCreditList(course.getCcredit());
        }else{
            courses = courseService.findPrecise(course.getCname(),course.getCcredit());
        }
        sendResponse(response, courses);
    }

    private void findById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int cid = Integer.parseInt(request.getParameter("cid"));
        Course student = courseService.findById(cid);
        sendResponse(response, student);
    }

    private void findByClassId(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int classid = Integer.parseInt(request.getParameter("classid"));
        List<Course> result = courseService.findByClassId(classid);
        sendResponse(response, result);
    }


    private void save(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Course course = parseRequestBody(request, Course.class);
        TeacherService teacherService = new TeacherService();
        Teacher teacher = teacherService.findById(course.getTid());
        boolean result = false;


        if (teacher == null && course.getTid() != 0) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("教师不存在，请输入正确的教师工号。");
            return;
        }else if(course.getTid() == 0){
            course.setTname("未分配授课教师");
            result = courseService.insertCourse(course);
        } else{
            course.setTname(teacher.getTname());
            result = courseService.insertCourse(course);
        }

        sendResponse(response, result);
    }

    private void deleteById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer cid = Integer.parseInt(request.getParameter("cid"));
        boolean result = courseService.deleteById(cid);
        sendResponse(response, result);
    }

    private void updateCourse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Course course = parseRequestBody(request, Course.class);
        boolean result = courseService.updateById(course);
        sendResponse(response, result);
    }

    private <T> T parseRequestBody(HttpServletRequest request, Class<T> clazz) throws IOException {
        String body = request.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);
        return JSON.parseObject(body, clazz);
    }

    private void sendResponse(HttpServletResponse response, Object data) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(JSON.toJSONString(data));
    }
}
