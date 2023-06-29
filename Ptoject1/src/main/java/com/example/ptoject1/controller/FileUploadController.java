package com.example.ptoject1.controller;

import com.example.ptoject1.Pojo.Teacher;
import com.example.ptoject1.mapper.teacherMapper;
import com.example.ptoject1.utlis.Mybatis;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.ibatis.session.SqlSession;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@WebServlet(value = "/api/upload")
public class FileUploadController extends HttpServlet {


    private String uploadDirectory; // 指定文件保存路径
        @Override
        public void init() throws ServletException {
            super.init();

            // 设置文件保存路径

            uploadDirectory = getServletContext().getRealPath("/uploads");
            File directory = new File(uploadDirectory);
            if (!directory.exists()) {
                directory.mkdirs();
            }
        }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (ServletFileUpload.isMultipartContent(request)) {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);

            try {
                List<FileItem> items = upload.parseRequest(request);
                for (FileItem item : items) {
                    if (!item.isFormField()) {
                        // 保存上传的文件到指定目录
                        String fileName = item.getName();
                        File file = new File(uploadDirectory, fileName);
                        item.write(file);
                        System.out.println("File saved: " + file.getAbsolutePath());

                        // 解析Excel文件，提取教师信息
                        List<Teacher> teachers = parseTeachersFromExcel(file);
                        System.out.println(teachers);
                        // 插入教师信息到数据库
                        insertTeachersIntoDatabase(teachers);

                        response.setStatus(HttpServletResponse.SC_OK);
                        return;
                    }
                }
            } catch (FileUploadException e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                e.printStackTrace();
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                e.printStackTrace();
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private List<Teacher> parseTeachersFromExcel(File file) throws Exception {
        List<Teacher> teachers = new ArrayList<>();

        try (InputStream is = new FileInputStream(file)) {
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheetAt(0); // 假设教师信息在第一个工作表中

            for (Row row : sheet) {
                DataFormatter dataFormatter = new DataFormatter();
                String name = dataFormatter.formatCellValue(row.getCell(0));
                String password = dataFormatter.formatCellValue(row.getCell(1));


                Teacher teacher = new Teacher();
                teacher.setTname(name);
                teacher.setPassword(password);

                teachers.add(teacher);
            }

            workbook.close();
        }

        return teachers;
    }

    private void insertTeachersIntoDatabase(List<Teacher> teachers) {
        try (SqlSession session = Mybatis.getInstance().openSession()) {
            teacherMapper teacherMapper = session.getMapper(teacherMapper.class);
            for (Teacher teacher : teachers) {
                teacherMapper.save(teacher);
            }
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
