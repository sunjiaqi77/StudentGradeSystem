package com.example.ptoject1.controller;

import com.alibaba.fastjson2.JSON;
import com.example.ptoject1.Pojo.Teacher;
import com.example.ptoject1.service.TeacherService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet(value = "/teacher/*")
public class TeacherController extends HttpServlet {
    private TeacherService teacherService;

    @Override
    public void init() throws ServletException {
        super.init();
        teacherService = new TeacherService(); // 根据实际情况初始化TeacherService
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        System.out.println(pathInfo);
        switch (pathInfo) {
            case "/addTeacher":
                addTeacher(request, response);
                break;
            case "/login":
                login(request, response);
                break;
            case "/updateTeacher":
                updateTeacher(request, response);
                break;
            case "/findBySearch":
                findBySearch(request, response);
                break;
            default:
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        System.out.println(pathInfo);
        switch (pathInfo) {
            case "/findById":
                findById(request, response);
                break;
            case "/deleteById":
                deleteById(request, response);
                break;
            default:
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }

    private void addTeacher(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Teacher teacher = parseRequestBody(request, Teacher.class);
        boolean result = teacherService.save(teacher);
        sendResponse(response, result);
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Teacher teacher = parseRequestBody(request, Teacher.class);
        Teacher t = teacherService.findById(teacher.getTid());
        boolean success = (t.getTid() != null && t.getPassword().equals(teacher.getPassword()));
        sendResponse(response, success);
    }

    private void findById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int tid = Integer.parseInt(request.getParameter("tid"));
        System.out.println(tid);
        Teacher teacher = teacherService.findById(tid);
        sendResponse(response, teacher);
    }

    private void findBySearch(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Teacher teacher = parseRequestBody(request, Teacher.class);
        List<Teacher> teachers= new ArrayList<>();
        System.out.println(teacher);
        if(teacher == null || (teacher.getTid() == null  && teacher.getTname() == null)){
            teachers = teacherService.findAll();
        }else if(teacher.getTid() == null && teacher.getTname() != null){
            System.out.println("1");
            teachers = teacherService.findByName(teacher.getTname());
        }else if(teacher.getTname() == null && teacher.getTid() != null){
            System.out.println("2");
            teachers = teacherService.findByIdList(teacher.getTid());
        }else if(teacher.getTid() != null  && teacher.getTname() != null ){
            System.out.println(3);
            teachers = teacherService.findByIdList(teacher.getTid());
        }
        sendResponse(response, teachers);
    }

    private void deleteById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int tid = Integer.parseInt(request.getParameter("tid"));
        boolean result = teacherService.deleteById(tid);
        sendResponse(response, result);
    }

    private void updateTeacher(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Teacher teacher = parseRequestBody(request, Teacher.class);
        boolean result = teacherService.updateById(teacher);
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
