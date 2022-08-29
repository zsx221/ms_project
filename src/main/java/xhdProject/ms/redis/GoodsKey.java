package xhdProject.ms.redis;

public class GoodsKey extends BasePrefix {

    public static GoodsKey getGoodList = new GoodsKey(60, "gl");

    public GoodsKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
}
