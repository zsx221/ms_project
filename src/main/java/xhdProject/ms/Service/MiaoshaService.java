package xhdProject.ms.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xhdProject.ms.domin.Miaosha_user;
import xhdProject.ms.domin.OrderInfo;
import xhdProject.ms.redis.MiaoshaKey;
import xhdProject.ms.redis.RedisService;
import xhdProject.ms.vo.GoodsVo;

@Service
public class MiaoshaService {
    @Autowired
    GoodsService goodsService;

    @Autowired
    RedisService redisService;


    @Autowired
    OrderService orderService;

    @Transactional//事务，形成原子操作，遇到错误就全部回滚
    public OrderInfo miaosha(Miaosha_user user, GoodsVo goodsVo) {
        //减库存 下订单 写入秒杀订单
        boolean success = goodsService.reduceStock(goodsVo);
        //order_info miaosha_order
        if (success) {
            //order_info maiosha_order
            return orderService.createOrder(user, goodsVo);
        } else {
            setGoodsOver(goodsVo.getId());
            return null;
        }


//    	Goods g=new Goods();
//    	g.setId(goodsVo.getId());
//    	g.setGoodsStock(goodsVo.getGoodsStock()-1);
//    	goodsDao.reduceStock(g);
    }

    private void setGoodsOver(Long goodsId) {
        // TODO Auto-generated method stub
        redisService.set(MiaoshaKey.isGoodsOver, "" + goodsId, true);
    }

}
