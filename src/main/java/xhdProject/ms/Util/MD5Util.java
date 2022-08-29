package xhdProject.ms.Util;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Util {
    private static final String salt = "1a2b3c4d";//固定salt

    public static String md5(String src) {
        return DigestUtils.md5Hex(src);
    }

    public static String inputPassToFormPass(String inputPass) {
        String str = "" + salt.charAt(0) + salt.charAt(2) + inputPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    public static String inputPassToDbPass(String input, String saltDB) {
        String formPassToForm = inputPassToFormPass(input);
        String formPassToDB = FormPassToDBPass(formPassToForm, saltDB);
        return formPassToDB;
    }

    public static String FormPassToDBPass(String formPass, String salt) {
        String str = "" + salt.charAt(0) + salt.charAt(2) + formPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    public static void main(String[] args) {
        System.out.println(inputPassToDbPass("123456", "1a2b3c4d"));
    }
}
