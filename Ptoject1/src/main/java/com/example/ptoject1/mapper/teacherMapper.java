package com.example.ptoject1.mapper;

import com.example.ptoject1.Pojo.Teacher;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface teacherMapper {

    @Select("select * from teacher")
    public List<Teacher> findAll();
    @Select("select * from teacher where tid = #{tid}")
    public List<Teacher> findByIdList(Integer tid);
    @Select("select * from teacher where tid = #{tid}")
    public Teacher findById(Integer tid);
    @Select("select * from teacher where tname = #{tname}")
    public  List<Teacher> findByName(String tname);
    @Update("update teacher set tname=#{tname}, password=#{password} where tid=#{tid}" )
    public boolean updateById(Teacher teacher);
    @Insert("insert into teacher (tname, password) values(#{tname},#{password})")
    public boolean save(Teacher teacher);
    @Update("delete from teacher where tid = #{tid}")
    public boolean deleteById(Integer tid);
}
