package xhdProject.ms.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xhdProject.ms.Dao.OrderDao;
import xhdProject.ms.domin.MiaoshaOrder;
import xhdProject.ms.domin.Miaosha_user;
import xhdProject.ms.domin.OrderInfo;
import xhdProject.ms.vo.GoodsVo;

import java.util.Date;

@Service
public class OrderService {

    @Autowired
    OrderDao orderdao;


    public MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(long userId, long goodsId) {
        // TODO Auto-generated method stub
        return orderdao.getMiaoshaOrderByUserIdGoodsId(userId, goodsId);
    }

    @Transactional
    public OrderInfo createOrder(Miaosha_user user, GoodsVo goodsVo) {
        // TODO Auto-generated method stub
        OrderInfo orderinfo = new OrderInfo();
        orderinfo.setCreateDate(new Date());
        orderinfo.setDeliveryAddrId(0l);
        orderinfo.setGoodsCount(1);
        orderinfo.setGoodsId(goodsVo.getId());
        orderinfo.setGoodsName(goodsVo.getGoodsName());
        orderinfo.setGoodsPrice(goodsVo.getMiaoshaPrice());
        orderinfo.setOrderChannel(1);
        orderinfo.setUserId(user.getId());
        long orderId = orderdao.insert(orderinfo);
        MiaoshaOrder miaoshaOrder = new MiaoshaOrder();
        miaoshaOrder.setGoodsId(goodsVo.getId());
        miaoshaOrder.setOrderId(orderId);
        miaoshaOrder.setUserId(user.getId());
        orderdao.insertMiaoshaOrder(miaoshaOrder);
        return orderinfo;
    }
}
