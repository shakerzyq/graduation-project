package com.example.demo.util;

import io.swagger.models.auth.In;

/**
 * @author 周杨清
 * @date 2021/2/26 14:55
 */
public class DateAnalyzer {


    public static String dataAnaluzer(long timestamp){
        //时间差
        long timeDifference =  timestamp-System.currentTimeMillis();
        Integer day = Integer.parseInt(timeDifference/86400000+"") ;
        System.out.println("多少天 "+day);
       /* switch (day){
            case 1
        }*/
        return null;
    }

}
