package xhdProject.ms.Dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import xhdProject.ms.domin.MiaoshaOrder;
import xhdProject.ms.domin.OrderInfo;

@Mapper
public interface OrderDao {
    @Select("select * from miaosha_order where user_id=#{userId} and goods_id=#{goodsId}")
    public MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(@Param("userId") long userId, @Param("goodsId") long goodsId);

    @Insert("insert into order_info(user_id, user_name, delivery_address, goods_id, goods_name, goods_count, goods_price, order_channel, status, create_date)values("
            + "#{userId},#{userName}, #{deliveryAddress},  #{goodsId}, #{goodsName}, #{goodsCount}, #{goodsPrice}, #{orderChannel},#{status},#{createDate} )")
    public long insert(OrderInfo orderinfo);

    @Insert("insert into miaosha_order(user_id,goods_id,order_id)values(#{userId},#{goodsId},#{orderId})")
    public int insertMiaoshaOrder(MiaoshaOrder miaoshaOrder);

}
