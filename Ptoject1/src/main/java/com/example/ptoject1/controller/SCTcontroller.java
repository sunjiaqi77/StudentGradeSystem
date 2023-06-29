package com.example.ptoject1.controller;

import com.alibaba.fastjson2.JSON;
import com.example.ptoject1.Pojo.CourseTeacherInfo;
import com.example.ptoject1.Pojo.SCTInfo;
import com.example.ptoject1.Pojo.StudentCourseTeacher;
import com.example.ptoject1.service.SCTService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(value = "/SCT/*")
public class SCTcontroller extends HttpServlet {
    private SCTService sctService;

    @Override
    public void init() throws ServletException {
        super.init();
        sctService = new SCTService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        switch (pathInfo) {
            case "/deleteBySCT":
                deleteBySCT(request, response);
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
            case "/findBySid":
                findBySid(request, response);
                break;
            case "/updateById":
                updateById(request, response);
                break;
            case "/deleteById":
                deleteById(request, response);
                break;
            case "/find":
                find(request, response);
                break;
            default:
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }

    private void findBySid(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int sid = Integer.parseInt(request.getParameter("sid"));
        List<CourseTeacherInfo> result = sctService.findBySid(sid);
        sendResponse(response, result);
    }


    private void deleteBySCT(HttpServletRequest request, HttpServletResponse response) throws IOException {
        StudentCourseTeacher studentCourseTeacher = parseRequestBody(request, StudentCourseTeacher.class);
        System.out.println("正在删除 sct 记录：" + studentCourseTeacher);
        boolean result = sctService.deleteBySCT(studentCourseTeacher);
        sendResponse(response, result);
    }

    private void findBySearch(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> map = parseRequestBody(request, Map.class);
        List<SCTInfo> result = sctService.findBySearch(map);
        sendResponse(response, result);
    }

    private void updateById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println(request.getParameter("sid"));
        int sid = Integer.parseInt(request.getParameter("sid"));
        int cid = Integer.parseInt(request.getParameter("cid"));
        int tid = Integer.parseInt(request.getParameter("tid"));
        int grade = Integer.parseInt(request.getParameter("grade"));
        boolean result = sctService.updateById(sid, cid, tid, grade);
        sendResponse(response, result);
    }

    private void deleteById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int sid = Integer.parseInt(request.getParameter("sid"));
        int cid = Integer.parseInt(request.getParameter("cid"));
        int tid = Integer.parseInt(request.getParameter("tid"));
        String term = request.getParameter("term");
        boolean result = sctService.deleteById(sid, cid, tid, term);
        sendResponse(response, result);
    }
    private void find(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int sid = Integer.parseInt(request.getParameter("sid"));
        int cid = Integer.parseInt(request.getParameter("cid"));
        SCTInfo result = sctService.find(sid, cid);
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


