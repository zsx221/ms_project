package xhdProject.ms.Dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import xhdProject.ms.domin.User;

@Mapper
public interface UserDao {
    @Select("select * from user where id=#{id} ")
    public User getById(@Param("id") int id);

    @Insert(value = {"insert into user (id,name) values(#{id},#{name})"})
    public int Insert(User user);
}
