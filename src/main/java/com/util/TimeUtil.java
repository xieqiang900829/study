package com.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by WD42700 on 2019/5/8.
 */
public class TimeUtil {

    /**
     * 返回当前时间
     * @return
     */
    public static String time(){
        Date date = new Date();
        SimpleDateFormat  sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        return sdf.format(date);
    }

}
