package com.example.ptoject1.mapper;

import com.example.ptoject1.Pojo.Classes;
import com.example.ptoject1.Pojo.Course;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface courseMapper {
    @Select("select * from course")
    public List<Course> findAll();
    @Select("select * from course where cid = #{cid}")
    public Course findById(Integer cid);
    @Select("select * from course where ccredit = #{ccredit}")
    public List<Course> findByCreditList(float ccredit);
    @Select("select * from course where cname = #{cname}")
    public  List<Course> findByName(String cname);
    @Select("Select * from course where cname = #{cname} and ccredit = #{ccredit}")
    public List<Course> findPrecise(@Param("cname") String cname, @Param("ccredit") float ccredit);
    @Insert("insert into course (cname, ccredit, tid, tname) values (#{cname}, #{ccredit}, #{tid}, #{tname})")
    public boolean insertCourse(Course course);
    @Update("Update course set cname = #{cname}, ccredit = #{ccredit}, tid = #{tid}, tname = #{tname} where cid = #{cid}" )
    public boolean updateById(Course course);
    @Update("delete from course where cid = #{cid}")
    public boolean deleteById(Integer cid);
    @Select("Select * from course where tid=#{tid}")
    public List<Course> findMyCourse(Integer tid);
    @Select("SELECT DISTINCT class.id, class.name from (select courseclass.id, courseclass.classid, courseclass.cid from courseclass,(SELECT * FROM course WHERE tid=#{tid}) as a\n" +
            "WHERE a.cid = courseclass.cid) as b, class WHERE b.classid=class.id\n")
    public List<Classes> findClassId(Integer tid);

    @Select("select course.cid, course.cname, course.ccredit, course.tid, course.tname \n" +
            "from course, (select * from courseclass where classid = #{classid}) \n" +
            "as a where a.cid = course.cid;")
    List<Course> findByClassId(Integer classid);
}
