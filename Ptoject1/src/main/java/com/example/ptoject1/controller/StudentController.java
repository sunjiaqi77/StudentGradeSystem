package com.example.ptoject1.controller;

import com.alibaba.fastjson2.JSON;
import com.example.ptoject1.Pojo.Student;
import com.example.ptoject1.service.StudentService;

import com.example.ptoject1.Pojo.Classes;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(value = "/student/*")
public class StudentController extends HttpServlet {
    private StudentService studentService;

    @Override
    public void init() throws ServletException {
        super.init();
        studentService = new StudentService(); // 根据实际情况初始化StudentService
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        switch (pathInfo) {
            case "/addStudent":
                addStudent(request, response);
                break;
            case "/login":
                login(request, response);
                break;
            case "/updateStudent":
                updateStudent(request, response);
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
        switch (pathInfo) {
            case "/findById":
                findById(request, response);
                break;
            case "/findByPage":
                findByPage(request, response);
                break;
            case "/getLength":
                getLength(request, response);
                break;
            case "/deleteById":
                deleteById(request, response);
                break;
            case "/findByCourseName":
                findByCourseName(request, response);
                break;
            case "/findByClassName":
                findByClassName(request, response);
                break;
            case "/findByClassId":
                findByClassId(request, response);
                break;
            case "/findBySid":
                findBySid(request, response);
                break;
            case "/export":
                export(request, response);
                break;
            default:
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }

    private void addStudent(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("1");
        Student student = parseRequestBody(request, Student.class);
        System.out.println(student);
        boolean result = studentService.save(student);
        sendResponse(response, result);
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Student student = parseRequestBody(request, Student.class);
        Student s = studentService.findById(student.getSid());
        boolean success = (s != null && s.getPassword().equals(student.getPassword()));
        sendResponse(response, success);
    }

    private void export(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer classid = Integer.valueOf(request.getParameter("classid"));
        studentService.export(classid);
        sendResponse(response, "true");
    }

    private void findBySearch(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Student student = parseRequestBody(request, Student.class);
        List<Student> students = new ArrayList<>();
        System.out.println(student);
        if(student == null || (student.getSid() == null  &&  student.getSname()==null)){
            students = studentService.findAll();
        }else if(student.getSid() == null && student.getSname() != null){
            System.out.println("1");
            students = studentService.findByName(student.getSname());
        }else if(student.getSname() == null && student.getSid() != null){
            System.out.println("2");
            students = studentService.findByIdList(student.getSid());
        }
        sendResponse(response, students);

    }

    private void findById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int sid = Integer.parseInt(request.getParameter("sid"));
        Student student = studentService.findById(sid);
        sendResponse(response, student);
    }

    private void findByPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int page = Integer.parseInt(request.getParameter("page"));
        int size = Integer.parseInt(request.getParameter("size"));
        List<Student> students = studentService.findByPage(page, size);
        sendResponse(response, students);
    }

    private void getLength(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int length = studentService.getLength();
        sendResponse(response, length);
    }

    private void deleteById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int sid = Integer.parseInt(request.getParameter("sid"));
        boolean result = studentService.deleteById(sid);
        sendResponse(response, result);
    }

    private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Student student = parseRequestBody(request, Student.class);
        boolean result = studentService.updateById(student);
        sendResponse(response, result);
    }

    private void findByCourseName(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer cid = Integer.valueOf(request.getParameter("cid"));

        List<Classes> result = studentService.findByCourseName(cid);
        sendResponse(response, result);
    }

    private void findByClassName(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer classid = Integer.valueOf(request.getParameter("classId"));

        List<Student> result = studentService.findByClassName(classid);
        sendResponse(response, result);
    }

    private void findByClassId(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer classid = Integer.valueOf(request.getParameter("classId"));
        Classes result = studentService.findByClassId(classid);
        sendResponse(response, result);
    }

    private void findBySid(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer sid = Integer.valueOf(request.getParameter("sid"));
        Classes result = studentService.findBySid(sid);
        sendResponse(response, result);
    }


    private <T> T parseRequestBody(HttpServletRequest request, Class<T> clazz) throws IOException {
        String body = request.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);
        return JSON.parseObject(body.trim(), clazz);
    }

    private void sendResponse(HttpServletResponse response, Object data) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(JSON.toJSONString(data));
    }
}
