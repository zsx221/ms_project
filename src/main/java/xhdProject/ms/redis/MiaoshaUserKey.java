package xhdProject.ms.redis;

public class MiaoshaUserKey extends BasePrefix {
    public static final int TOKEN_EXPIRE = 3600 * 24 * 2;
    public static MiaoshaUserKey token = new MiaoshaUserKey(TOKEN_EXPIRE, "tk");//给token设置一个过期时间
    public static MiaoshaUserKey getById = new MiaoshaUserKey(0, "id");//对象缓存
    private MiaoshaUserKey(int expireSeconds, String prefix) {//构造器
        super(expireSeconds, prefix);
    }


}
