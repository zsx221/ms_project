package xhdProject.ms.Service;


import com.alibaba.druid.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import xhdProject.ms.Dao.MiaoshaUerDao;
import xhdProject.ms.Util.MD5Util;
import xhdProject.ms.Util.UUIDUtil;
import xhdProject.ms.domin.Miaosha_user;
import xhdProject.ms.exception.GlobalException;
import xhdProject.ms.redis.KeyPrefix;
import xhdProject.ms.redis.MiaoshaUserKey;
import xhdProject.ms.redis.RedisService;
import xhdProject.ms.result.CodeMsg;
import xhdProject.ms.vo.LoginVo;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
public class MiaoshaUserService {
    public static final String COOKI_NAME_TOKEN = "token";

    private static Logger log = LoggerFactory.getLogger(MiaoshaUserService.class);
    @Autowired
    MiaoshaUerDao miaoshaUerDao;

    @Autowired
    RedisService redisService;

    static JedisPool jedisPool;
    public Miaosha_user getById(long id) {// 此处使用对象缓存
        //取缓存
        Miaosha_user user=redisService.get(MiaoshaUserKey.getById,""+id,Miaosha_user.class);
        if (user!=null){
            return  user;
        }
        //缓存没有就从数据库去取
        user=miaoshaUerDao.getById(id);//根据id从数据库取出数据放在Miaosha_user对象里面
        if (user!=null){
            redisService.set(MiaoshaUserKey.getById,""+id,user);//加缓存
        }
        return user;
    }
    public boolean updatePassword(String token,long id,String formPass){//修改密码
        //取user
        Miaosha_user user= getById(id);
        if (user==null){//查出来的为空，说明不存在，就没办法改密码
            throw  new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        //更新数据库
        Miaosha_user toBeUpdate =new Miaosha_user();
        toBeUpdate.setId(id);
        toBeUpdate.setPassword(MD5Util.FormPassToDBPass(formPass,user.getSalt()));
        miaoshaUerDao.update(toBeUpdate);
        // 处理缓存
        redisService.delete(MiaoshaUserKey.getById,""+id);
        user.setPassword(toBeUpdate.getPassword());
        redisService.set(MiaoshaUserKey.token,token,user);//更新缓存里面的密码
        return true;
    }
    public String login(HttpServletResponse response, LoginVo login) {
        if (login == null) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String mobile = login.getMobile();
        String formPass = login.getPassword();//表单输入的账号密码
        //d3b1294a61a07da9b49b6e22b2cbd7f9
        Miaosha_user user = getById(Long.parseLong(mobile));//Miaosha_user中的id对应账号
        if (user == null) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        //验证密码
        //b7797cce01b4b131b433b6acf4add449==123456
        String dbpass = user.getPassword();//数据库中查询的密码
        String saltDB = user.getSalt();
        String calcPass = MD5Util.FormPassToDBPass(formPass, saltDB);
        if (!calcPass.equals(dbpass)) {
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        //生成唯一标识的token，用来标识用户
        String token = UUIDUtil.uuid();
        addCookie(response, token, user);
        return token;
    }

    public Miaosha_user getByToken(HttpServletResponse response, String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        Miaosha_user user = redisService.get(MiaoshaUserKey.token, token, Miaosha_user.class);
        //延长有效期
        if (user != null) {
            addCookie(response, token, user);
        }
        return user;
    }

    private void addCookie(HttpServletResponse response, String token, Miaosha_user user) {
        redisService.set(MiaoshaUserKey.token, token, user);//把token信息写入缓存
        Cookie cookie = new Cookie(COOKI_NAME_TOKEN, token);
        cookie.setMaxAge(MiaoshaUserKey.token.expirSeconds());//和redis缓存失效时间一致
        cookie.setPath("/");
        response.addCookie(cookie);//将session信息存储到cookie里面
    }


}
