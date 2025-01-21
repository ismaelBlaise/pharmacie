package com.s5.pharmacie_backoffice.utils;

import java.sql.Date;
import java.util.Calendar;

public class DateUtil {
    public static Date[] recupererDebutFinMois(Date dateDonnee){
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(dateDonnee);

        calendar.set(Calendar.DAY_OF_MONTH,1);
        Date debut=new Date(calendar.getTimeInMillis());


        calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date fin=new Date(calendar.getTimeInMillis());

        return new Date[]{debut,fin};
    }
}
