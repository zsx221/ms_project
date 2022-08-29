package xhdProject.ms.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xhdProject.ms.Service.GoodsService;
import xhdProject.ms.Service.MiaoshaService;
import xhdProject.ms.Service.MiaoshaUserService;
import xhdProject.ms.Service.OrderService;
import xhdProject.ms.domin.MiaoshaOrder;
import xhdProject.ms.domin.Miaosha_user;
import xhdProject.ms.domin.OrderInfo;
import xhdProject.ms.redis.RedisService;
import xhdProject.ms.result.CodeMsg;
import xhdProject.ms.vo.GoodsVo;

import java.util.List;

@Controller
@RequestMapping("/miaosha")
public class MiaoshaController {
    @Autowired
    RedisService redisService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    MiaoshaUserService miaoshaUserService;

    @Autowired
    OrderService orderService;

    @Autowired
    MiaoshaService miaoshaService;

    @RequestMapping("/do_miaosha")
    public String do_miaosha(Model model, Miaosha_user user,
                             @RequestParam("goodsId") long goodsId) {
        model.addAttribute("user", user);
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        if (user == null) {
            return "login";
        }
//		System.out.println("goodName    "+goodsList.get(0).getGoodsName());
        model.addAttribute("goodsList", goodsList);
        //判断库存
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goods.getStockCount();
//		System.out.println(stock);
        if (stock < 0) {
            model.addAttribute("errmsg", CodeMsg.MIAO_SHA_OVER);
//			System.out.println("1错误");
            return "miaosha_fail";
        }
        //判断是否已经秒杀到了
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
//		System.out.println(order.getId());
        if (order != null) {
            model.addAttribute("errmsg", CodeMsg.REPETE_MIAOSHA);
//			System.out.println("2错误");
            return "miaosha_fail";
        }
        //减库存 下订单  ,写入秒杀订单
        OrderInfo orderInfo = miaoshaService.miaosha(user, goods);
        model.addAttribute("orderInfo", orderInfo);
        model.addAttribute("goods", goods);
        return "order_detail";
    }
}	
