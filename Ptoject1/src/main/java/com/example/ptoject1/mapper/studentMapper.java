package com.example.ptoject1.mapper;

import com.example.ptoject1.Pojo.Classes;
import com.example.ptoject1.Pojo.Student;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface studentMapper {
    @Select("select * from student")
    public List<Student> findAll();
    @Select("select * from student where sid = #{sid}")
    public Student findById(Integer sid);
    @Select("SELECT s.sid, s.sname, c.name\n" +
            "FROM student s\n" +
            "JOIN class c ON s.classid = c.id\n" +
            "WHERE s.sid = #{sid}")
    public Classes findBySid(Integer sid);
    @Select("select * from student where sid = #{sid}")
    public List<Student> findByIdList(Integer sid);
    @Select("select * from student where sname = #{sname}")
    public  List<Student> findByName(String sname);
    @Select("select * from student where sid = #{sid} and sname = #{sname}")
    public List<Student> findBySearch(Student student);
    @Update("update student set sname=#{sname}, password=#{password} where sid=#{sid}" )
    public boolean updateById(Student student);
    @Insert("insert into student (sname, classid, password) values(#{sname}, #{classid}, #{password})")
    public boolean save(Student student);
    @Update("delete from student where sid = #{sid}")
    public boolean deleteById(Integer sid);

    @Select("SELECT * from student where classid = #{classid};")
    public List<Student> findByClassName(Integer classid);
}
