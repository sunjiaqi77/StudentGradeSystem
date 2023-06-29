package com.example.ptoject1.mapper;

import com.example.ptoject1.Pojo.CourseTeacherInfo;
import com.example.ptoject1.Pojo.SCTInfo;
import com.example.ptoject1.Pojo.StudentCourseTeacher;
import org.apache.ibatis.annotations.*;

import java.util.List;


public interface studentCourseTeacherMapper {

    @Select("SELECT c.cid, c.cname, c.tid, t.tname, c.ccredit, sct.grade\n" +
            "FROM student AS s\n" +
            "JOIN courseclass AS cc ON s.classid = cc.classid\n" +
            "JOIN course AS c ON cc.cid = c.cid\n" +
            "JOIN sct ON s.sid = sct.sid AND c.cid = sct.cid\n" +
            "JOIN teacher AS t ON c.tid = t.tid\n" +
            "WHERE s.sid = #{sid};")
    public List<CourseTeacherInfo> findByStudentId(Integer sid);

    @Select("SSELECT * FROM `sct` where sid = #{sid} and cid = #{cid} and tid = #{tid}")
    public List<SCTInfo> findBySearch(Integer sid, String sname, Integer cid, String cname, Integer tid);

    @Select("SELECT * FROM sct WHERE sid = #{sid} AND cid = #{cid}")
    SCTInfo findBySCT(@Param("sid") Integer sid, @Param("cid") Integer cid);

    @Insert("INSERT INTO sct (sid, cid, tid, term) VALUES (#{sid}, #{cid}, #{tid})")
    public boolean insert(StudentCourseTeacher studentCourseTeacher);

    @Update("UPDATE sct SET grade = #{grade} WHERE sid = #{sid} AND tid = #{tid} AND cid = #{cid}")
    public boolean updateById(Integer sid, Integer cid, Integer tid, Integer grade);

    @Delete("DELETE FROM sct WHERE sid = #{sid} AND tid = #{tid} AND cid = #{cid}")
    public boolean deleteBySCT(StudentCourseTeacher sct);
}
