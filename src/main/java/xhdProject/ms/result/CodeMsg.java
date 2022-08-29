package xhdProject.ms.result;

public class CodeMsg {
    //通用异常 500100
    public static CodeMsg success = new CodeMsg(0, "success");
    public static CodeMsg SERVER_ERROR = new CodeMsg(500100, "服务器异常");
    public static CodeMsg BIND_ERROR = new CodeMsg(500101, "参数校验异常:%s");
    //登录异常  5002xx
    public static CodeMsg SESSION_ERROR = new CodeMsg(500210, "Session不存在或者已经失效");
    public static CodeMsg PASSWORD_EMPTY = new CodeMsg(500211, "登录密码不能为空");
    public static CodeMsg MOBILE_EMPTY = new CodeMsg(500212, "登录账号不能为空");
    public static CodeMsg MOBILE_ERROR = new CodeMsg(500213, "登录账号格式不正确，请重新登录");
    public static CodeMsg MOBILE_NOT_EXIST = new CodeMsg(500214, "手机号码不存在");
    public static CodeMsg PASSWORD_ERROR = new CodeMsg(500215, "密码错误");
    //秒杀模块  5005xx
    public static CodeMsg MIAO_SHA_OVER = new CodeMsg(500500, "商品已经秒杀完毕");
    public static CodeMsg REPETE_MIAOSHA = new CodeMsg(500501, "不能重复秒杀");
    private int code;
    private String msg;

    private CodeMsg(int code, String msg) {
        this.code = code;//错误编号
        this.msg = msg;//错误提示
    }

    public CodeMsg fillArgs(Object... args) {
        // TODO Auto-generated method stub
        int code = this.code;
        String message = String.format(msg, args);
        return new CodeMsg(code, message);
    }
    //商品模块  5003xx


    //订单模块  5004xx

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}	
