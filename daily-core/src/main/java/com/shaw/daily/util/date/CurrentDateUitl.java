package com.shaw.daily.util.date;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by shaw on 2017/9/28.
 */

public class CurrentDateUitl {

    public static final Calendar cal = Calendar.getInstance();
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    private static final Date date = new Date();


    //获取当前日期
    public static String getCurrentDate() {
        return dateFormat.format(date);
    }


    //获取以往日期
    public static String getBackDate(int backDays) {
        cal.add(Calendar.DATE, backDays);
        return dateFormat.format(cal.getTime());
    }

    //重置数据
    public static void restart() {
        cal.setTime(date);
    }

}
