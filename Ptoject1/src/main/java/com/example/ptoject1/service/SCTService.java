package com.example.ptoject1.service;

import com.example.ptoject1.Pojo.CourseTeacherInfo;
import com.example.ptoject1.Pojo.SCTInfo;
import com.example.ptoject1.Pojo.Student;
import com.example.ptoject1.Pojo.StudentCourseTeacher;
import com.example.ptoject1.mapper.studentCourseTeacherMapper;
import com.example.ptoject1.utlis.Mybatis;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Map;

public class SCTService {

    public List<CourseTeacherInfo> findBySid(Integer sid) {
        try (SqlSession sqlSession = Mybatis.getInstance().openSession()){
            studentCourseTeacherMapper mapper = sqlSession.getMapper(studentCourseTeacherMapper.class);
            return mapper.findByStudentId(sid);
        }catch (RuntimeException e){
            e.printStackTrace();
        }
        return null;
    }


    public SCTInfo find(Integer sid, Integer cid) {
        try (SqlSession sqlSession = Mybatis.getInstance().openSession()){
            studentCourseTeacherMapper mapper = sqlSession.getMapper(studentCourseTeacherMapper.class);
            return mapper.findBySCT(sid, cid);
        }catch (RuntimeException e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean save(StudentCourseTeacher studentCourseTeacher) {
        try (SqlSession sqlSession = Mybatis.getInstance().openSession()){
            studentCourseTeacherMapper mapper = sqlSession.getMapper(studentCourseTeacherMapper.class);
            return mapper.insert(studentCourseTeacher);
        }catch (RuntimeException e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteBySCT(StudentCourseTeacher studentCourseTeacher) {
        try (SqlSession sqlSession = Mybatis.getInstance().openSession()){
            studentCourseTeacherMapper mapper = sqlSession.getMapper(studentCourseTeacherMapper.class);
            return mapper.deleteBySCT(studentCourseTeacher);
        }catch (RuntimeException e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteById(Integer sid, Integer cid, Integer tid, String  term) {
        StudentCourseTeacher studentCourseTeacher = new StudentCourseTeacher();
        studentCourseTeacher.setSid(sid);
        studentCourseTeacher.setCid(cid);
        studentCourseTeacher.setTid(tid);

        try (SqlSession sqlSession = Mybatis.getInstance().openSession()){
            studentCourseTeacherMapper mapper = sqlSession.getMapper(studentCourseTeacherMapper.class);
            return mapper.deleteBySCT(studentCourseTeacher);
        }catch (RuntimeException e){
            e.printStackTrace();
        }
        return false;
    }

//    public SCTInfo findByIdWithTerm(Integer sid, Integer cid, Integer tid) {
//        try (SqlSession sqlSession = Mybatis.getInstance().openSession()){
//            studentCourseTeacherMapper mapper = sqlSession.getMapper(studentCourseTeacherMapper.class);
//            return mapper.findBySearch(
//                    sid, null,
//                    cid, null,
//                    tid, null).get(0);
//        }catch (RuntimeException e){
//            e.printStackTrace();
//        }
//        return null;
//    }

    public boolean updateById(Integer sid, Integer cid, Integer tid, Integer grade) {
        try (SqlSession sqlSession = Mybatis.getInstance().openSession()){
            studentCourseTeacherMapper mapper = sqlSession.getMapper(studentCourseTeacherMapper.class);
            boolean ok = mapper.updateById(sid, cid, tid, grade);
            sqlSession.commit();
            return ok;
        }catch (RuntimeException e){
            e.printStackTrace();
        }
        return false;
    }

    public List<SCTInfo> findBySearch(Map<String, Object> map) {
        Integer sid = null, cid = null, tid = null;
        String sname = null, cname = null;

        if (map.containsKey("cid")) {
            cid = (Integer) map.get("cid");
        }
        if (map.containsKey("sid")) {
            sid = (Integer)  map.get("sid");
        }
        if (map.containsKey("tid")) {
            tid = Integer.parseInt((String) map.get("tid"));
        }
        if (map.containsKey("sname")) {
            sname = (String) map.get("sname");
        }
        if (map.containsKey("cname")) {
            cname = (String) map.get("cname");
        }
        System.out.println(cid + " " + sid + " " + sname + " " + cname);

        System.out.println("SCT 查询：" + map);

        try (SqlSession sqlSession = Mybatis.getInstance().openSession()){
            studentCourseTeacherMapper mapper = sqlSession.getMapper(studentCourseTeacherMapper.class);
            System.out.println(mapper.findBySearch(
                    sid, sname,
                    cid, cname,
                    tid));
            return mapper.findBySearch(
                    sid, sname,
                    cid, cname,
                    tid);
        }catch (RuntimeException e){
            e.printStackTrace();
        }
        return null;
    }

}
