package zyq.graduation.management.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author 周杨清
 * @date 2021/3/16 16:31
 */
public class MakeMD5 {

    public static String  makeMd5(String pwd){
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            byte[] bs = instance.digest(pwd.getBytes());
            String str = "";
            //第一，将所有的数据，转换成正数
            for (byte b : bs) {
                int temp = b & 255;
                //第二，将所有的数据，转换成16进制格式
                if(temp >=0 && temp <=15){
                    str = str +"0"+ Integer.toHexString(temp);
                }else{
                    str = str + Integer.toHexString(temp);;
                }
            }
            return str;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }
}
