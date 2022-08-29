package xhdProject.ms.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.IWebContext;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.util.StringUtils;
import xhdProject.ms.Service.GoodsService;
import xhdProject.ms.Service.MiaoshaUserService;
import xhdProject.ms.domin.Miaosha_user;
import xhdProject.ms.redis.GoodsKey;
import xhdProject.ms.redis.RedisService;
import xhdProject.ms.vo.GoodsVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    RedisService redisService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    MiaoshaUserService miaoshaUserService;

    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;

    @RequestMapping(value = "/to_list", produces = "text/html; charset=utf-8")
    @ResponseBody//直接返回源代码
    public String to_list(HttpServletRequest request, HttpServletResponse response, Model model, Miaosha_user user) {
        model.addAttribute("user", user);

        //从缓存里面取文件，取缓存
        String html = redisService.get(GoodsKey.getGoodList, "", String.class);
        if (!StringUtils.isEmpty(html)) {
            return html;
        }
        //缓存里面没有，自己手动渲染,这里用ThymeleafViewResolver来渲染
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        model.addAttribute("goodsList", goodsList);
        IWebContext ctx = new WebContext(request, response,
                request.getServletContext(), request.getLocale(), model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goods_list", ctx);
        if (!StringUtils.isEmpty(html)) {
            redisService.set(GoodsKey.getGoodList, "", html);//加到缓存里面去
        }
        return html;
    }

    @RequestMapping("/to_detail/{goodsId}")
    public String to_detail(Model model, Miaosha_user user, @PathVariable("goodsId") long goodsId) {
        model.addAttribute("user", user);
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        model.addAttribute("goods", goods);
        //
        long startdate = goods.getStartDate().getTime();
        long enddate = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();
        int miaoshaStatus;//显示状态 0为没开始，2为结束，1为进行中
        int remainSeconds;//显示剩余时间

        if (now < startdate) {//秒杀还没开始
            miaoshaStatus = 0;
            remainSeconds = (int) (startdate - now) / 1000;
        } else if (now > enddate) {//秒杀结束了
            miaoshaStatus = 2;
            remainSeconds = -1;
        } else {//秒杀正在进行中
            miaoshaStatus = 1;
            remainSeconds = 0;
        }
        model.addAttribute("miaoshaStatus", miaoshaStatus);
        model.addAttribute("remainSeconds", remainSeconds);
        return "goods_detail";
    }
}
