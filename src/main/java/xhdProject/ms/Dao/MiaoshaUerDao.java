package xhdProject.ms.Dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import xhdProject.ms.domin.Miaosha_user;

@Mapper
public interface MiaoshaUerDao {
    @Select("select * from miaosha_user where id=#{id}")
    public Miaosha_user getById(@Param("id") long id);

    @Update("update miaosha_user set password=#{password} where id=#{id}")
    public void update(Miaosha_user toBeUpdate);
}
