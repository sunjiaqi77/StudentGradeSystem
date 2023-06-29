package com.example.ptoject1.mapper;

import org.apache.ibatis.annotations.Select;
import com.example.ptoject1.Pojo.Classes;
import java.util.List;

public interface classMapper {

    @Select("SELECT class.id, class.name from class, \n" +
            "(SELECT * from courseclass where cid = #{cid}) \n" +
            "as a where a.classid= class.id;")
    List<Classes> findByCourseName(Integer cid);

    @Select("SELECT * from class where id=#{id}")
    Classes findById(Integer id);

    @Select("select * from class")
    public List<Classes> findCAll();
}
