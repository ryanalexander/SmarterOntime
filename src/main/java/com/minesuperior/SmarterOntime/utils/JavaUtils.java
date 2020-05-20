package com.minesuperior.SmarterOntime.utils;

import java.util.Calendar;
import java.util.Date;

public class JavaUtils {
    public static int getDayNumberOld(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK);
    }
}
