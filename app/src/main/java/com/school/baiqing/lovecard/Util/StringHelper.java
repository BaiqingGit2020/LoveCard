package com.school.baiqing.lovecard.Util;

import android.icu.text.SimpleDateFormat;

import java.text.ParseException;
import java.util.Date;

public class StringHelper {
    // strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
    // HH时mm分ss秒，
    // strTime的时间格式必须要与formatType的时间格式相同
    public static Date stringToDate(String strTime)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        date = formatter.parse(strTime);
        return date;
    }
    // formatType格式为yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
    // data Date类型的时间
    public static String dateToString(Date data) {
        return new SimpleDateFormat("yyyy年MM月dd日").format(data);
    }
}
