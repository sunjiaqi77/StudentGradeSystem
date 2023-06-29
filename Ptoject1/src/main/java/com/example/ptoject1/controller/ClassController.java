package com.example.ptoject1.controller;

import com.alibaba.fastjson2.JSON;
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

@WebServlet(value = "/class/*")
public class ClassController extends HttpServlet {
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
            case "/findAll":
                findCAll(request, response);
                break;
        }
    }

    private void findCAll(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("1");
        Classes classes = parseRequestBody(request, Classes.class);
        List<Classes> cls = new ArrayList<>();
        if(classes == null) {
            cls = studentService.findCAll();
        }
        sendResponse(response,cls);
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
