package xhdProject.ms.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import xhdProject.ms.Service.MiaoshaUserService;
import xhdProject.ms.redis.RedisService;
import xhdProject.ms.result.Result;
import xhdProject.ms.vo.LoginVo;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("/login")
public class LoginController {

    private static Logger log = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    RedisService redisService;
    @Autowired
    private MiaoshaUserService userService;

    @RequestMapping("/to_login")
    public String Login() {
        return "login";
    }

    @RequestMapping("/do_login")
    @ResponseBody
    public Result<String> do_login(HttpServletResponse response, @Valid LoginVo login) {
        log.info(login.toString());//打印
        log.info(response.toString());
//		 //参数校验
//		String passInput= login.getPassword();//得到密码
//		String mobile=login.getMobile();
//		if (StringUtils.isEmpty(passInput)) {
//			return Result.error(CodeMsg.PASSWORD_EMPTY);//检验密码是否为空
//		}
//		if (StringUtils.isEmpty(mobile)) {
//			return Result.error(CodeMsg.MOBILE_EMPTY);//检验账号是否为空
//		}
//	if(!ValidatorUtil.isMobile(mobile) ) {
//		return Result.error(CodeMsg.MOBILE_ERROR);
//	}
        //登录
        String token = userService.login(response, login);//获取token
//	Cookie[] cookies = request.getCookies();//这样便可以获取一个cookie数组
//	for(Cookie cookie : cookies){
//	cookie.getName();//get the cookie name
//	cookie.getValue(); //get the cookie value
//	}
        return Result.success(token);
    }
}
