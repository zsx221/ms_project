package xhdProject.ms.redis;

public interface KeyPrefix {
    public int expirSeconds();

    public String getPrefix();
}
