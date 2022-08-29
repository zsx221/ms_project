package xhdProject.ms.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import xhdProject.ms.domin.Miaosha_user;
import xhdProject.ms.result.Result;

@Controller
@RequestMapping("/user")
public class UserController {
    @RequestMapping("/info")
    @ResponseBody
    public Result<Miaosha_user> info(Model model, Miaosha_user user) {
        return Result.success(user);
    }
}
