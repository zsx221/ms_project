package xhdProject.ms.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import xhdProject.ms.Service.UserService;
import xhdProject.ms.domin.User;
import xhdProject.ms.redis.RedisService;
import xhdProject.ms.redis.UserKey;
import xhdProject.ms.result.CodeMsg;
import xhdProject.ms.result.Result;

@Controller
@RequestMapping("/demo")
public class DemoController {


    @Autowired
    UserService userService;

    @RequestMapping("/hello")
    @ResponseBody
    public Result<String> hello() {
        return Result.success("hello,imooc");
    }

    @RequestMapping("/helloerror")
    @ResponseBody // 返回数据
    public Result<String> helloerror() {
        return Result.error(CodeMsg.SERVER_ERROR);
    }

    @RequestMapping("/thymeleaf")
    String thymeleaf(Model model) {
        model.addAttribute("name", "xhd");
        return "thymeleaf";
    }

    @RequestMapping("/mybatis")
    @ResponseBody
    public Result<String> home() {
        return Result.success("Hello,world");
    }

    @RequestMapping("/get")
    @ResponseBody
    public Result<User> dbGet() {
        User user = userService.getByID(2);
        return Result.success(user);
    }

    @RequestMapping("/cr")
    @ResponseBody
    public Result<Boolean> dbcr() {
        userService.cr();
        return Result.success(true);
    }

    @RequestMapping("/redis/get")
    @ResponseBody
    public Result<User> redisGet() {
        RedisService redisService = new RedisService();
        User user = redisService.get(UserKey.getById, "" + 1, User.class);
        return Result.success(user);
    }

    @RequestMapping("/redis/Set")
    @ResponseBody
    public Result<Boolean> redisSet() {
        User user = new User(1, "22");
        RedisService redisService = new RedisService();
        redisService.set(UserKey.getById, "" + 1, user);
        return Result.success(true);
    }
}
