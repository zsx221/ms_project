package xhdProject.ms.redis;

public abstract class BasePrefix implements KeyPrefix {

    private int expirSeconds;
    private String prefix;

    public BasePrefix(String prefix) {
        this(0, prefix);
    }

    public BasePrefix(int expirSeconds, String prefix) {
        this.expirSeconds = expirSeconds;
        this.prefix = prefix;
    }

    public int expirSeconds() {//默认0代表永不过期
        return expirSeconds;
    }

    public String getPrefix() {
        String className = getClass().getSimpleName();
        return className + ":" + prefix;
    }
}
