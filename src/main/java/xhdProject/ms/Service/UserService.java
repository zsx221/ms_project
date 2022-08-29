package xhdProject.ms.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xhdProject.ms.Dao.UserDao;
import xhdProject.ms.domin.User;
import xhdProject.ms.redis.RedisService;

@Service
public class UserService {
    @Autowired
    static RedisService redisService;
    @Autowired//根据类型自动注入，不用创建对象了
    UserDao userDao;

    public User getByID(int id) {
        return userDao.getById(id);
    }

    @Transactional
    public boolean cr() {
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        user1.setId(2);
        user1.setName("ldh");
        userDao.Insert(user1);
        user2.setId(3);
        user2.setName("xhd3");
        userDao.Insert(user2);
        user3.setId(4);
        user3.setName("xhd4");
        userDao.Insert(user3);
        return true;
    }

//	public static Miaosha_user getByToken(String token) {
//		if (StringUtils.isEmpty(token)) {
//			return null;
//		}
//		//延迟token时间
//		return redisService.get(MiaoshaUserKey.token, token, Miaosha_user.class);
//			
//	}
}
