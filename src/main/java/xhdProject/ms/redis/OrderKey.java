package xhdProject.ms.redis;

public class OrderKey extends BasePrefix {

    public static OrderKey getMiaoshaOrderByUidGid = new OrderKey("moug");

    public OrderKey(String prefix) {
        super(prefix);
    }
}
